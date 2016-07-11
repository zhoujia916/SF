package puzzle.sf.service;

import java.util.Date;
import java.util.Map;
import java.util.List;
import puzzle.sf.utils.Page;
import puzzle.sf.entity.SystemMenu;

public interface ISystemMenuService{ 

	public boolean insert(SystemMenu entity);

    public boolean update(SystemMenu entity);

    public boolean delete(Map<String, Object> map);

    public SystemMenu query(Map<String, Object> map);
    
    public List<SystemMenu> queryList(Object param);

    public List<SystemMenu> queryList(Map<String, Object> map, Page page);
    
}
