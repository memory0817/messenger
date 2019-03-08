package com.kh.messenger.sample;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class ChatController implements Initializable {
	


    @FXML  private TextFlow tfDisplay;
    @FXML  private ColorPicker cpColor;
    @FXML  private TextArea taMsg;
    @FXML  private Button btnSend;
    @FXML  private ScrollPane spRoll;
    @FXML  private ComboBox<String> cbTextSize;
    
    private Color textColor;
    private double textSize;
    private List<String> tsize
    			= Arrays.asList("12","13","14","15","16","17","18","19","20");
    
       
    
    ChatController(String msg){
    	System.out.println("생성자 호출됨!!" + msg);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		cbTextSize.getItems().addAll(tsize);
		cbTextSize.getSelectionModel().select(0);
		
		//글자색, 글자 기본크기값
		textColor = Color.BLACK; 
		cpColor.setValue(Color.BLACK);
		tfDisplay.setLineSpacing(2); //줄간격
		tfDisplay.setTextAlignment(TextAlignment.RIGHT);//텍스트정렬
		spRoll.vvalueProperty().bind(tfDisplay.heightProperty());
		cpColor.setOnAction(e->{
			
			textColor = cpColor.getValue();			
			
		});
		
		btnSend.setOnAction(e -> {
			Text text = new Text();			
			String msg = taMsg.getText()+"\n";
			text.setText(msg);			
			text.setFont(Font.font("Verdana",FontWeight.BOLD,19));
			text.setFill(textColor);			
			tfDisplay.getChildren().add(text);
			
			taMsg.clear();
			taMsg.requestFocus();
			
			
		});
		
		
		taMsg.setOnKeyPressed(event -> {
		
		if(event.getCode().equals(KeyCode.ENTER)) {
		btnSend.fire();
		}
		
	});
	
	
	taMsg.setOnKeyReleased(event -> {
		
		if (event.getCode().equals(KeyCode.ENTER)) {
			taMsg.clear();
			taMsg.requestFocus();
		}
		
	});
		
		

	}

}
