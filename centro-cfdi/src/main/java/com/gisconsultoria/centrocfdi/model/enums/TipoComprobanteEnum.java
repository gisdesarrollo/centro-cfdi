package com.gisconsultoria.centrocfdi.model.enums;

/**
 * @author Luis Enrique Morales Soriano
 */
public enum TipoComprobanteEnum {

    I(0),
    E(1),
    T(2),
    N(3),
    P(4);

    public final int number;

    private TipoComprobanteEnum(int number){
        this.number = number;
    }

}
