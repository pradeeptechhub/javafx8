package com.patel.pradeep.login;

import com.patel.pradeep.model.User;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

//https://youtu.be/mNFWYzF_vHQ
//https://youtu.be/wII6ufsn82c
public class LoginFormValidation extends Application {

	private final static String My_PASS = "admin";
	private final static BooleanProperty GRANTED_ACCESS = new SimpleBooleanProperty(false);
	private final static int MAX_ATTEMPTS = 3;
	private final IntegerProperty ATTEMPTS = new SimpleIntegerProperty(0);

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		int weight = 18;

		// Create user
		User user = new User();
		// Create Transparent Stage
		// primaryStage.initStyle(StageStyle.TRANSPARENT);

		StackPane root = new StackPane();
		Scene scene = new Scene(root, 500, 300, Color.rgb(0, 0, 0, 0));

		Label label = new Label("Login Page");
		label.setTextFill(Color.BLUE);
		label.setFont(Font.font("SanSerif", FontWeight.BOLD, weight));

		Label labelUserName = new Label("User Name");
		labelUserName.setTextFill(Color.BLUE);
		labelUserName.setFont(Font.font("SanSerif", FontWeight.BOLD, weight));

		Label labelPassword = new Label("Password");
		labelPassword.setTextFill(Color.BLUE);
		labelPassword.setFont(Font.font("SanSerif", FontWeight.BOLD, weight));

		// userNameField holding user name
		TextField userNameField = new TextField();
		userNameField.setFont(Font.font("SanSerif", FontWeight.BOLD, weight));
		//public void bind(ObservableValue<? extends String> newObservable)
		//Create a unidirection binding for this Property. 
		//newObservable - The observable this Property should be bound to.
		user.userNameProperty().bind(userNameField.textProperty());

		// Password Field
		PasswordField passwordField = new PasswordField();
		passwordField.setFont(Font.font("SanSerif", FontWeight.BOLD, weight));
		user.passwordProperty().bind(passwordField.textProperty());

		// Login Page Layout
		GridPane loginLayout = new GridPane();
		
		loginLayout.setPadding(new Insets(15));
		//Adding Margins for each cell
		GridPane.setMargin(label, new Insets(15));
		GridPane.setMargin(labelUserName, new Insets(15));
		GridPane.setMargin(labelPassword, new Insets(15));
		GridPane.setMargin(userNameField, new Insets(15));
		GridPane.setMargin(passwordField, new Insets(15));
		loginLayout.setAlignment(Pos.CENTER);

		loginLayout.add(label, 0, 0, 2, 1); // ROW0
		GridPane.setHalignment(label, HPos.CENTER);

		loginLayout.add(labelUserName, 0, 1); // ROW1
		loginLayout.add(userNameField, 1, 1); // ROW1
		loginLayout.add(labelPassword, 0, 2); // ROW2
		loginLayout.add(passwordField, 1, 2); // ROW2

		loginLayout.setStyle("-fx-background-color:  #5DADEC;");
		loginLayout.setGridLinesVisible(true);

		root.getChildren().addAll(loginLayout);

		// When user hits enter key
		passwordField.setOnAction(e -> {
			if (GRANTED_ACCESS.get()) {
				System.out.printf("user %s is granted access.\n", user.userNameProperty().getValue());
				System.out.printf("user %s entered the password: %s \n", user.getUserName(), user.getPassword());
				// Platform.exit();
				MenuBarBand mbar = new MenuBarBand();
				mbar.setOrignalScene(scene);
				
				userNameField.clear();
				passwordField.clear();
				
				try {
					mbar.start(primaryStage);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			ATTEMPTS.set(ATTEMPTS.add(1).get());
			System.out.println("ATTEMPTS: " + ATTEMPTS.get());
		});

		// Listener when user types in the passwordField
		passwordField.textProperty().addListener((obs, ov, nv) -> {
			boolean granted = passwordField.getText().equals(My_PASS);
			GRANTED_ACCESS.set(granted);
		});

		// Listener on number of attempts
		ATTEMPTS.addListener((obs, ov, nv) -> {
			if (MAX_ATTEMPTS == nv.intValue()) {
				// failed attempts
				System.out.printf("User %s is denied access.\n", user.getUserName());
				Platform.exit();
			}
		});

		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
