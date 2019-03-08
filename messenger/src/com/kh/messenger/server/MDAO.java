package com.kh.messenger.server;

import java.util.List;

import com.kh.messenger.common.MemberDTO;

public interface MDAO {
	
	
	//회원정보 가져오기
	MemberDTO getMember(String id);
	

	// 회원 로그인
	boolean isLogin(String id, String pw);
	
	
	// 대화명 가져오기
	String getNickName(String id);
	
	// 접속자의 친구목록 가져오기
	List<MemberDTO> getFriends(String id);
	
	// 접속자를 친구로 등록한 친구목록 가져오기
	List<MemberDTO> getFriendedList(String id);

	//친구 추가
	boolean addFriend(String myId, String friendId);

	//친구 삭제
	boolean delFriend(String myId, String friendId);

	
	//회원가입
	boolean memberJoin(MemberDTO memberDTO);
	
	
	//회원탈퇴
	boolean memeberOut(String id);
	
	//아이디조회
	String findId(String tel, String birth);
	
	
	//비밀번호조회
	String findPw(String id, String tel, String birth);
	
	
	//회원유무검증
	boolean isMember(String id);
	
}
