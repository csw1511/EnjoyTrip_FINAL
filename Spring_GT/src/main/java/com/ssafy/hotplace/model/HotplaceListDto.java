package com.ssafy.hotplace.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "HotplaceDto : 핫플레이스 게시글 목록", description = "핫 플레이스 게시글 목록을 나타낸다.")
public class HotplaceListDto {
	
	@ApiModelProperty(value = "핫플레이스 글 목록")
	private List<HotplaceDto> places;

	public List<HotplaceDto> getPlaces() {
		return places;
	}

	public void setPlaces(List<HotplaceDto> places) {
		this.places = places;
	}

	@Override
	public String toString() {
		return "HotplaceListDto [places=" + places + "]";
	}
	
	

}
