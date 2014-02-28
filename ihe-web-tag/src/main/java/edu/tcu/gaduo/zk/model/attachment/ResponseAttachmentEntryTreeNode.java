/**
 * 
 */
package edu.tcu.gaduo.zk.model.attachment;

import java.util.Collection;
import java.util.List;

import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.TreeNode;

import edu.tcu.gaduo.ihe.utility.webservice.nonblock.ResponseAttachmentEntry;

/**
 * The <code>ResponseAttachmentEntryTreeNode.java</code> class.
 * 
 * @version $Name: $, $Revision: 1.0 $, $Date: 2014/1/27 $
 * @author <a href="mailto:leesh@datacom.com.tw">Siang Hao Lee</a>
 */

public class ResponseAttachmentEntryTreeNode<E> extends DefaultTreeNode<ResponseAttachmentEntry> {

    private static final long serialVersionUID = 1L;

    public ResponseAttachmentEntryTreeNode(ResponseAttachmentEntry data) {
        super(data);
    }
    public ResponseAttachmentEntryTreeNode(ResponseAttachmentEntry data, Collection< ? extends TreeNode<ResponseAttachmentEntry>> children) {
        super(data, children);
    }
    public ResponseAttachmentEntryTreeNode(ResponseAttachmentEntry data, Collection< ? extends TreeNode<ResponseAttachmentEntry>> children, boolean nullAsMax) {
        super(data, children, nullAsMax);
    }
    
    public ResponseAttachmentEntry getAttachmentEntry() {
        return super.getData();
    }
    
    public List<TreeNode<ResponseAttachmentEntry>> getAttachmentEntryList() {
        return super.getChildren();
    }

}
