package com.streamit.mzaink.utilities;

import java.io.File;
import java.util.ArrayList;

public class FindFiles {
      private static ArrayList<File> fileList;

      public static ArrayList<File> getMusicFiles(File mainDir) {
            if (mainDir != null && mainDir.exists() && mainDir.isDirectory()) {
                  fileList = new ArrayList<>();

                  recursiveWalk(mainDir);

                  return fileList;
            }

            return null;
      }

      private static void recursiveWalk(File directory) {
            File[] subFiles = directory.listFiles();
            for (File f : subFiles) {
                  if (f.isFile() && (isMusicFile(f))) {
                        fileList.add(f);
                  } else if (f.isDirectory()) {
                        recursiveWalk(f);
                  }
            }
      }

      private static boolean isMusicFile(File fileName) {
            String[] extensions = fileName.getName().split("[.]");
            for (String e : extensions) {
                  switch (e.toLowerCase()) {
                        case "mp4":
                        case "mp3":
                        case "wav":
                        case "wma":
                        case "ogg":
                              return true;
                  }
            }
            return false;
      }
}
