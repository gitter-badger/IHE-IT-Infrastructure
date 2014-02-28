/**
 * 
 */
package edu.tcu.gaduo.ihe.utility.webservice.nonblock;

import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.context.MessageContext;
/**
 * @author Gaduo
 *
 */
public interface IResponse {
    public void parser(MessageContext msgContext);
    public void parser(SOAPEnvelope envelope);
    public boolean clean();
}
