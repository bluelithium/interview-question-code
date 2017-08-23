package com.jwtk.znlover.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Validator;
import org.owasp.esapi.errors.IntrusionException;
import org.owasp.esapi.errors.ValidationException;
import org.owasp.esapi.reference.validation.HTMLValidationRule;
import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jwtk.znlover.domain.JwtkMessage;
import com.jwtk.znlover.repository.JwtkMessageRepository;


@Controller
public class IndexContoller {

	private static final String INDEX_PAGE = "index";

	@Autowired
	private JwtkMessageRepository jwtkMessageRepository;

	@RequestMapping("/")
	public String index( Model model, HttpServletResponse response) {
		
		Cookie cookie = new Cookie("jwtk", "011001110202423424214234");
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
		
		List<JwtkMessage> msg = jwtkMessageRepository.findAllByOrderByDateDesc();
		model.addAttribute("msg", msg);
		
		return INDEX_PAGE;
	}
	
	@PostMapping("/putMessage")
	@ResponseBody
	public String putMessage(@RequestParam("message") String message, HttpServletRequest request) {
		JwtkMessage jwtkMessage = new JwtkMessage();
		
		String safeMessage = ESAPI.encoder().encodeForHTML(message);
		jwtkMessage.setMessage(safeMessage);
		
		jwtkMessage.setAccessIP(getIpAddr(request));
		jwtkMessage.setDate(new Date());
		jwtkMessageRepository.saveAndFlush(jwtkMessage);
		
		return "{status:ok}";
	}
	
	@GetMapping("/getMessage")
	@ResponseBody
	public String getMessages() {
		List<JwtkMessage> msg = jwtkMessageRepository.findAllByOrderByDateDesc();
		return JSON.toJSONString(msg);
	}
	
//	@PostMapping("/putArticle")
//	@ResponseBody
//	public String putArticle(@RequestParam("artical") String artical, HttpServletRequest request) throws ScanException, PolicyException, IOException {
//		InputStream resourceStream = ESAPI.securityConfiguration().getResourceStream("antisamy-esapi.xml");
//		Policy policy = Policy.getInstance(resourceStream);
//		
//		AntiSamy antiSamy = new AntiSamy();
//		CleanResults cr = antiSamy.scan(artical, policy);
//		String clearHTML = cr.getCleanHTML();
//		
//		return "{status:ok}";
//	}
	
	@PostMapping("/putArticle")
	@ResponseBody
	public String putArticle(@RequestParam("artical") String artical, HttpServletRequest request) throws ValidationException {
		
        String safeArtical = ESAPI.validator().getValidSafeHTML("htmlInput", artical, 1000, true);
        
		return "{status:ok}";
	}

	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} else {
			return request.getRemoteAddr();
		}
	}
}
