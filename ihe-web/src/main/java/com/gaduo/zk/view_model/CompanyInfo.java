/**
 * 
 */
package com.gaduo.zk.view_model;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.zkoss.bind.annotation.Init;


import com.gaduo.ihe.utility.xml.XMLPath;
import com.gaduo.zk.model.CompanyInfomation;

public class CompanyInfo {
    public static Logger logger = Logger.getLogger(CompanyInfo.class);
    private XMLPath configurations;
    private List<CompanyInfomation> companyInfomations;
    @Init
    public void init(){
		setConfigurations(new XMLPath(getClass().getClassLoader().getResourceAsStream("configuration.xml")));
        setCompanyInfomations(new ArrayList<CompanyInfomation>());
        String expression = "Configuration/Connection";
        NodeList nodeList = configurations.QueryNodeList(expression);
        
        for(int i = 0; i < nodeList.getLength(); i++) {
            CompanyInfomation companyInfo = new CompanyInfomation();
            Node node = nodeList.item(i);
            String name = node.getAttributes().getNamedItem("name").getNodeValue();
            //logger.debug(name);
            companyInfo.setName(name);
            NodeList childNodes = node.getChildNodes();
            for(int j = 0; j < childNodes.getLength(); j++) {
                Node child = childNodes.item(j);
                if(child.getNodeName().equals("PatitentId")) {
                    //logger.debug("PatitentId：" + child.getTextContent());
                    companyInfo.setPatitentId(child.getTextContent());
                }
                if(child.getNodeName().equals("Repository")) {
                    NodeList repository = child.getChildNodes();
                    for(int k = 0; k < repository.getLength(); k++) {
                        Node rep = repository.item(k);
                        if(rep.getNodeName().equals("Endpoint")) {
                            //logger.debug("Endpoint：" + rep.getTextContent());
                            companyInfo.setRepositoryEndpoint(rep.getTextContent());
                        }
                        if(rep.getNodeName().equals("UniqueId")) {
                            //logger.debug("UniqueId：" + rep.getTextContent());
                            companyInfo.setRepositoryUniqueId(rep.getTextContent());
                        }                        
                    }
                }
                if(child.getNodeName().equals("Registry")) {
                    NodeList rigistry = child.getChildNodes();
                    for(int k = 0; k < rigistry.getLength(); k++) {
                        Node rep = rigistry.item(k);
                        if(rep.getNodeName().equals("Endpoint")) {
                            //logger.debug("Endpoint：" + rep.getTextContent());
                            companyInfo.setRegistryEndpoint(rep.getTextContent());
                        }
                    }                        
                }
            }     
            companyInfomations.add(companyInfo);
        }
        System.gc();        
    }
    
    public XMLPath getConfigurations() {
        return this.configurations;
    }
    public void setConfigurations(XMLPath configurations) {
        this.configurations = configurations;
    }

    public List<CompanyInfomation> getCompanyInfomations() {
        return companyInfomations;
    }

    public void setCompanyInfomations(List<CompanyInfomation> companyInfomations) {
        this.companyInfomations = companyInfomations;
    }
    
}
