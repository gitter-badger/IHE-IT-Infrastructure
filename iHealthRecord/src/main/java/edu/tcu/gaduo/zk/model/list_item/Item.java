package edu.tcu.gaduo.zk.model.list_item;

public class Item {
	public static final String DOCUMENT = "檔案";
	public static final String FOLDER = "資料夾";
	
	
	private String id;
	private String timestamp;
	private String type;
	private String name;
	private String repositoryUniqueId;
	private String uniqueId;
	private Item parent;

	public Item(String id, String timestamp, String title, String description, String type, Item parent) {
		super();
		this.id = id;
		this.timestamp = timestamp;
		this.type = type;
		this.name = timestamp + "_" + title + "_" + description;
		this.parent = parent;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	
	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public Item getParent() {
		return parent;
	}

	public void setParent(Item parent) {
		this.parent = parent;
	}

	public String getRepositoryUniqueId() {
		return repositoryUniqueId;
	}

	public void setRepositoryUniqueId(String repositoryUniqueId) {
		this.repositoryUniqueId = repositoryUniqueId;
	}
	
}
