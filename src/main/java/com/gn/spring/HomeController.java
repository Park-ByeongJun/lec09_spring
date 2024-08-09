package com.gn.spring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@EnableAspectJAutoProxy
@Controller
public class HomeController {

	// 클라이언트가 요청한 서비스 주소와 메소드를 연결
	// http://localhost:8090
	// http://localhost:8090/ 자바 인식 다름
	
	private static final Logger LOGGER = LogManager.getLogger(HomeController.class);
	
	@RequestMapping(value= {"","/"},method=RequestMethod.GET)
	public String home() {
		// /WEB-INF/views/home.jsp 와 같음
		return "home";
	}
}