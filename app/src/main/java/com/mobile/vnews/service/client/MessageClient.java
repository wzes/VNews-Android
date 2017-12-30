package com.mobile.vnews.service.client;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.mobile.vnews.module.bean.Message;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MessageClient {
    private Channel channel;

    public MessageClient(String host, int port, String user_id) {
        String host1 = host;
        int port1 = port;
        String user_id1 = user_id;
        EventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(worker);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ClientInit());
        try {
            channel = bootstrap.connect(host, port).sync().channel();
            // login
            channel.writeAndFlush("login" + user_id);
            Log.i("TAG", "MessageClient: " + "login");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     *
     * @param message
     */
    public void sendMessage(Message message) {
        sendMessage(JSON.toJSONString(message));
    }

    /**
     *
     * @param message
     */
    public void sendMessage(String message) {
        this.channel.writeAndFlush(message);
    }

    public void stopConn() {
        this.channel.close();
    }
}
