/**
 * 
 */
package com.gaduo.zk.model;

/**
 * @author Gaduo
 *
 */
public class CompanyInfomation {
    String name;
    String patitentId;
    String repositoryEndpoint;
    String repositoryUniqueId;
    String registryEndpoint;
    
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPatitentId() {
        return this.patitentId;
    }
    public void setPatitentId(String patitentId) {
        this.patitentId = patitentId;
    }
    public String getRepositoryEndpoint() {
        return this.repositoryEndpoint;
    }
    public void setRepositoryEndpoint(String repositoryEndpoint) {
        this.repositoryEndpoint = repositoryEndpoint;
    }
    public String getRepositoryUniqueId() {
        return this.repositoryUniqueId;
    }
    public void setRepositoryUniqueId(String repositoryUniqueId) {
        this.repositoryUniqueId = repositoryUniqueId;
    }
    public String getRegistryEndpoint() {
        return this.registryEndpoint;
    }
    public void setRegistryEndpoint(String RegistryEndpoint) {
        this.registryEndpoint = RegistryEndpoint;
    }    
    
}
