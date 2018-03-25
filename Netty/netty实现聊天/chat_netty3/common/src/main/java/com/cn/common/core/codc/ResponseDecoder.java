package com.cn.common.core.codc;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import com.cn.common.core.model.Response;
/**
 * <pre>
 * 数据包格式
 * +——----——+——-----——+——----——+——----——+——----——+——----——+
 * |  包头	|  模块号      |  命令号    |  结果码    |  长度       |   数据     |  
 * +——----——+——-----——+——----——+——----——+——----——+——----——+
 * </pre>
 * @author -琴兽-
 *
 */
public class ResponseDecoder extends FrameDecoder{
	
	/**
	 * 数据包基本长度
	 */
	public static int BASE_LENTH = 4 + 2 + 2 + 4 + 4;

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
		if(buffer.readableBytes() >= BASE_LENTH){
			//第一个可读数据包的起始位置
			int beginIndex;
			
			while(true) {
				//包头开始游标点
				beginIndex = buffer.readerIndex();
				//标记初始读游标位置
				buffer.markReaderIndex();
				if (buffer.readInt() == ConstantValue.HEADER_FLAG) {
					break;
				}
				//未读到包头标识略过一个字节
				buffer.resetReaderIndex();
				buffer.readByte();
				
				//不满足
				if(buffer.readableBytes() < BASE_LENTH){
					return null;
				}
			}
			//读取模块号命令号
			short module = buffer.readShort();
			short cmd = buffer.readShort();
			
			int stateCode = buffer.readInt();
			
			//读取数据长度 
			int lenth = buffer.readInt();
			if(lenth < 0 ){
				channel.close();
			}
			
			//数据包还没到齐
			if(buffer.readableBytes() < lenth){
				buffer.readerIndex(beginIndex);
				return null;
			}
			
			//读数据部分
			byte[] data = new byte[lenth];
			buffer.readBytes(data);
			
			Response response = new Response();
			response.setModule(module);
			response.setCmd(cmd);
			response.setStateCode(stateCode);
			response.setData(data);
			//解析出消息对象，继续往下面的handler传递
			return response;
		}
		//数据不完整，等待完整的数据包
		return null;
	}

}
