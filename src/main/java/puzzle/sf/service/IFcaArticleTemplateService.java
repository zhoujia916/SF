package puzzle.sf.service;

import java.util.Date;
import java.util.Map;
import java.util.List;
import puzzle.sf.utils.Page;
import puzzle.sf.entity.FcaArticleTemplate;

public interface IFcaArticleTemplateService{ 

	public boolean insert(FcaArticleTemplate entity);

    public boolean update(FcaArticleTemplate entity);

    public boolean delete(Map<String, Object> map);

    public FcaArticleTemplate query(Map<String, Object> map);
    
    public List<FcaArticleTemplate> queryList(Object param);

    public List<FcaArticleTemplate> queryList(Map<String, Object> map, Page page);
    
}
