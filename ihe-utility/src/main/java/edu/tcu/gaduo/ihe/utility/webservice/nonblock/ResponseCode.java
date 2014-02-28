/**
 * 
 */
package edu.tcu.gaduo.ihe.utility.webservice.nonblock;

/**
 * @author Gaduo
 *
 */
public class ResponseCode {

    private String code;
    private String codingScheme;
    private String display;
    
    /**
     * @param code
     * @param display
     */
    public ResponseCode(String code, String codingScheme, String display) {
        super();
        this.code = code;
        this.codingScheme = codingScheme;
        this.display = display;
    }
    public String getDisplay() {
        return display;
    }
    public void setDisplay(String display) {
        this.display = display;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getCodingScheme() {
        return codingScheme;
    }
    public void setCodingScheme(String codingScheme) {
        this.codingScheme = codingScheme;
    }
    
    public boolean compare(ResponseCode arg0, ResponseCode arg1) {
        if(arg0.getCode().compareTo(arg1.getCode()) == 0)
            if (arg0.getCodingScheme().compareTo(arg1.getCodingScheme()) == 0)
                return true;
        return false;
    }
    
    @Override
    public boolean equals(Object target) {
        if(this.getCode().compareTo(((ResponseCode)target).getCode()) == 0)
            if (this.getCodingScheme().compareTo(((ResponseCode)target).getCodingScheme()) == 0)
                return true;
        return false;
    }
    
    @Override
    public int hashCode() {
        return code.hashCode() * codingScheme.hashCode() * display.hashCode();
    }
}
