<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- 반응형 웹 -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>main</title>

<!-- css -->
<link rel="stylesheet" href="/jsp_pj_ict05/resources/css/common/header.css">
<link rel="stylesheet" href="/jsp_pj_ict05/resources/css/common/footer.css">
<link rel="stylesheet" href="/jsp_pj_ict05/resources/css/customer/login.css">
<!-- js -->
<script src="https://kit.fontawesome.com/7e22bb38b7.js" crossorigin="anonymous"></script>

<!-- (3-4). 자바스크립트소스 연결 -->
<!-- defer : html 을  다 읽은 후에 자바스크립트를 실행한다. 페이지가 모두 로드된 후에 해당 외부 스크립트 실행 -->
<script src="/jsp_pj_ict05/resources/js/customer/login.js" defer></script>
</head>
<body>
	<div class="wrap">
		<!-- header 시작 -->
		<%@ include file="../../common/header.jsp" %>
		<!-- header 끝 -->
		
		<!-- 컨텐츠 시작 -->
		<div id="container">
			<div id="contents">
				<!-- 상단 중앙1 시작 -->
				<div id="section1">
					<h1 align="center">로그인</h1>
				</div>
				<!-- 상단 중앙2 시작 -->
				<div id="section2">
					<div id="s2_inner">
						<div class="join">
							<form name="loginform" action="loginAction.do" method="post"
								onsubmit="return loginCheck()">
								
								<%
								String sessionID = (String)request.getSession().getAttribute("sessionID");
								
								//세션이 없는 경우 : 로그인 실패
								if(sessionID == null){
								%>
									<script type="text/javascript">
									alert("아이디와 비밀번호가 일치하지 않습니다.!!");
									</script>
									
									<table>
										<tr>
											<th> * 아이디 </th>
											<td>
												<input type="text" class="input" name="user_id" size="20" placeholder="공백없이 20자 이내로 작성" required autofocus>
											</td>
										</tr>
										<tr>
											<th> * 비밀번호 </th>
											<td>
												<input type="password" class="input" name="user_password" size="20" placeholder="공백없이 20자 이내로 작성" required>
											</td>
										</tr>
										<tr>
											<td colspan="2" style="border-bottom:none">
											<br>
											<div align="right">
												<input class="inputButton" type="submit" value="로그인">
												<input class="inputButton" type="reset" value="초기화">
												<input class="inputButton" type="button" value="회원가입" onclick="window.location='join.do'">
											</div>
										</tr>
									</table>
								<%	
								}
								else{
								%>
									<script type="text/javascript">
									alert("로그인 성공입니다.");
									</script>
									
									<table width="500px">
										
										<tr>
											<th colspan="2" align="center">
												<span style="color: blue"><b><%=sessionID %></b></span>님 <br>로컬호스트에 오신것을 진심으로 환영합니다.^^
											 </th>
										</tr>
										<tr>
											<td colspan="2" style="border-bottom:none">
											<br>
											<div align="right">
												<input class="inputButton" type="button" value="회원수정" onclick="window.location='/jsp_pj_ict05/modifyCustomer.do'">
												<input class="inputButton" type="reset" value="회원탈퇴" onclick="window.location='/jsp_pj_ict05/deleteCustomer.do'">
												<input class="inputButton" type="button" value="로그아웃" onclick="window.location='/jsp_pj_ict05/logout.do'">
											</div>
										</tr>
									</table>
								<%
								}
								%>
								
								
							</form>
						</div>
					</div>
				</div>
				<!-- 상단 중앙2 끝 -->
			</div>
		</div>
		<!-- 컨텐츠 끝 -->
		
		<!-- footer 시작 -->
		<%@ include file="../../common/footer.jsp" %>
		<!-- footer 끝 -->
	</div>
</body>
</html>