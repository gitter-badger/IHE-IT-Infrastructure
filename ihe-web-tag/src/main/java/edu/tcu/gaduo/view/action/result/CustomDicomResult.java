package edu.tcu.gaduo.view.action.result;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;

import edu.tcu.gaduo.view.action.DicomViewerAction;

public class CustomDicomResult implements Result {
	private static final long serialVersionUID = 456658625542763508L;

	protected static final Log log = LogFactory.getLog(CustomDicomResult.class);

	public void execute(ActionInvocation invocation) throws Exception {
		DicomViewerAction action = (DicomViewerAction) invocation.getAction();
        HttpServletResponse response = ServletActionContext.getResponse();
        OutputStream ouputStream = response.getOutputStream();
        response.setContentType("text/html");

        List<String> imageList = action.getImageUrlList();
        System.out.println("imageList : " + imageList);
        
        StringBuilder html = new StringBuilder("<html><head></head><body><table>");
        for (String image : imageList) {
        	html.append("<tr><td>");
        	html.append("<img src=\"");
        	html.append(image);
        	html.append("\"/></td></tr>");
        	html.append("\r\n");
        }
        html.append("</table></body></html>");
        
        ouputStream.write(html.toString().getBytes());
        ouputStream.close();
	}
}