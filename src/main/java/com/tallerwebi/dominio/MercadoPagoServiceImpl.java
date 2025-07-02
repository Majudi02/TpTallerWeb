package com.tallerwebi.dominio;


import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;

import com.tallerwebi.presentacion.PedidoPlatoDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MercadoPagoServiceImpl {

    private final PreferenceClient preferenceClient;

    private static final String BASE_URL ="https://cb26-2800-810-507-abe6-345c-1eb6-ae60-215f.ngrok-free.app";

    public MercadoPagoServiceImpl() {
        this.preferenceClient = new PreferenceClient();
    }


    public Preference crearPreferencia(List<PedidoPlatoDto> platos, Long idUsuario) throws MPException, MPApiException {
        List<PreferenceItemRequest> items = platos.stream()
                .map(pedidoPlato -> PreferenceItemRequest.builder()
                        .title(pedidoPlato.getPlato().getNombre())
                        .quantity(1)
                        .unitPrice(BigDecimal.valueOf(
                                Float.parseFloat(pedidoPlato.getPlato().getPrecio().toString())))
                        .build())
                .collect(Collectors.toList());

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .backUrls(
                        PreferenceBackUrlsRequest.builder()
                                .success(BASE_URL+"/pago-exitoso?idUsuario=" + idUsuario)
                                .failure(BASE_URL+"/pago-fallido")
                                .pending(BASE_URL+"/pago-pendiente")
                                .build()
                )
                .build();

        return preferenceClient.create(preferenceRequest);
    }
}
