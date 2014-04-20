package edu.tcu.gaduo.ihe.constants.atna;

public enum EventOutcomeIndicator {
	Success(0),
	MinorFailure(4),
	SeriousFaailure(8),
	MajorFailure(12);
	private int value;
	
	EventOutcomeIndicator(int v){
		value = v ;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}
