package edu.tcu.gaduo.hl7.v2.QBP;

import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.v25.segment.MSH;
import ca.uhn.hl7v2.model.v25.segment.RCP;
import ca.uhn.hl7v2.model.v25.segments.QPD;

public class QBP {

	protected char Start_Block = '\u000b';
	protected char End_Block = '\u001c';
	protected char Carriage_Return = 13;
    protected MSH msh;
    protected QPD qpd;
    protected RCP rcp;

    /**
	 * @return the rcp
	 */
	public RCP getRcp(RCP rcp) {
		this.rcp = rcp;
 		try {
			rcp.getRcp1_QueryPriority().setValue("I");
		} catch (DataTypeException e) {
			e.printStackTrace();
		}
		return rcp;
	}
    
    
}
