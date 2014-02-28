package edu.tcu.gaduo.view.action.result;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;

import edu.tcu.gaduo.view.action.XMLViewerAction;

public class CustomXMLResult implements Result {
	private static final long serialVersionUID = 456658625542763508L;

	protected static final Log log = LogFactory.getLog(CustomXMLResult.class);

	public void execute(ActionInvocation invocation) throws Exception {
		XMLViewerAction action = (XMLViewerAction) invocation.getAction();
        HttpServletResponse response = ServletActionContext.getResponse();
        OutputStream ouputStream = response.getOutputStream();
        response.setContentType("text/html; charset=utf-8");

        String html = action.getHtml();
        ouputStream.write(html.getBytes("utf-8"));
        ouputStream.close();
	}
}