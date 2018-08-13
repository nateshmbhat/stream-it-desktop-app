package com.streamit.nateshmbhat.streamIt.server;

import com.streamit.mzaink.Controller;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


import javax.rmi.CORBA.Util;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyHttpServer {
    private static int myPort = 8090;
    private static String myIp;

    public static int getMyPort() {
        return myPort;
    }

    public static String getMyIp() {
        return myIp;
    }

    public static String getLocalIp() {
        String ip = "";
        try (final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            ip = socket.getLocalAddress().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ip;
    }


    public static void startServer() {
        print(File.separator);
        HttpServer server = null;

        try {
            server = HttpServer.create(new InetSocketAddress(myPort), 0);
            myIp = getLocalIp() ;
            print("Server started at : " + myIp + " , port = " + myPort ) ;
        } catch (IOException e) {
            e.printStackTrace();
        }

        server.createContext("/getFile", new GetFile());
        server.createContext("/ping", new PingClass());
        server.createContext("/getSongs", new GetSongs());
        server.createContext("/getSongInfo", new GetSongInfo());
        server.setExecutor(null); // creates a default executor
        server.start();
    }


    static class PingClass implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {

            String response = myIp + ":" + myPort;
            httpExchange.sendResponseHeaders(200, response.length()) ;

            PrintWriter printWriter = new PrintWriter(httpExchange.getResponseBody()) ;
            printWriter.write(response);
            printWriter.close();
        }
    }


    static class GetSongInfo implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {

            print("handling GetSongInfo");

            OutputStream os = httpExchange.getResponseBody();

            String queryParams = httpExchange.getRequestURI().getQuery();

            print("Query params = " + queryParams);

            String songMetadata = Utility.getAudioMetaData(queryParams);

            PrintWriter printWriter = new PrintWriter(os);

            httpExchange.sendResponseHeaders(200, songMetadata.length());
            printWriter.write(songMetadata);
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

