/**  
 * 
 * @Title:  Run.java   
 * @Package com.bicon.botu   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 123774135@qq.com     
 * @date:   2018年9月6日 下午2:04:39   
 * @version V1.0 
 * @Copyright: 2018 www.tydic.com Inc. All rights reserved. 
 * 
 */  
package com.bicon.botu;

import com.bicon.botu.server.VertxHttpServer;

/**   
 * @ClassName:  Run   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 123774135@qq.com 
 * @date:   2018年9月6日 下午2:04:39   
 *     
 * @Copyright: 2018 
 * 
 */
public class Run {

	public static void main(String[] args) {
		new VertxHttpServer().doStart();
	}
	
	

}
