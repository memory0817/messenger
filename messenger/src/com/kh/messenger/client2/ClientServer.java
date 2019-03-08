package com.kh.messenger.client2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.kh.messenger.client2.ReceiveChatWindowController;
import com.kh.messenger.common.Command;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ClientServer {

	ServerSocket serverSocket; // 서버소켓
	ExecutorService executorService; // 스레드풀이용
	List<Client> connections = new Vector<Client>(); // 클라이언트정보 저장

	final String HOSTNAME = "localhost";
	final int PORT = 7002;

	ClientServer() {
		System.out.println("ClientServer()");
		startServer();
	}

	// 서버소켓 생성 후 클라이언트가 접속하면 클라이언트 소켓을 생성
	void startServer() {

		executorService = Executors.newFixedThreadPool(10);

		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress(HOSTNAME, PORT));
			System.out.println("startServer() [서버소켓 생성됨]");
		} catch (IOException e) {
			System.out.println("startServer() [서버소켓 생성실패]");
			if (!serverSocket.isClosed()) {
				stopServer();
			}
			return;
		}

		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						// 클라이언트가 접속할때까지 대기상태로 있다가 요청이 오면 통신 socket을 반환함
						Socket socket = serverSocket.accept();

						String message = "[연결 수락: " + socket.getRemoteSocketAddress() + ": " + Thread.currentThread().getName()
								+ "]";
						System.out.println(message);

						Client client = new Client(socket);

						// 접속한 클라이언트를 vector에 저장 => client관리용도
						connections.add(client);
						System.out.println("[연결 개수: " + connections.size() + "]");
					} catch (IOException e) {
						if (!serverSocket.isClosed()) {
							stopServer();
						}
						break;
					}
				}
			}
		};
		executorService.submit(runnable);

	}

	// 접속한 클라이언트 소켓을 모두 close 하고 서버소켓을 close
	void stopServer() {

		try {
			Iterator<Client> iterator = connections.iterator();
			while (iterator.hasNext()) {
				Client client = iterator.next();
				client.socket.close();
				iterator.remove();
			}
			if (serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
			}
			if (executorService != null && !executorService.isShutdown()) {
				executorService.shutdown();
			}

			System.out.println("stopServer()!");
		} catch (Exception e) {
		}
	}

	// 접속한 요청한 클라이언트와 통신하는 기능
	class Client {

		Socket socket;
		ObjectInputStream ois;
		ObjectOutputStream oos;
		Command command;

		Stage chatWindow;
		ReceiveChatWindowController receiveChatWindowController;

		String message, senderID, receiverID;

		public Client(Socket socket) {
			this.socket = socket;

			try {
				oos = new ObjectOutputStream(socket.getOutputStream());
				ois = new ObjectInputStream(socket.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}

			receive();
		}

		void receive() {
			Runnable runnable = new Runnable() {

				@Override
				public void run() {
					try {
						while (true) {

							command = (Command) ois.readObject();
							System.out.println(command.getType().name() + "호출됨!!");

							switch (command.getType()) {
							case SENDMESSAGE: {
								String[] args = command.getArgs();
								message = args[0];
								senderID = args[1];
								receiverID = args[2];
								System.out.println("args="+message+":"+senderID+":"+receiverID);								
								chattHandle(Client.this, message, senderID, receiverID);
							}
								break;
							default:
								break;
							}
							String message = "[요청 처리: " + socket.getRemoteSocketAddress() + ": " + Thread.currentThread().getName()
									+ "]";
							System.out.println(message);

							for(Client client: connections) {
								client.send(command);
							}
						}
					} catch (Exception e) {
						try {
							connections.remove(Client.this);
							String message = "[클라이언트 통신 안됨: " + socket.getRemoteSocketAddress() + ": "
									+ Thread.currentThread().getName() + "]";
							
							 Platform.runLater(()->receiveChatWindowController.display(senderID+">>"  + "님이 대화창을 나가셨습니다."));								
							
							System.out.println("[연결 개수: " + connections.size() + "]");

							socket.close();
						} catch (IOException e1) {
						}
					}
				}
			};
			executorService.submit(runnable);
		}

		protected void chattHandle(Client client,String message, String senderID, String receiverID) {

			System.out.println("대화창호출!");
			Platform.runLater(()->{
				if (chatWindow == null) {
					chatWindow = new Stage(StageStyle.DECORATED);
					FXMLLoader loader = new FXMLLoader(getClass().getResource("newChat.fxml"));
					receiveChatWindowController = new ReceiveChatWindowController();
					loader.setController(receiveChatWindowController);
	
					Parent p = null;
					try {
						p = loader.load();
					} catch (IOException e) {
					}
					receiveChatWindowController.setDialog(chatWindow);
	
					Scene scene = new Scene(p);
					chatWindow.setScene(scene);
					chatWindow.setTitle("대화창");
					chatWindow.show();
	
					chatWindow.setOnCloseRequest(event->{
						try {
							client.socket.close();
						} catch (IOException e) {	}
					});					
					receiveChatWindowController.receiveMsg(client, message, senderID, receiverID);
				} else {
					chatWindow.show();
					receiveChatWindowController.receiveMsg(client, message, senderID, receiverID);
				}
			});
		}

		void send(Command command) {
			Runnable runnable = new Runnable() {

				@Override
				public void run() {
					try {
						oos.writeObject(command);
						oos.flush();
						System.out.println(command.getType().name() + ": 결과 성공적으로 보냄!");
					} catch (IOException e) {

						try {
							connections.remove(Client.this);
							String message = "[클라이언트 통신 안됨: " + socket.getRemoteSocketAddress() + ": "
									+ Thread.currentThread().getName() + "]";
							Platform.runLater(()->receiveChatWindowController.display(senderID+">>"  + "님이 대화창을 나가셨습니다."));							
							
//							mSvrCtr.uiUpdate(UiCommand.SERVER_LOG, message);
//							mSvrCtr.uiUpdate(UiCommand.SERVER_LOG, "[연결 개수: " + connections.size() + "]");

							socket.close();
						} catch (IOException e1) {
						}
					}
				}

			};

			executorService.submit(runnable);
		}
	}

}
