package com.kh.messenger.sample;

//다이얼로그 유틸버전
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
//	Button button = new Button("click me!!");
	Button[] button = new Button[10];
	List<Button> list = new ArrayList<>();

	private boolean answer;
	
	
	
	public static void main(String[] args) {
		Application.launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {

	 window = primaryStage;
	 button[0] = new Button("alert()");
	 button[1] = new Button("alert2()");
	 button[2] = new Button("alert3()");
	 button[3] = new Button("alert4()");
	 button[4] = new Button("alert5()");
	 button[5] = new Button("alert6()");
	 button[6] = new Button("textInputDialog()");
	 button[7] = new Button("choiceDialog()");
	 button[8] = new Button("alert5()");
	 button[9] = new Button("alert5()");
	 
	 for(int i=0; i<button.length; i++) {
	 list.add(button[i]);
	 }
	 
//	 button.setOnAction(e->AlertDialog.display("알림","찾고자하는 아이디가 없습니다!!"));
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
	 
	 //DialogUtil 실행
	 button[0].setOnAction(e->{
		 Optional<ButtonType> optional = DialogUtil.dialog(AlertType.CONFIRMATION, "정보", "친구등록", "친구등록하시겠습니까?");	
		 if(optional.get() == ButtonType.OK) {
			 System.out.println("ok클릭");
		 }else if(optional.get() == ButtonType.CANCEL) {
			 System.out.println("cancel클릭");
		 }
	 });
	 button[1].setOnAction(e->{
		 DialogUtil.dialog(AlertType.INFORMATION, "정보", "친구등록", "새로운 친구가 등록되었습니다!");		 
	 });
	 
	 button[2].setOnAction(e->{
		 String value =
		 DialogUtil.textInputDialog("친구아이디를 입력하세요", "친구등록", "새로 등록한 친구의 이메일정보를 입력하세요!!" , null);
		 System.out.println("입력값-->" + value);
	 });
	 
	 
	 button[3].setOnAction(e->{
		 
		 List<String> list = new ArrayList<>();
		 list.add("친구등록");
		 list.add("친구삭제");
		 list.add("친구조회");
		 String value =	 DialogUtil.choiceDialog(list,"친구조회","친구관리", "친구관리", "친구관리");	
		 System.out.println("선택값 -->" + value);
	 });
	 
	 
	 
	 button[4].setOnAction(e->{
		 List<ButtonType> types = new ArrayList<>();
		 types.add(ButtonType.YES);
		 types.add(ButtonType.NO);
		 types.add(ButtonType.CLOSE);

		 //버튼만들기
		 ButtonType bt1 = new ButtonType("버튼1");
		 ButtonType bt2 = new ButtonType("버튼2");
		 ButtonType bt3 = new ButtonType("버튼3");
		 
		 types.add(bt1);		 types.add(bt2);    types.add(bt3);
		 
		 Optional<ButtonType> optional = 
		 DialogUtil.dialog("친구찾기", "친구조회", "친구목록에서 삭제하시겠습니까?", types );
		 
		 
		 if(optional.get() == ButtonType.YES) {
			 System.out.println("yes클릭됨");
			 
		 }else if(optional.get() == ButtonType.NO){
			 System.out.println("no클릭됨");
			 
		 }else if(optional.get() == ButtonType.CLOSE){
			 System.out.println("close클릭됨");
			 
		 }else if(optional.get() == bt1){
			 System.out.println("b1클릭됨");
			 
		 }else if(optional.get() == bt2){
			 System.out.println("b2클릭됨");
			 
		 }else if(optional.get() == bt3){
			 System.out.println("b3클릭됨");
			 
		 }
		 
	 });
	 
	 
	 
	 button[5].setOnAction(e->{
		 
		 //버튼만들기
		 ButtonType bt1 = new ButtonType("버튼1");
		 ButtonType bt2 = new ButtonType("버튼2");
		 ButtonType bt3 = new ButtonType("버튼3");
		 
			 
		 Optional<ButtonType> optional = 
		 DialogUtil.dialog("친구찾기", "친구조회", "친구목록에서 삭제하시겠습니까?", bt1,bt2,bt3,ButtonType.YES,ButtonType.NO,ButtonType.CLOSE );
		 
		 
		 if(optional.get() == ButtonType.YES) {
			 System.out.println("yes2클릭됨");
			 
		 }else if(optional.get() == ButtonType.NO){
			 System.out.println("no2클릭됨");
			 
		 }else if(optional.get() == ButtonType.CLOSE){
			 System.out.println("close2클릭됨");
			 
		 }else if(optional.get() == bt1){
			 System.out.println("b1-2클릭됨");
			 
		 }else if(optional.get() == bt2){
			 System.out.println("b2-2클릭됨");
			 
		 }else if(optional.get() == bt3){
			 System.out.println("b3-2클릭됨");
			 
		 }
		 	 
	 });
	 
	 
	 
//	 button[6].setOnAction(e->{
//		 DialogUtil.textInputDialog();
//	 });
//	 button[7].setOnAction(e->{
//		 DialogUtil.choiceDialog();
//	 });
	 
	 
	 VBox stack = new VBox(); 
//	 StackPane stack = new StackPane(); //Alert,confirm할때
	 stack.setAlignment(Pos.CENTER);
//	 stack.getChildren().add(button); //Alert,confirm할때
	 stack.getChildren().addAll(list);	
	 
	 Scene scene = new Scene(stack,300,250);
	 window.setScene(scene);
	 window.setTitle("Dialog Test");
	 window.show();
	 
	 
		
	}

}
