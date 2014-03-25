/**
 * 
 */
package edu.tcu.gaduo.ihe.utility.ws.nonblock._interface;

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
