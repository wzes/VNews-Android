package com.mobile.vnews.service.client;

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
		System.out.println(arg1);
	}
}
