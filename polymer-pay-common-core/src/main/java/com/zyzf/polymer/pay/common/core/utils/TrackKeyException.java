package com.zyzf.polymer.pay.common.core.utils;


/**
 * 二磁道密钥处理异常
 * exception 
 * @author 廉月程
 * 创建时间：2015-6-9
 */
public class TrackKeyException extends Exception{
	private static final long serialVersionUID = -6792009069635572497L;

	public TrackKeyException(){
		super();
	}
	
	public TrackKeyException(String msg){
		super(msg);
	}
	
	public TrackKeyException(String msg,Throwable cause){
		super(msg,cause);
	}
	
	public TrackKeyException(Throwable cause){
		super(cause);
	}
}
