    package com.tallerwebi.presentacion;

    import com.mercadopago.client.payment.PaymentClient;
    import com.mercadopago.exceptions.MPApiException;
    import com.mercadopago.exceptions.MPException;
    import com.mercadopago.resources.payment.Payment;
    import com.tallerwebi.dominio.*;
    import com.tallerwebi.dominio.entidades.Pago;
    import com.tallerwebi.dominio.entidades.Pedido;
    import com.tallerwebi.dominio.entidades.UsuarioNutriya;
    import com.tallerwebi.infraestructura.PagoRepositorioImpl;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Controller;
    import org.springframework.web.bind.annotation.*;


    @Controller
    public class ControladorPago {

        private PedidoService pedidoService;
        private PagoServicio pagoServicio;
        private ServicioUsuario servicioUsuario;

        @Autowired
        public ControladorPago(PedidoService pedidoService, PagoServicio pagoServicio, ServicioUsuario servicioUsuario) {
            this.servicioUsuario = servicioUsuario;
            this.pedidoService = pedidoService;
            this.pagoServicio = pagoServicio;
        }

        @GetMapping("/pago-exitoso")
        public String pagoExitoso() {
            return "pago-exitoso";
        }

        @GetMapping("/pago-fallido")
        public String pagoFallido () {
            return "pago-fallido";
        }

        @GetMapping("/pago-pendiente")
        public String pagoPendiente() {
            return "pago-pendiente";
        }

        @PostMapping("/webhook-mercadopago")
        public ResponseEntity<Void> recibirNotificacion(@RequestBody WebhookPagoDto webhook) throws MPException, MPApiException {
            System.out.println("📩 Webhook recibido con datos: " + webhook);
            if ("payment".equals(webhook.getType())) {
                Long paymentId = webhook.getData().getId();

                PaymentClient paymentClient = new PaymentClient();
                Payment payment = paymentClient.get(paymentId);

                Pago nuevoPago = new Pago();
                nuevoPago.setIdPagoMercadoPago(payment.getId());
                nuevoPago.setEstado(payment.getStatus());
                nuevoPago.setMetodoPago(payment.getPaymentMethodId());
                nuevoPago.setTipoPago(payment.getPaymentTypeId());
                nuevoPago.setMonto(payment.getTransactionAmount().doubleValue());
                nuevoPago.setMoneda(payment.getCurrencyId());
                nuevoPago.setFechaCreacion(payment.getDateCreated().toLocalDateTime());
                nuevoPago.setFechaAprobacion(payment.getDateApproved() != null ? payment.getDateApproved().toLocalDateTime() : null);
                nuevoPago.setCorreoPagador(payment.getPayer().getEmail());

                String externalReference = payment.getExternalReference();
                if (externalReference != null) {
                    Long idUsuario = Long.parseLong(externalReference);

                    // Confirmamos el pedido
                    pedidoService.confirmarPedido(idUsuario);

                    PedidoDto pedidoDto = pedidoService.buscarPedidoPendientePorUsuario(idUsuario);
                    if (pedidoDto != null) {
                        System.out.println("UASDFADF********");
                        UsuarioNutriya usuario = servicioUsuario.buscarPorId(idUsuario);
                        Pedido pedidoEntidad = pedidoDto.obtenerEntidad(usuario);
                        pedidoEntidad.setId(pedidoDto.getId());
                        nuevoPago.setPedido(pedidoEntidad);
                    }


                }

                pagoServicio.guardarPago(nuevoPago);
                System.out.println("✅ Pago guardado correctamente con ID: " + payment.getId());
            }

            return ResponseEntity.ok().build();
        }
    }

