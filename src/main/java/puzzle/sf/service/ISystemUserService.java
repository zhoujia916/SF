package puzzle.sf.service;

import java.util.Date;
import java.util.Map;
import java.util.List;

import puzzle.sf.entity.SystemAuthority;
import puzzle.sf.utils.Page;
import puzzle.sf.entity.SystemUser;

public interface ISystemUserService{ 

	public boolean insert(SystemUser entity);

    public boolean update(SystemUser entity);

    public boolean delete(Map<String, Object> map);

    public SystemUser query(Map<String, Object> map);
    
    public List<SystemUser> queryList(Object param);

    public List<SystemUser> queryList(Map<String, Object> map, Page page);

    public List<SystemAuthority> queryAuthority(int userId);
}
