package com.ssafy.hotplace.model.service;

import java.sql.SQLException;
import java.util.Map;

import com.ssafy.hotplace.model.HotplaceDto;
import com.ssafy.hotplace.model.HotplaceListDto;

public interface HotplaceService {
	void registHotplace(HotplaceDto hotplaceDto) throws Exception;
	HotplaceListDto listPlace() throws Exception;
	HotplaceListDto getHotplace(int contentId) throws Exception;
	void updateHit(int articleNo) throws Exception;
	int checkFavorite(Map<String, String> param) throws Exception;
	void addFavorite(Map<String, String> param) throws Exception;
	void deleteFavorite(Map<String, String> param) throws Exception;
}
