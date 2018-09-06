/**  
 * 
 * @Title:  FreemarkUtil.java   
 * @Package com.bicon.botu.ui   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 123774135@qq.com     
 * @date:   2018年9月4日 下午1:38:29   
 * @version V1.0 
 * @Copyright: 2018 www.tydic.com Inc. All rights reserved. 
 * 
 */  
package com.bicon.botu.ui;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**   
 * @ClassName:  FreemarkUtil   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 123774135@qq.com 
 * @date:   2018年9月4日 下午1:38:29   
 *     
 * @Copyright: 2018 
 * 
 */
public class FreemarkUtil {

	
	public static String loadIndex(String filename)  {
		Configuration cfg = new Configuration();
		cfg.setClassForTemplateLoading(FreemarkUtil.class,"/static");
		Map<String,String> root = new HashMap<String,String>();
		Template t1;
		try {
			t1 = cfg.getTemplate(filename);
			StringWriter wtie = new StringWriter();
			t1.process(root, wtie);
			return wtie.toString();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
		
	}
	
	public static void main(String[] args) throws Exception {
		//创建Freemarker配置实例
		Configuration cfg = new Configuration();
		cfg.setClassForTemplateLoading(FreemarkUtil.class,"/file");
		//cfg.setDirectoryForTemplateLoading(new File("D:\\eclipse-workspace\\ik-es-server\\src\\main\\resources\\file"));
		//创建数据模型
		Map<String,String> root = new HashMap<String,String>();
		root.put("user", "老高");
		//加载模板文件
		Template t1 = cfg.getTemplate("a.ftl");
		//显示生成的数据,//将合并后的数据打印到控制台
		Writer out = new OutputStreamWriter(System.out);
		t1.process(root, out);
		out.flush();
	}
}
