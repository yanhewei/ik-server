/**  
 * 
 * @Title:  DefaultServer.java   
 * @Package com.bicon.botu.server   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 123774135@qq.com     
 * @date:   2018年9月3日 上午11:40:11   
 * @version V1.0 
 * @Copyright: 2018 www.tydic.com Inc. All rights reserved. 
 * 
 */  
package com.bicon.botu.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**   
 * @ClassName:  DefaultServer   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 123774135@qq.com 
 * @date:   2018年9月3日 上午11:40:11   
 *     
 * @Copyright: 2018 
 * 
 */
public class DefaultServer implements Server{

	
private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final static int CPU_NUMBER = Math.max(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors()*2) ;
	
	
	private EventLoopGroup boss = NioEventLoopGroupUtil.builderEventLoopGroup(1);
	private EventLoopGroup work = NioEventLoopGroupUtil.builderEventLoopGroup(CPU_NUMBER);
	
	@Override
	public void doStart() {
		ServerBootstrap boot = new ServerBootstrap();
		boot.group(boss, work);
		boot.option(ChannelOption.SO_REUSEADDR,true); //设置SO_REUSEADDR为true,意味着地址可以复用,比如如下场景某个进程占用了80端口,然后重启进程,原来的socket1处于TIME-WAIT状态,进程启动后,使用一个新的socket2,要占用80端口,如果这个时候不设置SO_REUSEADDR=true,那么启动的过程中会报端口已被占用的异常。
		boot.childOption(ChannelOption.SO_KEEPALIVE, true); //SO_KEEPALIVE=true,是利用TCP的SO_KEEPALIVE属性,当SO_KEEPALIVE=true的时候,服务端可以探测客户端的连接是否还存活着,如果客户端因为断电或者网络问题或者客户端挂掉了等,那么服务端的连接可以关闭掉,释放资源
		boot.childOption(ChannelOption.TCP_NODELAY, true); //如果TCP_NODELAY没有设置为true,那么底层的TCP为了能减少交互次数,会将网络数据积累到一定的数量后,服务器端才发送出去,会造成一定的延迟。在互联网应用中,通常希望服务是低延迟的,建议将TCP_NODELAY设置为true。
		boot.childOption(ChannelOption.SO_SNDBUF, 1024*2);
		boot.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
		boot.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
		//boot.option(ChannelOption.ALLOCATOR, new  PooledByteBufAllocator(true,16,16,8192,11));
		//boot.childOption(ChannelOption.ALLOCATOR, new  PooledByteBufAllocator(true,16,16,8192,11));
		boot.channel(NioServerSocketChannel.class);
		boot.childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				
				//ch.pipeline().addLast("logging", new LoggingHandler(LogLevel.WARN));
				ch.pipeline().addLast("http-decoder",new HttpRequestDecoder()); // 请求消息解码器
			    ch.pipeline().addLast("http-aggregator",new HttpObjectAggregator(65536));// 目的是将多个消息转换为单一的request或者response对象
			    ch.pipeline().addLast("http-encoder",new HttpResponseEncoder());//响应解码器
				ch.pipeline().addLast("http-chunked",new ChunkedWriteHandler());//目的是支持异步大文件传输（）
				ch.pipeline().addLast("fileServerHandler",new HttpServerHandler());// 业务逻辑
				
			}
		});
		ChannelFuture future = null;
		try {
			future = boot.bind(9092).sync();
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			release(boss,work);
		}

        		
	}

	private void release(EventLoopGroup boss,EventLoopGroup work) {
		if(null != boss) {
			boss.shutdownGracefully();
		}
		if(null != work) {
			work.shutdownGracefully();
		}
	}
	
	@Override
	public void doStop() {
			
	}

	public static void main(String[] args) {
		new DefaultServer().doStart();
	}
}
