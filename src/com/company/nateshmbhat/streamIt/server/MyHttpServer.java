package com.company.nateshmbhat.streamIt.server ;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

public class MyHttpServer {
    public static void main(String[] args) {
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(8090), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        server.createContext("/getFile", new GetFile());
        server.createContext("/getSongs", new GetSongs());
        server.setExecutor(null); // creates a default executor
        server.start();
    }


    static class GetSongs implements HttpHandler {
        @Override
        public void handle(HttpExchange reqres) throws IOException {

//          TODO : Call method to get a HashMap of all filenames and thier paths

            System.out.println("Running getSongs handler .. ");

            ArrayList<String> allSongs = new ArrayList<>();
            allSongs.add("C:\\Users\\Natesh\\Desktop\\ae watan.mp3");
            allSongs.add("C:\\Users\\Natesh\\Desktop\\ae watan.mp3");
            allSongs.add("C:\\Users\\Natesh\\Desktop\\ae watan.mp3");
            allSongs.add("C:\\Users\\Natesh\\Desktop\\ae watan.mp3");

            HashMap<String,String> postBody =  Utility.parsePostRequest(Utility.getPostRequestBody(reqres.getRequestBody()));
            print(postBody.toString())  ;

            OutputStream response = reqres.getResponseBody();
            String paths = allSongs.get(0);

            System.out.println("\n paths = " + paths);
            PrintWriter writer = new PrintWriter(response);

            reqres.sendResponseHeaders(200, paths.length());
            writer.write(paths);
            writer.close();
        }
    }


    static class GetFile implements HttpHandler {

        @Override
        public void handle(HttpExchange t) throws IOException {
            //Get requested file which was requested using the GET request

            print("Query params = " + t.getRequestURI().getQuery());
            print(Utility.getPostRequestBody(t.getRequestBody()));

            File file = new File("C:\\Users\\Natesh\\Desktop\\ae watan.mp3");
            print(file.toPath().toString());

            t.sendResponseHeaders(200, file.length());

            OutputStream os = t.getResponseBody();
            Files.copy(file.toPath(), os);
            os.close();
        }


        public static String getPostParamsString(HashMap<String, String> map) {
            String res = "";

            return res;
        }
    }

    public static void print(String s) {
        System.out.println(s);
    }

}

