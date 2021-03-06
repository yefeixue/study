package com.cn.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;
import com.cn.common.core.codc.RequestDecoder;
import com.cn.common.core.codc.ResponseEncoder;

/**
 * netty服务端入门
 * 
 * @author -琴兽-
 * 
 */
@Component
public class Server {

	/**
	 * 启动
	 */
	public void start() {

		// 服务类
		ServerBootstrap b = new ServerBootstrap();

		// 创建boss和worker
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		//业务线程池，代替netty3中的消息处理时的自定义线程池
		//内部是 消息串行化，比3中自定义好，对同一个通道中的请求有序处理
		final EventLoopGroup eventLoopGroup = new NioEventLoopGroup();


		try {
			// 设置循环线程组事例
			b.group(bossGroup, workerGroup);

			// 设置channel工厂
			b.channel(NioServerSocketChannel.class);

			// 设置管道
			b.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new RequestDecoder());
					ch.pipeline().addLast(new ResponseEncoder());
					ch.pipeline().addLast(eventLoopGroup,new ServerHandler());
				}
			});

			b.option(ChannelOption.SO_BACKLOG, 2048);// 链接缓冲池队列大小

			//设置netty使用的buffer类型，这个为对象池结构
			b.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
			// 绑定端口
			b.bind(10102).sync();

			System.out.println("start!!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
