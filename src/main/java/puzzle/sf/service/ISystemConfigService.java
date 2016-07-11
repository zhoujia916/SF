package puzzle.sf.service;

import java.util.Date;
import java.util.Map;
import java.util.List;
import puzzle.sf.utils.Page;
import puzzle.sf.entity.SystemConfig;

public interface ISystemConfigService{ 

	public boolean insert(SystemConfig entity);

    public boolean update(SystemConfig entity);

    public boolean delete(Map<String, Object> map);

    public SystemConfig query(Map<String, Object> map);
    
    public List<SystemConfig> queryList(Object param);

    public List<SystemConfig> queryList(Map<String, Object> map, Page page);
    
}
