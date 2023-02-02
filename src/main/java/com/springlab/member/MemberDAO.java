package com.springlab.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.springlab.common.JDBCUtil;


@Repository("memberDAO")

public class MemberDAO {
	//DAO 
	// CRUD 객체
	
	//1. JDBC관련 변수 선언
	private Connection conn = null;
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	//2. SQL 쿼리를 담는 상수에 담아서 처리, 변수 생성후 할당 : 상수명 : 전체 대문자로 사용 
	private final String MEMBER_INSERT = "insert into member (idx, id, pass, name, email, age, weight) values( (select nvl(max(idx),0)+1 from member),?,?,?,?,?,?)";
	private final String MEMBER_UPDATE = "update member set email=?, age=?, weight=? where idx =?";
	private final String MEMBER_DELETE = "select * from member where idx=?";
	private final String MEMBER_GET = "select * from member where idx= ?";
	private final String MEMBER_LIST = "select * from member order by idx desc";
	
	private final String MEMBER_LOGIN = "select * from member where id = ? and pass = ?"; 
		//로그인 처리 쿼리
	
	
	//DB에서 ID와 패스워드를 확인하는 메소드 
	
	public Boolean getLogin(MemberDTO dto) {
		//객체 선언 : DB에서 select 한 레코드를 user 에 담아서 리턴 
		Boolean result = false; 
		
	//	System.out.println("DAO - " + dto.getId());
	//	System.out.println("DAO - " + dto.getPassword() );
		
		try {
			System.out.println("==> JDBC로 getUser() 시작");
			conn = JDBCUtil.getConnection(); 
			
			//MEMBER_LOGIN = "select * from member where id = ? and pass = ?"
			pstmt = conn.prepareStatement(MEMBER_LOGIN); 
			
			//pstmt 에 할당된 쿼리에 ? , ?  에 변수값을 할당후 실행 : dto에 변수값으로 할당. 
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPass());
			
			rs = pstmt.executeQuery();    //select 문이므로 executeQuery()를 실행후 rs 로 리턴
			
			//rs의 담긴 값을 가져와서 DTO (user) 에 저장후 리턴 돌려줌 
			
			if (rs.next()){		//레코드의 값이 존재할때 커서를 해당 레코드로 이동 
				
				result = true; 
				System.out.println("DB에서 ID 와 Pass가 일치해서 잘 로그인 되었습니다. ");
				
				
				System.out.println("JDBC로 DB를 잘 쿼리해서 DTO로 잘 전송");
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();  //개발이 완료후에는 주석 처리 
			System.out.println("JDBC로 쿼리 실행중 올류발생 ");
			
		}finally {
			//모두 사용한 객체를 제거 : conn, pstmt, rs 
			JDBCUtil.close(rs, pstmt, conn);
		}
			
		return result; 
		
	}
	
	
	
	
	
	
	
	
	//3. 위의 메소드 처리
	
