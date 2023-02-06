<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.List" %>
<%@ page import = "com.springlab.member.MemberDTO" %>
<%
List<MemberDTO> memberList = (List) session.getAttribute("memberList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>회원정보 목록 출력 페이지</h1>
	
		<form action="getMemberList.jsp" meghod="post">
		<table border="1" cellspacing="0" cellpadding="0" width="1300">
			<tr>
				<td> <select name="searhCondition">
					<option value="TITLE">이름</option>
					<option value="TITLE">비밀번호</option>
					</select>
					<input type="text" name="searchkeyword"/>
					<input type="submit" value="검색"/>
				</td>
			</tr>
		</table>
		
		<!-- 서버에서 담은 List에 저장된 DTO를 끄집어내서 출력하는 테이블 -->
		<table border="1" cellspacing="0" cellpadding="0" width="700">
			<tr>
				<th bgcolor="orange" width="100">번호</th>
				<th bgcolor="orange" width="200">아이디</th>
				<th bgcolor="orange" width="150">비밀번호</th>
				<th bgcolor="orange" width="150">이름</th>
				<th bgcolor="orange" width="100">이메일</th>
				<th bgcolor="orange" width="200">나이</th>
				<th bgcolor="orange" width="150">몸무게</th>
				<th bgcolor="orange" width="150">회원 등록 날짜</th>
				<th bgcolor="orange" width="100">회원 로그인 횟수</th>
			</tr>
			
			<!-- tr을 for문으로 루프돌려 List의 DTO 값을 끄집어내서 출력 -->
			<%
			for(MemberDTO dto : memberList){
						// 향상된 for문	
						// boardList의 index를 dto변수에 저장
						// dto의 값을 아래에 저장 (getter를 사용해서) (index0 ~ 끝까지)
			%>
			<tr>
				<td align="center"><%= dto.getIdx() %></td>
				<td>
					<!-- jsp로 요청하면 안되고 do요청(controller요청)으로 해야한다. -->
					<a href="getMember.do?idx=<%= dto.getIdx()%>">
						<%= dto.getId() %>
					</a>
				</td>
				<td align="center"><%= dto.getPass() %></td>
				<td align="center"><%= dto.getName() %></td>
				<td align="center"><%= dto.getEmail() %></td>
				<td align="center"><%= dto.getAge() %></td>
				<td align="center"><%= dto.getWeight() %></td>
				<td align="center"><%= dto.getRegdate() %></td>
				<td align="center"><%= dto.getCnt() %></td>
			</tr>
			
			<%
				}
			%>
		</table>
	
		<p/>
		<a href="insertMember.jsp">회원정보 등록</a>

	</form>
	
</body>
</html>