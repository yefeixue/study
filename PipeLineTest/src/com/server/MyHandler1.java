package com.server;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.UpstreamMessageEvent;

public class MyHandler1 extends SimpleChannelHandler {

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {

		ChannelBuffer buffer = (ChannelBuffer)e.getMessage();
		
		byte[] array = buffer.array();
		String message = new String(array);
		System.out.println("handler1:" + message);
		
		//传递
		ctx.sendUpstream(new UpstreamMessageEvent(ctx.getChannel(), "abc", e.getRemoteAddress()));
		ctx.sendUpstream(new UpstreamMessageEvent(ctx.getChannel(), "efg", e.getRemoteAddress()));
	}
}
