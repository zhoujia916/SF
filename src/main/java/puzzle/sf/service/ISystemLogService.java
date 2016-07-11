package puzzle.sf.service;

import java.util.Date;
import java.util.Map;
import java.util.List;
import puzzle.sf.utils.Page;
import puzzle.sf.entity.SystemLog;

public interface ISystemLogService{ 

	public boolean insert(SystemLog entity);

    public boolean update(SystemLog entity);

    public boolean delete(Map<String, Object> map);

    public SystemLog query(Map<String, Object> map);

    public List<SystemLog> queryList(Object param);

    public List<SystemLog> queryList(Map<String, Object> map, Page page);
    
}
