package com.ys.happyhouse.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ys.happyhouse.model.MemberDto;
import com.ys.happyhouse.model.service.JwtService;
import com.ys.happyhouse.model.service.MemberService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/user")
@CrossOrigin("*")
@RequiredArgsConstructor
public class MemberController {
private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	
	private final MemberService memberService;
	private final JwtService jwtService;
	

    @ApiOperation(value = "같은 아이디가 있는지 유효성을 검사한다. 있으면 1, 없으면 0 를 반환한다.", response = List.class)
	@GetMapping("/idcheck/{userid}")
	public ResponseEntity<Integer> idCheck(@PathVariable String userid) throws Exception {
		logger.debug("idCheckController - 호출");
		return new ResponseEntity<Integer>(memberService.idCheck(userid), HttpStatus.OK);
	}
    
    @ApiOperation(value = "로그인을 한다. 성공하면 1, 실패하면 0 를 반환한다.", response = List.class)
    @GetMapping("/{access_token}")
	public ResponseEntity<?> login(@PathVariable String access_token) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		logger.info("loginController : {}", access_token);
		if(jwtService.isUsable(access_token)) {
			MemberDto member = new MemberDto();
			member = memberService.platformLogin(jwtService.getSubject(access_token));
			
			if(member != null) {
				resultMap.put("userInfo", member);
				resultMap.put("message", "SUCCESS");
			}else {
				resultMap.put("message", "FAIL");
			}
			
		}else {
			resultMap.put("message", "EXPIRED");
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}
   
    @ApiOperation(value = "회원가입을 한다. 성공하면 1, 실패하면 0 를 반환한다.", response = List.class)
    @PostMapping("/")
 	public ResponseEntity<?> register(@RequestBody MemberDto memberDto, HttpSession session) throws Exception {
		logger.debug("registerController - 호출");
		
		if(memberService.register(memberDto) == 1) {
			System.out.println("회원가입 성공");
			String userToken = jwtService.createToken(memberDto.getUserid().toString(), (60*10000*15));
			logger.info("회원가입 유저에게 내부 토큰 발행: {} ", userToken);
			session.setAttribute("access_token", userToken); //세션으로 토큰을 저장해준다.
			
			return new ResponseEntity<String>(userToken, HttpStatus.OK);
		}
		
		//회원가입 실패
 		return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
 	}
    
    @ApiOperation(value = "회원 탈퇴를 한다. 성공하면 1, 실패하면 0 를 반환한다.", response = List.class)
    @DeleteMapping("/{userid}")
	public ResponseEntity<Integer> deleteMember(@PathVariable String userid) throws Exception {
 		logger.debug("registerController - 호출");
 		return new ResponseEntity<Integer>(memberService.deleteMember(userid), HttpStatus.OK);
 	}
   
    
    @GetMapping("/isAuth/{access_token}")
    public ResponseEntity<?> isAuth(@PathVariable String access_token) throws Exception {
 		logger.info("isAuth : {}", access_token);
 		if(jwtService.isUsable(access_token)) {
 			return new ResponseEntity<Void>(HttpStatus.OK);
 		}
 		return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
 	}
    

}
