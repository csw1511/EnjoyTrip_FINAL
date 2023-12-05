package com.ssafy.hotplace.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.hotplace.model.HotplaceDto;
import com.ssafy.hotplace.model.HotplaceListDto;
import com.ssafy.hotplace.model.mapper.HotplaceMapper;

@Service
public class HotplaceServiceImpl implements HotplaceService {
	
	private HotplaceMapper hotplaceMapper;
	
	@Autowired
	public HotplaceServiceImpl(HotplaceMapper hotplaceMapper) {
		super();
		this.hotplaceMapper = hotplaceMapper;
	}
	
	@Override
	@Transactional
	public void registHotplace(HotplaceDto hotplaceDto) throws Exception {
		hotplaceMapper.registHotplaceId(hotplaceDto.getContentId());
		hotplaceMapper.registHotplace(hotplaceDto);	
	}
	

	@Override
	public HotplaceListDto listPlace() throws Exception {
		
		List<HotplaceDto> list = hotplaceMapper.listPlace();
		HotplaceListDto hotplaceListDto = new HotplaceListDto();
		hotplaceListDto.setPlaces(list);
		
		return hotplaceListDto;
	}

	@Override
	public HotplaceListDto getHotplace(int contentId) throws Exception {
		List<HotplaceDto> list = hotplaceMapper.getHotplace(contentId);
		HotplaceListDto hotplaceListDto = new HotplaceListDto();
		hotplaceListDto.setPlaces(list);
		return hotplaceListDto;
	}

	@Override
	@Transactional
	public void updateHit(int articleNo) throws Exception {
		hotplaceMapper.updateHit(articleNo);
	}

	@Override
	public int checkFavorite(Map<String, String> param) throws Exception {
		return hotplaceMapper.checkFavorite(param);
	}

	@Override
	@Transactional
	public void addFavorite(Map<String, String> param) throws Exception {
		hotplaceMapper.addFavorite(param);
		
	}

	@Override
	@Transactional
	public void deleteFavorite(Map<String, String> param) throws Exception {
		hotplaceMapper.deleteFavorite(param);
		
	}

	

}
