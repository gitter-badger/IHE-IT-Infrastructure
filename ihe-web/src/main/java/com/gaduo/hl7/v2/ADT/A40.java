package com.gaduo.hl7.v2.ADT;

import com.gaduo.hl7.pid.PID;

public class A40 extends ADT {
    private PID pid01, pid02;

    public A40(PID pid01, PID pid02, String SendingApplication, String SendingFacility, String ReceivingApplication, String ReceivingFacility) {
        super();
        this.pid01 = pid01;
        this.pid02 = pid02;
        this.setSendingApplication(SendingApplication);
        this.setSendingFacility(SendingFacility);
        this.setReceivingApplication(ReceivingApplication);
        this.setReceivingFacility(ReceivingFacility);
    }

    public String toString() {

        StringBuffer bs = new StringBuffer();

        char Start_Block = '\u000b';
        char End_Block = '\u001c';
        char Carriage_Return = 13;
        String ADT_A40_1 = "MSH|^~\\&|" + SendingApplication + "|" + SendingFacility + "|" + ReceivingApplication + "|"
                + ReceivingFacility + "|" + timestamp + "||ADT^A40^ADT_A39|Gaduo-" + timestamp + "|P|2.3.1";
        String ADT_A40_2 = "EVN||" + timestamp;
        String ADT_A40_3 = "PID" + pid01;
        String ADT_A40_4 = "MRG" + pid02;
        bs.append(Start_Block).append(ADT_A40_1).append(Carriage_Return);
        bs.append(ADT_A40_2).append(Carriage_Return);
        bs.append(ADT_A40_3).append(Carriage_Return);
        bs.append(ADT_A40_4).append(Carriage_Return);
        bs.append(End_Block).append(Carriage_Return);

        return bs.toString();
    }

}
