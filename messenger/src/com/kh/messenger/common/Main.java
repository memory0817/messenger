package com.kh.messenger.common;

//알터다이얼로그 레이아웃 버전
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	Stage window;
	Button button = new Button("click me!!");


	private boolean answer;
	
	
	
	public static void main(String[] args) {
		Application.launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {

	 window = primaryStage;
	 
	 
	 button.setOnAction(e->AlertDialog.display("알림","찾고자하는 아이디가 없습니다!!"));
//	 button.setOnAction(e-> {
//		 
//		 answer = ConfirmDialog.display("확인!!", "로그아웃 하시겠습니까?");
//		 
//		 if(answer) {
//			 System.out.println("OK");
//		 }else {
//			 System.out.println("NO");
//		 }
//	 
//	 
//	 });
//	 

	 
	  
	 StackPane stack = new StackPane(); //Alert,confirm할때
	 stack.setAlignment(Pos.CENTER);
	 stack.getChildren().add(button); //Alert,confirm할때
//	 stack.getChildren().addAll(list);	
	 
	 Scene scene = new Scene(stack,300,250);
	 window.setScene(scene);
	 window.setTitle("Dialog Test");
	 window.show();
	 
	 
		
	}

}
