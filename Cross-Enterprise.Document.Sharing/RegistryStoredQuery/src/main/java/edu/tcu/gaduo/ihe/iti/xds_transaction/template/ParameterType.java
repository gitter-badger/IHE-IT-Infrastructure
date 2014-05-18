package edu.tcu.gaduo.ihe.iti.xds_transaction.template;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="Parameter")
@XmlAccessorType (XmlAccessType.FIELD)
public class ParameterType {

	@XmlAttribute
	protected String name;
	
	@XmlElement(name="Value")
	protected List<ValueType> values;

	public ParameterType(){
		values = new ArrayList<ValueType>();
	}
	
	public ParameterType(String name) {
		this();
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the values
	 */
	public List<ValueType> getValues() {
		return values;
	}

	/**
	 * @param values the values to set
	 */
	public void addValues(ValueType value) {
		this.values.add(value);
	}
}
 