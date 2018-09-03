/**    
 * @Title: InvokerUtil.java  
 * @Package org.gateway.util  
 * @Description: TODO(用一句话描述该文件做什么)  
 * @author yanhewei@vv.cc.com    
 * @date 2018年9月2日 上午11:48:41  
 * @version V1.0    
*/
package com.bicon.botu.tools;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bicon.botu.annotation.RequestMapping;
import com.bicon.botu.annotation.Router;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

/**  
 * @ClassName: InvokerUtil  
 * @Description: TODO(这里用一句话描述这个类的作用)  
 * @author yanhewei@vv.cc.com  
 * @date 2018年9月2日 上午11:48:41  
 *    
*/
public class InvokerUtil {

	public static List<String> scanPackage(String packages) throws IOException{
		ClasspathPackageScanner classpath = new ClasspathPackageScanner(packages);
		return classpath.getFullyQualifiedClassNameList();
	}
	
	public static Map<String,Multimap<Class<?>,RequestMapping>> builder(String packages){
		Map<String,Multimap<Class<?>,RequestMapping>> classAndMethodMap = Maps.newHashMap();
		
		List<String> list = Lists.newArrayList();
		try {
			list = InvokerUtil.scanPackage(packages);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		for(String cn : list) {
			try {
				Class<?> clazz = Class.forName(cn);
				Router router = clazz.getAnnotation(Router.class);
				
				if(null != router) {
					Multimap<Class<?>,RequestMapping> multimap = ArrayListMultimap.create();
					//获取每个类里面方法上的注解
					Method[] methods = clazz.getDeclaredMethods();
					for(Method method : methods) {
						RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
						if(null != requestMapping) {
							multimap.put(clazz, requestMapping);
						}
					}
					classAndMethodMap.put(router.router(), multimap);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return classAndMethodMap;
	}
	
	public static Map<Class<?>,Map<RequestMapping,Method>> builderRequestMappingaAndMethod(String packages){
		
		Map<Class<?>,Map<RequestMapping,Method>> classAndMethdmap = Maps.newHashMap();
		
		List<String> list = Lists.newArrayList();
		try {
			list = InvokerUtil.scanPackage(packages);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		for(String cn : list) {
			try {
				Class<?> clazz = Class.forName(cn);
				Router router = clazz.getAnnotation(Router.class);
				if(null != router) {
					//获取每个类里面方法上的注解
					Method[] methods = clazz.getDeclaredMethods();
					Map<RequestMapping,Method> map = Maps.newHashMap();
					for(Method method : methods) {
						RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
						if(map.containsKey(requestMapping)) {
							throw new BusinessException("500", "false", "该类里面已经存在一个该注解");
						}
						map.put(requestMapping, method);
					}
					classAndMethdmap.put(clazz, map);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return classAndMethdmap;
	}
	
	
public static List<String> getRouters(String packages){
		
	    List<String> routers = Lists.newArrayList();
		
		List<String> list = Lists.newArrayList();
		try {
			list = InvokerUtil.scanPackage(packages);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		for(String cn : list) {
			try {
				Class<?> clazz = Class.forName(cn);
				Router router = clazz.getAnnotation(Router.class);
				if(null != router) {
					//获取每个类里面方法上的注解
					Method[] methods = clazz.getDeclaredMethods();
					
					for(Method method : methods) {
						RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
						String routeruri = router.router()+"/"+requestMapping.value();
						if(routers.contains(routeruri)) {
							throw new BusinessException("500", "false", "已经存在给路由了!");
						}
						routers.add(router.router()+"/"+requestMapping.value());
						
					}
					
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return routers;
	}
	
	
	
   public static Object find(Set<Class<?>> collection,Map<String,Object> beansWithAnnotationMap){
	   for(Map.Entry<String, Object> entry : beansWithAnnotationMap.entrySet()) {
		   //String key = entry.getKey();
		   Object ob = entry.getValue();
		   if(collection.contains(ob.getClass())) {
			   return ob;
		   }
	   }
	   return null;
		
	}
	
   
   
	
	
}
