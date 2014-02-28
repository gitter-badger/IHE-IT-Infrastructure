package edu.tcu.gaduo.zk.model.code;

import java.util.List;



/**
 * @author Gaduo
 *
 */
public interface Codes {
    public List<Code> findAll();
    public Code get(int i);
}
