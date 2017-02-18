package com.patel.pradeep.login;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MenuBarBand extends Application {

	private Scene orignalScene;
	
	public static void main(String[] args) {
		launch(args);
	}

	private FileChooser fileChooser;
	private File file;
	private Desktop desktop = Desktop.getDesktop();

	@Override
	public void start(Stage primaryStage) throws Exception {

		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 300, 250, Color.WHITE);

		MenuBar menuBar = new MenuBar();
		root.setTop(menuBar);

		// File Menu
		Menu fileMenu = new Menu("File");
		MenuItem item1 = new MenuItem("New Project		Ctrl+Shift+N");
		MenuItem item2 = new MenuItem("Open Single File");
		MenuItem item3 = new MenuItem("Open Multiple Files");

		fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
				new ExtensionFilter("All Files", "*.*"), new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
				new ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"));

		item2.setOnAction(e -> {
			// Single File Selection
			file = fileChooser.showOpenDialog(primaryStage);
			if (file != null) {
				try {
					desktop.open(file);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		item3.setOnAction(e -> {
			// Multiple File Selection
			List<File> fileList = fileChooser.showOpenMultipleDialog(primaryStage);
			if (fileList != null) {
				fileList.stream().forEach(selectedFiles -> {
					try {
						desktop.open(selectedFiles);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});
			}
		});

		MenuItem item4 = new MenuItem("LogOut");
		item4.setOnAction(e -> {
			if(getOrignalScene() != null){
				primaryStage.setScene(orignalScene);
				primaryStage.show();
			}
		});
		
		MenuItem item5 = new MenuItem("Exit");
		item5.setOnAction(e -> {
			Platform.exit();
		});
		fileMenu.getItems().addAll(item1, item2, item3, new SeparatorMenuItem(), item4, item5);

		// View Menu
		Menu viewMenu = new Menu("View");
		CheckMenuItem check1 = new CheckMenuItem("Editor");
		check1.setSelected(true);
		CheckMenuItem check2 = new CheckMenuItem("Line Number");
		check2.setSelected(true);
		viewMenu.getItems().addAll(check1, check2, new SeparatorMenuItem());

		// Create ToolBar
		Menu toolBar = new Menu("Toolbar");
		toolBar.getItems().addAll(new CheckMenuItem("File"), new CheckMenuItem("Run"), new CheckMenuItem("Debug"));
		viewMenu.getItems().add(toolBar);

		// Mode Menu
		Menu mode = new Menu("Mode");
		ToggleGroup toggleGroup = new ToggleGroup();
		RadioMenuItem mode1 = new RadioMenuItem("Desktop");
		mode1.setToggleGroup(toggleGroup);
		mode1.setSelected(true);
		RadioMenuItem mode2 = new RadioMenuItem("Mobile");
		mode2.setToggleGroup(toggleGroup);

		/*
		 * RadioButton mode1 = new RadioButton("Desktop");
		 * mode1.setToggleGroup(toggleGroup); mode1.setSelected(true);
		 * RadioButton mode2 = new RadioButton("Mobile");
		 * mode2.setToggleGroup(toggleGroup);
		 */

		mode.getItems().addAll(mode1, mode2);

		menuBar.getMenus().addAll(fileMenu, viewMenu, mode);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public Scene getOrignalScene() {
		return orignalScene;
	}

	public void setOrignalScene(Scene orignalScene) {
		this.orignalScene = orignalScene;
	}

}
