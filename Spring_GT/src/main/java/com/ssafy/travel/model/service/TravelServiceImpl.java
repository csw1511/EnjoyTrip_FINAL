package com.ssafy.travel.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.travel.model.AttractionDto;
import com.ssafy.travel.model.FindByContentIdDto;
import com.ssafy.travel.model.PlanDetailDto;
import com.ssafy.travel.model.PlanDto;
import com.ssafy.travel.model.SidoGugunCodeDto;
import com.ssafy.travel.model.UpdatePlanArticleNoDto;
import com.ssafy.travel.model.mapper.TravelMapper;

@Service
public class TravelServiceImpl implements TravelService {
	
	private TravelMapper travelMapper;
	
	public TravelServiceImpl(TravelMapper travelMapper) {
		super();
		this.travelMapper = travelMapper;
	}

	@Override
	public List<SidoGugunCodeDto> getSido() throws Exception {
		return travelMapper.getSido();
	}

	@Override
	public List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception {
		return travelMapper.getGugunInSido(sido);
	}

	@Override
	public List<AttractionDto> getAttractions(Map<String, String> map) throws Exception {
		return travelMapper.getAttractions(map);
	}

	@Override
	public List<AttractionDto> searchAttraction(String title) throws Exception {
		return travelMapper.searchAttraction(title);
	}

	@Override
	public void savePlanDetail(PlanDetailDto planDetailDto) throws Exception {
		travelMapper.savePlanDetail(planDetailDto);
	}

	@Override
	public void savePlan(PlanDto planDto) throws Exception {
		travelMapper.savePlan(planDto);
	}

	@Override
	public int getPlanId() throws Exception {
		return travelMapper.getPlanId();
	}

	@Override
	public List<FindByContentIdDto> findBySelectedAttr(String contentId) throws Exception {
		return travelMapper.findBySelectedAttr(contentId);
	}

	@Override
	public List<FindByContentIdDto> findByUserId(String userId) throws Exception {
		return travelMapper.findByUserId(userId);
	}

	@Override
	public List<PlanDto> planList() throws Exception {
		return travelMapper.planList();
	}

	@Override
	public List<FindByContentIdDto> plansByPlanId(int planId) throws Exception {
		return travelMapper.plansByPlanId(planId);
	}

	@Override
	public PlanDto planListByPlanId(int planId) throws Exception {
		return travelMapper.planListByPlanId(planId);
	}

	@Override
	public void updatePlanArticleNo(UpdatePlanArticleNoDto dto) throws Exception {
		travelMapper.updatePlanArticleNo(dto);
		
	}

}
