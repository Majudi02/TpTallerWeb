package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Pago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("pagoService")
public class ServicioPagoImpl implements ServicioPago {

    private final RepositorioPago pagoRepositorio;

    @Autowired
    public ServicioPagoImpl(RepositorioPago pagoRepositorio) {
        this.pagoRepositorio = pagoRepositorio;
    }

    @Override
    public void guardarPago(Pago pago) {
        pagoRepositorio.guardar(pago);
    }

    @Override
    public Pago obtenerPagoPorIdPedido(Integer idPedido) {
        return pagoRepositorio.buscarPorPedidoId(idPedido);
    }
}