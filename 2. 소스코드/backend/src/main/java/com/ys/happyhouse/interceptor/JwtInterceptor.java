package com.ys.happyhouse.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ys.happyhouse.model.service.JwtService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor{
	private static final String HEADER_AUTH = "authorization";
	
	private static final Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);
	
    private final JwtService jwtService;
    
    // 인터셉터 방식을 사용하는 이유: 매번 토큰 내부의 subject에 들어있는 userid로 DB를 조회해서 정보를 가져오는 것은 비효율적이라고 판단
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	final String token = (String) request.getSession().getAttribute("token");
        logger.info("JWT Interceptor Operate: {}", token);
        if(token == null || jwtService.isUsable(token)){
            return true;
        }else{
        	System.out.println("토큰이 만료되어 로그인 화면으로 돌아갑니다.");
        	response.sendRedirect(request.getContextPath()+"/login");
            return false;
        }
    }
}
