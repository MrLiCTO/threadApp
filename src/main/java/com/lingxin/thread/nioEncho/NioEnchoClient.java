package com.lingxin.thread.nioEncho;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/12/8.
 */
public class NioEnchoClient {
    private Selector selector;
    private ExecutorService tp = Executors.newCachedThreadPool();

    public static void main(String[] args) throws Exception{
        new NioEnchoClient().start("192.168.1.177",8000);
    }

    public void start(String ip, int port)throws Exception{
        init(ip,port);
        work();
    }

    private void init(String ip, int port) throws Exception {
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        this.selector = SelectorProvider.provider().openSelector();
        channel.connect(new InetSocketAddress(ip, port));
        channel.register(selector, SelectionKey.OP_CONNECT);
    }

    private void work() throws Exception {
        for (; ; ) {
            if (!selector.isOpen())
                break;
            selector.select();
            Iterator<SelectionKey> itera = selector.selectedKeys().iterator();
            while (itera.hasNext()) {
                SelectionKey selectionKey = itera.next();
                itera.remove();

                if (selectionKey.isConnectable()) {
                    connect(selectionKey);
                } else if (selectionKey.isReadable()) {
                    read(selectionKey);
                } else if (selectionKey.isWritable()) {
                    write(selectionKey);
                }
            }
        }
    }

    private void connect(SelectionKey sk) {
        SocketChannel ssc = (SocketChannel) sk.channel();
        try {
            if (ssc.isConnectionPending()) {
                ssc.finishConnect();
            }
            SelectionKey clientKey = ssc.register(selector, SelectionKey.OP_WRITE);
            EnchoClient enchoClient = new EnchoClient();
            clientKey.attach(enchoClient);

            InetAddress ia = ssc.socket().getInetAddress();
            System.out.println(ia.getHostAddress() +"has connected " + "...");
        } catch (IOException e) {
            System.out.println("connected failed" + "...");
            e.printStackTrace();
        }

    }

    private void write(SelectionKey sk) throws Exception{
        SocketChannel channel = (SocketChannel)sk.channel();
        channel.write(ByteBuffer.wrap("hello 哈哈哈哈....".getBytes()));
        channel.register(selector,SelectionKey.OP_READ);
    }

    private void read(SelectionKey sk) throws Exception{
        SocketChannel channel = (SocketChannel) sk.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        channel.read(buffer);
        String msg=new String(buffer.array()).trim();
        System.out.println("from server receive is:"+msg);
    }
}
