package com.br.ciapoficial.enums;

public enum EstadoEnum {

    AC(0), AL(1), AM(2),
    AP(3), BA(4) , CE(5),
    DF(6), ES(7), GO(8),
    MA(9), MG(10), MS(11),
    MT(12), PA(13), PB(14),
    PE(15), PI(16), PR(17),
    RJ(18), RN(19), RO(20),
    RR(21), RS(22), SC(23),
    SE(24), SP(25), TO(26);

    private int index;

    EstadoEnum(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
