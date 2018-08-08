package com.company.nateshmbhat.streamIt.server;

import java.net.*;
import java.io.*;

class Main extends Thread {
    private ServerSocket serverSocket;

    public Main(int port) throws IOException {
        serverSocket = new ServerSocket(port);
//        serverSocket.setSoTimeout(10000);
    }

    public void run() {
        System.out.println("Waiting for client on port " +
                serverSocket.getLocalPort() + "...");
        Socket server = null;
        try {
            server = serverSocket.accept();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        System.out.println("Just connected to " + server.getRemoteSocketAddress());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream((server.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        PrintWriter out = null;
        try {
            out = new PrintWriter(server.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] data = new byte[1024];
        String msg = new String(data);

        while (true) {
            try {
                in.read(data);
                if (msg.isEmpty()) break;

                System.out.println(new String(data));

                out.println(msg.toUpperCase()); ;
                out.flush();

            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args) {

        Thread t = null;
        try {
            t = new Main(3230);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        t.start();
    }
}