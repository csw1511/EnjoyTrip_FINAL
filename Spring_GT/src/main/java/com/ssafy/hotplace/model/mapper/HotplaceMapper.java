package com.ssafy.hotplace.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.hotplace.model.HotplaceDto;

@Mapper
public interface HotplaceMapper {
	void registHotplaceId(int contentId) throws SQLException;
	void registHotplace(HotplaceDto hotplaceDto) throws SQLException;
	List<HotplaceDto> listPlace() throws SQLException;
	List<HotplaceDto> getHotplace(int contentId) throws SQLException;
	void updateHit(int articleNo) throws SQLException;
	int checkFavorite(Map<String, String> param) throws SQLException;
	void addFavorite(Map<String, String> param) throws SQLException;
	void deleteFavorite(Map<String, String> param) throws SQLException;
	
}
