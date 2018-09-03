
package com.bicon.botu.tools;

/**
 * 
* @ClassName: BusinessException 
* @Description: 自定义业务异常
* @author cssuger@163.com 
* @date 2016年5月24日 下午5:13:09 
*
 */
public class BusinessException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4107999795109334873L;
	
	private String code;
	
	private String flage;
	
	private String msg;

	public BusinessException(){
		
	}
	
	public BusinessException(String code,String flage,String msg){
		this.code = code;
		this.flage = flage;
		this.msg = msg;
	}
	public BusinessException(String flage,String msg){
		super();
		this.msg = msg;
		this.flage = flage;
		
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String isFlage() {
		return flage;
	}

	public void setFlage(String flage) {
		this.flage = flage;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "BusinessException [code=" + code + ", flage=" + flage
				+ ", msg=" + msg + "]";
	}

	
	
}
