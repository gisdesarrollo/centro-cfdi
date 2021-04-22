package com.gisconsultoria.centrocfdi.model.enums;

/**
 * @author Luis Enrique Morales Soriano
 */
public enum TipoRelacionEnum {

    NotaDeCreditoDeLosDocumentosRelacionados 			(1),
    NotaDeDebitoDeLosDocumentosRelacionados 			(2),
    DevolucionDeMercanciaSobreFacturasOTrasladosPrevios (3),
    SustitucionDeLosCfdiPrevios 						(4),
    TrasladosDeMercanciasFacturadosPreviamente 			(5),
    FacturaGeneradaPorLosTrasladosPrevios 				(6),
    CfdiPorAplicacionDeAnticipo 						(7),
    FacturasGeneradasPorPagosEnParcialidades 			(8),
    FacturaGeneradaPorPagosDiferidos 					(9);

    public final int number;

    private TipoRelacionEnum(int number){
        this.number = number;
    }

}
