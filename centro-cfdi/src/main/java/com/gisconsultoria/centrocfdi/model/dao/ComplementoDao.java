package com.gisconsultoria.centrocfdi.model.dao;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Luis Enrique Morales Soriano
 */
@XmlRootElement(name = "Complemento")
public class ComplementoDao {

    private String complementoProp;

    public String getComplementoProp() {
        return complementoProp;
    }

    public void setComplementoProp(String complementoProp) {
        this.complementoProp = complementoProp;
    }
}
