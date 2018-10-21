/**  
 * 
 * @Title:  CacheManager.java   
 * @Package com.bicon.botu.cache   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 123774135@qq.com     
 * @date:   2018年9月6日 上午9:40:34   
 * @version V1.0 
 * @Copyright: 2018 www.tydic.com Inc. All rights reserved. 
 * 
 */  
package com.bicon.botu.cache;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**   
 * @ClassName:  CacheManager   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 123774135@qq.com 
 * @date:   2018年9月6日 上午9:40:34   
 *     
 * @Copyright: 2018 
 * 
 */
public class CacheManager {

	
	private Cache<String, String> cache = CacheBuilder.newBuilder().initialCapacity(1024*1024).maximumSize(10000).concurrencyLevel(16).build();
	
	private static CacheManager cacheManager ;
	
	private java.util.concurrent.locks.ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	
	private CacheManager() {
		
	}
	
	public static CacheManager instance() {
		if(null == cacheManager) {
			synchronized (CacheManager.class) {
				if(null == cacheManager) {
					cacheManager = new CacheManager();
				}
			}
		}
		return cacheManager;
	}
	
	public void put(String key,String value) {
		cache.put(key, value);
		
	}
	
	public String readvalue(String key){
		
		return	cache.getIfPresent(key);
		
		
	}
}
