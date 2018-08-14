package com.streamit.mzaink;

//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.File;
import java.util.List;

public class PlayConsoleController {

      @FXML
      private ListView<String> listView;

      private List<File> musicFiles;
      private ObservableList<String> musicFileNames;

      public void initialize() {
            musicFiles = Controller.getFilesFromFX();
            musicFileNames = FXCollections.observableArrayList();

            for (File f : musicFiles) {
                  musicFileNames.add(f.getName());
            }
            if (listView != null)
                  listView.setItems(musicFileNames);
            else
                  System.out.println("Empty list.");
      }

}
