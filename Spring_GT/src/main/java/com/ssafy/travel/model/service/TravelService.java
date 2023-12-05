package com.ssafy.travel.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.travel.model.AttractionDto;
import com.ssafy.travel.model.FindByContentIdDto;
import com.ssafy.travel.model.PlanDetailDto;
import com.ssafy.travel.model.PlanDto;
import com.ssafy.travel.model.SidoGugunCodeDto;
import com.ssafy.travel.model.UpdatePlanArticleNoDto;

public interface TravelService {
	List<SidoGugunCodeDto> getSido() throws Exception;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception;
	List<AttractionDto> getAttractions(Map<String, String> map) throws Exception;
	List<AttractionDto> searchAttraction(String title) throws Exception;
	void savePlanDetail(PlanDetailDto planDetailDto) throws Exception;
	void savePlan(PlanDto planDto) throws Exception;
	int getPlanId() throws Exception;
	List<FindByContentIdDto> findBySelectedAttr(String contentId) throws Exception;
	List<FindByContentIdDto> findByUserId(String userId) throws Exception;
	List<PlanDto> planList() throws Exception;
	PlanDto planListByPlanId(int planId) throws Exception;
	List<FindByContentIdDto> plansByPlanId(int planId) throws Exception;
	void updatePlanArticleNo(UpdatePlanArticleNoDto dto) throws Exception;
}
