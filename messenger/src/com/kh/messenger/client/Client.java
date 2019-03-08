package com.kh.messenger.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.kh.messenger.common.Command;

import javafx.application.Platform;

public class Client {
	
	SendChatWindowController sendChatWindowController;
	Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Command command;
	final int PORT = 7002;
	
	String receiverIP, receiverID;
	
	Client(SendChatWindowController sendChatWindowController, String receiverIP, String receiverID){
		this.sendChatWindowController = sendChatWindowController;
		this.receiverIP = receiverIP;
		this.receiverID = receiverID;
		System.out.println("클라이언트 생성됨");
		startClient();
	}
	
	void startClient() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					socket = new Socket();
					socket.connect(new InetSocketAddress("localhost", PORT));
					
					oos = new ObjectOutputStream(socket.getOutputStream());
					ois = new ObjectInputStream(socket.getInputStream());	
					System.out.println("[연결 완료: "  + socket.getRemoteSocketAddress() + "]");
					Platform.runLater(()->{
						try {
//							sendChatWindowController.display("[연결 완료: "  + socket.getRemoteSocketAddress() + "]");
							sendChatWindowController.btnSendDisable(false);
						} catch (Exception e) {}
					});
				} catch(Exception e) {
					System.out.println("client1에서 접속시도함:"+receiverIP + ":"+PORT);					
					e.printStackTrace();					
					Platform.runLater(()->sendChatWindowController.display("[서버 통신 안됨1]"));
					if(!socket.isClosed()) { stopClient(); }
					return;
				}
				receive();
			}
		};
		thread.start();
	}
	
	void stopClient() {
		try {
			Platform.runLater(()->{
				sendChatWindowController.display("[연결 끊음]");
				sendChatWindowController.btnSendDisable(true);
			});
			if(socket!=null && !socket.isClosed()) {
				socket.close();
			}
		} catch (IOException e) {}
	}		
	
	void receive() {
		while(true) {
			try {
				command = (Command) ois.readObject();
				System.out.println(command.getType().name() + ": 결과수신됨!!");

				switch (command.getType()) {
				case SENDMESSAGE: {
						 String args [] = command.getArgs();
						 String message = args[0];
						 String senderID = args[1];
						 String receiverID = args[2];

						 Platform.runLater(()->sendChatWindowController.display(senderID+">>"  + message));
				}
						break;
				}

			} catch (Exception e) {
				Platform.runLater(()->sendChatWindowController.display(receiverID+"님이 나가셨습니다"));
				stopClient();
				break;
			}
		}
	}
	
	void send(Command command) {
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {		
			
					oos.writeObject(command);
					oos.flush();
					System.out.println(command.getType().name() + ": 결과 성공적으로 보냄!");
//					Platform.runLater(()->sendChatWindowController.display("[보내기 완료]"));
				} catch(Exception e) {
					Platform.runLater(()->sendChatWindowController.display("[서버 통신 안됨3]"));
					stopClient();
				}				
			}
		};
		thread.start();
	}
}
