package edu.tcu.gaduo.ihe.hl7v2.message.sender;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.hl7v2.message.sender._interface.ISocketSender;

public class SocketSender implements ISocketSender {
    public static Logger logger = Logger.getLogger(SocketSender.class);
	private static ISocketSender instance = null; 
	private SocketSender() {
		
	}
	public synchronized static ISocketSender getInstance(){
		if(instance == null) {
			instance = new SocketSender();
		}
		return instance;
	}
	
	public String send(String ip, int port, String request) {
        logger.info("\n" + request);
        String response = null ;
        byte[] bytes = null;
        try {
            Socket socket = new Socket(ip, port);
            socket.setSoTimeout(2*10*1000);
            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();
            
            bytes = request.getBytes();
            os.write(bytes);
            os.flush();
            bytes = new byte[500];
            is.read(bytes);
            socket.close();
            response = new String(bytes);
        } catch (IOException e) {
        	logger.error(e.toString());
//            e.printStackTrace();
    		return e.toString();
        }
        logger.info("\n" + response);
		return response;
	}
}
