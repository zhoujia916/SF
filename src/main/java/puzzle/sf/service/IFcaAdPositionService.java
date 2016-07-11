package puzzle.sf.service;

import java.util.List;
import java.util.Map;

import puzzle.sf.entity.FcaAdPosition;
import puzzle.sf.utils.Page;

public interface IFcaAdPositionService{ 

	public boolean insert(FcaAdPosition entity);

    public boolean update(FcaAdPosition entity);

    public boolean delete(Map<String, Object> map);

    public FcaAdPosition query(Map<String, Object> map);
    
    public List<FcaAdPosition> queryList(Object param);

    public List<FcaAdPosition> queryList(Map<String, Object> map, Page page);
}
