package com.attraction.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.attraction.entity.Gugun;

@Mapper
public interface AttractionQueryMapper {
	
	List<Gugun> selectAllGugun();
}
