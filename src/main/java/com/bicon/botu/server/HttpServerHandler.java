/**    
 * @Title: HttpServerHandler.java  
 * @Package org.gateway.server  
 * @Description: TODO(用一句话描述该文件做什么)  
 * @author yanhewei@vv.cc.com    
 * @date 2018年9月1日 下午8:29:27  
 * @version V1.0    
*/
package com.bicon.botu.server;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import com.google.common.net.HttpHeaders;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

/**  
 * @ClassName: HttpServerHandler  
 * @Description: TODO(这里用一句话描述这个类的作用)  
 * @author yanhewei@vv.cc.com  
 * @date 2018年9月1日 下午8:29:27  
 *    
*/
@ChannelHandler.Sharable
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest>{

	private Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	
	
	public HttpServerHandler( ){
		
	}
		
		
	
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
		//builderFullHttpResponse();
		//读取文件
		StringBuffer sb = new StringBuffer();
		Files.readLines(new File("C:\\test\\disease.txt"), 	Charset.defaultCharset(), new LineProcessor<String>() {

			@Override
			public boolean processLine(String line) throws IOException {
				sb.append(line).append("\r\n");
				return true;
			}

			@Override
			public String getResult() {
				// TODO Auto-generated method stub
				return null;
			}
		});
		ByteBuf byteBuf = Unpooled.wrappedBuffer(sb.toString().getBytes());
		sendMessage(builderFullHttpResponse(byteBuf,msg),ctx);
	}
	
	private FullHttpResponse builderFullHttpResponse(ByteBuf byteBuf,FullHttpRequest msg) {
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0,HttpResponseStatus.OK, byteBuf);
        response.headers().set(HttpHeaders.CONTENT_TYPE, "text/html");
		response.headers().set(HttpHeaders.CONTENT_LENGTH,response.content().readableBytes());
		response.headers().set(HttpHeaders.CONNECTION,  HttpHeaderValues.KEEP_ALIVE);
		return response;
	}
	
	private void sendMessage(FullHttpResponse fullHttpResponse,ChannelHandlerContext ctx) {
		 ctx.writeAndFlush(fullHttpResponse);
	}
  
	@Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
       
        super.channelReadComplete(ctx);
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if(null != cause) cause.printStackTrace();
        if(null != ctx) ctx.close();
    }


}
