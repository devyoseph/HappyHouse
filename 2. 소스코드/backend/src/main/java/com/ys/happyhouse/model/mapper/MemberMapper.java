package com.ys.happyhouse.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ys.happyhouse.model.MemberDto;

@Mapper
public interface MemberMapper {
	int idCheck(String userid) throws Exception;
	int login(String userid) throws Exception;
	int register(MemberDto memberDto) throws Exception;
	int deleteMember(String id) throws Exception;
	
	int platformIdCheck(String userid) throws Exception;
	MemberDto platformLogin(String platform_id) throws Exception;
}
