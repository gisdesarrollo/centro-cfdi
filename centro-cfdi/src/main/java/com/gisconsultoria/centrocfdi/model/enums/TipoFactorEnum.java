package com.gisconsultoria.centrocfdi.model.enums;

/**
 * @author Luis Enrique Morales Soriano
 */
public enum TipoFactorEnum {

    TASA(0),
    CUOTA(1),
    EXENTO(2);

    public final int number;

    private TipoFactorEnum(int number){
        this.number = number;
    }

}
