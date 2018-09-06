/**  
 * 
 * @Title:  ESNlpJob.java   
 * @Package com.bicon.botu.job   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 123774135@qq.com     
 * @date:   2018年9月6日 上午10:26:13   
 * @version V1.0 
 * @Copyright: 2018 www.tydic.com Inc. All rights reserved. 
 * 
 */  
package com.bicon.botu.job;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateParser;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.core.util.datetime.FastDateFormat;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bicon.botu.cache.CacheManager;
import com.bicon.botu.tools.Constans;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.google.common.base.Strings;

/**   
 * @ClassName:  ESNlpJob   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 123774135@qq.com 
 * @date:   2018年9月6日 上午10:26:13   
 *     
 * @Copyright: 2018 
 * 
 */
@DisallowConcurrentExecution
public class ESNlpJob implements Job{
	private  Logger logger = LoggerFactory.getLogger(this.getClass()); 
	CacheManager cacheManager = CacheManager.instance();
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("分词更新任务开始,开始时间:"+FastDateFormat.getInstance("yyyyMMddHHmmss").format(new Date()));
		String uri = cacheManager.readvalue(Constans.URL_KEY);
		if(!StringUtils.isBlank(uri)) {
			String resmg = doexecute(uri,1000+"");
			logger.info(resmg);
		}
		logger.info("分词更新任务结束,结束时间:"+FastDateFormat.getInstance("yyyyMMddHHmmss").format(new Date()));
	}
    
	private String doexecute(String uri,String timeout) {
		int out = 0;
		if(!Strings.isNullOrEmpty(timeout)) {
			out = 60;
		}else {
			out = Integer.parseInt(timeout);
		}
		RequestConfig rc = RequestConfig.custom().setConnectionRequestTimeout(out * 1000).setConnectTimeout(out * 1000)
				.setSocketTimeout(600 * 1000).build();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		StringBuffer sb = new StringBuffer();
		BufferedReader in = null;
		InputStreamReader inputStreamReader = null;
		HttpPost post = new HttpPost(uri);
		post.setConfig(rc);
		try {
			
			response = httpclient.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {

				String charset = "UTF-8";
				// 获取编码，默认为utf-8
				inputStreamReader = new InputStreamReader(response.getEntity().getContent(), charset);
				in = new BufferedReader(inputStreamReader);
				String line;
				while ((line = in.readLine()) != null) {
					if(!Strings.isNullOrEmpty(line)) {
						sb.append(line);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("getRemoteWords {} error"+ e.fillInStackTrace());
			sb.append("{code:500,tip:"+e.getMessage()+"}");
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(inputStreamReader);
			IOUtils.closeQuietly(response);
		}
		return sb.toString();
	}
	
	
}
