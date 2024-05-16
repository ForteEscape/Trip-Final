package com.attraction.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.attraction.entity.AttractionDescription;
import com.attraction.entity.AttractionInfo;
import com.attraction.entity.Gugun;
import com.attraction.entity.Sido;

@Mapper
public interface AttractionCommandMapper {
	
	void insertAttractionInfo(List<AttractionInfo> attractionInfo) throws SQLException;
	
	void insertAttractionDescription(List<AttractionDescription> attractionDescription) throws SQLException;
	
	void insertSidoData(List<Sido> sido) throws SQLException;
	
	void insertGunGuData(List<Gugun> gugun) throws SQLException;
	
	void testInsert(AttractionInfo info) throws SQLException;
}
