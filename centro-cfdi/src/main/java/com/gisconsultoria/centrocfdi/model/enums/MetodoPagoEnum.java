package com.gisconsultoria.centrocfdi.model.enums;

/**
 * @author Luis Enrique Morales Soriano
 */
public enum MetodoPagoEnum {

    PUE(0),
    PPD(1);

    public final int tipo;

    private MetodoPagoEnum(int tipo){
        this.tipo = tipo;
    }

}
