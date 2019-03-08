package com.kh.messenger.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.kh.messenger.common.Command;
import com.kh.messenger.common.DialogUtil;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RootController implements Initializable {

	
	

	
	@FXML private Button cancel,login;
	@FXML private TextField id;
	@FXML private PasswordField pw;
	@FXML private Label msg; 
	
	Stage primaryStage;
	String loginId;
	
	public RootController() {

		
	}

	
	Parent memberJoinWindow = null;
	Parent messengerMainWindow = null;
	
	Protocol protocol = null;
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		
		//로그인 버튼 이벤트
		login.setOnAction(event->{
			

			
			System.out.println("로그인버튼출력!!");
			System.out.println("아이디: " + id.getText());
			System.out.println("비밀번호: " + pw.getText());
			
			
			login.setDisable(true);
			
			loginId = id.getText();
			protocol = new Protocol(this);
			
			
			
			protocol.isMember(id.getText(),pw.getText());
			Platform.setImplicitExit(false);
			
			

				
				
				
				
	   });
		cancel.setOnAction(event->{
			System.out.println("취소버튼클릭!!");
			id.clear();
			pw.clear();
			id.requestFocus();
		});
		
		
		
	}
		
		

	
	
	
	
		
		
		public void doMemjoin(Event e) throws IOException{
			
			Stage dialog = new Stage(StageStyle.DECORATED);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(primaryStage);
			dialog.setTitle("회원가입");
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("memjoin.fxml"));
			memberJoinWindow = loader.load();
			
			
			MemberjoinController controller = (MemberjoinController)(loader.getController());
			controller.setDialog(dialog);
			Scene scene = new Scene(memberJoinWindow);
			
			dialog.setScene(scene);
			dialog.setResizable(false);
			dialog.show();

		}
		
	public void searchId(Event e) throws IOException {

		Stage dialog = new Stage(StageStyle.DECORATED);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(login.getScene().getWindow());
		dialog.setTitle("아이디찾기");

		Parent parent = FXMLLoader.load(getClass().getResource("findId.fxml"));
		Button findidCloseBtn = (Button)parent.lookup("#findidCloseBtn");
		
		
		Scene scene = new Scene(parent);

		dialog.setScene(scene);
		dialog.setResizable(false);
		dialog.show();
		findidCloseBtn.setOnAction(event -> dialog.close());

	}

	public void searchPw(Event e) throws IOException {
		
		

		Stage dialog = new Stage(StageStyle.DECORATED);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(login.getScene().getWindow());
		dialog.setTitle("비밀번호찾기");

		Parent parent = FXMLLoader.load(getClass().getResource("findPw.fxml"));
		Button findpwCloseBtn = (Button)parent.lookup("#findpwCloseBtn");
		
		Scene scene = new Scene(parent);

		dialog.setScene(scene);
		dialog.setResizable(false);
		dialog.show();
		findpwCloseBtn.setOnAction(event -> dialog.close());
		

		


	}
	
	

	public void setPrimaryStage(Stage primaryStage) {

		this.primaryStage=primaryStage;
		
	}









	public void doLogin(Command command) {
		

		
		int status = command.getResults().getStatus();
		
		//로그인 버튼 활성화
		Platform.runLater(()->{
			
			login.setDisable(false);
			
		});
		
		
		//중복로긴
		if(status == -1) {
			String ip = (String)command.getResults().elementAt(0);
			Platform.runLater(() ->{
			DialogUtil.dialog(AlertType.ERROR, "중복로그인", "중복로그인은 불가능합니다. 접근IP정보 : "+ip, null);
			});
			return;
		}
		
		boolean flag =
				((Boolean)command.getResults().elementAt(0)).booleanValue();
		//정상 로그인
		if(flag) {
			Platform.runLater(() ->{
				
				Stage dialog = new Stage(StageStyle.UTILITY);
//				dialog.initModality(Modality.WINDOW_MODAL);
//				dialog.initOwner(primaryStage);
				dialog.setTitle("메신져 메인");
				primaryStage.hide();
				
				FXMLLoader loader = new FXMLLoader(getClass().getResource("messengerMain.fxml"));
				try {
					messengerMainWindow = loader.load();
				} catch (IOException e) { }
				
				MessengerMainController controller = (MessengerMainController)(loader.getController());
				controller.setDialog(primaryStage, dialog);
				controller.setInitial(loginId,protocol);
				protocol.setMessengerMainController(controller);
				Scene scene = new Scene(messengerMainWindow);
				
				dialog.setScene(scene);
				dialog.setResizable(false);
				dialog.show();
				
				login.setDisable(false);
				primaryStage.close();
				
				dialog.setOnCloseRequest(e -> {
					
					
					dialog.close();
					primaryStage.show();
					protocol.stopClient();
					
				});
				

				});

			
			
		// 유효한 로그인 아닌경우	
		}else {
			Platform.runLater(() ->{
				
			login.setDisable(false);
			DialogUtil.dialog(AlertType.ERROR, "알림", 
					"계정확인", "등록되지않은 ID 혹은 \n Password가 올바르지않습니다.");	
			protocol.stopClient();
				
				
			});
		}
		
		
	}

	public void loginBtnDisable(boolean status) {
		login.setDisable(status);
		
	}
	







	
	
	
	}
	
	
	
	
	