	//3-1. 글 등록 메소드 처리 : insertMember()
	public void insertMember(MemberDTO dto) {
		System.out.println("==> JDBC로 insertBoard() 기능 처리 - 시작");
		// Connection 객체를 사용해 PreparedStatement 객체 활성화
		
		try {
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(MEMBER_INSERT);
			
			//pstmt 에 ? 변수값 할당
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPass());
			pstmt.setString(3, dto.getName());
			pstmt.setString(4, dto.getEmail());
			pstmt.setInt(5, dto.getAge());
			pstmt.setDouble(6, dto.getWeight());
			
			pstmt.executeUpdate();
			
			System.out.println("==> JDBC로 insertBoard() 기능처리 - 완료");
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("==> JDBC로 insertBoard() 기능처리 - 실패");
		}finally {
			JDBCUtil.close(pstmt, conn);
			System.out.println("모든 객체가 잘 close() 되었습니다.");
		}
	}

	//3-2 글 수정 처리 메소드 : updateMember()
	public void updateMember(MemberDTO dto) {
		System.out.println();
		
		try {
			//객체 생성
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(MEMBER_UPDATE);
			
			//글 수정하는 변수값 할당
			pstmt.setString(1, dto.getEmail());
			pstmt.setInt(2, dto.getAge());
			pstmt.setDouble(3, dto.getWeight());
			pstmt.setInt(4, dto.getIdx());
			
			pstmt.executeUpdate();
			
			System.out.println("==> JDBC로 updateBoard() 기능처리 - 완료");
			
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("==> JDBC로 updateBoard() 기능처리 - 실패");
		}finally {
			JDBCUtil.close(pstmt, conn); 
		}
	}
	
	//3-3 글 삭제 처리 메소드 : deleteMember()
	public void deleteMember(MemberDTO dto) {
		System.out.println();
		
		try {
			//객체 생성
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(MEMBER_DELETE);
			
			pstmt.setInt(1, dto.getIdx());
			
			pstmt.executeUpdate();
			
			System.out.println("==> JDBC로 updateBoard() 기능처리 - 완료");
			
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("==> JDBC로 updateBoard() 기능처리 - 실패");
		}finally {
			JDBCUtil.close(pstmt, conn); 
		}
	}
	
	//3-4 글 상세 조회 처리 메소드 : getMember() : 레코드 1개를 DB에서 select 해서 DTO 객체에 담아서 리턴
	public MemberDTO getMember(MemberDTO dto) {
		System.out.println("==> JDBC로 getMember() 시작");
		
		MemberDTO member = new MemberDTO();
		
		try {
			
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(MEMBER_GET);
			//MEMBER_GET = "select * from member where idx= ?";
			pstmt.setInt(1, dto.getIdx());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				System.out.println("\"DB에서 값이 잘 select 되었습니다.  ");
				member.setIdx(rs.getInt("IDX"));
				member.setId(rs.getString("ID"));
				member.setPass(rs.getString("PASS"));
				member.setName(rs.getString("NAME"));
				member.setEmail(rs.getString("EMAIL"));
				member.setAge(rs.getInt("AGE"));
				member.setWeight(rs.getDouble("WEIGHT"));
				member.setRegdate(rs.getDate("REGDATE"));
				member.setCnt(rs.getInt("CNT"));
				
				System.out.println("JDBC로 DB를 잘 쿼리해서 DTO로 잘 전송");
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("JDBC로 쿼리 실행중 오류발생");
		}finally {
			JDBCUtil.close(rs, pstmt, conn);
		}
		
		
		
		
		/*
		System.out.println("==> JDBC로 getMember() 기능처리 - 시작");
		memberDTO member = new memberDTO();
		
		try {
			System.out.println("==> JDBC로 getMember");
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(MEMBER_GET);
			
			pstmt.setString(1, dto.getid());
			pstmt.setString(2, dto.getpass());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				member.setidx(rs.getInt("IDX"));
				member.setid(rs.getString("ID"));
				member.setpass(rs.getString("PASS"));
				member.setname(rs.getString("NAME"));
				member.setemail(rs.getString("EMAIL"));
				member.setage(rs.getInt("AGE"));
				member.setweight(rs.getDouble("WEIGHT"));
				member.setregdate(rs.getDate("REGDATE"));
				member.setcnt(rs.getInt("CNT"));
				
			}else {
				System.out.println("레코드의 결과가 없습니다. ");
			}
			System.out.println("==> JDBC로 getBoard() 기능처리 - 완료");

		}catch (Exception e){
			e.printStackTrace();
			System.out.println("==> JDBC로 getBoard() 기능처리 - 실패");
			System.out.println("==> JDBC로 getBoard() 기능처리 - 실패");
		}finally {
			JDBCUtil.close(rs, pstmt, conn);
		}
		*/		return member;
		
	}
	
	//3-5 글 목록 처리 메소드: getmemberList() : 많은 레코드
	public List<MemberDTO> getMemberList(MemberDTO dto){
		System.out.println("==> JDBC로 getMemberList() 기능처리 - 시작");
		
		List<MemberDTO> memberList = new ArrayList<MemberDTO>();
		MemberDTO member;
		
		try {
			conn = JDBCUtil.getConnection();
			
			pstmt = conn.prepareStatement(MEMBER_LIST);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				do {
					member = new MemberDTO();
					
					member.setIdx(rs.getInt("IDX"));
					member.setId(rs.getString("ID"));
					member.setPass(rs.getString("PASS"));
					member.setName(rs.getString("NAME"));
					member.setEmail(rs.getString("EMAIL"));
					member.setAge(rs.getInt("AGE"));
					member.setWeight(rs.getDouble("WEIGHT"));
					member.setRegdate(rs.getDate("REGDATE"));
					member.setCnt(rs.getInt("CNT"));
					
					memberList.add(member);
				}while(rs.next());
			}else {
				System.out.println("테이블에 레코드가 비어 있습니다. ");
			}
			System.out.println("==> JDBC로 getBoardList() 기능처리 - 완료");

		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("==> JDBC로 getBoardList() 기능처리 - 실패");
		}finally {
			JDBCUtil.close(rs, pstmt, conn);
		}
		return memberList;
	}
	
	
	//3-6 로그인 처리 메소드
	public MemberDTO getmember(MemberDTO dto) {
		MemberDTO member = new MemberDTO();
		
		try {
			System.out.println("==> JDBC로 getUser() 시작");
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(MEMBER_GET);
			
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPass());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				System.out.println("DB에서 값이 잘 select 되었습니다.  " );
				
				member.setIdx(rs.getInt("idx"));
				member.setId(rs.getString("id"));
				member.setPass(rs.getString("pass"));
				member.setName(rs.getString("name"));
				member.setEmail(rs.getString("email"));
				member.setAge(rs.getInt("age"));
				member.setWeight(rs.getDouble("weight"));
				member.setRegdate(rs.getDate("regdate"));
				member.setCnt(rs.getInt("cnt"));
				
				System.out.println("JDBC로 DB를 잘 쿼리해서 DTO로 잘 전송");
				
			}

		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("JDBC로 쿼리 실행중 오류발생");
		}finally {
			JDBCUtil.close(rs, pstmt, conn);
		}
		
		return member;
	}
	
}
