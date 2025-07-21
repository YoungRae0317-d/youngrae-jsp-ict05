package pj.mvc.jsp.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pj.mvc.jsp.service.CustomerServiceImpl;

// http://localhost/jsp_pj_ict05/main.do <-index.jsp 시작
@WebServlet("*.do")
public class CustomerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public CustomerController() {
        super();
    }

    // 1단계. 웹브라우저가 전송한 HTTP 전송을 받음
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		action(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void action(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		//2단계. 클라이언트 요청 분석
		
		//한글 안깨지게 처리
		request.setCharacterEncoding("UTF-8");
		
		// url ===>http://localhost/jsp_pj_ict05/main.do
		// uri ===>				   /jsp_pj_ict05/main.do
		// contextPath ===> 	   /jsp_pj_ict05
		// 최종 url ===> 							/main.do
		String uri =request.getRequestURI();
		String contextPath = request.getContextPath();	//플젝명
		String url = uri.substring(contextPath.length());	// uri.substring(시작위치);
		String viewPage="";
		CustomerServiceImpl service = new CustomerServiceImpl();
		
		
		
		// [첫페이지] -----------------------
		if(url.equals("/main.do") || url.equals("/*.do")) {
			System.out.println("<<<url ==> /main.do>>>");
			viewPage="/common/main.jsp";
		}
		
		// [회원가입] -----------------------
		// 회원가입 페이지
		else if(url.equals("/join.do")) {
			System.out.println("url ==> joinAction.do");
			
			viewPage="/customer/join/join.jsp";
		}
		
		// id 중복확인 버튼 클릭시------------
		
		else if(url.equals("/idConfirmAction.do")) {
			service.idConfirmAction(request, response);
			
			viewPage = "/customer/join/idConfirmAction.jsp";
		}
		
		//회원가입 버튼 클릭시
		else if(url.equals("/joinAction.do")) {
			viewPage="/customer/join/joinAction.jsp";
			
			service.signInAction(request, response);
		}
		
		// [로그인]
		else if(url.equals("/login.do")) {
			viewPage="/customer/login/login.jsp";
		}
		
		//로그인 처리 페이지
		else if(url.equals("/loginAction.do")) {
			
			service.loginAction(request, response);
			viewPage = "/customer/login/loginAction.jsp";
			
		}
		
		//로그아웃 처리
		else if(url.equals("/logout.do")) {
			System.out.println("<<<url==>/loginAction.do");
			
			//세션삭제
			request.getSession().invalidate();
			
			viewPage="/common/main.jsp";
		}
		// [회원탈퇴]-----------------------
		//회원탈퇴 - 인증화면
		else if(url.equals("/deleteCustomer.do")) {
			System.out.println("<<<url==>/deleteCustomer.do");
			
			
			viewPage="/customer/mypage/customerinfo/deleteCustomer.jsp";
		}
		//회원탈퇴 처리
		else if(url.equals("/deleteCustomerAction.do")) {
			System.out.println("<<<url==>/deleteCustomerAction.do");
			
			service.deleteCustomerAction(request, response);
			viewPage="/customer/mypage/customerinfo/deleteCustomerAction.jsp";
		}
		
		
		// [회원수정]-----------------------
		// 회원수정 - 인증화면
		else if(url.equals("/modifyCustomer.do")) {
			System.out.println("<<<url==>/modifyCustomer.do");
			
			
			viewPage="/customer/mypage/customerinfo/modifyCustomer.jsp";
		}
		
		//회원수정 - 상세페이지
		else if(url.equals("/modifyDetailAction.do")) {
			System.out.println("<<<url==>/modifyDetailAction.do");
			
			service.modifyDetailAction(request, response);
			
			viewPage="/customer/mypage/customerinfo/modifyDetailAction.jsp";
		}
		
		//회원수정 -수정처리
		else if(url.equals("/modifyCustomerAction.do")) {
			System.out.println("<<<url==>/modifyCustomerAction.do");
			
			
			service.modifyCustomerAction(request, response);
			viewPage="/customer/mypage/customerinfo/modifyCustomerAction.jsp";
			
		}
		
		
		
		
		//RequestDispatcher: 서블릿 또는 JSP 요청을 받은후에 다른 컴포넌트로 요청을 위임
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
	}
}
