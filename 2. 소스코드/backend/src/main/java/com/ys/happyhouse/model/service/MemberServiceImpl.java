package com.ys.happyhouse.model.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ys.happyhouse.model.LikeDto;
import com.ys.happyhouse.model.MemberDto;
import com.ys.happyhouse.model.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberMapper memberMapper;
	private static final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);
	
	@Override
	public int idCheck(String userid) throws Exception {
		return memberMapper.idCheck(userid);
	}

	@Override
	public int login(String userid) throws Exception {
		return memberMapper.login(userid);
	}

	@Override
	public int register(MemberDto memberDto) throws Exception {
		return memberMapper.register(memberDto);
	}

	@Override
	public int deleteMember(String id) throws Exception {
		return memberMapper.deleteMember(id);
	}

	@Override
	public MemberDto platformLogin(String platform_id) throws Exception{
		return memberMapper.platformLogin(platform_id);
	}

	@Override
	public int platformIdCheck(String platform_id) throws Exception {
		return memberMapper.platformIdCheck(platform_id);
	}
	
}
