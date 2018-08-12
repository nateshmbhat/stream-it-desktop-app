package com.streamit.nateshmbhat.streamIt.server;

import com.streamit.mzaink.Controller;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


import javax.rmi.CORBA.Util;
import javax.swing.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyHttpServer {
      private static int myPort = 8090;
      private static String myIp;
      private List<File> mp3Files = new ArrayList<>();

      public static void startServer() {
            print(File.separator);
            HttpServer server = null;

            try {
                  server = HttpServer.create(new InetSocketAddress(myPort), 0);
            } catch (IOException e) {
                  e.printStackTrace();
            }

            Utility.getAudioMetaData("C:\\Users\\Natesh\\Desktop\\ae watan testing .mp3") ;

            server.createContext("/getFile", new GetFile());
            server.createContext("/getSongs", new GetSongs());
            server.createContext("/getSongInfo", new GetSongInfo());
            server.setExecutor(null); // creates a default executor
            server.start();
      }

      static class GetSongInfo implements HttpHandler{
            @Override
            public void handle(HttpExchange httpExchange) throws IOException {

                  print("handling GetSongInfo") ;

                  OutputStream os = httpExchange.getResponseBody();

                  String queryParams = httpExchange.getRequestURI().getQuery();

                  print("Query params = " + queryParams);

                  String songMetadata = Utility.getAudioMetaData(queryParams) ;

                  PrintWriter printWriter = new PrintWriter(os) ;

                  httpExchange.sendResponseHeaders(200 , songMetadata.length());
                  printWriter.write(songMetadata) ;
                  printWriter.close();
            }
      }


      static class GetSongs implements HttpHandler {
            private List<File> mp3Files;

            @Override
            public void handle(HttpExchange reqres) throws IOException {

                  //Done.
                  if (Controller.getFilesFromFX() != null) {
                        mp3Files = Controller.getFilesFromFX();
                  }

                  System.out.println("Running getSongs handler .. ");

                  HashMap<String, String> postBody = Utility.parsePostRequest(Utility.getPostRequestBody(reqres.getRequestBody()));
                  print(postBody.toString());

                  OutputStream response = reqres.getResponseBody();
                  StringBuilder paths = new StringBuilder();

                  for (File songPath : mp3Files) {
                        paths.append(songPath.toPath().toString() + "\n");
                  }

                  System.out.println("\n paths String = " + paths);
                  PrintWriter writer = new PrintWriter(response);

                  reqres.sendResponseHeaders(200, paths.length());
                  writer.write(paths.toString());
                  writer.close();
            }
      }


      static class GetFile implements HttpHandler {

            @Override
            public void handle(HttpExchange t) throws IOException {
                  //Get requested file which was requested using the GET request
                  OutputStream os = t.getResponseBody();

                  String queryParams = t.getRequestURI().getQuery();

                  print("Query params = " + queryParams);


                  File file = new File(queryParams);
                  print(file.toPath().toString());

                  t.sendResponseHeaders(200, file.length());

                  Files.copy(file.toPath(), os);
                  os.close();
            }
      }

      public static void print(String s) {
            System.out.println(s);
      }

}

