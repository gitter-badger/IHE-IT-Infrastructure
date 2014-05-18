package edu.tcu.gaduo.ihe.iti.xds_transaction.template;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import edu.tcu.gaduo.ihe.constants.ProvideAndRegistryDocumentSet_B_UUIDs;

@XmlType(name = "DocumentAuthor")
@XmlAccessorType (XmlAccessType.FIELD)
public class DocumentAuthorType extends AuthorType{
	public DocumentAuthorType(){
		super();
		uuid = ProvideAndRegistryDocumentSet_B_UUIDs.DOC_ENTRY_AUTHOR;
	}
	
}
