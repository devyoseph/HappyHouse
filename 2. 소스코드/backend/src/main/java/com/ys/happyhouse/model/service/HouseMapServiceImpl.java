package com.ys.happyhouse.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ys.happyhouse.model.HouseDto;
import com.ys.happyhouse.model.SidoGugunCodeDto;
import com.ys.happyhouse.model.mapper.HouseMapMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HouseMapServiceImpl implements HouseMapService {
	
	private final HouseMapMapper houseMapMapper;

	@Override
	public List<SidoGugunCodeDto> getSido() throws Exception {
		return houseMapMapper.getSido();
	}

	@Override
	public List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception {
		return houseMapMapper.getGugunInSido(sido);
	}

	@Override
	public List<HouseDto> getDongInGugun(String gugun) throws Exception {
		return houseMapMapper.getDongInGugun(gugun);
	}

	@Override
	public List<HouseDto> getAptInDong(String dong) throws Exception {
		return houseMapMapper.getAptInDong(dong);
	}

	@Override
	public List<HouseDto> searchAptByName(String aptName) throws Exception {
		return houseMapMapper.searchAptByName(aptName);
	}

}
