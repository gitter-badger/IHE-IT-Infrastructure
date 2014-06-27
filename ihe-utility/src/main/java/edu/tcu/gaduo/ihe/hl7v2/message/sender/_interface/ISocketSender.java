package edu.tcu.gaduo.ihe.hl7v2.message.sender._interface;

public interface ISocketSender {
	public String send(String ip, int port, String request);
}
