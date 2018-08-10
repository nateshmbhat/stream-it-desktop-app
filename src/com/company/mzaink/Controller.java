package com.company.mzaink;


import com.company.mzaink.utilities.RecursiveWalkThroughDirectories;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.util.List;



public class Controller {
      private static  List<File> fileList;

      @FXML
      private DirectoryChooser directoryChooser;
      @FXML
      private GridPane mainGridPane;

      private RecursiveWalkThroughDirectories recursivePrintClass;

      public void initialize() {
            recursivePrintClass = new RecursiveWalkThroughDirectories();
      }


      //This function handles the button click on continue button of the main window.

      public void handleContinueButtonClick() {
            directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select the root folder you want to add to your playlist.");
            directoryChooser.setInitialDirectory(new File(System.getProperty("user.home"))) ;
            fileList = recursivePrintClass.getFiles(directoryChooser.showDialog(mainGridPane.getScene().getWindow()));
            System.out.println("******************************************************");
            if (fileList != null) {
                  for (File f : fileList) {
                        System.out.println(f.getName());
                  }
            }
      }

      public static List<File> getFilesFromFX() {
            return fileList;
      }
}
