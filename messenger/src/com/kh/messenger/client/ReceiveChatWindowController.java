package com.kh.messenger.client;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.kh.messenger.common.Command;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class ReceiveChatWindowController implements Initializable{

	@FXML
	private TextFlow tfDisplay;
	@FXML
	private ColorPicker cpColor, cpColor2;
	@FXML
	private TextArea taMsg;
	@FXML
	private Button btnSend;
	@FXML
	private ScrollPane spRoll;

	@FXML
	private ComboBox<String> cbTextSize;
	private Color textColor, textBgColor;
	private double textSize;
	private List<String> tsize = 
			Arrays.asList("12", "13", "14", "15", "16", "17", "18", "19", "20");
	
	ClientServer.Client client;
	Stage dialog;
	
	String receiverID,senderID;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		cbTextSize.getItems().addAll(tsize);
		cbTextSize.getSelectionModel().select(0);

		// 글자색, 글자 크기 기본값
		textColor = Color.BLACK;
		textBgColor = Color.WHITE;
		
		cpColor.setValue(Color.BLACK);
		cpColor2.setValue(Color.WHITE);
		
		textSize = 13;
		tfDisplay.setLineSpacing(10); // 줄간격
		tfDisplay.setTextAlignment(TextAlignment.LEFT); // 텍스트 정렬
		tfDisplay.setBackground(new Background(
		new BackgroundFill(Color.WHITE,new CornerRadii(3),new Insets(0,0,0,0))));			
		
		spRoll.vvalueProperty().bind(tfDisplay.heightProperty());

		cpColor.setOnAction(e -> {
			textColor = cpColor.getValue();
		});
		cpColor2.setOnAction(e -> {
			textBgColor = cpColor2.getValue();
			tfDisplay.setBackground(new Background(
			new BackgroundFill(textBgColor,new CornerRadii(3),new Insets(0,0,0,0))));			
		});

		cbTextSize.setOnAction(e -> {
			textSize = Double.parseDouble(cbTextSize.getSelectionModel().getSelectedItem());
		});
		
		
		btnSend.setOnAction(event->{
			
			Command command = new Command(Command.CommandType.SENDMESSAGE);
			String args [] = {taMsg.getText(),receiverID,senderID};
			command.setArgs(args);
			
			Text text = new Text();
			String msg = receiverID+">>"+ taMsg.getText() + "\n";
			text.setText(msg);
			text.setFont(Font.font("나눔고딕", Double.valueOf(textSize)));
			text.setFill(textColor);
		
			tfDisplay.setPrefHeight(spRoll.getPrefHeight());		
			tfDisplay.getChildren().add(text);
					
			client.send(command);
			taMsg.clear();
			taMsg.requestFocus();	
		});

		//taMsg에서 enter키 눌렀을때
		taMsg.setOnKeyPressed(event->{
			if(event.getCode().equals(KeyCode.ENTER)) {
				btnSend.fire();
			}
		});		
		//taMsg에서 enter키 땟을때
		taMsg.setOnKeyReleased(event -> {
			if (event.getCode().equals(KeyCode.ENTER)) {
				taMsg.clear();
				taMsg.requestFocus();
			}
		});
		
		
		
	}

	public void display(String msg) {
		Text text = new Text();
		msg = msg + "\n";
		text.setText(msg);
		text.setFont(Font.font("나눔고딕", Double.valueOf(textSize)));
		text.setFill(textColor);
	
		tfDisplay.setPrefHeight(spRoll.getPrefHeight());		
		tfDisplay.getChildren().add(text);

	}
	
	public void  btnSendDisable(boolean status) {
		if(status) {
			btnSend.setDisable(true);
		}else {
			btnSend.setDisable(false);			
		}
	}

	public void setDialog(Stage dialog) {
		this.dialog = dialog;		
	}
	
	// 메시지수신
	public void receiveMsg(ClientServer.Client client,String message, String senderID, String receiverID) {
		this.client = client;
		this.senderID = senderID;
		this.receiverID = receiverID;
		
		Text text = new Text();
		String msg = senderID+">>"+ message + "\n";
		text.setText(msg);
		text.setFont(Font.font("나눔고딕", Double.valueOf(textSize)));
		text.setFill(textColor);
	
		tfDisplay.setPrefHeight(spRoll.getPrefHeight());		
		tfDisplay.getChildren().add(text);
		
	}

}

