package com.gaduo.webservice.parser;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;

import com.gaduo.webservice.LeafClass.ExtrinsicObject;
import com.gaduo.webservice.LeafClass.RegistryPackage;
import com.gaduo.webservice.LeafClass._interface.IEBXMLParser;
import com.gaduo.zk.model.KeyValue.KeyValuesImpl;


public class MetadataParser {
    public static Logger logger = Logger.getLogger(MetadataParser.class);
    private Map<String, KeyValuesImpl> mapArray = new TreeMap<String, KeyValuesImpl>();
    private final String EXTRINSICOBJECT = "urn:uuid:7edca82f-054d-47f2-a032-9b2a5b5186c1";
    private final String REGISTRYPACKAGE = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:RegistryPackage";
    private final String ASSOCIACTION = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Association";

    @SuppressWarnings({"unchecked"})
    public MetadataParser(OMElement element) {
        OMElement firstLayer = element.getFirstElement();
        OMElement secondLayer = firstLayer.getFirstElement();
        Iterator<OMElement> list = null;
        if (firstLayer != null && firstLayer.getLocalName().equals("RegistryObjectList")) {
            logger.trace(firstLayer.getLocalName());
            list = firstLayer.getChildElements();
        }
        if (secondLayer != null && secondLayer.getLocalName().equals("RegistryObjectList")) {
            logger.trace(secondLayer.getLocalName());
            list = secondLayer.getChildElements();
        }
        while (list != null && list.hasNext()) {
            OMElement e = list.next();
            String objectType = e.getAttributeValue(new QName("objectType"));
            if (objectType != null) {
                String id = e.getAttributeValue(new QName("id"));
                if (objectType.equals(EXTRINSICOBJECT)) {
                    KeyValuesImpl map = new KeyValuesImpl();
                    mapArray.put(id, map);
                    IEBXMLParser eo = new ExtrinsicObject();
                    eo.execute(e, map);
                }
                else if (objectType.equals(REGISTRYPACKAGE)) {
                    KeyValuesImpl map = new KeyValuesImpl();
                    mapArray.put(id, map);
                    IEBXMLParser rp = new RegistryPackage();
                    rp.execute(e, map);
                }
                else if (objectType.equals(ASSOCIACTION)) {
                    KeyValuesImpl map = new KeyValuesImpl();
                    mapArray.put(id, map);
                    IEBXMLParser asso = new com.gaduo.webservice.LeafClass.Association();
                    asso.execute(e, map);
                }
            }

        }
    }


    public Map<String, KeyValuesImpl> getMapArray() {
        return mapArray;
    }

    public void setMapArray(Map<String, KeyValuesImpl> array) {
        this.mapArray = array;
    }

}
