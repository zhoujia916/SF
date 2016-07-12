package puzzle.sf.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import puzzle.sf.entity.SfArticle;
import puzzle.sf.mapper.SqlMapper;
import puzzle.sf.service.ISfArticleService;
import puzzle.sf.utils.Page;

@Service("sfArticleService")
public class SfArticleServiceImpl implements ISfArticleService {
	
	@Autowired
	private SqlMapper sqlMapper;
	
	/**
	* 插入单条记录
	*/
	@Override
	public boolean insert(SfArticle entity){
        try {
            if(sqlMapper.insert("SfArticleMapper.insert", entity)){
                entity.setArticleUrl("http://192.168.1.254:9080/admin/article/view/"+entity.getArticleId());
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
	}

	/**
	* 更新单条记录
	*/
	@Override
    public boolean update(SfArticle entity){
    	return sqlMapper.update("SfArticleMapper.update", entity);
    }

	/**
	* 删除单条记录
	*/
	@Override
    public boolean delete(Map<String, Object> map){
    	return sqlMapper.delete("SfArticleMapper.delete", map);
    }

	/**
	* 查询单条记录
	*/
	@Override
    public SfArticle query(Map<String, Object> map){
    	return sqlMapper.query("SfArticleMapper.query", map);
    }

	/**
	* 查询多条记录
	*/
	@Override
    public List<SfArticle> queryList(Object param){
    	return sqlMapper.queryList("SfArticleMapper.queryList", param);
    }
    
    
    /**
	* 查询分页记录
	*/
	@Override
    public List<SfArticle> queryList(Map<String, Object> map, Page page){
    	return sqlMapper.queryList("SfArticleMapper.queryList", map, page);
    }
    
}
