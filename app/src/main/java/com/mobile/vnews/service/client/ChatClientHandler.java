package com.mobile.vnews.service.client;

import android.content.Intent;

import com.mobile.vnews.util.Utils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author xuantang
 */
public class ChatClientHandler extends SimpleChannelInboundHandler<String> {

	@Override
	protected void channelRead0(ChannelHandlerContext arg0, String arg1)
			throws Exception {
		// get messages
		try {
			Intent intent = new Intent();
			intent.setAction("com.mobile.vnews.message");
			intent.putExtra("message", arg1);
			Utils.getContext().sendBroadcast(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
