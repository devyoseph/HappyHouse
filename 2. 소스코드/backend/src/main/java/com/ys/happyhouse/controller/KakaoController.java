package com.ys.happyhouse.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ys.happyhouse.model.MemberDto;
import com.ys.happyhouse.model.service.JwtService;
import com.ys.happyhouse.model.service.MemberServiceImpl;

import io.swagger.models.Model;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/kakao")
@CrossOrigin("*")
@RequiredArgsConstructor
public class KakaoController {
	//모든 변수를 private final: 값이 변경되지 않고 숨김
	private final String KAKAO_BASE_URL = "https://kauth.kakao.com/oauth/authorize";
	private final String KAKAO_GET_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
	private final String KAKAO_PROFILE_URL = "https://kapi.kakao.com/v2/user/me";
	private final String KAKAO_CLIENT_ID = "502d0d9b099ab039b2b06ad17afc71e9";
	private final String REDIRECT_URI = "http://localhost:80/kakao/"; //경로 확인할 것
	
	private static final Logger logger = LoggerFactory.getLogger(KakaoController.class);
	
	private final MemberServiceImpl memberService;
	private final JwtService jwtService;

	
	// 카카오 연동정보 조회
	@PostMapping("/")
	public ResponseEntity<?> register(@RequestParam("code") String code, HttpSession session) throws Exception {
		logger.info("code : {}", code);
                
        Map<String, Object> resultMap = getUserInfo(getAccessToken(code));
       
      //데이터 내려주기: Http 신호에 따라서 로그인/회원가입 구현
        if(resultMap.get("message").equals("LOGIN")) {
        	String userToken = jwtService.createToken((String)resultMap.get("userid"), (60*1000*15));
			logger.info("카카오 유저에게 내부 토큰 발행: {} ", userToken);
        	resultMap.put("access_token", userToken); //세션으로 토큰을 저장해준다.
        	
        	return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        
        }else if(resultMap.get("message").equals("REGISTER")) {
        	return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK); 
        }else {
        	return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK); 
        }
        
        // Vue 에서는 message를 분석해 이동한다.
	}
	
    //토큰발급
	public Map<String, String> getAccessToken (String authorize_code) {
        String access_Token = "";
        String refresh_Token = "";
        String id_Token = "";

        try {
            URL url = new URL(KAKAO_GET_TOKEN_URL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //  URL연결은 입출력에 사용 될 수 있고, POST 혹은 PUT 요청을 하려면 setDoOutput을 true로 설정해야함.
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //	POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id="+KAKAO_CLIENT_ID);  //발급받은 key
            sb.append("&redirect_uri="+REDIRECT_URI);  //설정해 놓은 경로
            sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();

            logger.info("responseCode(200이면 성공) : {}", conn.getResponseCode());

            //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            logger.info("response body : {}" + result);

            //    Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            
            id_Token = element.getAsJsonObject().get("id_token").getAsString();
            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();
            
            logger.info("id_token : {}" + id_Token);
            logger.info("access_token : {}" + access_Token);
            logger.info("refresh_token : {}" + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        	
        	Map<String, String> tokens = new HashMap<>();
        	tokens.put("id", id_Token);
        	tokens.put("access_token", access_Token);
        	tokens.put("refresh_token", refresh_Token);
        	
        return tokens;
    }
	
    //유저정보조회
    public Map<String, Object> getUserInfo (Map<String, String> tokens) throws Exception {

        //  정보를 담아 리턴할 Dto, Map 선언
    	MemberDto member = new MemberDto();
    	Map<String, Object> resultMap = new HashMap<String, Object>();
 
        try {
            URL url = new URL(KAKAO_PROFILE_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            //    요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + tokens.get("access_token"));

            logger.info("responseCode(200이면 정상) : {}" + conn.getResponseCode());

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            String id = tokens.get("id"); // access_token과 같이 발급된 id_token
            //JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
            
            //String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String email = kakao_account.getAsJsonObject().get("email").getAsString();
            
            int account_exist = memberService.platformIdCheck(id);
            
          //platform_id 값이 존재하지 않는다면 부분 정보를 가지고 회원가입으로 이동
            if(account_exist == 0) {
            	logger.info("카카오 회원가입 진행: {}", email); //userid를 비워서 보내기 때문에 프론트엔드단에서 회원가입으로 이동
            	
            	member.setPlatform_id(id);
            	member.setPlatform("kakao");
            	member.setEmail(email);
            	member.setRefresh_token(tokens.get("refresh_token"));
            	
            	resultMap.put("userInfo", member);
                resultMap.put("message", "REGISTER"); //회원가입시 표기
            	
            }else if(account_exist == 1) {
            	logger.info("카카오 로그인 진행: {}", email); 
            	resultMap.put("userid", id);
                resultMap.put("message", "LOGIN"); //로그인시 표기
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return resultMap;
    }
 }