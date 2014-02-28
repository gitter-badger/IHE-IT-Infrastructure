/**
 * $Id: templates-FileHeader.xml,v 1.0 2012/4/27 下午3:53:36 $ Copyright (c) 2012 Datacom Technology Corp. All Rights
 * Reserved. H I S T O R Y [&gt;: General, +: Add, #: Modification, -: Remove, ~: BugFixed] $Log:
 * templates-FileHeader.xml,v $ $Revision 1.0 2012/4/27 下午3:53:36 AllenLo $Java Code的File Header $
 */

package edu.tcu.gaduo.view.lintener;

import javax.servlet.http.HttpSessionEvent;

import com.dtc.view.listener.SessionListener;

/**
 * The <code>CutomHttpSessionAttributeListener.java</code> class.
 * 
 * @version $Name: $, $Revision: 1.0 $, $Date: 2012/4/27 下午3:53:39 $
 * @author <a href="mailto:allen.lo@datacom.com.tw">Allen Lo</a>
 */

public class CutomHttpSessionAttributeListener extends SessionListener {
    public static final String VERSION = "$Name: $, $Revision: 1.0 $, $Date: 2012/4/27 下午3:53:41 $";
    public static final String COPYRIGHT = "Copyright (c) 2012 Datacom Technology Corp. All Rights Reserved.";
    
/*
    private static Log log = LogFactory.getLog(CutomHttpSessionAttributeListener.class);
    @Autowired
    private SheetAuditController audit;
*/    
    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
/*
        HttpSession session = event.getSession();
        Entity operator = (Entity) session.getAttribute(SessionManager.SESSION_ATT_USER);
        if (operator != null) // Session timeout, just get user from session
            try {
                getSheetAuditController().auditLogout(null, new Date(), operator);
            } catch (Exception e) {
                log.error(e.getLocalizedMessage(), e);
            } finally {
                SessionManager.clearSession(session, null);
            }
*/
        super.sessionDestroyed(event);
    }
}
