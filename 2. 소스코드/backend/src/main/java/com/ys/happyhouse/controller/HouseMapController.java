package com.ys.happyhouse.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ys.happyhouse.model.HouseDto;
import com.ys.happyhouse.model.SidoGugunCodeDto;
import com.ys.happyhouse.model.service.HouseMapService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/map")
//@CrossOrigin("*")
@RequiredArgsConstructor
@Api("하우스 컨트롤러")
public class HouseMapController {
	
	private final Logger logger = LoggerFactory.getLogger(HouseMapController.class);

	private final HouseMapService houseMapService;
	
	@ApiOperation(value = "없음", notes = "요청시 <big>시도 목록<big>을 바로 리턴.", response = List.class)
	@GetMapping("/sido")
	public ResponseEntity<List<SidoGugunCodeDto>> sido() throws Exception {
		return new ResponseEntity<List<SidoGugunCodeDto>>(houseMapService.getSido(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "sido", notes = "/gugun?sido: 요청시 해당하는 시도에 대한 <big>구군 목록<big>을 리턴.", response = List.class)
	@GetMapping("/gugun")
	public ResponseEntity<List<SidoGugunCodeDto>> gugun(@RequestParam("sido") String sido) throws Exception {
		return new ResponseEntity<List<SidoGugunCodeDto>>(houseMapService.getGugunInSido(sido), HttpStatus.OK);
	}
	
	@ApiOperation(value = "gugun", notes = "/dong?sido: 요청시 해당하는 구군에 대한 <big>동 목록<big>을 리턴.", response = List.class)
	@GetMapping("/dong")
	public ResponseEntity<List<HouseDto>> dong(@RequestParam("gugun") String gugun) throws Exception {
		return new ResponseEntity<List<HouseDto>>(houseMapService.getDongInGugun(gugun), HttpStatus.OK);
	}
	
	@ApiOperation(value = "dong", notes = "/apt?dong: 요청시 해당하는 동에 대한 <big>아파트 목록<big>을 리턴.", response = List.class)
	@GetMapping("/apt")
	public ResponseEntity<List<HouseDto>> apt(@RequestParam("dong") String dong) throws Exception {
		return new ResponseEntity<List<HouseDto>>(houseMapService.getAptInDong(dong), HttpStatus.OK);
	}
	
	@ApiOperation(value = "name", notes = "/apt?name: <big>아파트 목록<big>을 검색", response = List.class)
	@GetMapping("/apt/{name}")
	public ResponseEntity<List<HouseDto>> aptSearch(@PathVariable("name") String name) throws Exception {
		return new ResponseEntity<List<HouseDto>>(houseMapService.searchAptByName(name), HttpStatus.OK);
	}
}
