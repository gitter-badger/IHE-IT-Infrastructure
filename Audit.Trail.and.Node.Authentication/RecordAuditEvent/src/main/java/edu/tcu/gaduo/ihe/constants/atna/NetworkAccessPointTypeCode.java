package edu.tcu.gaduo.ihe.constants.atna;

public enum NetworkAccessPointTypeCode {

	MachineName(1),
	IPAddress(2),
	TelephoneNumber(3);
	
	private int value;
	
	NetworkAccessPointTypeCode(int value){
		this.setValue(value);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
