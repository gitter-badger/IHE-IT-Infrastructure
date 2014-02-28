/**
 * 
 */
package edu.tcu.gaduo.zk.model;

import java.util.Iterator;
import java.util.List;

import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;


import edu.tcu.gaduo.ihe.utility.AxiomUtil;
import edu.tcu.gaduo.ihe.utility._interface.IAxiomUtil;
import edu.tcu.gaduo.zk.model.KeyValue.KeyValue;
import edu.tcu.gaduo.zk.view_model.xds_b.iti_18.IParameter;


/**
 * @author Gaduo
 */
public class QueryGenerator implements Cloneable{
	public static Logger logger = Logger.getLogger(QueryGenerator.class);
    private OMElement query;
    private CompanyInfomation companyRegistry;
    private KeyValue queryType;
    private String returnType;
    private String queryUUID;
    private IParameter parameter;
    private IAxiomUtil axiom;
    
    public QueryGenerator() {
        setAxiom(new AxiomUtil());
    }
    public boolean build() {
    	if(companyRegistry == null){
        	logger.warn("companyRegistry is null");
        	return false;
    	}
        this.query = axiom.createOMElement("QueryGenerator", null, null);
        this.query.addChild(axiom.createOMElement("RegistryUrl", companyRegistry.getRegistryEndpoint()));
        this.query.addChild(axiom.createOMElement("QueryUUID", this.getQueryType().getValue()));
        this.query.addChild(axiom.createOMElement("ReturnType", this.getReturnType()));
//        query.setReturnType("LeafClass");
        if(parameter == null){
        	logger.warn("parameter is null");
        	return false;
        }
        List<OMElement> list = parameter.getParameters();
        if(list != null) {
            Iterator<OMElement> iterator = list.iterator();
            while(iterator.hasNext()) {
                OMElement element = iterator.next();
                this.query.addChild(element);
            }
        }else {
        	logger.warn("list is null");
            return false;
        }
        return true;
    }

    public OMElement getQuery() {
        return this.query;
    }

    public void setQuery(OMElement source) {
        this.query = source;
    }

    public String getReturnType() {
        return returnType;
    }
    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }
    public String getQueryUUID() {
        return queryUUID;
    }
    public void setQueryUUID(String queryUUID) {
        this.queryUUID = queryUUID;
    }

    public KeyValue getQueryType() {
        return queryType;
    }

    public void setQueryType(KeyValue queryType) {
        this.queryType = queryType;
    }
    public IParameter getParameter() {
        return parameter;
    }
    public void setParameter(IParameter parameter) {
        this.parameter = parameter;
    }
    public IAxiomUtil getAxiom() {
        return this.axiom;
    }
    public void setAxiom(AxiomUtil axiom) {
        this.axiom = axiom;
    }
    
    public QueryGenerator clone() {
        QueryGenerator q = new QueryGenerator();
        q.queryType = this.queryType;
        q.companyRegistry = this.companyRegistry;
        q.queryUUID = this.queryUUID;
        return q;
    }
    
    public CompanyInfomation getCompanyRegistry() {
        return companyRegistry;
    }
    public void setCompanyRegistry(CompanyInfomation companyRegistry) {
        this.companyRegistry = companyRegistry;
    }
}
