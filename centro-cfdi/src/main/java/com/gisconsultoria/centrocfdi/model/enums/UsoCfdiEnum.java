package com.gisconsultoria.centrocfdi.model.enums;

/**
 * @author Luis Enrique Morales Soriano
 */
public enum UsoCfdiEnum {

    AdquisicionDeMercancias 													(0),
    DevolucionesDescuentosOBonificaciones 										(1),
    GastosEnGeneral 															(2),
    Construcciones																(3),
    MobilarioYEquipoDeOficinaPorInversiones 									(4),
    EquipoDeTransporte 															(5),
    EquipoDeComputoYAccesorios 													(6),
    DadosTroquelesMoldesMatricesYHerramental 									(7),
    ComunicacionesTelefonicas 													(8),
    ComunicacionesSatelitales 													(9),
    OtraMaquinariaYEquipo 														(10),
    HonorariosMedicosDentalesYGastosHospitalarios 								(11),
    GastosMedicosPorIncapacidadODiscapacidad									(12),
    GastosFunerales 															(13),
    Donativos 																	(14),
    InteresesRealesEfectivamentePagadosPorCreditosHipotecariosCasaHabitacion 	(15),
    AportacionesVoluntariasAlSar 												(16),
    PrimasPorSegurosDeGastosMedicos 											(17),
    GastosDeTransportacionEscolarObligatoria 									(18),
    DepositosEnCuentasParaElAhorroPrimasQueTenganComoBasePlanesDePensiones 		(19),
    PagosPorServiciosEducativosColegiaturas 									(20),
    PorDefinir 																	(21);

    public final int number;

    private UsoCfdiEnum(int number){
        this.number = number;
    }

}
