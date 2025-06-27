package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.PedidoPlato;
import com.tallerwebi.presentacion.PedidoPlatoDto;

public interface ServicioPedidoPlato {

    PedidoPlatoDto buscarPorId(Long id);
    void guardar(PedidoPlatoDto pedidoPlato);
    void finalizarPedido(Long id);
}
