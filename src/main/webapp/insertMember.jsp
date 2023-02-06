<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here (새글 등록)</title>
</head>
<body>

<center>
	<h1>새 글 등록</h1>
	<a href="Logout.do">Log-Out</a>
	<form action="insertMember.do" method="post">
		<table border="1" cellspacing="0" cellpadding="0">
		
			<tr><td bgcolor="orange" width="70" align="center">아이디 : </td>
				<td><input type="text" name="id"></td>
			</tr>
			
			<tr><td bgcolor="orange" align="center">패스워드 : </td>
				<td><input type="text" name="pass"></td>
			</tr>
			
			<tr><td bgcolor="orange"align="center">이름 : </td>
				<td><input type="text" name="name"></td>
			</tr>
			
			<tr><td bgcolor="orange" align="center">이메일 : </td>
				<td><input type="text" name="email"></td>
			</tr>
			
			<tr><td bgcolor="orange"align="center">나이 : </td>
				<td><input type="text" name="age"></td>
			</tr>
			
			<tr><td bgcolor="orange" align="center">몸무게 : </td>
				<td><input type="text" name="weight"></td>
			</tr>
			
			<tr><td colspan="2">
				<input type="submit" align="right" values="새글등록"></td>
			</tr>
		</table>
	</form>
	<p/><hr>
		<a href="getMemberList.do">글 목록 바로가기</a>
	
	
	

</center>

</body>
</html>