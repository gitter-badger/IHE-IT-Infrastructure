package edu.tcu.gaduo.view.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class XMLViewerAction extends ActionSupport {
	private static final long serialVersionUID = -3005120433417787513L;

	private String html;
	
	@Override
    public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = (HttpSession) request.getSession();
		
		html = (String) session.getAttribute("html");
		
		return SUCCESS;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}
}