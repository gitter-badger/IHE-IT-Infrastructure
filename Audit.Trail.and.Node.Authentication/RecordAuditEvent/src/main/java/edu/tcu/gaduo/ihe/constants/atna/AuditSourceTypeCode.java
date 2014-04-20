package edu.tcu.gaduo.ihe.constants.atna;

public enum AuditSourceTypeCode {

	One(1),
	Two(2),
	Three(3);
	
	
	private int value;
	
	AuditSourceTypeCode(int value){
		this.setValue(value);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
