package com.springlab.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.springlab.member.MemberDAO;
import com.springlab.member.MemberDTO;





//@WebServlet("/DispatcherServlet")
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public DispatcherServlet() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		process(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		process(request, response);
	}
	
	
	//doGet, doPost 메소드에서 받는 내용을 처리
	public void process (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//URL : 	http://localhost:8080/mylab/login.do
		//URI : 	/mylab/login.do
		//path : 
		
		
		//1. 클라이언트의 요청 path 정보를 추출 한다. 
		String uri = request.getRequestURI();
			System.out.println("uri : " + uri);
		
			//lastIndexOf("/") : URI에서 뒤의 /까지 한다.
		String path = uri.substring(uri.lastIndexOf("/"));	
			System.out.println("path : " + path);
		
		//2. 클라이언트의 요청 정보에 따라서 적절하게 분기 처리함.
		if (path.equals("/login.do")) {
			
			System.out.println("사용자 정보 처리");
			System.out.println("/login.do 요청을 보냈습니다. ");
			
			// 1. 클라이언트에서 보내는 변수 값을 받아서 변수에 저장 
			String id = request.getParameter("id");
			String pass = request.getParameter("pass");
			
			System.out.println("폼에서 넘긴 변수 id 값 출력 : " + id);
			System.out.println("폼에서 넘긴 변수 pass 값 출력 : " + pass);
			
			//2. 클라이언트에서 넘긴 변수값을 받아서 저장된 변수를 DTO에 Setter 주입 
			MemberDTO dto = new MemberDTO();
			dto.setId(id);
			dto.setPass(pass);
			
			//3. 비즈니스 로직을 처리하는 이터페이스에 dto를 주입해서 비즈니스 로직을 처리
			MemberDAO member = new MemberDAO();
			
			Boolean result = member.getLogin(dto); 
			
			//4. 백엔드의 로직을 모두 처리후 View 페이지로 이동 
			if(result == true) {
				response.sendRedirect("getMemberList.do");
				System.out.println("아이디와 패스워드가 일치");
			}else {
				response.sendRedirect("login.jsp");
				System.out.println("아이디와 패스워드 불일치");
			}
			
			
			
		}else if (path.equals("/getMemberList.do")) {
			System.out.println("게시판 정보 출력");
			
			MemberDAO dao = new MemberDAO();
			MemberDTO dto = new MemberDTO();
			
			List<MemberDTO> memberList = dao.getMemberList(dto);
			
			HttpSession session = request.getSession();
			
			session.setAttribute("memberList", memberList);
			
			response.sendRedirect("getMemberList.jsp");
			
			
		}else if(path.equals("/getMember.do")) {
			System.out.println("회원정보 상세 페이지 - /getBoard.do 요청함");
			
			//1. 클라이언트의 넘긴 변수 값 받기 ("idx")
			String idx = request.getParameter("idx");
		
			
			System.out.println("폼에서 넘긴 변수 idx 값 출력 : " + idx);
			
			//2. 비즈니스 로직 처리 : 파라미터로 받은 값을 DTO에 저장 후 getmember(dto)메소드 호출
			MemberDAO dao = new MemberDAO();
			MemberDTO dto = new MemberDTO();
			
			//클라이언트에서 받은 값을 dto에 setter주입
			dto.setIdx(Integer.parseInt(idx));
			
			//리턴을 받아온다.
			MemberDTO member = dao.getmember(dto);	//getmember=>DAO에서 함수 호출
			
			//DB의 값이 저장된 DTO (board)를 session 변수에 할당해서 뷰 페이지로 전달
			HttpSession session = request.getSession();
			
			session.setAttribute("member", member);
			
			response.sendRedirect("getMember.jsp");
			
			
			
		}else if(path.equals("/updateMember.do")) {	//*******
			System.out.println("글 수정 처리");
			//MEMBER_UPDATE = "update member set email=?, age=?, 
			//weight=? where idx =?";
			//1. 클라이언트에서 넘어오는 변수를 받음
			
			String idx = request.getParameter("idx");
			String email = request.getParameter("email");
			String age = request.getParameter("age");
			String weight = request.getParameter("weight");
			
			MemberDTO dto = new MemberDTO();
			
			dto.setIdx(Integer.parseInt(idx));
			dto.setEmail(email);
			dto.setAge(Integer.parseInt(age));
			dto.setWeight(Double.valueOf(weight));
			
			MemberDAO dao = new MemberDAO();
			
			dao.updateMember(dto);
			
			response.sendRedirect("getMemberList.do");
			
		}else if (path.equals("/deleteMember.do")) {
			System.out.println("글 삭제 처리");
			
			//1. idx
			String idx = request.getParameter("idx");
			
			//2. DTO, DAO 
			MemberDAO dao = new MemberDAO();
			MemberDTO dto = new MemberDTO();
			dto.setIdx(Integer.parseInt(idx));
			dao.deleteMember(dto);
			
			//3. 삭제 후 View페이지로 이동
			response.sendRedirect("getMemberList.do");
			
		}else if(path.equals("/insertMember.do")) {
			System.out.println("member 테이블의 값을 저장");
			
			//1. insertMember페이지에서 넘어오는 값을 새로운 변수에 저장
			String id = request.getParameter("id");
			String pass = request.getParameter("pass");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String age = request.getParameter("age");
			String weight = request.getParameter("weight");
			
			MemberDTO dto = new MemberDTO();
			dto.setId(id);
			dto.setPass(pass);
			dto.setName(name);
			dto.setEmail(email);
			dto.setAge(Integer.parseInt(age));
			dto.setWeight(Double.valueOf(weight));
			MemberDAO user = new MemberDAO();
			user.insertMember(dto);
			
			response.sendRedirect("getMemberList.do");
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
