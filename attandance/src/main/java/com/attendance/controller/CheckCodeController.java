package com.attendance.controller;

/**
 * 获取图片验证码
 */

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.attendance.pojo.RandomCode;
import com.mysql.cj.api.Session;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CheckCodeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@RequestMapping(value = "Login")
	public String login(){
		return "/Login";
	}

	@RequestMapping(value = "service.do")
	protected void doGet(
			HttpServletRequest request,
			HttpServletResponse response
	)throws ServletException, IOException {
		response.setContentType("image/jpeg");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		RandomCode rc = new RandomCode();
		try {
			rc.getRandcode(request, response);
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	@RequestMapping(value = "abc.do")
	protected void abc(
			HttpSession session){
		String rand = session.getAttribute("Rand").toString();
session.setAttribute("rand",rand);
//		System.out.println(session.getAttribute("Rand").toString());
	}

}
