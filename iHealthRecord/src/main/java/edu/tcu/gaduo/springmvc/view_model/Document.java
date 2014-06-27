package edu.tcu.gaduo.springmvc.view_model;


public class Document implements Comparable<Document>{

	private String entryUUID;
	private String name;
	private String uniqueId;
	
	/**
	 * @return the entryUUID
	 */
	public String getEntryUUID() {
		return entryUUID;
	}
	/**
	 * @param entryUUID the entryUUID to set
	 */
	public void setEntryUUID(String entryUUID) {
		this.entryUUID = entryUUID;
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
	 * @return the uniqueId
	 */
	public String getUniqueId() {
		return uniqueId;
	}
	/**
	 * @param uniqueId the uniqueId to set
	 */
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	@Override
	public int compareTo(Document doc) {
		if(this.uniqueId.equals(doc.uniqueId)){
			return 0;
		}
		return 1;
	}
	
	
}
