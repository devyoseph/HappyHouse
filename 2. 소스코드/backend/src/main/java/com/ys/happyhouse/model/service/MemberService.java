package com.ys.happyhouse.model.service;

import java.util.Map;

import com.ys.happyhouse.model.LikeDto;
import com.ys.happyhouse.model.MemberDto;

public interface MemberService {
	
	int idCheck(String userid) throws Exception;
	int login(String userid) throws Exception;
	int register(MemberDto memberDto) throws Exception;
	int deleteMember(String id) throws Exception;
	
	MemberDto platformLogin(String platform_id) throws Exception;
	int platformIdCheck(String userid) throws Exception;
	
}
