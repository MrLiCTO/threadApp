package com.lingxin.thread.nioEncho;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/12/5.
 */
public class NioEnchoServer {
    private Selector selector;
    private ExecutorService tp = Executors.newCachedThreadPool();
    public static Map<Socket, Long> time_stat = new HashMap<Socket, Long>(10240);

    private void startServer() throws Exception {
        selector = SelectorProvider.provider().openSelector();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        InetSocketAddress isa = new InetSocketAddress(InetAddress.getLocalHost(), 8000);
        ssc.socket().bind(isa);

        SelectionKey acceptKey = ssc.register(selector, SelectionKey.OP_ACCEPT);

        for (; ; ) {
            selector.select();
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> i = readyKeys.iterator();
            long e = 0;
            while (i.hasNext()) {
                SelectionKey sk = i.next();
                i.remove();
                if (sk.isAcceptable()) {
                    doAccept(sk);
                } else if (sk.isValid() && sk.isReadable()) {
                    SocketChannel channel = (SocketChannel) sk.channel();
                    if (!time_stat.containsKey(channel.socket()))
                        time_stat.put(channel.socket(), System.currentTimeMillis());
                    //doRead();
                } else if (sk.isValid() && sk.isWritable()) {
                    //doWrite();
                    SocketChannel channel = (SocketChannel) sk.channel();
                    e = System.currentTimeMillis();
                    Long l = time_stat.get(channel.socket());
                    System.out.println("spend time:" + (e - l) + "ms!!!");
                }
            }
        }
    }

    private void doAccept(SelectionKey sk) {
        ServerSocketChannel ssc = (ServerSocketChannel) sk.channel();
        SocketChannel clientChannal;
        try {
            clientChannal = ssc.accept();
            SelectionKey clientKey = clientChannal.register(selector, SelectionKey.OP_READ);
            EnchoClient enchoClient = new EnchoClient();
            clientKey.attach(enchoClient);

            InetAddress ia = clientChannal.socket().getInetAddress();
            System.out.println("accept connection from :" + ia.getHostAddress() + "...");
        } catch (Exception e) {
            System.out.println("accept connection failed ...");
            e.printStackTrace();
        }
    }

    private void doWrite(SelectionKey sk) {

    }

    private void doRead(SelectionKey sk) {
        SocketChannel channel = (SocketChannel) sk.channel();
        ByteBuffer bb = ByteBuffer.allocate(8192);
        int len;
        try {
            len = channel.read(bb);
            if (len < 0) {
                //disconect(sk);
                return;
            }
        } catch (Exception e) {
            System.out.println("failed read from client...");
            e.printStackTrace();
            //disconect(sk);
            return;
        }
        bb.flip();
        tp.execute(new HandleMsg(sk,bb));
    }

    class HandleMsg implements Runnable {
        SelectionKey skk;
        ByteBuffer bbb;

        public HandleMsg(SelectionKey skk, ByteBuffer bbb) {
            this.skk = skk;
            this.bbb = bbb;
        }

        @Override
        public void run() {
            EnchoClient enchoClient = (EnchoClient) skk.attachment();
            enchoClient.enqueue(bbb);
            skk.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            selector.wakeup();
        }
    }
}
