/**  
 * 
 * @Title:  WordItemHander.java   
 * @Package com.bicon.botu.server   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 123774135@qq.com     
 * @date:   2018年9月4日 上午11:44:45   
 * @version V1.0 
 * @Copyright: 2018 www.tydic.com Inc. All rights reserved. 
 * 
 */  
package com.bicon.botu.server;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import com.google.common.io.Files;
import com.google.common.io.LineProcessor;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

/**   
 * @ClassName:  WordItemHander   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 123774135@qq.com 
 * @date:   2018年9月4日 上午11:44:45   
 *     
 * @Copyright: 2018 
 * 
 */
public class WordItemHander implements Handler<RoutingContext>{

	@Override
	public void handle(RoutingContext event) {
		//读取文件
		//进行文件扫描
		
				StringBuffer sb = new StringBuffer();
				try {
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
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				HttpServerResponse httpServerResponse =	event.response();
				httpServerResponse.putHeader("Content-Type", "text/html");
				httpServerResponse.end(sb.toString());
				httpServerResponse.close();
	}

}
