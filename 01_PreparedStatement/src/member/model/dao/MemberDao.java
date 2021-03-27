package member.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import member.model.vo.Member;

/**
 * 
 * DAO
 * Data Access Object
 * DB에 접근하는 클래스
 * 
 * 1. 드라이버 클래스 등록(최초1회)
 * 2. Connection 객체 생성(접속할 DB서버의 url, user, password)
 * 3. 자동커밋여부 설정 : true(기본값) / false -> app에서 직접 트랜잭션 제어
 * 4. PreparedStatement 객체생성(미완성쿼리) 및 값대입
 * 5. Statement 객체 실행. DB에 쿼리 요청
 * 6. 응답처리 DML : int리턴, DQL : ResultSet리턴 -> 자바객체로 전환
 * 7. 트랜잭션 처리(DML)
 * 8. 자원반납(생성의역순)
 */
public class MemberDao {
	// 1. 드라이버 클래스 등록(최초1회)
	String driverClass = "oracle.jdbc.OracleDriver";
	// 2. Connection 객체 생성(접속할 DB서버의 url, user, password)
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String user = "student";
	String password = "student";
	
	
	public int insertMember(Member member) {
		Connection conn = null;
		String sql = "insert into member values(?,?,?,?,?,?,?,?,?,default)"; //4. PreparedStatement 객체생성(미완성쿼리) 및 값대입
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			Class.forName(driverClass);
			// 2. Connection 객체 생성(접속할 DB서버의 url, user, password)
			
			conn = DriverManager.getConnection(url, user, password);
			// 3. 자동커밋여부 설정(DML) : true(기본값) / false -> app에서 직접 트랜잭션 제어
			conn.setAutoCommit(false);
			// 4. PreparedStatement 객체생성(미완성쿼리) 및 값대입
			pstmt = conn.prepareStatement(sql); //쿼리를 실행하는 객체
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getGender());
			pstmt.setInt(5, member.getAge());
			pstmt.setString(6, member.getEmail());
			pstmt.setString(7, member.getPhone());
			pstmt.setString(8, member.getAddress());
			pstmt.setString(9, member.getHobby());
			
			// 5. Statement 객체 실행. DB에 쿼리 요청
			// 6. 응답처리 DML : int리턴, DQL : ResultSet리턴 -> 자바객체로 전환
			result = pstmt.executeUpdate(); //정상적으로 처리되면 1리턴
			
			
			// 7. 트랜잭션 처리(DML)
			if(result > 0)
				conn.commit();
			else
				conn.rollback();

		} catch (ClassNotFoundException e) {
			//ojdbc6.jar 프로젝트 연동실패
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		
			// 8. 자원반납(생성의역순)
			try {
				if(pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		return result;
	}
	
	public List<Member> selectAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select * from member order by enroll_date desc";
		List<Member> list = null;
		try {
			//1. 드라이버 클래스 등록(최초1회)
			Class.forName(driverClass);
			//2. Connection 객체 생성(접속할 DB서버의 url, user, password)
			//3. 자동커밋여부 설정 : true(기본값) / false -> app에서 직접 트랜잭션 제어
			conn = DriverManager.getConnection(url, user, password);
			//4. PreparedStatement 객체생성(미완성쿼리) 및 값대입
			pstmt = conn.prepareStatement(sql);
			
			//5. Statement 객체 실행. DB에 쿼리 요청
			rset = pstmt.executeQuery();
			//6. 응답처리 DML : int리턴, DQL : ResultSet리턴 -> 자바객체로 전환
			//테이블로 리턴된다
			//다음행 존재여부 리턴. 커서가다음행에 접근.
			list = new ArrayList<>();
			while(rset.next()) {
				//컬럼명은 대소문자를 구분하지않는다
				String memberId = rset.getString("member_id");
				String password = rset.getString("password");
				String memberName = rset.getString("member_name");
				String gender = rset.getString("gender");
				int age = rset.getInt("age");
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("address");
				String hobby = rset.getString("hobby");
				Date enrollDate = rset.getDate("enroll_date");
				
				Member member = new Member(memberId, password, memberName, gender, age, email, phone, address, hobby, enrollDate);
				list.add(member);
			}
			//7. 트랜잭션 처리(DML)
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//8. 자원반납(생성의역순)
			try {
				if(rset != null)
					rset.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if(pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}

	public Member selectOne(String memberId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select * from member where member_id = ?";
		Member member = null;
		try {
			Class.forName(driverClass);
			conn = DriverManager.getConnection(url, user, password);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);//첫번째 물음표에 memberId을 넣는다
			rset = pstmt.executeQuery();
			while(rset.next()) {				
				memberId = rset.getString("member_id");
				String password = rset.getString("password");
				String memberName = rset.getString("member_name");
				String gender = rset.getString("gender");
				int age = rset.getInt("age");
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("address");
				String hobby = rset.getString("hobby");
				Date enrollDate = rset.getDate("enroll_date");
				
				member = new Member(memberId, password, memberName, gender, age, email, phone, address, hobby, enrollDate);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rset != null)
					rset.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if(pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return member;
	}

	public Member selectTwo(String memberName) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select * from member where member_name like ?";
		Member member = null;
		try {
			Class.forName(driverClass);
			conn = DriverManager.getConnection(url, user, password);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + memberName + "%");
			rset = pstmt.executeQuery();
			while(rset.next()) {				
				String memberId = rset.getString("member_id");
				String password = rset.getString("password");
				memberName = rset.getString("member_name");
				String gender = rset.getString("gender");
				int age = rset.getInt("age");
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("address");
				String hobby = rset.getString("hobby");
				Date enrollDate = rset.getDate("enroll_date");
				
				member = new Member(memberId, password, memberName, gender, age, email, phone, address, hobby, enrollDate);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rset != null)
					rset.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if(pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return member;
	}

	public int updateMember(Member member) {
		Connection conn = null;
		String sql = "update member set password = ?, email = ?, phone = ?, address = ?, hobby = ? where member_id = ?"; 
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			Class.forName(driverClass);
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql); //쿼리를 실행하는 객체
			pstmt.setString(1, member.getPassword());
			pstmt.setString(2, member.getEmail());
			pstmt.setString(3, member.getPhone());
			pstmt.setString(4, member.getAddress());
			pstmt.setString(5, member.getHobby());
			pstmt.setString(6, member.getMemberId());
			result = pstmt.executeUpdate(); //정상적으로 처리되면 1리턴
			
			if(result > 0)
				conn.commit();
			else
				conn.rollback();

		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		
			try {
				if(pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		}
		return result;
	}

	public int deleteMember(String deleteMemberId) {
		Connection conn = null;
		String sql = "delete from member where member_id = ?"; 
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			Class.forName(driverClass);
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql); //쿼리를 실행하는 객체
			pstmt.setString(1, deleteMemberId);
			result = pstmt.executeUpdate(); //정상적으로 처리되면 1리턴
			
			if(result > 0)
				conn.commit();
			else
				conn.rollback();

		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		
			try {
				if(pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		}
		return result;
	}

}
