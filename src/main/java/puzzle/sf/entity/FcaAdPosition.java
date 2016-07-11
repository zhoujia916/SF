package puzzle.sf.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.lang.StringBuilder;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="fca_ad_position")
public class FcaAdPosition implements Serializable{
	/**
	* Constructor
	*/
	public FcaAdPosition(){
	}
	
	/**
	* Override toString method
	*/
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
        sb.append("AutoAdPosition{");
        sb.append("positionId=").append(positionId);
        sb.append(", positionName=").append(positionName);
        sb.append(", width=").append(width);
        sb.append(", height=").append(height);
        sb.append(", sortOrder=").append(sortOrder);
        sb.append("}").append(System.getProperty("line.separator"));
        return sb.toString();
	}

	/**
	* Fields
	*/
	@Column(name="position_id", unique=true)
	private Integer positionId;
	@Column(name="position_name", nullable=true, length=100)
	private String positionName;
	@Column(name="width", nullable=true)
	private Integer width;
	@Column(name="height", nullable=true)
	private Integer height;
	@Column(name="sort_order", nullable=true)
	private Integer sortOrder;
	
	
	/**
	* Getter and Setter
	*/
	public Integer getPositionId(){
		return positionId;
	}
	
	public void setPositionId(Integer positionId){
		this.positionId = positionId;
	}
		
	public String getPositionName(){
		return positionName;
	}
	
	public void setPositionName(String positionName){
		this.positionName = positionName;
	}
		
	public Integer getWidth(){
		return width;
	}
	
	public void setWidth(Integer width){
		this.width = width;
	}
		
	public Integer getHeight(){
		return height;
	}
	
	public void setHeight(Integer height){
		this.height = height;
	}
		
	public Integer getSortOrder(){
		return sortOrder;
	}
	
	public void setSortOrder(Integer sortOrder){
		this.sortOrder = sortOrder;
	}
		
}
