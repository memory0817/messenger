package com.kh.messenger.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kh.messenger.common.MemberDTO;

public class MDAOimpl implements MDAO {
	
	DataBaseUtil db;
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	

	public MDAOimpl() {
		db = DataBaseUtil.getInstance();
			
	}
	
	@Override
	public boolean isLogin(String id, String pw) {
		
		boolean isLogin = false;
		
		
		StringBuffer sql = new StringBuffer();
		sql.append("select nickname from member ");
		sql.append("where id=? and pw=? ");
		
		try {
			System.out.println("fff"+id+" "+pw);
			conn = db.getConnection();
			System.out.println("fff2"+id+" "+pw);
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			isLogin = rs.next();
			
		} catch (SQLException e) {
			String errLoc = "boolean isLogin(String id, String pw)";
			db.pirintSQLExcption(e, errLoc);
		}finally {
			db.close(conn, pstmt, rs);
		}
		
		return isLogin;
				
	}
	
	//닉네임가져오기
	@Override
	public String getNickName(String id) {
		
		String nickname = null;
		StringBuffer sql = new StringBuffer();
		
		sql.append("select nickname from member ");
		sql.append("where id=? ");
		
		
		try {
			conn = db.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			
			
			rs = pstmt.executeQuery();

			if(rs.next()) {
				nickname = rs.getString("nickname");
			};
			
			
		} catch (SQLException e) {
			String errLoc = "String getNickName(String id)";
			db.pirintSQLExcption(e, errLoc);
		}finally {
			db.close(conn, pstmt, rs);
		}
		
		
		return nickname;
	}

	//친구목록얻어오기
	@Override
	public List<MemberDTO> getFriends(String id) {
		
		List<MemberDTO> list = new ArrayList<>();
		MemberDTO memberDTO = null;
		StringBuffer sql = new StringBuffer();
		
		sql.append("select id, nickname, gender, region, birth, tel ");
		sql.append("from member ");
		sql.append("where id in (select friendsid  ");
		sql.append("             from friends ");
		sql.append("             where myid=?) ");
		sql.append("order by id ");
		
		
		
		try {
			conn = db.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			
			
			rs = pstmt.executeQuery();

			
			while(rs.next()) {
				memberDTO = new MemberDTO();
				memberDTO.setId(rs.getString("id"));
				memberDTO.setNickname(rs.getString("nickname"));
				memberDTO.setGender(rs.getString("gender"));
				memberDTO.setRegion(rs.getString("region"));
				memberDTO.setBirth(rs.getString("birth"));
				memberDTO.setTel(rs.getString("tel"));
				list.add(memberDTO);
				
			};
			
			
		} catch (SQLException e) {
			String errLoc = "List<MemberDTO> getFriends(String id)";
			db.pirintSQLExcption(e, errLoc);
		}finally {
			db.close(conn, pstmt, rs);
		}
		
		
		return list;
	}
	
	// 접속자를 친구로 등록한 친구목록 가져오기
		@Override
		public List<MemberDTO> getFriendedList(String id) {
			
			List<MemberDTO> list = new ArrayList<>();
			MemberDTO memberDTO = null;
			StringBuffer sql = new StringBuffer();
			
			sql.append("select id, nickname, gender, region, birth, tel ");
			sql.append("from member ");
			sql.append("where id in ( select myid "); 
			sql.append("                from friends "); 
			sql.append("               where friendsid=? ) ");
			sql.append("order by id ");
			
			try {
				conn = db.getConnection();
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, id);

				rs = pstmt.executeQuery();
				while(rs.next()) {
					memberDTO = new MemberDTO();
					memberDTO.setId(rs.getString("id"));
					memberDTO.setNickname(rs.getString("nickName"));
					memberDTO.setGender(rs.getString("gender"));
					memberDTO.setRegion(rs.getString("region"));
					memberDTO.setBirth(rs.getString("birth"));
					memberDTO.setTel(rs.getString("tel"));
					list.add(memberDTO);
				};
			} catch (SQLException e) {
				String errLoc = "List<MemberDTO> getFriends(String id)";			
				db.pirintSQLExcption(e, errLoc);
			} finally {
				db.close(conn, pstmt, rs);
			}
			
