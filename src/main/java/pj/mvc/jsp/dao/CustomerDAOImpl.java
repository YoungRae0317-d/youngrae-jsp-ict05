package pj.mvc.jsp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import pj.mvc.jsp.dto.CustomerDTO;

public class CustomerDAOImpl implements CustomerDAO{
	
	//커넥션 풀 객체를 보관
	DataSource dataSource = null;
	//싱글톤 객체 생성
	static CustomerDAOImpl instance = new CustomerDAOImpl();
	public static CustomerDAOImpl getInstance() {
		if(instance==null) {
			instance=new CustomerDAOImpl();
		}
		return instance;
	}
	//디폴트 생성자
	//커넥션풀(DBCP : DataBase Connection Pool 방식) - context.xml에 설정
	private CustomerDAOImpl() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/jsp_pj_ict05");
		}catch(NamingException e){
			e.printStackTrace();
		}
		
	}
	
	

	// ID 중복확인 처리
	@Override
	public int useridCheck(String strId) {
		System.out.println("CustomerDAOImpl - useridCheck()");
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		int selectCnt = 0;
		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM mvc_customer_tbl "
					+ "WHERE user_id = ?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, strId);
			
			rs = pstmt.executeQuery();
			
			//존재하면
			if(rs.next()) {
				selectCnt=1;
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
				if(rs != null) rs.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				
			}
		}
		
		return selectCnt;
	}

	//회원가입 처리
	@Override
	public int insertCustomer(CustomerDTO dto) {
		System.out.println("CustomerDAOImpl - insertCustomer()");
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		int insertCnt=0;
		
		try {
			//1.DB연결 => 데이터베이스 커넥션 생성
			conn = dataSource.getConnection();
			
			//2. SQL 작성 => PrepareStatement 작성
			String sql = "INSERT INTO mvc_customer_tbl(user_id, user_password, user_name, user_birthday, user_address, user_hp, user_email, user_regdate) "
					+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUser_id());
			pstmt.setString(2, dto.getUser_password());
			pstmt.setString(3, dto.getUser_name());
			pstmt.setDate(4, dto.getUser_birthday());
			pstmt.setString(5, dto.getUser_address());
			pstmt.setString(6, dto.getUser_hp());
			pstmt.setString(7, dto.getUser_email());
			pstmt.setTimestamp(8, dto.getUser_regdate());
			
			//3. 실행
			insertCnt = pstmt.executeUpdate();
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null)pstmt.close();
				if(conn != null)conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return insertCnt;
	}

	//로그인 처리/ 회원정보 인증(수정, 탈퇴)
	@Override
	public int idPasswordChk(String strId, String strPassword) {
		
		System.out.println("CustomerDAOImpl - idPasswordChk()");
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		int selectCnt = 0;
		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM mvc_customer_tbl "
					+ "WHERE user_id = ? AND user_password = ?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, strId);
			pstmt.setString(2, strPassword);
			rs = pstmt.executeQuery();
			
			//존재하면
			if(rs.next()) {
				selectCnt=1;
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
				if(rs != null) rs.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				
			}
		}
		
		return selectCnt;
	}

	//회원정보 인증처리 및 탈퇴처리
	@Override
	public int deleteCustomer(String strId) {
		return 0;
	}

	//회원정보 인증처리 및 상세페이지 조회
	@Override
	public CustomerDTO getCustomerDetail(String strId) {
		return null;
	}

	//회원정보 수정처리
	@Override
	public int updateCustomer(CustomerDTO dto) {
		return 0;
	}

}
