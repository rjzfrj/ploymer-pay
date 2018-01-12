package com.zyzf.polymer.pay.common.core.utils;


/**
 * 密码密钥处理异常
 * exception 
 * @author 廉月程
 * 创建时间：2015-6-9
 */
public class PinKeyException extends Exception{
	private static final long serialVersionUID = -6792009069635572497L;

	public PinKeyException(){
		super();
	}
	
	public PinKeyException(String msg){
		super(msg);
	}
	
	public PinKeyException(String msg,Throwable cause){
		super(msg,cause);
	}
	
	public PinKeyException(Throwable cause){
		super(cause);
	}
}
