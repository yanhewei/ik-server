/**
 * 
 */
package com.bicon.botu.server;

import java.util.concurrent.ThreadFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * 
 * @ClassName: NioEventLoopGroupUtil  
 * @Description: TODO(这里用一句话描述这个类的作用)  
 * @author yanhewei@vv.cc.com  
 * @date 2018年8月25日 下午8:05:58  
 *
 */
public class NioEventLoopGroupUtil {
	
	private static Logger logger = LoggerFactory.getLogger(NioEventLoopGroupUtil.class);
	
	private static final  String  OS_NAME = System.getProperty("os.name").toLowerCase() ;
	
	
	
	public static boolean isLinux(){
		return OS_NAME.indexOf("linux")>=0;
	}
	
	public static boolean isMacOS(){
		return OS_NAME.indexOf("mac")>=0 && OS_NAME.indexOf("os")>0 && OS_NAME.indexOf("x")<0;
	}
	
	public static boolean isMacOSX(){
		return OS_NAME.indexOf("mac")>=0 && OS_NAME.indexOf("os")>0 && OS_NAME.indexOf("x")>0;
	}
	
	public static boolean isWindows(){
		return OS_NAME.indexOf("windows")>=0;
	}

	/**
	 * 
	 * @Title: builderEventLoopGroup  
	 * @Description: 根据操作系统类型进行判断到底采用哪种 EventLoopGroup对象
	 * @param @return    设定文件  
	 * @return EventLoopGroup    返回类型  
	 * @throws
	 */
	public static EventLoopGroup builderEventLoopGroup(int nThreads) {
		if(isLinux()) {
			return new EpollEventLoopGroup(nThreads,new ThreadFactory() {
				
				@Override
				public Thread newThread(Runnable r) {
					Thread thread = new Thread(r);
					return thread;
				}
			}) ;
		}else {
             return new NioEventLoopGroup(nThreads,new ThreadFactory() {
				
				@Override
				public Thread newThread(Runnable r) {
					Thread thread = new Thread(r);
					return thread;
				}
			}) ;
		}
	}
}
