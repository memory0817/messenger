package com.kh.messenger.sample;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {

	public static void main(String[] args) {
		
		
		Socket socket = null;
		InputStream is = null;
		OutputStream os = null;
		
		try {
			socket = new Socket();
			System.out.println("[연결요청]");
			socket.connect(new InetSocketAddress("192.168.0.121",9000));
			System.out.println("[연결성공]");
			
	String msg="ㅡ.ㅡ";
			byte[] bytes = msg.getBytes("UTF-8");
			os = socket.getOutputStream();
			os.write(bytes);
			os.flush();
			System.out.println("[데이터전송]");
			
			
			bytes = new byte[100];
			is = socket.getInputStream();
			int readByteCount = is.read(bytes);
			msg = new String(bytes,0,readByteCount,"UTF-8");
			System.out.println("[데이터수신성공!]" + msg);
			
			
			
			
			
			
		} catch (IOException e) {
			System.out.println("연결실패");
			e.printStackTrace();
		}
		
		
		if(socket.isConnected()) {
			try {
				is.close();
				os.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

	}
	
	

}
