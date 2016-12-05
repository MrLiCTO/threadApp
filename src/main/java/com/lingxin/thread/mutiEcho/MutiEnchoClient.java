package com.lingxin.thread.mutiEcho;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/12/1.
 */
public class MutiEnchoClient {
    private static final ExecutorService tp = Executors.newCachedThreadPool();
    public static class ClientBranch implements Runnable{
        @Override
        public void run() {
            Socket client = null;
            PrintWriter writer = null;
            BufferedReader reader = null;
            try {
                client = new Socket();
                client.connect(new InetSocketAddress("localhost", 8000));
                writer = new PrintWriter(client.getOutputStream(), true);
                writer.println("hello");
                writer.println("嗨...");
                writer.flush();

                reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String str = reader.readLine();
                String str1 = reader.readLine();
                if (str != null) {
                    System.out.println("from Server :" + str);
                }
                if (str1 != null) {
                    System.out.println("from Server :" + str1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (reader != null) reader.close();
                    if (writer != null) writer.close();
                    if (client != null) client.close();
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            tp.execute(new ClientBranch());
        }
    }
}