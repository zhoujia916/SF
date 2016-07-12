package puzzle.sf.service;

import java.util.List;
import java.util.Map;

import puzzle.sf.entity.SfArticle;
import puzzle.sf.utils.Page;

public interface ISfArticleService{ 

	public boolean insert(SfArticle entity);

    public boolean update(SfArticle entity);

    public boolean delete(Map<String, Object> map);

    public SfArticle query(Map<String, Object> map);
    
    public List<SfArticle> queryList(Object param);

    public List<SfArticle> queryList(Map<String, Object> map, Page page);
    
}
