/**  
 * 
 * @Title:  WordItemResource.java   
 * @Package com.bicon.botu.restfull   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 123774135@qq.com     
 * @date:   2018年9月3日 下午12:44:12   
 * @version V1.0 
 * @Copyright: 2018 www.tydic.com Inc. All rights reserved. 
 * 
 */  
package com.bicon.botu.restfull;

import com.bicon.botu.annotation.RequestMapping;
import com.bicon.botu.annotation.RequestMethod;
import com.bicon.botu.annotation.Router;

/**   
 * @ClassName:  WordItemResource   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 123774135@qq.com 
 * @date:   2018年9月3日 下午12:44:12   
 *     
 * @Copyright: 2018 
 * 
 */
@Router(router="wordItemResource")
public class WordItemResource {

	@RequestMapping(value="word/{prarms}",method=RequestMethod.GET)
	public String readWordItem(String prarms) {
		return "hellword"+prarms;
	}
}
