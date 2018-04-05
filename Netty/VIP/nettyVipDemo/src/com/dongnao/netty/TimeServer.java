package com.dongnao.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 
 * @author Five��ʦ
 * @createTime 2017��11��13�� ����8:42:39
 * 
 */
public class TimeServer {

	public static void main(String[] args) throws Exception {
		int port=8080; //�����Ĭ�϶˿�
		new TimeServer().bind(port);
	}
	
	public void bind(int port) throws Exception{
		//1���ڷ���˽��ܿͻ��˵�����
		EventLoopGroup acceptorGroup = new NioEventLoopGroup();
		//2���ڽ���SocketChannel�������д
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			//Netty��������NIO�������ĸ���������
			ServerBootstrap sb = new ServerBootstrap();
			//������NIO�߳��鴫�븨����������
			sb.group(acceptorGroup, workerGroup)
				//���ô�����ChannelΪNioServerSocketChannel����
				.channel(NioServerSocketChannel.class)
				//����NioServerSocketChannel��TCP����
				.option(ChannelOption.SO_BACKLOG, 1024)
				//���ð�IO�¼��Ĵ�����
				.childHandler(new ChannelInitializer<SocketChannel>() {
					//����NIOSocketChannel�ɹ����ڽ��г�ʼ��ʱ��������ChannelHandler���õ�ChannelPipeline�У����ڴ�������IO�¼�
					@Override
					protected void initChannel(SocketChannel arg0) throws Exception {
						
						arg0.pipeline().addLast(new TimeServerHandler());
					}
				});
			//�󶨶˿ڣ�ͬ���ȴ��ɹ���sync()��ͬ�������������ȴ�bind������ɲż�����
			//ChannelFuture��Ҫ�����첽������֪ͨ�ص�
			ChannelFuture cf = sb.bind(port).sync();
			System.out.println("�����������8080�˿ڡ�");
			//�ȴ�����˼����˿ڹر�
			cf.channel().closeFuture().sync();
		} finally {
			//�����˳����ͷ��̳߳���Դ
			acceptorGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}

