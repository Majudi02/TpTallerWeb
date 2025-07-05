package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Pago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("pagoService")
public class PagoServiceImpl implements PagoServicio {

    private final PagoRepositorio pagoRepositorio;

    @Autowired
    public PagoServiceImpl(PagoRepositorio pagoRepositorio) {
        this.pagoRepositorio = pagoRepositorio;
    }

    @Override
    public void guardarPago(Pago pago) {
        System.out.println("📝 Intentando guardar pago: " + pago);
        pagoRepositorio.guardar(pago);
        System.out.println("✅ Pago guardado correctamente con ID: ");
    }

    @Override
    public Pago obtenerPagoPorIdMercadoPago(Long idPagoMP) {
        return pagoRepositorio.buscarPorIdPagoMercadoPago(idPagoMP);
    }

    @Override
    public Pago obtenerPagoPorIdPedido(Integer idPedido) {
        return pagoRepositorio.buscarPorPedidoId(idPedido);
    }
}