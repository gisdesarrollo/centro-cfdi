package com.gisconsultoria.centrocfdi.model.dao;

import javax.xml.bind.annotation.*;

import com.gisconsultoria.centrocfdi.model.enums.TipoRelacionEnum;


@XmlType(name = "CfdiRelacionado")
@XmlAccessorType(XmlAccessType.FIELD)
public class ComprobanteCfdiRelacionado {

    @XmlElement(name = "CfdiRelacionado")
    private CfdiRelacionadoDao cfdiRelacionado;

    @XmlAttribute(name = "TipoRelacion")
    private TipoRelacionEnum tipoRelacion;

    public CfdiRelacionadoDao getCfdiRelacionado() {
        return cfdiRelacionado;
    }

    public void setCfdiRelacionado(CfdiRelacionadoDao cfdiRelacionado) {
        this.cfdiRelacionado = cfdiRelacionado;
    }

    public TipoRelacionEnum getTipoRelacion() {
        return tipoRelacion;
    }

    public void setTipoRelacion(TipoRelacionEnum tipoRelacion) {
        this.tipoRelacion = tipoRelacion;
    }
}
