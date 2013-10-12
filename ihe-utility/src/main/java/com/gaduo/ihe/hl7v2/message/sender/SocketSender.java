package com.gaduo.ihe.hl7v2.message.sender;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.gaduo.ihe.hl7v2.message.sender._interface.ISocketSender;

public class SocketSender implements ISocketSender {

    public static Logger logger = Logger.getLogger(SocketSender.class);
	public String send(String ip, int port, String request) {
        logger.info("\n" + request);
        String response = null ;
        byte[] bytes = null;
        try {
            Socket socket = new Socket(ip, port);
            bytes = request.getBytes();
            OutputStream os = socket.getOutputStream();
            os.write(bytes);
            os.flush();
            bytes = new byte[1000];
            InputStream is = socket.getInputStream();
            is.read(bytes);
            socket.close();
            logger.info("\n" + bytes.length);
            response = new String(bytes);
        } catch (IOException e) {
        	logger.error(e.toString());
            e.printStackTrace();
        }
        logger.info("\n" + response);
		return response;
	}

}
