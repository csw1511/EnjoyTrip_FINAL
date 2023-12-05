package com.ssafy.travel.controller;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.travel.model.AttractionDto;
import com.ssafy.travel.model.FindByContentIdDto;
import com.ssafy.travel.model.PlanDetailDto;
import com.ssafy.travel.model.PlanDto;
import com.ssafy.travel.model.SidoGugunCodeDto;
import com.ssafy.travel.model.UpdatePlanArticleNoDto;
import com.ssafy.travel.model.service.TravelService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/map")
@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE} , maxAge = 6000)
@Api("지도 정보 ")
public class TravelController {
	private final Logger logger = LoggerFactory.getLogger(TravelController.class);
	
	private TravelService travelService;

	public TravelController(TravelService travelService) {
		super();
		this.travelService = travelService;
	}

	@ApiOperation(value = "시도 정보", notes = "전국의 시도를 반환한다.", response = List.class)
	@GetMapping("/sido")
	public ResponseEntity<List<SidoGugunCodeDto>> getSido() throws Exception {
		logger.info("getSido");
		List<SidoGugunCodeDto> sidoListDto = travelService.getSido();
//		System.out.println("결과 : "+sidoListDto);
//		return ResponseEntity.ok().headers(header).body(sidoListDto);
		
		return new ResponseEntity<List<SidoGugunCodeDto>>(sidoListDto, HttpStatus.OK);

	}

	@ApiOperation(value = "구군 정보", notes = "시도에 속한 구군을 반환한다.", response = List.class)
	@GetMapping("/gugun")
	public ResponseEntity<List<SidoGugunCodeDto>> getGugunInSido(@RequestParam @ApiParam(value="도시 코드", required=true) String sido) throws Exception {
		logger.info("getGugunInSido String - {}", sido);
		List<SidoGugunCodeDto> gugunListDto = travelService.getGugunInSido(sido); 
//		System.out.println("결과 : "+gugunListDto);
//		return ResponseEntity.ok().headers(header).body(gugunListDto);
		return new ResponseEntity<List<SidoGugunCodeDto>>(gugunListDto, HttpStatus.OK);
	}
	
	@ApiOperation(value = "관광지 정보", notes = "모든 지역 또는 지정한 지역의 관광지 리스트를 반환한다.", response = List.class)
	@GetMapping("/attractions")
	public ResponseEntity<List<AttractionDto>> getAttractions(@RequestParam @ApiParam(value="시도, 구군 코드", required=true) Map<String, String> map) throws Exception {
		logger.info("getAttractions String - {}", map);
		
		List<AttractionDto> attractionDtos = travelService.getAttractions(map);
//		System.out.println("결과 : "+attractionDtos);
		return new ResponseEntity<List<AttractionDto>>(attractionDtos, HttpStatus.OK);
	}
	
	@ApiOperation(value = "이름으로 검색한 관광지 정보", notes = "사용자가 입력한 이름을 가진 관광지 리스트를 반환한다.", response = List.class)
	@GetMapping("/searchbytitle")
	public ResponseEntity<List<AttractionDto>> searchAttraction(@RequestParam @ApiParam(value="관광지 이름", required=true) String title) throws Exception{
		logger.info("searchAttraction String - {}", title);

		List<AttractionDto> attractionDtos = travelService.searchAttraction(title);
		return new ResponseEntity<List<AttractionDto>>(attractionDtos, HttpStatus.OK);
	}
	

