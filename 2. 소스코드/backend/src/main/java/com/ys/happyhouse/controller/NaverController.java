package com.ys.happyhouse.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Mapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.ys.happyhouse.model.MemberDto;
import com.ys.happyhouse.model.service.JwtService;
import com.ys.happyhouse.model.service.MemberServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/naver")
@CrossOrigin("*")
@RequiredArgsConstructor
public class NaverController {
	
	/* 네이버 토큰 관련 정보
	 * access_token은 발급 받은 후 12시간-24시간(정책에 따라 변동 가능)동안 유효합니다.
	 * refresh token은 한달간 유효하며, refresh token 만료가 1주일 이내로 남은 시점에서 사용자 토큰 갱신 요청을 하면\
	 * 갱신된 access token과 갱신된 refresh token이 함께 반환됩니다.
	 */
	
	private final MemberServiceImpl memberService;
	
	private final JwtService jwtService; //내부적으로 토큰을 발행
	
	private static final Logger logger = LoggerFactory.getLogger(NaverController.class);
	
	//모든 변수를 private final: 값이 변경되지 않고 숨김
	private final String NAVER_PROFILE_URL = "https://openapi.naver.com/v1/nid/me";
	
	@PostMapping("/") 
	public ResponseEntity<?> register(@RequestBody String access_token, HttpSession session) throws Exception {
		
		return getNaverProfile(access_token, session);
		
	}
	
	//  프로필 정보 받아주기(네이버 공식 문서 예시): access_token, refresh_token, token_type, expires_in
	private ResponseEntity<?> getNaverProfile(String access_token, HttpSession session) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		
        String header = "Bearer " + access_token; // 1. access token 받기
        
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Authorization", header);
        String responseBody = get(NAVER_PROFILE_URL, requestHeaders);
        
        JSONParser parser = new JSONParser();
		JSONObject jsonObj = (JSONObject) parser.parse(responseBody); // 데이터: resultcode, message, response
		JSONObject jsonObj2 =  (JSONObject) jsonObj.get("response"); // response: id, email
		
		int account_exist = memberService.platformIdCheck((String)jsonObj2.get("id")); //이미 아이디가 있는지 확인
        
		//platform_id 값이 존재하지 않는다면 부분 정보를 가지고 회원가입으로 이동
        if(account_exist == 0) {
        	logger.info("네이버 회원가입 진행: {}", jsonObj2.toString()); //userid를 비워서 보내기 때문에 프론트엔드단에서 회원가입으로 이동
        	
        	MemberDto member = new MemberDto();
        	member.setPlatform_id((String) jsonObj2.get("id"));
        	member.setPlatform("naver");
        	member.setEmail((String) jsonObj2.get("email"));
        	member.setRefresh_token((String) jsonObj2.get("refresh_token"));
        	
        	resultMap.put("userInfo", member);
            resultMap.put("message", "REGISTER"); //회원가입시 표기
        	
        	System.out.println("아이디, 닉네임 지정 페이지로 이동하기");
        	
        	return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK); // 회원가입: PARTIAL_CONTENT
        }
		
		//만약 회원값이 존재한다면 정보를 로드해서 가져오기	
        logger.info("네이버 로그인 진행: {}", jsonObj2.toString());
        
        MemberDto member = memberService.platformLogin((String)jsonObj2.get("id"));
        
        if(member != null) { //로그인 성공시
        	String userToken = jwtService.createToken(member.getUserid().toString(), (60*1000*15));
			logger.info("네이버 유저에게 내부 토큰 발행: {} ", userToken);
			resultMap.put("userInfo", member);
			resultMap.put("message", "LOGIN"); //로그인시 표기
			resultMap.put("access_token", userToken); //세션으로 토큰을 저장해준다.
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK); // 로그인 성공: OK
        }
        
        resultMap.put("message", "FAIL"); //로그인 실패시
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK); // 로그인 실패: NOT_ACCEPTABLE
	}

	 private static String get(String apiUrl, Map<String, String> requestHeaders){
	        HttpURLConnection con = connect(apiUrl);
	        try {
	            con.setRequestMethod("GET");
	            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
	                con.setRequestProperty(header.getKey(), header.getValue());
	            }


	            int responseCode = con.getResponseCode();
	            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
	                return readBody(con.getInputStream());
	            } else { // 에러 발생
	                return readBody(con.getErrorStream());
	            }
	        } catch (IOException e) {
	            throw new RuntimeException("API 요청과 응답 실패", e);
	        } finally {
	            con.disconnect();
	        }
	 }
	 private static HttpURLConnection connect(String apiUrl){
	        try {
	            URL url = new URL(apiUrl);
	            return (HttpURLConnection)url.openConnection();
	        } catch (MalformedURLException e) {
	            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
	        } catch (IOException e) {
	            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
	        }
	    }


    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();


            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    } 
}
