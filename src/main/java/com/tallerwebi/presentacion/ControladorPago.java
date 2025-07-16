    package com.tallerwebi.presentacion;

    import com.mercadopago.client.payment.PaymentClient;
    import com.mercadopago.exceptions.MPApiException;
    import com.mercadopago.exceptions.MPException;
    import com.mercadopago.resources.payment.Payment;
    import com.tallerwebi.dominio.*;
    import com.tallerwebi.dominio.entidades.Pago;
    import com.tallerwebi.dominio.entidades.Pedido;
    import com.tallerwebi.dominio.entidades.UsuarioNutriya;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.ModelMap;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.servlet.ModelAndView;


    @Controller
    public class ControladorPago {

        private final ServicioPago servicioPago;
        private PedidoService pedidoService;
        private ServicioUsuario servicioUsuario;

        @Autowired
        public ControladorPago(PedidoService pedidoService, ServicioPago servicioPago, ServicioUsuario servicioUsuario) {
            this.servicioUsuario = servicioUsuario;
            this.pedidoService = pedidoService;
            this.servicioPago = servicioPago;
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


                Pago nuevoPago = crearPago(payment);

                String externalReference = payment.getExternalReference();

                if (externalReference != null && "approved".equals(payment.getStatus())) {
                    Long idUsuario = Long.parseLong(externalReference);

                    pedidoService.confirmarPedido(idUsuario);

                    PedidoDto pedidoDto = pedidoService.buscarPedidoPendientePorUsuario(idUsuario);
                    if (pedidoDto != null) {
                        UsuarioNutriya usuario = servicioUsuario.buscarPorId(idUsuario);
                        Pedido pedidoEntidad = pedidoDto.obtenerEntidad(usuario);
                        pedidoEntidad.setId(pedidoDto.getId());
                        nuevoPago.setPedido(pedidoEntidad);
                    }
                }
                servicioPago.guardarPago(nuevoPago);
            }

            return ResponseEntity.ok().build();
        }

        private static Pago crearPago(Payment payment) {
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
            return nuevoPago;
        }

        @PostMapping("/guardar-pago")
        public ModelAndView guardarPago(@ModelAttribute("pago") Pago pago) {
            servicioPago.guardarPago(pago);
            return new ModelAndView("redirect:/pedido/exito");
        }

        @GetMapping("/pedido/exito")
        public ModelAndView pagoExitoso(@RequestParam("idPedido") Integer idPedido) {
            Pago pago = servicioPago.obtenerPagoPorIdPedido(idPedido);
            ModelMap modelo = new ModelMap();
            modelo.put("pago", pago);
            return new ModelAndView("pago-exitoso", modelo);
        }
    }

