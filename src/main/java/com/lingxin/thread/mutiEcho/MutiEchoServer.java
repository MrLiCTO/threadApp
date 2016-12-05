package com.lingxin.thread.mutiEcho;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

/**
 * Created by Administrator on 2016/11/30.
 */
public class MutiEchoServer {
    private static final ExecutorService tp = Executors.newCachedThreadPool();

    static class HandleMsg implements Runnable {
        Socket clientSocket;

        public HandleMsg(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            BufferedReader is = null;
            PrintWriter os = null;

            try {
                is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                os = new PrintWriter(clientSocket.getOutputStream(),true);
                long s = System.currentTimeMillis();
                String inputLine = null;
                if((inputLine=is.readLine())!=null){
                    os.println(inputLine);
                }
                if((inputLine=is.readLine())!=null){
                    os.println(inputLine);
                }
                os.flush();
                long e = System.currentTimeMillis();
                System.out.println("spend:" + (e - s) + "ms");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (os != null)
                        os.close();
                    if (is != null)
                        is.close();
                    if (clientSocket != null)
                        clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ServerSocket enchoServer = null;
        Socket client = null;
        try {
            enchoServer = new ServerSocket(8000);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                client = enchoServer.accept();
                System.out.println("address:" + client.getRemoteSocketAddress() + "connect!");
                tp.execute(new HandleMsg(client));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
