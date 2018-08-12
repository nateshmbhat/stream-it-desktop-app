package com.streamit.mzaink;


import com.streamit.mzaink.utilities.FindFiles;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Controller {
      private static List<File> musicFiles;

      @FXML
      private DirectoryChooser directoryChooser;

      @FXML
      private GridPane mainGridPane;

      @FXML
      private Button continueButton;

      public void initialize() {
            musicFiles = new ArrayList<>();
            directoryChooser = new DirectoryChooser();
      }


      /**
       * This function handles the button click on continue button of the main window.
       * It first sets up a FileChooser for the user to select a folder in which he has stored his
       * music files.
       * <p>
       * Then go ahead and  do validation on his choice as described further on.
       **/
      public void handleContinueButtonClick() {
            setUpFileChooser();

            musicFiles = FindFiles.getMusicFiles(directoryChooser.showDialog(mainGridPane.getScene().getWindow()));

            /*
             *Check if the user pressed cancel button, if he did, flag an alert.
             * Else check if the folder/directory he selected didn't contain any songs, if so, flag an alert.
             * Else advance to the next stage and display all the music files so found.
             * */
            if (musicFiles == null) {
                  createAlert("No directory selected.",
                          "Select a directory to include music files.");
            } else if (musicFiles.isEmpty()) {
                  createAlert("No music files found.",
                          "Select another folder containing music files.");
            } else {
                  // TODO : Populate a ListView using the names of the music files.
                  //TODO : Job Done. Now work on optimizations.
                  for (File f : musicFiles) {
                        System.out.println(f.getName());
                  }

                  try {
//                        createPlayConsoleStage();
                        createPlayConsoleScene();
                  } catch (IOException e) {
                        e.printStackTrace();
                  }


            }
      }

      /**
       * Function to install a new scene onto an existing primaryStage built in the Main class.
       * This scene is called the Play Console and shall be used to display various functionality.
       * @throws IOException
       */

      public void createPlayConsoleScene() throws IOException{
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("PlayConsole.fxml"));

            Stage stage = (Stage) mainGridPane.getScene().getWindow();

            stage.setTitle("Play console");
            stage.setMaximized(true);
            stage.setScene(new Scene(root, stage.getWidth(), stage.getHeight()));
            stage.show();
      }


      /**
       * Function to create an alert box.
       **/
      private void createAlert(String title, String headerText) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(headerText);
            alert.showAndWait();
      }

      /**
       * Function to set up a file chooser
       **/
      private void setUpFileChooser() {
            directoryChooser.setTitle("Select the root folder you want to add to your playlist.");
            directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
      }

      /**
       * Function to be called from inside MyHttpServer*
       */
      public static List<File> getFilesFromFX() {
            if (musicFiles == null || musicFiles.isEmpty()) {
                  return null;
            } else {
                  return new ArrayList<>(musicFiles);
            }
      }
}
