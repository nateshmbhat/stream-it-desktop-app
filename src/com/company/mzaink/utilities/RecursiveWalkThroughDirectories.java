package com.company.mzaink.utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/*
     **This class aims at recursively going into the directories recursively and find all
     * the files with .mp4 extension and puts them into a ArrayList.
*/


// TODO : optimise the recusion method speed

public class RecursiveWalkThroughDirectories {
      private List<File> fileList = new ArrayList<>();

      //This function should be called to get folders.
      public List<File> getFiles(File mainDir) {
            if (mainDir.exists() && mainDir.isDirectory()) {
                  File arr[] = mainDir.listFiles();
//                  System.out.println("**************************");
//                  System.out.println("File from main directory : " + mainDir);
//                  System.out.println("**************************");
                  if (arr != null) {
//                        System.out.println("[ " + mainDir + " ]");
                        RecursiveWalk(mainDir.listFiles(), 0);
                  } else {
                        System.out.println("Empty file.");
                  }

                  return fileList;
            }

            return null;
      }

      //RecursiveWalk() recursively goes inside the directories and selects
      //all those files which have a .mp4 extension as of now.
      public void RecursiveWalk(File[] arr, int level) {
            for (File f : arr) {
//                  for (int i = 0; i < level; ++i)
//                        System.out.print("\t\t\t");

                  if (f.isDirectory() && (f.getName().charAt(0) != '.')) {
//                        System.out.println("[ " + f.getName() + " ]");
                        RecursiveWalk(f.listFiles(), level + 1);
                  } else if (f.isFile()) {
                        if (f.getName().charAt(0) != '.') {
//                              System.out.println(f.getName());
                              String[] extensions = f.getName().split("[.]");
                              if (containsMP3(extensions)) {
                                    fileList.add(f);
                              }
                        }
                  }
            }
      }


      private boolean containsMP3(String[] extensions) {
            for (String ext : extensions) {
                  if (ext.equals("mp3")) return true;
            }
            return false;
      }

      private boolean containsMP4(String[] extensions) {
            for (String ext : extensions) {
                  //**For Natesh***********************************
                  //**Make that extension as mp3.
                  //**Don't trouble any other code.
                  if (ext.equals("mp4")) return true;
            }

            return false;
      }
}
