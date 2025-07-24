<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="/common/setting.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- 반응형 웹 -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>상세페이지</title>
<!-- css -->
<link rel="stylesheet" href="${path}/resources/css/common/header.css">
<link rel="stylesheet" href="${path}/resources/css/common/footer.css">
<link rel="stylesheet" href="${path}/resources/css/admin/ad_boardList.css">
<!-- js -->
<script src="https://kit.fontawesome.com/7e22bb38b7.js" crossorigin="anonymous"></script>

<!-- (3-4). 자바스크립트소스 연결 -->
<!-- defer : html 을  다 읽은 후에 자바스크립트를 실행한다. 페이지가 모두 로드된 후에 해당 외부 스크립트 실행 -->
<script src="${path}/resources/js/common/main.js" defer></script>

<script>
	$(function(){
		$('#btnList').click(function(){
			location.href="${path}/board_list.bc";
		});
		
		$('#btnEdit').click(function(){
			document.detailForm.action="${path}/password_chkAction.bc";
			document.detailForm.submit();
		});
	});
</script>

</head>
<body>
	<div class="wrap">
		<!-- header 시작 -->
		<%@ include file="/common/header.jsp" %>
		<!-- header 끝 -->
		
		<!-- 컨텐츠 시작 -->
		<div id="container">
			<div id="contents">
				<!-- 상단 중앙1 시작 -->
				<div>
					<h1 align="center">게시판 상세페이지</h1>
				</div>
				<!-- 상단 중앙1 종료 -->
				
				<!-- 상단 중앙2 시작 -->
				<div id="section2">
					<!-- 좌측메뉴 시작 -->
					<%@ include file="/admin/common/leftMenu.jsp" %>
					<!-- 좌측메뉴 종료 -->
					
					<!-- 우측화면 시작 -->
					<div id="right">
						<div class="table_div">
							<form name="detailForm" method="post">
								<table>
									<tr>
										<th style="width:200px">글번호</th>
										<td style="width:200px; text-align:center">${dto.b_num}</td>
										
										<th style="width:200px">조회수</th>
										<td style="width:200px; text-align:center">${dto.b_readCnt}</td>
									</tr>
									<tr>
										<th style="width:200px">작성자</th>
										<td style="width:200px; text-align:center">${dto.b_writer}</td>
										
										<th style="width:200px">비밀번호</th>
										<td style="width:200px; text-align:center">
											<input style="width:200px" type="password" class="input" name="b_password" id="b_password" size="30" placeholder="비밀번호 입력" required autofocus>
											
											<c:if test="${param.message == 'error'}">
												<br><span style="color:red">비밀번호 불일치</span>
											</c:if>
										</td>
									</tr>
									<tr>
										<th style="width:200px">글제목</th>
										<td colspan="3" style="text-align:center">${dto.b_title}</td>
									</tr>
									<tr>
										<th style="width:200px">글내용</th>
										<td colspan="3" style="text-align:center">${dto.b_content}</td>
									</tr>
									<tr>
										<th style="width:200px">작성일</th>
										<td colspan="3" style="text-align:center">${dto.b_regDate}</td>
									</tr>
									<tr>
										<td colspan="4" style="text-align:center">
										<br>
											<!-- 게시글번호 hidden 추가 : input이 없으므로(게시글번호는 입력받지 않으므로 input이 없음) -->
											<input type="hidden" name="hidden_b_num" value="${dto.b_num}">
											<input type="button" class="inputButton" value="수정/삭제" id="btnEdit">
											<input type="button" class="inputButton" value="목록" id="btnList">
										</td>
									</tr>
									
									
								</table>
							</form>
						</div>
					</div>
					<!-- 우측화면 종료 -->
				</div>
				<!-- 상단 중앙2 종료 -->
			</div>
		</div>
		<!-- 컨텐츠 끝 -->
		<!-- footer 시작 -->
		<%@ include file="/common/footer.jsp" %>
		<!-- footer 끝 -->
	</div>
</body>
</html>