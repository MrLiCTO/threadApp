package com.lingxin.thread.aioEnchoServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * Created by Administrator on 2016/12/9.
 */
public class AIOClient {
    public static void main(String[] args) throws Exception{
        final AsynchronousSocketChannel client=AsynchronousSocketChannel.open();
        client.connect(new InetSocketAddress(InetAddress.getLocalHost(),8000),null, new CompletionHandler<Void,Object>() {
            @Override
            public void completed(Void result, Object attachment) {
                client.write(ByteBuffer.wrap("heloo haha".getBytes()), null, new CompletionHandler<Integer, Object>() {
                    @Override
                    public void completed(Integer result, Object attachment) {
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        client.read(buffer, null, new CompletionHandler<Integer, Object>() {
                            @Override
                            public void completed(Integer result, Object attachment) {
                                buffer.flip();
                                System.out.println(new String(buffer.array()));

                                try {
                                    client.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void failed(Throwable exc, Object attachment) {
                                System.out.println("failed:"+exc);
                            }
                        });
                    }

                    @Override
                    public void failed(Throwable exc, Object attachment) {
                                System.out.println("failed:"+exc);
                    }
                });
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                                System.out.println("failed:"+exc);
            }
        });
        Thread.sleep(1000);
    }
}
