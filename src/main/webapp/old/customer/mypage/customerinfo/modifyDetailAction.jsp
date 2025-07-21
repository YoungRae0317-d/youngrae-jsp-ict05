<%@page import="pj.mvc.jsp.dto.CustomerDTO"%>
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
<link rel="stylesheet" href="/jsp_pj_ict05/resources/css/customer/join.css">
<!-- js -->
<script src="https://kit.fontawesome.com/7e22bb38b7.js" crossorigin="anonymous"></script>

<!-- (3-4). 자바스크립트소스 연결 -->
<!-- defer : html 을  다 읽은 후에 자바스크립트를 실행한다. 페이지가 모두 로드된 후에 해당 외부 스크립트 실행 -->
<script src="/jsp_pj_ict05/resources/js/common/main.js" defer></script>

<script src="/jsp_pj_ict05/resources/js/customer/modify.js" defer></script>
</head>
<body>
	<div class="wrap">
		<!-- header 시작 -->
		<%@ include file="../../../common/header.jsp" %>
		<!-- header 끝 -->
		
		<!-- 컨텐츠 시작 -->
		<div id="container">
			<div id="contents">
				<!-- 상단 중앙1 시작 -->
				<div id="section1">
					<h1 align="center">회원정보 상세페이지</h1>
				</div>
				<!-- 상단 중앙2 시작 -->
				<div id="section2">
					<div id="s2_inner">
						<div class="join">
							<form name="modifyform" action="modifyCustomerAction.do" method="post"
								onsubmit="return modifyCheck()">
								
								<%
								int selectCnt = (Integer)request.getAttribute("selectCnt");
								CustomerDTO dto = (CustomerDTO)request.getAttribute("dto");
								
								if(selectCnt == 1){
								%>	
									<table>
										<tr>
											<th> 아이디 </th>
											<td>
												<%= dto.getUser_id() %>
											</td>
										</tr>
										<tr>
											<th> * 비밀번호 </th>
											<td>
												<input type="password" class="input" name="user_password" size="20" placeholder="공백없이 20자 이내로 작성" required>
											</td>
										</tr>
										<tr>	
											<th> * 비밀번호(확인) </th>
											<td>
												<input type="password" class="input" name="re_password" size="20" placeholder="공백없이 20자 이내로 작성" required>
											</td>
										</tr>	
										<tr>	
											<th> * 이름 </th>
											<td>
												<input type="text" class="input" name="user_name" size="20" value="<%=dto.getUser_name() %>" required>
											</td>
										</tr>	
										<tr>
											<th> * 생년월일 </th>
											<td>
												<input type="date" class="input" name="user_birthday" size="10" value="<%=dto.getUser_birthday() %>" placeholder="공백없이 5자 이내로 작성" required>
											</td>
										</tr>	
										<tr>
											<th> * 주소 </th>
											<td>
												<input type="text" class="input" name="user_address" size="50" value="<%=dto.getUser_address() %>">
											</td>
										</tr>
										<tr>
											<th> 연락처 </th>
											<%
												if(dto.getUser_hp()==null){
											%>
											<td>
												<input type="text" class="input" name="user_hp1" size="3" style="width:50px">
												-
												<input type="text" class="input" name="user_hp2" size="4" style="width:70px">
												-
												<input type="text" class="input" name="user_hp3" size="4" style="width:70px">
											</td>
											<%		
												}else{
													String hp = dto.getUser_hp();
													String[] hpArr = hp.split("-");
											%>
											<td>
												<input type="text" class="input" name="user_hp1" size="3" value="<%=hpArr[0] %>" style="width:50px">
												-
												<input type="text" class="input" name="user_hp2" size="4" value="<%=hpArr[1] %>" style="width:70px">
												-
												<input type="text" class="input" name="user_hp3" size="4" value="<%=hpArr[2] %>"style="width:70px">
											</td>
											<%		
												}
											%>
										</tr>
										<tr>
											<th> * 이메일 </th>
											<%		
													String email = dto.getUser_email();
													String[] emailArr = email.split("@");
											%>
											<td>
												<input type="text" class="input" name="user_email" size="20" value="<%= emailArr[0] %>" style="width:100px" required>
												@
												<input type="text" class="input" name="user_email2" size="20" value="<%= emailArr[1] %>" style="width:100px" required>
												<select class="input" name="user_email3" style="width:100px" onchange="detail_selectEmailChk()">
													<option value="0">직접입력</option>
													<option value="naver.com">naver.com</option>
													<option value="google.com">google.com</option>
													<option value="nate.com">nate.com</option>
													<option value="gmail.com">gmail.com</option>
												</select>
											</td>
										</tr>
										<tr>
											<td colspan="2" style="border-bottom:none">
											<br>
											<div align="right">
												<input class="inputButton" type="submit" value="회원수정">
												<input class="inputButton" type="reset" value="초기화">
												<input class="inputButton" type="button" value="가입취소" onclick="window.location='main.do'">
											</div>
										</tr>
									</table>
								<%
								}else{
								%>	
									<script type="text/javascript">
										alert("인증실패!!");
										window.location="/jsp_pj_ict05/modifyCustomer.do";
									</script>
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
		<%@ include file="../../../common/footer.jsp" %>
		<!-- footer 끝 -->
	</div>
</body>
</html>