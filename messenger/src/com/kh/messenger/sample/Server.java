package com.kh.messenger.sample;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		Socket socket = null;
		InputStream is = null;
		OutputStream os = null;
		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress("192.168.0.131", 9000));
			while(true) {
				System.out.println("[연결기다림]");
				serverSocket.accept();//클라이언트 접속을 기다리는 상태
				socket = serverSocket.accept();
				socket.getRemoteSocketAddress();//클라이언트의 정보를 얻어올수있음
				InetSocketAddress isa = (InetSocketAddress)socket.getRemoteSocketAddress();//얘가 반환하는거
				System.out.println("[연결수락함]"+isa.getHostName());
				System.out.println("[연결수락함]"+isa.getAddress());
				System.out.println("[연결수락함]"+isa.getPort());
				System.out.println("[연결수락함]"+isa.getHostString());
				
				byte[] bytes = new byte[100];
				is = socket.getInputStream();
				int readByteCount = is.read(bytes);
				String msg = new String(bytes,0,readByteCount,"UTF-8");
				System.out.println("[데이터수신] : " + msg);
				
				os = socket.getOutputStream();
				msg = "반갑습니다~";
				bytes = msg.getBytes("UTF-8");
				os.write(bytes);
				os.flush();
				System.out.println("[데이터 송신 완료!]");
				
				
				
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}finally {
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
