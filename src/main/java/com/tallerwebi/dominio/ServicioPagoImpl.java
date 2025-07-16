package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Pago;
import com.tallerwebi.dominio.entidades.PedidoPlato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("pagoService")
public class ServicioPagoImpl implements ServicioPago {

    private final RepositorioPago pagoRepositorio;

    @Autowired
    public ServicioPagoImpl(RepositorioPago pagoRepositorio) {
        this.pagoRepositorio = pagoRepositorio;
    }

    @Autowired
    private ServicioEmail servicioEmail;


    @Override
    public void guardarPago(Pago pago) {
        pagoRepositorio.guardar(pago);

        // Enviar email de confirmación al cliente
        if (pago.getPedido() != null && pago.getPedido().getUsuario() != null) {
            String destinatario = pago.getPedido().getUsuario().getEmail();
            String asunto = "Confirmación de pago - NutriYa";

            StringBuilder cuerpo = new StringBuilder();
            cuerpo.append("Gracias por tu compra. El pago se ha registrado correctamente para el pedido #")
                    .append(pago.getPedido().getId())
                    .append(" por un total de $")
                    .append(pago.getMonto())
                    .append(".\n\nDetalle de tu pedido:\n");

            if (pago.getPedido().getPedidoPlatos() != null && !pago.getPedido().getPedidoPlatos().isEmpty()) {
                for (PedidoPlato pedidoPlato : pago.getPedido().getPedidoPlatos()) {
                    if (pedidoPlato.getPlato() != null) {
                        cuerpo.append("- ")
                                .append(pedidoPlato.getPlato().getNombre())
                                .append(": $")
                                .append(pedidoPlato.getPlato().getPrecio())
                                .append("\n");
                    }
                }
            } else {
                cuerpo.append("No se encontraron platos asociados al pedido.\n");
            }

            servicioEmail.enviarEmail(destinatario, asunto, cuerpo.toString());
        }
    }



    @Override
    public Pago obtenerPagoPorIdPedido(Integer idPedido) {
        return pagoRepositorio.buscarPorPedidoId(idPedido);
    }

    public void setServicioEmail(ServicioEmail servicioEmail) {
        this.servicioEmail = servicioEmail;
    }
}