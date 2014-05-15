package edu.tcu.gaduo.springmvc.view_model;

public class Folder {

	private String entryUUID;
	private String lastUpdateTime;
	private String name;
	/**
	 * @return the lastUpdateTime
	 */
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}
	/**
	 * @param lastUpdateTime the lastUpdateTime to set
	 */
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
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
	
	
	
}
