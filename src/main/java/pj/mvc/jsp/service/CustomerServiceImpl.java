package pj.mvc.jsp.service;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pj.mvc.jsp.dao.CustomerDAO;
import pj.mvc.jsp.dao.CustomerDAOImpl;
import pj.mvc.jsp.dto.CustomerDTO;

public class CustomerServiceImpl implements CustomerService{

	
	
	// ID 중복확인 처리
	@Override
	public void idConfirmAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("CustomerServiceImpl - idConfirmAction()");
		
		//3단계.화면에서 입력받은 값을 가져온다.
		String strId = request.getParameter("user_id");
		
		//4단계. 싱글톤 방식으로 DAO 객체 생성, 다형성 적용
		CustomerDAO dao = CustomerDAOImpl.getInstance();
		
		//5단계. 아이디 중복확인 처리
		int selectCnt = dao.useridCheck(strId);
		
		//6단계. jsp로 처리결과 전달
		request.setAttribute("selectCnt", selectCnt);
		request.setAttribute("strId", strId);
		
	}

	//회원가입 처리
	@Override
	public void signInAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("CustomerServiceImpl - signInAction()");
		
		//3단계.화면에서 입력받은 값을 가져와서 DTO의 setter를 통해 값 전달
		
		
		//DTO 생성 -> setter -> 멤버변수 
		CustomerDTO dto = new CustomerDTO();
		dto.setUser_id(request.getParameter("user_id"));
		dto.setUser_password(request.getParameter("user_password"));
		dto.setUser_name(request.getParameter("user_name"));
		dto.setUser_birthday(Date.valueOf(request.getParameter("user_birthday")));
		dto.setUser_address(request.getParameter("user_address"));
		
		// hp은 필수가 아니므로 null값이 들어올 수 있으므로 값이 존재할 때만 받아온다.(010 1234 5678)
		String hp = "";
		String hp1 = request.getParameter("user_hp1");
		String hp2 = request.getParameter("user_hp2");
		String hp3 = request.getParameter("user_hp3");
		if(!hp1.equals("")&&!hp2.equals("")&&!hp3.equals("")) {
			hp = hp1+"-" +hp2+"-"+hp3;
		}
		dto.setUser_hp(hp);
		
		
		String email1 = request.getParameter("user_email1");
		String email2 = request.getParameter("user_email2");
		String email = email1+"@"+email2;
		dto.setUser_email(email);
		
		//등록일 => sysdate 안넣고 그냥 내가 한다할때.
		//아래문자 생략시 sysdate로 들어감.
		//dto.setUser_regdate(new Timestamp(System.currentTimeMillis()));
		
		//4단계. 싱글톤 방식으로 DAO 객체 생성, 다형성 적용
		//CustomerDAOImpl dao = new CustomerDAOImpl();
		CustomerDAO dao = CustomerDAOImpl.getInstance();
		
		//5단계. 회원가입 처리
		int insertCnt = dao.insertCustomer(dto);
		
		//6.단계.jsp로 처리결과 전달
		request.setAttribute("insertCnt", insertCnt);
	}

	//로그인 처리/ 회원정보 인증(수정, 탈퇴)
	@Override
	public void loginAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("CustomerServiceImpl-loginAction()");
		
		//3단계.화면에서 입력받은 값을 가져온다.
		String strId = request.getParameter("user_id");
		String strPassword = request.getParameter("user_password");
		
		
		
		//4단계. 싱글톤 방식으로 DAO 객체 생성, 다형성 적용
		//CustomerDAOImpl dao = new CustomerDAOImpl();
		CustomerDAO dao = CustomerDAOImpl.getInstance();
		
		//5단계. 로그인 처리
		int selectCnt = dao.idPasswordChk(strId, strPassword);
		
		// 로그인 성공시 세션ID를 설정(중요)
		if(selectCnt == 1) {
//			HttpSession session = request.getSession();
//			session.setAttribute("sessionID", strId);
			request.getSession().setAttribute("sessionID", strId);
			
		}
		
	}

	//회원정보 인증처리 및 탈퇴처리
	@Override
	public void deleteCustomerAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

	//회원정보 인증처리 및 상세페이지 조회
	@Override
	public void modifyDetailAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

	//회원정보 수정처리
	@Override
	public void modifyCustomerAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

}
