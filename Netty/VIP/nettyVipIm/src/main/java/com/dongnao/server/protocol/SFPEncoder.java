package com.dongnao.server.protocol;

import org.msgpack.MessagePack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class SFPEncoder extends MessageToByteEncoder<MessageObject> {

	@Override
	protected void encode(ChannelHandlerContext ctx, MessageObject msg, ByteBuf out) throws Exception {
		// TODO Auto-generated method stub
		out.writeBytes(new MessagePack().write(msg));
	}


}
