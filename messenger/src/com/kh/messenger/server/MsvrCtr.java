package com.kh.messenger.server;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class MsvrCtr implements Initializable {
	
	
	@FXML private Button btnStartStop;
	@FXML private TextArea taLog;
	
	private Stage primaryStage;
	
	//실제 메신저 서버의 인스턴스
	private MSvr msvr = new MSvr(this);
	
	@Override
	//로더 실행될때 생성
	public void initialize(URL location, ResourceBundle resources) {
		// 바로 구현안하고 메소드로 빼서 구현
		btnStartStop.setOnAction(event -> doBtnStartStop(event));

	}
	
	public void doBtnStartStop(ActionEvent event) {
		if(btnStartStop.getText().equals("서버시작")) {
			msvr.startServer();
		} else {
			msvr.stopServer();
		}
		
	}
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
		
	}

	public void uiUpdate(UiCommand uiCommand, String msg) {
		switch(uiCommand) {
		case SERVER_START:
			Platform.runLater(()->{
				displayText("[서버시작]");
				btnStartStop.setText("서버종료");
			});
			break;
		case SERVER_STOP:
			Platform.runLater(()->{
				displayText("[서버종료]");
				btnStartStop.setText("서버시작");
			});
			break;
		case SERVER_LOG:
			Platform.runLater(()->displayText(msg));
		}
		
	}

	private void displayText(String msg) {
		taLog.appendText(msg + "\n");
		
	}

}
