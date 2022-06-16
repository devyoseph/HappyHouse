package com.ys.happyhouse.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ys.happyhouse.model.HouseDto;
import com.ys.happyhouse.model.SidoGugunCodeDto;

@Mapper
public interface HouseMapMapper {

	List<SidoGugunCodeDto> getSido() throws SQLException;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws SQLException;
	List<HouseDto> getDongInGugun(String gugun) throws SQLException;
	List<HouseDto> getAptInDong(String dong) throws SQLException;
	List<HouseDto> searchAptByName(String aptName) throws SQLException;;
}
