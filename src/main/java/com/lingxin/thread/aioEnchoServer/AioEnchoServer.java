package com.lingxin.thread.aioEnchoServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.SelectionKey;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2016/12/8.
 */
public class AioEnchoServer {
    public static void main(String[] args) throws Exception{
        new AioEnchoServer().start();
        while (true){
            Thread.sleep(1000);
        }
    }

    public final static int PORT = 8000;
    private AsynchronousServerSocketChannel server;

    public AioEnchoServer() throws IOException {
        this.server = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(InetAddress.getLocalHost(),PORT));
    }

    public void start() throws ExecutionException, InterruptedException, TimeoutException {
        System.out.println("Server listen on " + PORT);
        server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            final ByteBuffer buffer = ByteBuffer.allocate(1024);

            @Override
            public void completed(AsynchronousSocketChannel result, Object attachment) {
                System.out.println(Thread.currentThread().getName());
                Future<Integer> writeResult = null;
                try {
                    buffer.clear();
                    result.read(buffer).get(100, TimeUnit.SECONDS);
                    buffer.flip();
                    writeResult = result.write(buffer);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        server.accept(null, this);
                        writeResult.get();
                        result.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("failed:"+exc);
            }
        });

    }

}
