/**
 * 
 */
package com.gaduo.zk.model.iti_18;

import java.util.List;

import org.apache.axiom.om.OMElement;

/**
 * @author Gaduo
 *
 */
public class GetAssociations extends StoredQuery implements IParameter {
    private String UUID = "";
    public String getUUID() {
        return UUID;
    }

    public void setUUID(String uUID) {
        this.UUID = uUID;
    }

    public List<OMElement> getParameters() {
        super.list.add(super.addParameter("$uuid", UUID));
        return super.list;
    }
}
