package edu.tcu.gaduo.ihe.iti.xds_transaction.template;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="QueryGenerator")
@XmlAccessorType (XmlAccessType.FIELD)
public class QueryType {

	@XmlElement(name="QueryUUID")
	protected QueryUUIDType queryUUID;
	@XmlElement(name="ReturnType")
	protected ReturnTypeType returnType;
	@XmlElement(name="Parameter")
	protected List<ParameterType> parameters;

	public QueryType(){
		this.parameters = new ArrayList<ParameterType>();
	}
	

	/**
	 * @return the queryUUID
	 */
	public QueryUUIDType getQueryUUID() {
		return queryUUID;
	}

	/**
	 * @param queryUUID the queryUUID to set
	 */
	public void setQueryUUID(QueryUUIDType queryUUID) {
		this.queryUUID = queryUUID;
	}

	/**
	 * @return the returnType
	 */
	public ReturnTypeType getReturnType() {
		return returnType;
	}

	/**
	 * @param returnType the returnType to set
	 */
	public void setReturnType(ReturnTypeType returnType) {
		this.returnType = returnType;
	}

	/**
	 * @return the parameters
	 */
	public List<ParameterType> getParameters() {
		return parameters;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void addParameters(ParameterType parameter) {
		this.parameters.add(parameter);
	}
	
	
}
