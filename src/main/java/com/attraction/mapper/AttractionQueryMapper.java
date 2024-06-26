package com.attraction.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.attraction.entity.AttractionInfo;
import com.attraction.entity.Gugun;
import com.attraction.entity.Sido;

@Mapper
public interface AttractionQueryMapper {
	
	List<Gugun> selectAllGugun();
	
	List<Sido> selectAllSido();
	
	List<Gugun> selectBySidoCode(String sidoCode);
	
	List<AttractionInfo> selectAllAttractionInfo();

	AttractionInfo selectByContentId(String contentId);

	int getAttractionCount();
	
}
