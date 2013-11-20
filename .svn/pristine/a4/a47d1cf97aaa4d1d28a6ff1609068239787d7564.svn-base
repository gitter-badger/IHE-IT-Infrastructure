/**
 * 
 */
package com.gaduo.zk.view_model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

import com.gaduo.hl7.pid.PID;
import com.gaduo.hl7.v2.ADT.A01;
import com.gaduo.hl7.v2.ADT.A04;
import com.gaduo.hl7.v2.ADT.A05;
import com.gaduo.hl7.v2.ADT.A08;
import com.gaduo.hl7.v2.ADT.ADT;


/**
 * @author Gaduo
 */
public class ITI_08 {
    public static Logger logger = Logger.getLogger(ITI_08.class);
    private String ip;
    private int port;
    private String messageRequest;
    private String messageResponse;
    private PID pid;
    private String operator;

    @Init
    public void init() throws ParseException {
        this.setIp("129.6.24.143");
        this.setPort(9080);
        this.setOperator("ADT^A01");
        this.pid = new PID();
        this.pid.setPid03("2013031143^^^IHENA&1.3.6.1.4.1.21367.2010.1.2.300&ISO");
        this.pid.setPid05("Wang^Dai-Wei^^^");

        int inputDate = 19661109;
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        Date date = df.parse(String.valueOf(inputDate));
        this.pid.setPid07(date);
        this.pid.setPid08("F");
        this.pid.setPid11("Sec.2, Linong Street^^ Taipei ^112^ Taiwan");
        System.gc();   
    }

    @Command
    @NotifyChange({"messageRequest", "messageResponse"})
    public void submit() {
        ADT adt = null;
        if (operator.equals("ADT^A01")) {
            adt = new A01(this.getPid(), "NIST", "NIST_RCVR_FOXB1249", "MESA_XREF", "XYZ_HOSPITAL");
        }
        if (operator.equals("ADT^A04")) {
            adt = new A04(this.getPid(), "foxb1249", "Gaduo", "MESA_XREF", "XYZ_HOSPITAL");
        }
        if (operator.equals("ADT^A05")) {
            adt = new A05(this.getPid(), "foxb1249", "Gaduo", "MESA_XREF", "XYZ_HOSPITAL");
        }
        if (operator.equals("ADT^A08")) {
            adt = new A08(this.getPid(), "foxb1249", "Gaduo", "MESA_XREF", "XYZ_HOSPITAL");
        }
        if (operator.equals("ADT^A40")) {
            adt = new A01(this.getPid(), "foxb1249", "Gaduo", "MESA_XREF", "XYZ_HOSPITAL");
        }
        if (adt != null) {
            this.setMessageRequest(adt.toString());
            this.send(this.getMessageRequest());
        }
    }

    private void send(String message) {
        logger.trace(this.operator);
        logger.trace(message);
        try {
            Socket socket = new Socket(ip, port);
            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();
            byte[] bytes = message.getBytes();
            os.write(bytes);
            bytes = new byte[250];
            is.read(bytes);
            this.setMessageResponse(new String(bytes));
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Command
    public void checked(@BindingParam("checked") boolean checked) {

    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getMessageRequest() {
        return messageRequest;
    }

    public void setMessageRequest(String message) {
        this.messageRequest = message;
    }

    public String getMessageResponse() {
        return messageResponse;
    }

    public void setMessageResponse(String message) {
        this.messageResponse = message;
    }

    public PID getPid() {
        return pid;
    }

    public void setPid(PID pid) {
        this.pid = pid;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
