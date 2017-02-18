package com.patel.pradeep.login;

import com.patel.pradeep.model.User2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

//https://youtu.be/mNFWYzF_vHQ
//https://youtu.be/wII6ufsn82c
public class LoginFormValidation2 extends Application {

	private final static String My_PASS = "admin";
	private final static BooleanProperty GRANTED_ACCESS = new SimpleBooleanProperty(false);
	private final static int MAX_ATTEMPTS = 3;
	private final IntegerProperty ATTEMPTS = new SimpleIntegerProperty(0);

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Create user
		User2 user = new User2();
		// Create Transparent Stage
		primaryStage.initStyle(StageStyle.TRANSPARENT);

		Group root = new Group();
		Scene scene = new Scene(root, 320, 112, Color.rgb(0, 0, 0, 0));

		Color foreground = Color.rgb(255, 255, 255, 0.9);
		// Rectangle background
		Rectangle background = new Rectangle(320, 112);
		background.setX(0);
		background.setY(0);
		background.setArcHeight(15);
		background.setArcWidth(15);
		background.setFill(Color.rgb(0, 0, 0, 0.55));
		background.setStroke(foreground);

		// ReadOnly field holding user name
		Text userName = new Text();
		userName.setFont(Font.font("SanSerif", FontWeight.BOLD, 30));
		userName.setFill(foreground);
		userName.setSmooth(true);
		userName.textProperty().bind(user.userNameProperty());

		// Text Node
		HBox userNameCell = new HBox();
		userNameCell.prefWidthProperty().bind(primaryStage.widthProperty().subtract(45));
		userNameCell.getChildren().add(userName);

		// PadLock
		SVGPath padLock = new SVGPath();
		padLock.setFill(foreground);
		padLock.setContent("M17.308,7.564h-1.993c0-2.929-2.385-5.314-5.314-5.314S4.686,4.635,4.686,"
				+ "7.564H2.693c-0.244,0-0.443,0.2-0.443,0.443v9.3c0,0.243,0.199,0.442,0.443,"
				+ "0.442h14.615c0.243,0,0.442-0.199,0.442-0.442v-9.3C17.75,7.764,17.551,7.564,17.308,"
				+ "7.564 M10,3.136c2.442,0,4.43,1.986,4.43,4.428H5.571C5.571,5.122,7.558,3.136,10,3.136 M16.865,"
				+ "16.864H3.136V8.45h13.729V16.864z M10,10.664c-0.854,0-1.55,0.696-1.55,1.551c0,0.699,0.467,"
				+ "1.292,1.107,1.485v0.95c0,0.243,0.2,0.442,0.443,0.442s0.443-0.199,0.443-0.442V13.7c0.64-0.193,"
				+ "1.106-0.786,1.106-1.485C11.55,11.36,10.854,10.664,10,10.664 M10,12.878c-0.366,"
				+ "0-0.664-0.298-0.664-0.663c0-0.366,0.298-0.665,0.664-0.665c0.365,0,0.664,0.299,0.664,0.665C10.664,"
				+ "12.58,10.365,12.878,10,12.878");

		// First Row
		HBox row1 = new HBox();
		row1.getChildren().addAll(userNameCell, padLock);

		// Password Field
		PasswordField passwordField = new PasswordField();
		passwordField.setFont(Font.font("SanSerif", 20));
		passwordField.setStyle("-fx-text-fill:black;" + "-fx-prompt-text-fill:gray;" + "-fx-highlight-text-fill:black;"
				+ "-fx-highlight-fill:gray;" + "-fx-background-color:rgba(255,255,255,0.8)");
		passwordField.prefWidthProperty().bind(primaryStage.widthProperty().subtract(55));
		user.passwordProperty().bind(passwordField.textProperty());

		// Denied
		SVGPath deniedIcon = new SVGPath();
		deniedIcon.setFill(Color.rgb(255, 0, 0, 0.9));
		deniedIcon.setContent("M15.898,4.045c-0.271-0.272-0.713-0.272-0.986,0l-4.71,"
				+ "4.711L5.493,4.045c-0.272-0.272-0.714-0.272-0.986,0s-0.272,0.714,0,0.986l4.709,"
				+ "4.711l-4.71,4.711c-0.272,0.271-0.272,0.713,0,0.986c0.136,0.136,0.314,0.203,0.492,"
				+ "0.203c0.179,0,0.357-0.067,0.493-0.203l4.711-4.711l4.71,4.711c0.137,0.136,0.314,"
				+ "0.203,0.494,0.203c0.178,0,0.355-0.067,0.492-0.203c0.273-0.273,0.273-0.715,"
				+ "0-0.986l-4.711-4.711l4.711-4.711C16.172,4.759,16.172,4.317,15.898,4.045z");
		deniedIcon.setStroke(Color.WHITE);
		deniedIcon.setVisible(false);

		// Granted Icon
		SVGPath grantedIcon = new SVGPath();
		grantedIcon.setFill(Color.rgb(255, 0, 0, 0.9));
		grantedIcon.setContent("M7.629,14.566c0.125,0.125,0.291,0.188,0.456,0.188c0.164,0,"
				+ "0.329-0.062,0.456-0.188l8.219-8.221c0.252-0.252,0.252-0.659,"
				+ "0-0.911c-0.252-0.252-0.659-0.252-0.911,0l-7.764,7.763L4.152,"
				+ "9.267c-0.252-0.251-0.66-0.251-0.911,0c-0.252,0.252-0.252,0.66,0,0.911L7.629,14.566z");
		grantedIcon.setStroke(Color.WHITE);
		grantedIcon.setVisible(false);
		
		StackPane accessIndicator = new StackPane();
		accessIndicator.getChildren().addAll(deniedIcon,grantedIcon);
		accessIndicator.setAlignment(Pos.CENTER_RIGHT);
		grantedIcon.visibleProperty().bind(GRANTED_ACCESS);

		// Second Row
		HBox row2 = new HBox();
		row2.getChildren().addAll(passwordField, accessIndicator);
		HBox.setHgrow(accessIndicator, Priority.ALWAYS);
		
		//When user hits enter key
		passwordField.setOnAction(e -> {
			if(GRANTED_ACCESS.get()){
				System.out.printf("user %s is granted access.\n", user.userNameProperty().getValue());
				System.out.printf("user %s entered the password: %s \n", user.getUserName(),user.getPassword());
				Platform.exit();
			}else{
				deniedIcon.setVisible(true);
			}
			ATTEMPTS.set(ATTEMPTS.add(1).get());
			System.out.println("ATTEMPTS: "+ATTEMPTS.get());
		});
		
		//Listener when user types in the passwordField
		passwordField.textProperty().addListener((obs,ov,nv)->{
			boolean granted = passwordField.getText().equals(My_PASS);
			GRANTED_ACCESS.set(granted);
			if(granted){
				deniedIcon.setVisible(false);
			}
		});
		
		//Listener on number of attempts
		ATTEMPTS.addListener((obs,ov,nv)->{
			if(MAX_ATTEMPTS == nv.intValue()){
				//failed attempts
				System.out.printf("User %s is denied access.\n", user.getUserName());
				Platform.exit();
			}
		});
		
		VBox formLayout = new VBox();
		formLayout.getChildren().addAll(row1,row2);
		formLayout.setLayoutX(12);
		formLayout.setLayoutY(12);
		root.getChildren().addAll(background,formLayout);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
