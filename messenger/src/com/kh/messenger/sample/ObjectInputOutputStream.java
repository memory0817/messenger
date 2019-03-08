package com.kh.messenger.sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectInputOutputStream {

	public static void main(String[] args) throws Exception {
		
		FileOutputStream fos = new FileOutputStream("d:/temp/Login.dat"); //쓸수있는 스트림이 하나 만들어짐
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setId("test1@test.com");
		loginInfo.setPw("test1");
		
		
		oos.writeObject(loginInfo);
		oos.flush();
		
		oos.close(); fos.close();
		
		
		
		//서버에 받아서 읽기위해서는 역직렬화가 필요하다.
		FileInputStream fis = new FileInputStream("d:/temp/Login.dat");
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		LoginInfo info = (LoginInfo)ois.readObject();
		System.out.println(info.getId());
		System.out.println(info.getPw());
		
//		네트워크는 소켓을 이용하기때문에 file아웃풋인풋이 아니라 소켓인풋아웃풋으로 바뀔뿐 기본구조는 같다.
		
		
	}

}
