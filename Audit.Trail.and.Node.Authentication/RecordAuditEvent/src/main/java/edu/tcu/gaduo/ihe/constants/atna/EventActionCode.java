package edu.tcu.gaduo.ihe.constants.atna;

public enum EventActionCode {
	Create('C'),
	Read('R'),
	Update('U'),
	Delete('D'),
	Execute('E');
	
	private char value;
	
	EventActionCode(char v){
		setValue(v);
	}

	public char getValue() {
		return value;
	}

	public void setValue(char value) {
		this.value = value;
	}
}
