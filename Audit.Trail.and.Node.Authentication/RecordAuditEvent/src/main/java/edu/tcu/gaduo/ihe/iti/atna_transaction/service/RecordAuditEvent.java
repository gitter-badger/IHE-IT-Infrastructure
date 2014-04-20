package edu.tcu.gaduo.ihe.iti.atna_transaction.service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.axiom.om.OMElement;

import edu.tcu.gaduo.ihe.iti.xds_transaction.core.Transaction;

public class RecordAuditEvent extends Transaction {

	private int facility;
	private int severity; 
	
	public OMElement AuditGenerator(OMElement source){
		
		send(source);
		return source;
	}
	
	@Override
	public OMElement send(OMElement request) {
		int pri = facility * 8 + severity;
		try {
			InetAddress localMachine = InetAddress.getLocalHost();
		    String version = "1";
		    String timestamp = formatDate(new java.util.Date()); //"2014-02-10T00:53:09.624-06:00";
		    String host = localMachine.getHostAddress();
		    String app = "GaduoSyslogSender";
		    String proc = "183";
		    String messageId = "IHE+RFC-3881";
		    String str = "<" + pri + ">" + version + " " + timestamp + " " + host + " " + app + " " + proc + " " + messageId + " - " + request;

			byte[] bytes = str.getBytes("UTF-8");
			DatagramPacket packet = new DatagramPacket(bytes, 0, bytes.length, new InetSocketAddress("203.64.84.247", 2861));
			DatagramSocket datagramSocket = new DatagramSocket();
			datagramSocket.send(packet);
			datagramSocket.close();
		    
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	private String formatDate(Date date) {
        StringBuilder sb = new StringBuilder();
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        c.setTime(date);
        sb.append(c.get(Calendar.YEAR));
        sb.append('-');
        int f = c.get(Calendar.MONTH);
        if (f < 9) {
            sb.append('0');
        }
        sb.append(f + 1);
        sb.append('-');
        f = c.get(Calendar.DATE);
        if (f < 10) {
            sb.append('0');
        }
        sb.append(f);
        sb.append('T');
        f = c.get(Calendar.HOUR_OF_DAY);
        if (f < 10) {
            sb.append('0');
        }
        sb.append(f);
        sb.append(':');
        f = c.get(Calendar.MINUTE);
        if (f < 10) {
            sb.append('0');
        }
        sb.append(f);
        sb.append(':');
        f = c.get(Calendar.SECOND);
        if (f < 10) {
            sb.append('0');
        }
        sb.append(f);
        sb.append('.');
        f = c.get(Calendar.MILLISECOND);
        if (f < 100) {
            sb.append('0');
        }
        if (f < 10) {
            sb.append('0');
        }
        sb.append(f);
        sb.append('Z');
        return sb.toString();
    }

}
