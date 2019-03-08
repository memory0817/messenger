package com.kh.messenger.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MSrvMain extends Application{

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		//1. 화면로딩
		//2. 컨트롤러 정보 불러옴
		// fxml두개를 만들어 로더를 두개 만들어 Scene 매개값을 바꾸면 스위칭 가능
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MSvrScr.fxml"));
		// 루트컨테이너를 불러옴
		Parent parent = loader.load();
		
		MsvrCtr controller = loader.getController();
		controller.setPrimaryStage(primaryStage);
		
		Scene scene = new Scene(parent);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("메신저 서버 모니터링");
		primaryStage.show();
		
	}

}
