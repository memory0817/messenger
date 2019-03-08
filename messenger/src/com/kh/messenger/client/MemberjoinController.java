package com.kh.messenger.client;


import java.net.URL;
import java.awt.Dialog;
import java.lang.*;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.kh.messenger.common.Command;
import com.kh.messenger.common.MemberDTO;
import com.kh.messenger.common.DialogUtil;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class MemberjoinController implements Initializable {

	@FXML private TextField id;
	@FXML private PasswordField pw;
	@FXML private PasswordField pwChk;
	@FXML private TextField tel;
	@FXML private TextField nickname;
	@FXML private ToggleGroup sex;
	@FXML private RadioButton sex1;
	@FXML private RadioButton sex2;
	@FXML private DatePicker birth;
	@FXML private ComboBox<String> region;
	@FXML private Label msg;

	

	

	MemberDTO memberDTO = new MemberDTO();
	Stage stage = null;
	Protocol protocol;
		
		
	void setDialog(Stage stage) {
		this.stage = stage;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sex.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				memberDTO.setGender(newValue.getUserData().toString());
			}
			
			
			
		});
		
	}
	
	
	
	
	public void memberjoin(Event e) {
		
		//필수입력값 : 아이디,비밀번호,닉네임,성별,생년월일,전화번호
		if(id.getText().trim().equals("")) {
			msg.setText("아이디를 입력바랍니다!");
			id.requestFocus();
			return;
		}
		if(pw.getText().trim().equals("")) {
			msg.setText("비밀번호를 입력바랍니다!");
			pw.requestFocus();
			return;
		}
		if(pwChk.getText().trim().equals("")) {
			msg.setText("비밀번호를 입력바랍니다!");
			pwChk.requestFocus();
			return;
		}
		
		if(!pw.getText().trim().equals(pwChk.getText().trim())) {
			msg.setText("비밀번호가 맞지않습니다!");
			pwChk.requestFocus();
			return;
		}
		
		if(nickname.getText().trim().equals("")) {
			msg.setText("닉네임을 입력바랍니다!");
			nickname.requestFocus();
			return;
		}
		if(tel.getText().trim().equals("")) {
			msg.setText("전화번호를 입력바랍니다!");
			tel.requestFocus();
			return;
		}
		
		
		
		

		
		


		
		//유효성 체크
		boolean isID = Pattern.matches("^([A-Za-z0-9_.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,3})$", id.getText());
		if(!isID) {
			msg.setText("아이디를 이메일 형식으로 입력하세요.");
			id.requestFocus();
			return;
		}
		
		if(Member.getInstance().containsKey(id.getText())) {
			msg.setText("아이디 중복 오류입니다.");
			id.requestFocus();
			return;
		}
		
					
		boolean isPW = Pattern.matches("^([A-Za-z0-9_.-]{4,16})$", pw.getText());
		if(!isPW) {
			msg.setText("비밀번호는 4-16자리의 영어,숫자,\n특수문자( - _ . )만 가능합니다");
			pw.requestFocus();
			return;
		}
		
		boolean isTel = Pattern.matches("(\\d{3})" + "-" + "(\\d{3,4})" + "-" + "(\\d{4})", tel.getText());
		if(!isTel) {
			msg.setText("휴대폰번호가 형식과 맞질 않습니다.");
			tel.requestFocus();
			return;
		}
		

		boolean isNickname = Pattern.matches("(^[a-zA-Z0-9]*[^!\\\"#$%&(){}@`*:+;\\-.<>,^~|'\\[\\]]{4,10}$)", nickname.getText());
		if(!isNickname) {
			msg.setText("닉네임은 4~10자리 문자,숫자만\n가능합니다.");
			nickname.requestFocus();
			return;
		}
		
		
		if(birth.getValue() == null) {
			msg.setText("생년월일을 형식에 맞게 입력바랍니다!");
			birth.requestFocus();
			return;
		}
		
		
		
		
		
		

		
		memberDTO.setId(id.getText());	
		memberDTO.setPw(pw.getText());	
		memberDTO.setTel(tel.getText());	
		memberDTO.setNickname(nickname.getText());	
	
		memberDTO.setRegion((String)region.getValue());	
		memberDTO.setBirth(birth.getValue().toString());	

		
		
		System.out.println(memberDTO);

		
		protocol = new Protocol(this);
		protocol.memberJoin(memberDTO);
		
		stage.close();
		
			
	}
	
	
	// 회원가입 결과
		public void memberJoin(Command command) {
			boolean flag =
					((Boolean)command.getResults().elementAt(0)).booleanValue();
			if(flag) {
				Platform.runLater(() ->{				
					DialogUtil.dialog(AlertType.INFORMATION, "알림", 
							"회원가입", "회원가입 성공.");				
				});
				
			}else {
				Platform.runLater(() ->{				
					DialogUtil.dialog(AlertType.ERROR, "알림", 
							"회원가입", "회원가입 실패.");				
				});
				
			}
			
		}
		
	
	public void membercancel(Event e) {
		
		stage.close();

		
		
	}
	
	
	
	
	
	
	
	
	
	
	

}
