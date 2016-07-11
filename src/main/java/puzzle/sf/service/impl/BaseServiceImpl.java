package puzzle.sf.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import puzzle.sf.mapper.SqlMapper;

public class BaseServiceImpl {
    @Autowired
    protected SqlMapper sqlMapper;

    protected Logger logger;

    public BaseServiceImpl(){
        this.logger = LoggerFactory.getLogger(this.getClass());
    }
}
