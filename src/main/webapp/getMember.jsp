<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "com.springlab.member.MemberDTO" %>
<%
//session에 저장된 값을 가지고 온다.
	MemberDTO member = (MemberDTO) session.getAttribute("member");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
	<h1>회원정보 상세 페이지</h1>
	
	<center>
	<h1>글 상세 페이지</h1>
	<a href="Logout.do">Log-out</a>
	<hr>
	<!-- 폼의 value에 출력 -->
	
	<form action="updateMember.do" method="post">
		<!-- updateBoard.do 페이지로 넘길때 seq를 hidden으로 가져가기 -->
		<!-- 출력구문에는 나타나지 않고 변수값 넘길때 사용 -->
		
		<input type="hidden" name="idx" value="<%= member.getIdx()%>">
		
		<table border="1" cellspacing="0" cellpadding="0">
		

			
			<tr>
				<td bgcolor="orange">아이디 : </td> <!-- 수정불가 -->
				<td><%= member.getId() %></td>
			</tr>
			<tr>
				<td bgcolor="orange">패스워드 : </td> <!-- 수정불가 -->
				<td><%= member.getPass()%></td>
			</tr>
			<tr>
				<td bgcolor="orange">이름 : </td> <!-- 수정불가 -->
				<td><%= member.getName()%></td>
			</tr>
			<tr>
				<td bgcolor="orange">이메일 : </td> <!-- 수정 -->
				<td><input type="text" name="title" value="<%= member.getEmail()%>"></td>
			</tr>
						<tr>
				<td bgcolor="orange">나이 : </td> <!-- 수정 -->
				<td><input type="text" name="title" value="<%= member.getAge()%>"></td>
			</tr>
			
						<tr>
				<td bgcolor="orange">몸무게 : </td> <!-- 수정 -->
				<td><input type="text" name="title" value="<%= member.getWeight()%>"></td>
			</tr>
			
						<tr>
				<td bgcolor="orange">가입날짜 : </td> <!-- 수정불가 -->
				<td><%= member.getRegdate()%></td>
			</tr>
						<tr>
				<td bgcolor="orange">카운트 : </td> <!-- 수정불가 -->
				<td><%= member.getCnt()%></td>
			</tr>
			

			<tr>
				<!-- 글 수정은 넘어오는 seq의 값을 가지고 수정한다. -->
				<td colspan="2" align="right"><input type="submit" value="글 수정"></td>
			</tr>
		</table>
	</form>
	<p>
	<!-- do : controller에서 쿼리의 실행이 필요할 것들 -->
	<a href="insertMember.jsp">글 등록</a> &nbsp;&nbsp;&nbsp;
	<a href="deleteMember.do?seq=<%= member.getIdx()%>">글 삭제</a> &nbsp;&nbsp;&nbsp;
	<a href="getMember.do">글 목록</a>
	
</center>
</body>
</html>