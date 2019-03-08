package com.kh.messenger.client;

import com.kh.messenger.sample.ConfirmDialog;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class AppMain2 extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// 레이아웃 파일을 읽어 각 태그를 객체화 하고 최상위 루트컨테이너를 참조값으로 반환한다. 
		
//		setUserAgentStylesheet(STYLESHEET_CASPIAN);

		//힙메모리에 인스턴스화해서 올려야하기때문에 최상위타입으로 형변환해준다.
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("root.fxml"));
		Parent parent = loader.load();
		
		RootController controller = loader.getController();
		controller.setPrimaryStage(primaryStage);
		
				
		Scene scene = new Scene(parent);
		scene.getStylesheets().add(getClass().getResource("blackOnWhite.css").toString());
		
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("메신저로그인");
		
		primaryStage.show(); //윈도우창보여주기	
		
		primaryStage.setOnCloseRequest(e->{
			boolean answer = false;
			answer = ConfirmDialog.display("확인", "메신저를 종료하시겠습니까?");
			System.out.println(answer);
			if(answer) {
				primaryStage.close();
			}else {
				e.consume();
			}
			
		});
		
	}
	
	
	public static void main(String[] args) {
		Application.launch(args); // AppMain 객체 생성 및 메인 윈도우 생성
		

		
		
		

	}

}
