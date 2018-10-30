package com.streamit.mzaink;

//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.io.File;
import java.util.List;

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
                  listView.setItems(musicFileNames);
                  /**
                   * Style sheets added
                   */
//                  listView.getStylesheets().add(getClass().getResource("listStyles.css").toExternalForm());
            } else
                  System.out.println("Empty list.");
      }

      public void setCloseButton() {
            System.exit(0);
      }

}
