package com.oyataco.converToBatch.model;

public class DataMonth {
    private String anio;
    private String mes;
    private String totalDias;
    private String primerDia;

    //constructor
    public DataMonth(){
    }

    public DataMonth(String anio, String mes, String totalDias, String primerDia) {
        this.anio = anio;
        this.mes = mes;
        this.totalDias = totalDias;
        this.primerDia = primerDia;
    }


    //Getter&Setter

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getTotalDias() {
        return totalDias;
    }

    public void setTotalDias(String totalDias) {
        this.totalDias = totalDias;
    }

    public String getPrimerDia() {
        return primerDia;
    }

    public void setPrimerDia(String primerDia) {
        this.primerDia = primerDia;
    }


    @Override
    public String toString() {
        return "DataMonth [year= "+anio+", month= "+mes+", total_days= "+totalDias+", first_day= "+ primerDia +
                "]";
    }
}
