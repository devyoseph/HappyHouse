package com.ys.happyhouse.model.service;

import java.util.List;

import com.ys.happyhouse.model.HouseDto;
import com.ys.happyhouse.model.SidoGugunCodeDto;


public interface HouseMapService {

	List<SidoGugunCodeDto> getSido() throws Exception;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception;
	List<HouseDto> getDongInGugun(String gugun) throws Exception;
	List<HouseDto> getAptInDong(String dong) throws Exception;
	List<HouseDto> searchAptByName(String aptName) throws Exception;
}
