package com.kh.messenger.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class AppMain extends Application {

	public static void main(String[] args) {
		launch(args); // AppMain 객체 생성 및 메인 윈도우 생성
		
	
		
		
		

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// 레이아웃 파일을 읽어 각 태그를 객체화 하고 최상위 루트컨테이너를 참조값으로 반환한다. 
		
		
		Parent parent = FXMLLoader.load(getClass().getResource("root.fxml"));
		//힙메모리에 인스턴스화해서 올려야하기때문에 최상위타입으로 형변환해준다.
		
		
		Scene scene = new Scene(parent);
		primaryStage.setScene(scene);
		primaryStage.setTitle("GridPane2테스트");
		
		primaryStage.show(); //윈도우창보여주기		
		
	}
	
	

}
