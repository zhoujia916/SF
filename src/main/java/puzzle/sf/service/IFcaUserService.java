package puzzle.sf.service;

import java.util.Date;
import java.util.Map;
import java.util.List;
import puzzle.sf.utils.Page;
import puzzle.sf.entity.FcaUser;

public interface IFcaUserService{ 

	public boolean insert(FcaUser entity);

    public boolean update(FcaUser entity);

    public boolean delete(Map<String, Object> map);

    public FcaUser query(Map<String, Object> map);

    public FcaUser query(Integer userId, String userName);
    
    public List<FcaUser> queryList(Object param);

    public List<FcaUser> queryList(Map<String, Object> map, Page page);
    
}