	@ApiOperation(value = "관광지 방문계획 리스트 저장", notes = "사용자가 작성한 관광지 방문계획 리스트를 저장한다 ", response = List.class)
	@PostMapping("/reviewsavedetail")
	public ResponseEntity<?> reviewSaveDetail(@RequestBody @ApiParam(value="관광지 리스트", required = true) List<AttractionDto> planDetail) throws Exception{
		logger.info("reviewSaveDetail String - {}", planDetail);
		int count = 1;
		for(AttractionDto attractionDto : planDetail) {
			System.out.println(attractionDto.getContentId());
			System.out.println(attractionDto.getTitle());
			PlanDetailDto planDetailDto = new PlanDetailDto();
			planDetailDto.setPlanId(travelService.getPlanId());
			planDetailDto.setPlanOrder(count++);
			planDetailDto.setContentId(attractionDto.getContentId());
			planDetailDto.setMemo(attractionDto.getMemo());
			System.out.println(planDetailDto);
			travelService.savePlanDetail(planDetailDto);
		}
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "관광지 방문계획 저장", notes = "사용자가 작성한 관광지 방문계획과 세부내용을 저장한다 ", response = List.class)
	@PostMapping("/reviewsave")
	public ResponseEntity<?> reviewSave(@RequestBody @ApiParam(value="여행계획 세부내용", required = true) PlanDto plan) throws Exception{
		logger.info("reviewSave String - {}", plan);
		System.out.println(plan);
		travelService.savePlan(plan);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@ApiOperation(value = "타인의 여행계획 검색", notes = "선택한 관광지를 기준으로 타인의 여행계획을 검색한다 ", response = List.class)
	@GetMapping("/findbycontentid")
	public ResponseEntity<List<FindByContentIdDto>> findByContentId(@RequestParam @ApiParam(value="사용자가 선택한 관광지 content_id", required=true) String contentId ) throws Exception{
		System.out.println(contentId);

		List<FindByContentIdDto> findByContentIdDtos = travelService.findBySelectedAttr(contentId);
		System.out.println(findByContentIdDtos);
		return new ResponseEntity<List<FindByContentIdDto>>(findByContentIdDtos, HttpStatus.OK);
	}
	
	@ApiOperation(value = "자신의 여행계획 검색", notes = "사용자 본인이 작성한 여행계획을 검색한다 ", response = List.class)
	@GetMapping("/findbyuserid")
	public ResponseEntity<List<FindByContentIdDto>> findByUserId(@RequestParam @ApiParam(value="사용자가 선택한 유저아이디dsa user_id", required=false) String userId ) throws Exception{
		System.out.println(userId);

		List<FindByContentIdDto> findByContentIdDtos = travelService.findByUserId(userId);
		System.out.println(findByContentIdDtos);
		return new ResponseEntity<List<FindByContentIdDto>>(findByContentIdDtos, HttpStatus.OK);
	}
	
	@ApiOperation(value = "여행계획 리스트 반환", notes = "모든 여행계획을 리스트로 반환한다 ", response = List.class)
	@GetMapping("/planlist")
	public ResponseEntity<List<PlanDto>> planList() throws Exception{

		List<PlanDto> planDtos = travelService.planList();
		System.out.println(planDtos);
		return new ResponseEntity<List<PlanDto>>(planDtos, HttpStatus.OK);
	}
	
	@ApiOperation(value = "여행계획 작성내용 반환", notes = "해당 여행계획의 작성내용을 반환한다 ", response = List.class)
	@GetMapping("/planlistbyplanid/{planid}")
	public ResponseEntity<PlanDto> planListByPlanId(
			@PathVariable("planid") @ApiParam(value = "여행계획 planId", required = true) int planId
			) throws Exception{
		System.out.println(planId);

		PlanDto planDtos = travelService.planListByPlanId(planId);
		System.out.println(planDtos);
		return new ResponseEntity<PlanDto>(planDtos, HttpStatus.OK);
	}
	
	@ApiOperation(value = "여행계획 아이디를 통한 여행계획 리스트 반환", notes = "해당 여행계획의 모든 관광지를 리스트로 반환한다 ", response = List.class)
	@GetMapping("/travelreviewboard/view/{planid}")
	public ResponseEntity<List<FindByContentIdDto>> plansByPlanId(
			@PathVariable("planid") @ApiParam(value = "여행계획 planId", required = true) int planId
			) throws Exception{
		System.out.println(planId);

		List<FindByContentIdDto> findByContentIdDtos = travelService.plansByPlanId(planId);
		System.out.println(findByContentIdDtos);
		return new ResponseEntity<List<FindByContentIdDto>>(findByContentIdDtos, HttpStatus.OK);
	}
	

	@PostMapping("/updateplanarticleno")
	public ResponseEntity<?> updatePlanArticleNo(@RequestBody @ApiParam(value="플랜아이디와 아티클넘버", required=true) UpdatePlanArticleNoDto dto) throws Exception {
//	public ResponseEntity<?> updatePlanArticleNo(
//			@RequestParam @ApiParam(value="왜안들어옴", required=true) Map<String, String> map) throws Exception{
		System.out.println("잘들어옴?");
		logger.info("updatePlanArticleNo String - {}", dto);
		travelService.updatePlanArticleNo(dto);
		return null;
	}
	
}
