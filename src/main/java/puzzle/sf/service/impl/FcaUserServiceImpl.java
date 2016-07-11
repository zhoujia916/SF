package puzzle.sf.service.impl;

import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import puzzle.sf.entity.FcaUser;
import puzzle.sf.service.IFcaUserService;
import puzzle.sf.utils.Page;
import puzzle.sf.mapper.SqlMapper;
import puzzle.sf.utils.StringUtil;

@Service("fcaUserService")
public class FcaUserServiceImpl implements IFcaUserService {
	
	@Autowired
	private SqlMapper sqlMapper;
	
	/**
	* 插入单条记录
	*/
	@Override
	public boolean insert(FcaUser entity){
		return sqlMapper.insert("FcaUserMapper.insert", entity);
	}

	/**
	* 更新单条记录
	*/
	@Override
    public boolean update(FcaUser entity){
    	return sqlMapper.update("FcaUserMapper.update", entity);
    }

	/**
	* 删除单条记录
	*/
	@Override
    public boolean delete(Map<String, Object> map){
    	return sqlMapper.delete("FcaUserMapper.delete", map);
    }

	/**
	* 查询单条记录
	*/
	@Override
    public FcaUser query(Map<String, Object> map){
    	return sqlMapper.query("FcaUserMapper.query", map);
    }

    /**
     * 查询单条记录
     */
    public FcaUser query(Integer userId, String userName){
        Map<String, Object> map = new HashMap<String, Object>();
        if(userId != null && userId > 0){
            map.put("userId", userId);
        }
        if(StringUtil.isNotNullOrEmpty(userName)){
            map.put("userName", userName);
        }
        return sqlMapper.query("FcaUserMapper.query", map);
    }

	/**
	* 查询多条记录
	*/
	@Override
    public List<FcaUser> queryList(Object param){
    	return sqlMapper.queryList("FcaUserMapper.queryList", param);
    }
    
    
    /**
	* 查询分页记录
	*/
	@Override
    public List<FcaUser> queryList(Map<String, Object> map, Page page){
    	return sqlMapper.queryList("FcaUserMapper.queryList", map, page);
    }
    
}
