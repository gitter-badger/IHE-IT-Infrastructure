package edu.tcu.gaduo.ihe;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import edu.tcu.gaduo.ihe.hl7v2.message.sender.SocketSender;
import edu.tcu.gaduo.ihe.hl7v2.message.sender._interface.ISocketSender;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
		char Start_Block = '\u000b';
		char End_Block = '\u001c';
		char Carriage_Return = 13;

		String QBP_Q21_1 = "MSH|^~\\&|foxb1249|Gaduo|NIST_RCVR_FOXB1249|NIST|20101004143744||QBP^Q22^QBP_Q22|NIST-101004143744188|P|2.5";
		String QBP_Q21_2 = "QPD|Q22^Find Candidates^HL7|QRY124818486466|@PID.5.1.1^Wang";
		String QBP_Q21_3 = "RCP|I|1^RD";

		StringBuffer message = new StringBuffer();
		message.append(Start_Block).append(QBP_Q21_1).append(Carriage_Return)
				.append(QBP_Q21_2).append(Carriage_Return).append(QBP_Q21_3)
				.append(Carriage_Return).append(End_Block)
				.append(Carriage_Return);
		
		ISocketSender sender = new SocketSender();
		sender.send("203.64.84.112", 3602, message.toString());
    }
}
