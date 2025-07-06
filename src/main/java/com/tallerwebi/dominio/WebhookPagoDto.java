package com.tallerwebi.dominio;

public class WebhookPagoDto {
    private String action;
    private String type;
    private Data data;

    public static class Data {
        private Long id;


        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
    }


    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Data getData() { return data; }
    public void setData(Data data) { this.data = data; }


}
