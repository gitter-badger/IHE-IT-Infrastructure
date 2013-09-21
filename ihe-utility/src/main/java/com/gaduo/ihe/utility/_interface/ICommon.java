package com.gaduo.ihe.utility._interface;

import org.apache.axiom.om.OMElement;

public interface ICommon {
	public String createUUID();
	public String createTime();
	public void saveLog(String filename, String postfix, OMElement response);
	
	
}
