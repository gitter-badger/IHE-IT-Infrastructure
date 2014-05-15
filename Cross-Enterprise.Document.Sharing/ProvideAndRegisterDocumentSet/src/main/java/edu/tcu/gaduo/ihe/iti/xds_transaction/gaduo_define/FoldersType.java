package edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "Folders")
@XmlAccessorType (XmlAccessType.FIELD)
public class FoldersType {
	@XmlElement(name="Folder")
	protected List<FolderType> list;
	
	public FoldersType(){
		list = new ArrayList<FolderType>();
	}

	public void addFolder(FolderType folder) {
		list.add(folder);
	}
	
	public List<FolderType> getList(){
		return list;
	}
}
