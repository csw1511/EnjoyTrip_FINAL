package com.ssafy.travel.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.travel.model.AttractionDto;
import com.ssafy.travel.model.FindByContentIdDto;
import com.ssafy.travel.model.PlanDetailDto;
import com.ssafy.travel.model.PlanDto;
import com.ssafy.travel.model.SidoGugunCodeDto;
import com.ssafy.travel.model.UpdatePlanArticleNoDto;

@Mapper
public interface TravelMapper {
	
	List<SidoGugunCodeDto> getSido() throws SQLException;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws SQLException;
	List<AttractionDto> getAttractions(Map<String, String> map) throws SQLException;
	List<AttractionDto> searchAttraction(String title) throws SQLException;
	void savePlanDetail(PlanDetailDto planDetailDto) throws SQLException;
	void savePlan(PlanDto planDto) throws SQLException;
	int getPlanId() throws SQLException;
	List<FindByContentIdDto> findBySelectedAttr(String contentId) throws SQLException;
	List<FindByContentIdDto> findByUserId(String userId) throws SQLException;
	List<PlanDto> planList() throws SQLException;
	PlanDto planListByPlanId(int planId) throws SQLException;	
	List<FindByContentIdDto> plansByPlanId(int planId) throws SQLException;
	void updatePlanArticleNo(UpdatePlanArticleNoDto dto) throws SQLException;
	
}
