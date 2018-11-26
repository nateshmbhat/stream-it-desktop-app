package com.streamit.mzaink;

//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class PlayConsoleController {

      @FXML
      private ListView<String> listView;

      @FXML
      private Button closeButton;

      private List<File> musicFiles;
      private ObservableList<String> musicFileNames;

      public void initialize() {
            musicFiles = Controller.getFilesFromFX();
            musicFileNames = FXCollections.observableArrayList();

            for (File f : musicFiles) {
                  musicFileNames.add(f.getName());
            }
            if (listView != null) {
                  listView.getStylesheets().add(getClass().getResource("/com/streamit/mzaink/res/listStyles.css").toExternalForm());
                  listView.setItems(musicFileNames);
                  /**
                   * Style sheets added
                   */
            } else
                  System.out.println("Empty list.");
      }

      public void setCloseButton() {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Sure you want to exit? Streaming shall stop!");
            alert.setContentText("We shall miss you!");
            Optional<ButtonType> buttonType = alert.showAndWait();
            if(buttonType.isPresent() && buttonType.get().getText().equals("OK")){
                  System.out.println("Button Ok pressed.");
                  System.exit(0);
            }
            else {

            }
      }

}
