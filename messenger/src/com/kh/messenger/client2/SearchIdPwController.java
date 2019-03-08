package com.kh.messenger.client2;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

import com.kh.messenger.common.Command;
import com.kh.messenger.common.MemberDTO;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SearchIdPwController implements Initializable {
	
	
//	Stage stage = null;
//	void setDialog(Stage stage) {
//		this.stage = stage;
//	}
	
	
	@FXML private Button searchid;
	@FXML private Button searchpw;
	
	@FXML private Label msg2; 
	@FXML private Label msg3; 
	
	@FXML private TextField searchTel;
	@FXML private DatePicker searchbirth;
	
	@FXML private TextField searchpwid;
	@FXML private TextField searchTel2;
	@FXML private DatePicker searchbirth2;

	private Protocol protocol;

	

	

	

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {	}


	
	
	// 아이디 조회
	public void handleFindId(ActionEvent e) {
		
		
		System.out.println("아이디조회");
		System.out.println("전화번호: " + searchTel.getText());
		System.out.println("생년월일: " + searchbirth.getValue().toString());
		
		protocol = new Protocol(this);
		protocol.findId(searchTel.getText(), searchbirth.getValue().toString());
		
		
	}
	
	
	

	//아이디조회 수신
	public void findId(Command command) {
		String id = (String)command.getResults().elementAt(0);
		
		
		System.out.println(command.getResults().elementAt(0));
		
		Platform.runLater(() -> {

		if( id!=null ) {
			
			System.out.println("아이디찾기 성공!!");
			msg2.setText("'" + id + "'" + "입니다.");
		}else {
			msg2.setText("해당하는 정보를 찾을 수 없습니다.");
		}
		
		});	
		
		

		
	}
	//비밀전호 조회 수신
	public void handleFindPw(ActionEvent e) {
		
		System.out.println("비밀번호조회버튼출력!!");	
		System.out.println("아이디: "+searchpwid.getText());
		System.out.println("전화번호: " + searchTel2.getText());
		System.out.println("생년월일: " + searchbirth2.getValue().toString());
		
		protocol = new Protocol(this);
		protocol.findPw(searchpwid.getText(), searchTel2.getText(), searchbirth2.getValue().toString());
		
		
	}
	

	//비밀번호 조회
	public void findPw(Command command) {
		
		String pw = (String)command.getResults().elementAt(0);
		System.out.println(command.getResults().elementAt(0));
		
		Platform.runLater(() -> {

			if( pw!=null ) {
				
				System.out.println("비밀번호찾기 성공!!");
				msg3.setText("'" + pw + "'" + "입니다.");
			}else {
				msg3.setText("해당하는 정보를 찾을 수 없습니다.");
			}
			
			});	
			
		
	}
	
	

	

}
