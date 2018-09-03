/**    
 * @Title: Router.java  
 * @Package com.weconex.shiro.annotation  
 * @Description: TODO(用一句话描述该文件做什么)  
 * @author yanhewei@vv.cc.com    
 * @date 2018年8月27日 下午9:09:23  
 * @version V1.0    
*/
package com.bicon.botu.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**  
 * @ClassName: Router  
 * @Description: TODO(这里用一句话描述这个类的作用)  
 * @author yanhewei@vv.cc.com  
 * @date 2018年8月27日 下午9:09:23  
 *    
*/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Router {
  String router() default ""; 
}
