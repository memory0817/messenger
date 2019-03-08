package com.kh.messenger.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//임포트하나 컴파일러생겨서 지움 11.13 diologutil
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DialogUtil {
	//alert1~4
	public static Optional<ButtonType> dialog(AlertType type, String title, String headerText, String contentText) {
		
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.getDialogPane().setStyle(
				
				"-fx-background-color:#d9dee0;"
			+	"-fx-font-family: '나눔고딕';"
			+	"-fx-text-color:#d9dee0;"
			+ "-fx-max-width:280px; -fx-max-height:200px;"
			+ "-fx-pref-width:280px; -fx-max-height:200px;");
		Optional<ButtonType> optional = alert.showAndWait();
		return optional;
		
	}
	
	
/*	public static void alert() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("정보");
		alert.setHeaderText("친구 정보");
		alert.setContentText("부산/남/28세");
		alert.showAndWait();
		
	}
	
	public static void alert2() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("정보");
		alert.setHeaderText("친구 정보");
		alert.setContentText("부산/남/28세");
		alert.showAndWait();
		
	}
	
	
	
	public static void alert3() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("정보");
		alert.setHeaderText("친구 정보");
		alert.setContentText("부산/남/28세");
		alert.showAndWait();
		
	}
	
	
	public static void alert4() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("정보");
		alert.setHeaderText("친구 정보");
		alert.setContentText("부산/남/28세");
		alert.showAndWait();
		
	} */ 
	
	
	//alert5 AlertType.NONE 사용자정의 버튼 추가용
	public static Optional<ButtonType> dialog(String title, String headerText, String contentText, ButtonType... btype) {
		
		Alert alert = new Alert(AlertType.NONE,contentText,btype);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
//		alert.setContentText(contentText);
		alert.getDialogPane().setStyle(
				
				
				"-fx-background-color:#d9dee0;"
			+	"-fx-font-family: '나눔고딕';"
			+	"-fx-text-color:#d9dee0;"
			+ "-fx-max-width:280px; -fx-max-height:200px;"
			+ "-fx-pref-width:280px; -fx-max-height:200px;");
		Optional<ButtonType> optional = alert.showAndWait();
		return optional;
		
		
	}
	
/*	public static void alert5() {
		Alert alert = new Alert(AlertType.NONE,"test??",
								ButtonType.YES,ButtonType.NO,ButtonType.CLOSE,
								ButtonType.APPLY,ButtonType.CANCEL,
								ButtonType.FINISH,ButtonType.NEXT,ButtonType.PREVIOUS);
		alert.setTitle("정보");
		alert.setHeaderText("친구 정보");
		alert.setContentText("부산/남/28세");
		
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.YES) {
			System.out.println("yes");
		}else if(result.get() == ButtonType.NO) {
			System.out.println("no");
		}else if(result.get() == ButtonType.CLOSE) {
			alert.close();
		}
		
		
	}*/
	
	
	   //alert6 confirmation 사용자정의 버튼추가용
	public static Optional<ButtonType> dialog(String title, String headerText, String contentText, List<ButtonType> list) {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.getDialogPane().setStyle(
				
				"-fx-background-color:#d9dee0;"
			+	"-fx-font-family: '나눔고딕';"
			+	"-fx-text-color:#d9dee0;"
			+ "-fx-max-width:280px; -fx-max-height:200px;"
			+ "-fx-pref-width:280px; -fx-max-height:200px;");
		
		alert.getButtonTypes().setAll(list);
		
		Optional<ButtonType> optional = alert.showAndWait();
		return optional;
		
		
	}

	
	
	
/*	public static void alert6() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("정보");
		alert.setHeaderText("친구 정보");
		alert.setContentText("부산/남/28세");
		
		
		ButtonType buttonType1 = new ButtonType("1");
		ButtonType buttonType2 = new ButtonType("2");
		ButtonType buttonType3 = new ButtonType("3");
		ButtonType buttonType4 = new ButtonType("4");

		
		alert.getButtonTypes().setAll(buttonType1,buttonType2,buttonType3,buttonType4);
		
		
		
		
		
		
		
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == buttonType1) {
			System.out.println("button1클릭됨");
		}else if(result.get() == buttonType2){
			System.out.println("button2클릭됨");
		}else if(result.get() == buttonType3) {
			System.out.println("button3클릭됨");
		}else if(result.get() == buttonType4) {
			System.out.println("button4클릭됨");
		}else {
			System.out.println("error");
		}
		
	}*/
		
	
	public static String textInputDialog(String defaultText,String title, String headerText, String contentText) {
		TextInputDialog dialog = new TextInputDialog(defaultText);
		dialog.setTitle(title);
		dialog.setHeaderText(headerText);
		dialog.setContentText(contentText);
		dialog.getDialogPane().setStyle(
				
			"-fx-background-color:#000000;"
		+	"-fx-font-family: '나눔고딕';"
		+ "-fx-max-width:280px; -fx-max-height:200px;"
		+ "-fx-pref-width:280px; -fx-max-height:200px;"
				
				
		);
		
		Optional<String> result = dialog.showAndWait();
		if(result.isPresent()) {
			return result.get();
		}else {
		return "";
		}
	}
		
	
	
/*	public static void textInputDialog() {
		TextInputDialog dialog = new TextInputDialog("친구아이디입력!!");
		dialog.setTitle("정보");
		dialog.setHeaderText("친구 정보");
		dialog.setContentText("마산/남/20세");
		dialog.getDialogPane().setStyle(
				
			"-fx-background-color:yellow;"
		+ "-fx-max-width:280px; -fx-max-height:200px;"
		+ "-fx-pref-width:280px; -fx-pref-height:200px;"
				
				
		);
		
		Optional<String> result = dialog.showAndWait();
		if(result.isPresent()) {
			System.out.println("친구아이디:" + result.get());
		}
		result.ifPresent(name->System.out.println("친구아이디:" + result.get()));
		
	}*/
	
	public static String choiceDialog(
			List<String> choices, String defaultText, String title, String headerText, String contentText) {
		
		
		ChoiceDialog<String> dialog = 
				new ChoiceDialog<String>(defaultText, choices);
		
		dialog.setTitle(title);
		dialog.setHeaderText(headerText);
		dialog.setContentText(contentText);
		dialog.getDialogPane().setStyle(
				
			"-fx-background-color:#000000;"
		+	"-fx-font-family: '나눔고딕';"
		+ "-fx-max-width:280px; -fx-max-height:200px;"
		+ "-fx-pref-width:280px; -fx-pref-height:200px;"
				
				
		);
		
		Stage stage = (Stage)dialog.getDialogPane()
									.getScene()
									.getWindow();
		
		stage.getIcons().add(
				new Image(DialogUtil.class
						            .getResource("img/6.png")
						            .toString())				
		);
		
		
		Optional<String> result = dialog.showAndWait();
				
		if(result.isPresent()) {
			return result.get();
		}else {
		return null;
		}
	}
	
	
//	public static void main(String[] args) {
//		
//		
//		Platform.runLater(()->{
//		String answer = 
//				DialogUtil.textInputDialog("test", "회원탈퇴", "회원탈퇴를 하시겠습니까?", "test2");
//		System.out.println(answer);	
//	
//	
//	});
	
/*	public static void choiceDialog() {
		
		List<String> choices = new ArrayList<>();
		choices.add("친구1");
		choices.add("친구2");
		choices.add("친구3");
		choices.add("친구4");
		
		ChoiceDialog<String> dialog = 
				new ChoiceDialog<String>("친구3", choices);
		
		dialog.setTitle("정보");
		dialog.setHeaderText("친구 정보");
		dialog.setContentText("마산/남/20세");
		dialog.getDialogPane().setStyle(
				
			"-fx-background-color:yellow;"
		+ "-fx-max-width:280px; -fx-max-height:200px;"
		+ "-fx-pref-width:280px; -fx-pref-height:200px;"
				
				
		);
		
		Stage stage = (Stage)dialog.getDialogPane()
									.getScene()
									.getWindow();
		
		stage.getIcons().add(
				new Image(DialogUtil.class
						            .getResource("img/6.png")
						            .toString())				
		);
		
		
		Optional<String> result = dialog.showAndWait();
		if(result.isPresent()) {
			System.out.println("친구아이디:" + result.get());
		}
		
	}*/
	

}
