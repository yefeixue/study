package com.cn.client;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.cn.client.scanner.Invoker;
import com.cn.client.scanner.InvokerHoler;
import com.cn.client.swing.Swingclient;
import com.cn.common.core.model.Response;
/**
 * 消息接受处理类
 * @author -琴兽-
 *
 */
public class ClientHandler extends SimpleChannelHandler {
	
	/**
	 * 界面
	 */
	private Swingclient swingclient;
	
	public ClientHandler(Swingclient swingclient) {
		this.swingclient = swingclient;
	}

	/**
	 * 接收消息
	 */
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {

		Response response = (Response)e.getMessage();
	
		handlerResponse(response);
	}
	
	
	/**
	 * 消息处理
	 * @param channelId
	 * @param request
	 */
	private void handlerResponse(Response response){
		
		//获取命令执行器
		Invoker invoker = InvokerHoler.getInvoker(response.getModule(), response.getCmd());
		if(invoker != null){
			try {
				invoker.invoke(response.getStateCode(), response.getData());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			//找不到执行器
			System.out.println(String.format("module:%s  cmd:%s 找不到命令执行器", response.getModule(), response.getCmd()));
		}
	}

	/**
	 * 断开链接
	 */
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		swingclient.getTips().setText("与服务器断开连接~~~");
	}
}