			return list;
		}
		
	
	//친구추가
	@Override
	public boolean addFriend(String myId, String friendId) {
		boolean status = false;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into friends (myid, friendsid) ");
		sb.append("values (?, ?) ");
		
		try {
			conn = db.getConnection();
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, myId);
			pstmt.setString(2, friendId);
			int rows = pstmt.executeUpdate();
			if(rows == 1) {
				status = true;
			}
			
		} catch (SQLException e) {
			String errLoc = "boolean addFriend(String myId, String friendId)";
			db.pirintSQLExcption(e, errLoc);
		}finally {
			db.close(conn, pstmt, rs);
		}
		
		
		
		return status;
	}

	
	
	
	//친구삭제
	@Override
	public boolean delFriend(String myId, String friendId) {
		boolean status = false;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from friends where myid=? and friendsid=? ");
		
		try {
			conn = db.getConnection();
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, myId);
			pstmt.setString(2, friendId);
			int rows = pstmt.executeUpdate();
			if(rows == 1) {
				status = true;
			}
			
		} catch (SQLException e) {
			String errLoc = "boolean delFriend(String myId, String friendId)";
			db.pirintSQLExcption(e, errLoc);
		}finally {
			db.close(conn, pstmt, rs);
		}
		
		
		
		return status;
	}

	
	//회원가입
	@Override
	public boolean memberJoin(MemberDTO memberDTO) {
		boolean status = false;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into member (id,pw,tel,nickname,gender,region,birth) ");
		sb.append("values(?,?,?,?,?,?,?) ");
		
		try {
			conn = db.getConnection();
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, memberDTO.getId());
			pstmt.setString(2, memberDTO.getPw());
			pstmt.setString(3, memberDTO.getTel());
			pstmt.setString(4, memberDTO.getNickname());
			pstmt.setString(5, memberDTO.getGender());
			pstmt.setString(6, memberDTO.getRegion());
			pstmt.setString(7, memberDTO.getBirth());
			
			int rows = pstmt.executeUpdate();
			if(rows == 1) {
				status = true;
			}
			
		} catch (SQLException e) {
			String errLoc = "boolean memberJoin(MemberDTO memberDTO)";
			db.pirintSQLExcption(e, errLoc);
		}finally {
			db.close(conn, pstmt, rs);
		}
				
		return status;
	}

	//회원탈퇴
	@Override
	public boolean memeberOut(String id) {
		boolean status = false;
		StringBuffer sb1 = new StringBuffer();
		sb1.append("delete from friends where myid=? ");
		
		
		StringBuffer sb2 = new StringBuffer();
		sb2.append("delete from member where id=? ");
		
		
		
		try {
			conn = db.getConnection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sb1.toString());
			pstmt.setString(1, id);
			
			int rows1 = pstmt.executeUpdate();
			if(rows1 >= 0) {
				pstmt = conn.prepareStatement(sb2.toString());
				pstmt.setString(1, id);
				int rows2 = pstmt.executeUpdate();
				if(rows2 == 1) {
					status = true;
					conn.commit();
				}else {conn.rollback();}
			}else {conn.rollback();}
			
			} catch (SQLException e) {
			 try {
			conn.rollback();
			String errLoc = "boolean memeberOut(String id)";
			db.pirintSQLExcption(e, errLoc);
			 }catch(SQLException e1) {
				e1.printStackTrace(); 
			 }		
		}finally {
			db.close(conn, pstmt, rs);
		}
		
		
		return status;
	}
	
	//회원정보 가져오기
	@Override
	public MemberDTO getMember(String id) {
		
		MemberDTO memberDTO = new MemberDTO();
		StringBuffer sql = new StringBuffer();
		
		sql.append("select id,nickname,gender,region,birth,tel ");
		sql.append("from member where id=? ");

		
		
		
		try {
			conn = db.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			
			
			rs = pstmt.executeQuery();

			
			if(rs.next()) {
				memberDTO.setId(rs.getString("id"));
				memberDTO.setNickname(rs.getString("nickname"));
				memberDTO.setGender(rs.getString("gender"));
				memberDTO.setRegion(rs.getString("region"));
				memberDTO.setBirth(rs.getString("birth"));
				memberDTO.setTel(rs.getString("tel"));
								
			};
			
			
		} catch (SQLException e) {
			String errLoc = "MemberDTO getMember(String id)";
			db.pirintSQLExcption(e, errLoc);
		}finally {
			db.close(conn, pstmt, rs);
		}
		
		
		return memberDTO;
		
	}

	// 아이디 조회
	@Override
	public String findId(String tel, String birth) {
		String id = null;

		StringBuffer sql = new StringBuffer();
		
		sql.append("select id from member ");
		sql.append("where tel=? and birth=? ");
		
		
		try {
			conn = db.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, tel);
			pstmt.setString(2, birth);
			
			
			rs = pstmt.executeQuery();

			if(rs.next()) {
				id = rs.getString("id");
			};
			
			
		} catch (SQLException e) {
			String errLoc = "String findId(String tel, String birth)";
			db.pirintSQLExcption(e, errLoc);
		}finally {
			db.close(conn, pstmt, rs);
		}
		
		
		
		return id;
	}

	// 비밀번호 조회
	@Override
	public String findPw(String id, String tel, String birth) {
		String pw = null;
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select pw from member ");
		sql.append("where id=? and tel=? and birth=? ");
		
		
		try {
			conn = db.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			pstmt.setString(2, tel);
			pstmt.setString(3, birth);
			
			
			rs = pstmt.executeQuery();

			if(rs.next()) {
				pw = rs.getString("pw");
			};
			
			
		} catch (SQLException e) {
			String errLoc = "String findPw(String id, String tel, String birth)";
			db.pirintSQLExcption(e, errLoc);
		}finally {
			db.close(conn, pstmt, rs);
		}
		
		
		return pw;
	}

	
	//회원유무조회
	@Override
	public boolean isMember(String id) {
		boolean status = false;
		

		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select id from member ");
		sql.append("where id=? ");
		
		
		try {
			conn = db.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			
			
			rs = pstmt.executeQuery();

			if(rs.next()) {
				status = true;
			};
			
			
		} catch (SQLException e) {
			String errLoc = "boolean isMember(String id)";
			db.pirintSQLExcption(e, errLoc);
		}finally {
			db.close(conn, pstmt, rs);
		}
		
		
		return status;
		
	}

	

	
	
	
	
	
	
	
	
	
	
	

}
