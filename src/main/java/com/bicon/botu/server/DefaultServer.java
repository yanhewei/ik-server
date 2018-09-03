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
	
	
	@Override
	public void doStart() {
		

        		
	}

	@Override
	public void doStop() {
			
	}

	public static void main(String[] args) {
		new DefaultServer().doStart();
	}
}
