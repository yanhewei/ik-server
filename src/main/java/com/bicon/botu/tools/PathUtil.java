/**   
 * @Title: PathUtil.java 
 * @Package com.org.framework 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author cssuger@163.com   
 * @date 2017年2月18日 下午7:55:42 
 * @version V1.0   
 */
package com.bicon.botu.tools;

import java.net.URL;

/** 
 *@ClassName: PathUtil 
 *@Description: TODO(这里用一句话描述这个类的作用) 
 *@author cssuger@163.com 
 *@date 2017年2月18日 下午7:55:42  
 */
public class PathUtil {

	 private PathUtil() {

	 }

	    /**
	     * "file:/home/whf/cn/fh" -> "/home/whf/cn/fh"
	     * "jar:file:/home/whf/foo.jar!cn/fh" -> "/home/whf/foo.jar"
	     */
	    public static String getRootPath(URL url) {
	        String fileUrl = url.getFile();
	        int pos = fileUrl.indexOf('!');

	        if (-1 == pos) {
	            return fileUrl;
	        }

	        return fileUrl.substring(5, pos);
	    }

	    /**
	     * "cn.fh.lightning" -> "cn/fh/lightning"
	     * @param name
	     * @return
	     */
	    public static String dotToSplash(String name) {
	        return name.replaceAll("\\.", "/");
	    }

	    /**
	     * "Apple.class" -> "Apple"
	     */
	    public static String trimExtension(String name) {
	        int pos = name.indexOf('.');
	        if (-1 != pos) {
	            return name.substring(0, pos);
	        }

	        return name;
	    }

	    /**
	     * /application/home -> /home
	     * @param uri
	     * @return
	     */
	    public static String trimURI(String uri) {
	        String trimmed = uri.substring(1);
	        int splashIndex = trimmed.indexOf('/');

	        return trimmed.substring(splashIndex);
	    }
}
