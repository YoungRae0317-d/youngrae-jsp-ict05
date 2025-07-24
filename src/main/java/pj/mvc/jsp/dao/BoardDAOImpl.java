package pj.mvc.jsp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import pj.mvc.jsp.dto.BoardCommentDTO;
import pj.mvc.jsp.dto.BoardDTO;

public class BoardDAOImpl implements BoardDAO{

	//커넥션 풀 객체를 보관
		DataSource dataSource = null;
		//싱글톤 객체 생성
		static BoardDAOImpl instance = new BoardDAOImpl();
		public static BoardDAOImpl getInstance() {
			if(instance==null) {
				instance=new BoardDAOImpl();
			}
			return instance;
		}
		//디폴트 생성자
		//커넥션풀(DBCP : DataBase Connection Pool 방식) - context.xml에 설정
		private BoardDAOImpl() {
			try {
				Context context = new InitialContext();
				dataSource = (DataSource)context.lookup("java:comp/env/jdbc/jsp_pj_ict05");
			}catch(NamingException e){
				e.printStackTrace();
			}
			
		}
	
	//게시글 목록
	@Override
	public List<BoardDTO> boardList(int start, int end) {
		System.out.println("BoardDAOImpl - boardList()");
		
		Connection conn = null; //오라클연결
		PreparedStatement pstmt = null;	//sql문장
		ResultSet rs = null;	//select결과
		
		//1. list 생성
		List<BoardDTO> list = new ArrayList<BoardDTO>();
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "SELECT *"
					+ " FROM "
					+ "		(SELECT A.*"
					+ "	 	   , rownum AS rn"
					+ "		FROM "
					+ "			(SELECT * FROM mvc_board_tbl"
					+ "		  		   ORDER BY b_num DESC) A"
					+ "	 )"
					+ "WHERE rn BETWEEN ? AND ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			rs= pstmt.executeQuery();
			
			//데이터가 존재하면
			while(rs.next()) {
				//2.dto 생성
				BoardDTO dto = new BoardDTO();
				//3. dto에 1건의 rs 게시글 정보를 담는다.
				dto.setB_num(rs.getInt("b_num"));
				dto.setB_title(rs.getString("b_title"));
				dto.setB_content(rs.getString("b_content"));
				dto.setB_writer(rs.getString("b_writer"));
				dto.setB_password(rs.getString("b_password"));
				dto.setB_readCnt(rs.getInt("b_readCnt"));
				dto.setB_regDate(rs.getDate("b_regDate"));
				dto.setB_comment_count(rs.getInt("b_comment_count"));
				//4. list에 dto를 추가한다.
				list.add(dto);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}finally {
				
			}
		}
		
		// 5. list를 리턴
		return list;
	}

	//게시글 개수 구하기
	@Override
	public int boardCnt() {
		System.out.println("BoardDAOImpl - boardCnt()");
		
		Connection conn = null; //오라클연결
		PreparedStatement pstmt = null;	//sql문장
		ResultSet rs = null;	//select결과
		int total = 0;
		
		try {
			conn = dataSource.getConnection();
			
			String sql= "SELECT COUNT(*) AS cnt FROM mvc_board_tbl";
					
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				total=rs.getInt("cnt");
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				
			}
			
		}
		
		return total;
	}

	//조회수 증가
	@Override
	public void plusReadCnt(int num) {
		System.out.println("BoardDAOImpl - plusReadCnt()");
		Connection conn = null; //오라클연결
		PreparedStatement pstmt = null; //SQL문장
		
		try {
			conn = dataSource.getConnection();
			String sql="UPDATE mvc_board_tbl SET b_readCnt = b_readCnt+1 WHERE b_num= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				
			}
		}
	}

	//게시글 상세 처리
	@Override
	public BoardDTO getBoardDetail(int num) {
		System.out.println("BoardDAOImpl - getBoardDetail()");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardDTO dto = new BoardDTO();
		
		try {
			conn = dataSource.getConnection();
			String sql ="SELECT * FROM mvc_board_tbl WHERE b_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto.setB_num(rs.getInt("b_num"));
				dto.setB_title(rs.getString("b_title"));
				dto.setB_content(rs.getString("b_content"));
				dto.setB_writer(rs.getString("b_writer"));
				dto.setB_password(rs.getString("b_password"));
				dto.setB_readCnt(rs.getInt("b_readCnt"));
				dto.setB_regDate(rs.getDate("b_regDate"));
				dto.setB_comment_count(rs.getInt("b_comment_count"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
				if(rs!=null)rs.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				
			}
		}
		return dto;
	}

	//게시글 수정삭제 버튼 클릭시 - 비밀번호 인증처리
	@Override
	public int password_chk(int num, String password) {
		System.out.println("BoardDAOImpl - password_chk()");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int selectCnt = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT count(*) AS cnt FROM mvc_board_tbl "
					+ "WHERE b_num=? "
					+ "AND b_password=? ";			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				selectCnt=rs.getInt("cnt");
			}	
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
				if(rs!=null)rs.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
			}
		}
		return selectCnt;
	}

	//게시글 수정 처리
	@Override
	public void updateBoard(BoardDTO dto) {
		System.out.println("BoardDAOImpl - password_chk()");
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			String sql = "UPDATE mvc_board_tbl "
					+ "SET b_password = ? "
					+ "	,b_title= ? "
					+ "	,b_content = ? "
					+ "WHERE b_num= ? ";			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getB_password());
			pstmt.setString(2, dto.getB_title());
			pstmt.setString(3, dto.getB_content());
			pstmt.setInt(4, dto.getB_num());
			
			pstmt.executeUpdate();
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
			}
		}
	}

	//게시글 삭제 처리
	@Override
	public void deleteBoard(int board_num) {
		System.out.println("BoardDAOImpl - deleteBoard()");
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			String sql = "DELETE FROM mvc_board_tbl "
					+ "WHERE b_num = ? ";			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			
			pstmt.executeUpdate();
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
			}
		}
	}

	//게시글 작성 처리
	@Override
	public int insertBoard(BoardDTO dto) {
		System.out.println("BoardDAOImpl - insertBoard()");
		return 0;
	}

	//댓글 작성 처리
	@Override
	public int insertCommnet(BoardCommentDTO dto) {
		System.out.println("BoardDAOImpl - insertCommnet()");
		return 0;
	}

	//댓글 목록
	@Override
	public List<BoardCommentDTO> commentList(int board_num) {
		System.out.println("BoardDAOImpl - commentList()");
		return null;
	}

}
