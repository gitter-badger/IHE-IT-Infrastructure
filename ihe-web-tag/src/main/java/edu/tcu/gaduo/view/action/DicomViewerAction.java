package edu.tcu.gaduo.view.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class DicomViewerAction extends ActionSupport {
	private static final long serialVersionUID = -3005120433417787513L;

	private List<String> imageUrlList;
	
	@SuppressWarnings("unchecked")
	@Override
    public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = (HttpSession) request.getSession();
		
		imageUrlList = (List<String>) session.getAttribute("imageUrlList");
		
		return SUCCESS;
	}

	public List<String> getImageUrlList() {
		return imageUrlList;
	}

	public void setImageUrlList(List<String> imageUrlList) {
		this.imageUrlList = imageUrlList;
	}
}