package com.zyzf.polymer.pay.common.core.utils.encrypt;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zyzf.polymer.pay.common.core.utils.MD5;
import com.zyzf.polymer.pay.common.core.utils.PinKeyException;
import com.zyzf.polymer.pay.common.core.utils.TestDes;
import com.zyzf.polymer.pay.common.core.utils.TrackKeyException;

import it.sauronsoftware.base64.Base64;


/**
 * 3DES加密工具类
 * 三个平台加密功能
 * @author 颜铃璋 
 * @date 2012-10-11
 */
public class Des3 {
	private static final Log log = LogFactory.getLog(Des3.class);
	// 密钥
	private final static String secretKey = "zhongyi支付@163.com$#6688#$";
	// 向量
	private final static String iv = "87654321";
	// 加解密统一使用的编码方式
	private final static String encoding = "utf-8";

	/**
	 * 有向量 3DES加密
	 * @param plainText 普通文本
	 * @param secretKey
	 * @return
	 * @throws Exception 
	 */
	public static String encode(String plainText,String secretKey){
		try{
			Key deskey = null;
			DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
			deskey = keyfactory.generateSecret(spec);
	
			Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
			byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
			//return Des3Base64.encode(encryptData);
			byte[] bb = Base64.encode(encryptData);
			return new String(bb,"UTF-8");
		}catch(Exception e){
			e.printStackTrace();
		}
		return plainText;
	}
	
	/**
	 * 有向量 3DES加密
	 * @param plainText 普通文本
	 * @return
	 * @throws Exception 
	 */
	public static String encode(String plainText){
		return encode(plainText,secretKey);
	}

	/**
	 * 有向量 3DES解密
	 * 
	 * @param encryptText 加密文本
	 * @return
	 * @throws Exception
	 */
	public static String decode(String encryptText,String secretKey)throws Exception{
		try{
			Key deskey = null;
			DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
			deskey = keyfactory.generateSecret(spec);
			Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
	
			//byte[] decryptData = cipher.doFinal(Des3Base64.decode(encryptText));
			byte[] decryptData = cipher.doFinal(Base64.decode(encryptText.getBytes("UTF-8")));
			return new String(decryptData, encoding);
		}catch(Exception e){
			//e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 有向量 3DES解密
	 * @param encryptText 加密文本
	 * @return
	 * @throws Exception
	 */
	public static String decode(String encryptText)throws Exception{
		return decode(encryptText,secretKey);
	}
	
	
	/**
	 * 3DES解密
	 * @param dataS 解密数据
	 * @return keys 密钥
	 */
	public static String decode3DES(String dataS, String keys)throws Exception{
		String result="";
		 try {
			 	byte[] data=hexStringToByte(dataS);
			 	byte[] key=hexStringToByte(keys);
				byte[] km = new byte[24];
				System.arraycopy(key, 0, km, 0, 16);
				System.arraycopy(key, 0, km, 16, 8);
				Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
				DESedeKeySpec dks = new DESedeKeySpec(km);
				SecretKey k = SecretKeyFactory.getInstance("DESede")
						.generateSecret(dks);
				cipher.init(Cipher.DECRYPT_MODE, k);
				result=byte2hex(cipher.doFinal(data));
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(e);
			}
			return result;
	 }
	/**
	 * 3DES加密
	 * @param key 密钥
	 * @param data 待加密数据
	 * @return byte[] 加密数据
	 */
	public static byte[] encode3DES(byte[] key, byte[] data) throws Exception{
		try {
			byte[] km = new byte[24];
			System.arraycopy(key, 0, km, 0, 16);
			System.arraycopy(key, 0, km, 16, 8);
			Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
			DESedeKeySpec dks = new DESedeKeySpec(km);
			SecretKey k = SecretKeyFactory.getInstance("DESede")
					.generateSecret(dks);
			cipher.init(Cipher.ENCRYPT_MODE, k);
			byte[] result = cipher.doFinal(data);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	/**
	 * 3DES加密
	 * @param key 密钥
	 * @param dataS 待加密数据
	 * @return keys 加密数据
	 */
	public static String encode3DES(String dataS, String keys) throws Exception{
		byte[] data=hexStringToByte(dataS);
	 	byte[] key=hexStringToByte(keys);
	 	String result = byte2hex(encode3DES(key, data));
	 	return result;
	}

	/**
	 * DES解密
	 * @param myinfo
	 * @param key
	 * @return
	 */
	public static byte[] decodc(byte[] myinfo,byte[] key)throws Exception {
		String Algorithm = "DES"; //DES,DESede,Blowfish
		try {
			DESKeySpec dks = new DESKeySpec(key);

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Algorithm);
			SecretKey deskey = keyFactory.generateSecret(dks);

			//log.debug("解密前数据=[" + byte2hex(myinfo)+"]");

			Cipher c1 = Cipher.getInstance("DES/ECB/NoPadding");
			c1.init(Cipher.DECRYPT_MODE, deskey);

			byte[] cipherByte = c1.doFinal(myinfo);
			//log.debug("解密结果=[" + byte2hex(cipherByte)+"]");
			return cipherByte;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * DES加密
	 * @param myinfo
	 * @param key
	 * @return
	 */
	public static byte[] encodc(byte[] myinfo,byte[] key)throws Exception{
		// Security.addProvider(new com.sun.crypto.provider.SunJCE());
		String Algorithm = "DES"; //DES,DESede,Blowfish
		try {
			DESKeySpec dks = new DESKeySpec(key);

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Algorithm);
			SecretKey deskey = keyFactory.generateSecret(dks);
			//log.debug("加密前数据=[" + byte2hex(myinfo)+"]");
			Cipher c1 = Cipher.getInstance("DES/ECB/NoPadding");
			//Cipher c1 = Cipher.getInstance("DES");
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			byte[] cipherByte = c1.doFinal(myinfo);
			//log.debug("加密结果=[" + byte2hex(cipherByte)+"]");
			return cipherByte;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
    /**
     * 转换成十六进制字符串
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) {
        String hs="";
        String stmp="";

        for (int n=0;n<b.length;n++) {
            stmp=(java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length()==1) 
            	hs=hs+"0"+stmp;
            else 
            	hs=hs+stmp;
            //if (n<b.length-1)  hs=hs+":";
        }
        return hs.toUpperCase();
    }
    
    /**
     * 十六进制字符串
     * @param hex
     * @return
     */
	public static byte[] hexStringToByte(String hex) {
		hex=hex.toUpperCase();
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}
	private static byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}
	
	/**
	 * 转成十六进制数字
	 * @param n
	 * @return
	 */
	public static String Int2HexStr(int n){
		String HexStr=Integer.toHexString(n);
		HexStr=HexStr.length()==1?"0"+HexStr:HexStr;
		return HexStr;
	}
	
	/**
	 * PIN加密
	 * @param Pan 卡号
	 * @param Pin 密码
	 * @param PinKey 密码密钥
	 * @return
	 */
	public static String encryptPin(String Pan,String Pin,String PinKey)throws PinKeyException{
		String DesPin="";
		try{
			Pan=Pan.substring(Pan.length()-13, Pan.length()-1);
			int PanLen=Pan.length();
			for(int i=0;i<16-PanLen;i++)
			{
				Pan="0"+Pan;
			}
			Pin=Int2HexStr(Pin.length())+Pin;
			int PinLen=Pin.length();
			for(int j=0;j<16-PinLen;j++)
			{
				Pin+="F";
			}
			byte[] res=TestDes.XORContent(hexStringToByte(Pin),hexStringToByte(Pan));
			log.debug("异或数据=["+Pan+"]["+Pin+"]");
			log.debug("异或的结果=["+byte2hex(res)+"]");
			if(PinKey.length()==32)
			{
				log.debug("=====加密开始========");
				log.debug("加密数据=["+byte2hex(res)+"]["+PinKey+"]");
				byte[] res1=encode3DES(TestDes.getDESKey(PinKey),res);
				DesPin=byte2hex(res1);
				log.debug("加密的结果=["+DesPin+"]");
				log.debug("=====加密结束========");
			}else{
				log.debug("=====加密开始========");
				log.debug("加密数据=["+byte2hex(res)+"]["+PinKey+"]");
				byte[] res1=encodc(res,TestDes.getDESKey(PinKey));
				DesPin=byte2hex(res1);
				log.debug("加密的结果=["+DesPin+"]");
				log.debug("=====加密结束========");
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new PinKeyException("密码密钥错误！");
		}
		return DesPin;
	}
	
	/**
	 * 密码解密
	 * @param Pan
	 * @param DesPin
	 * @param PinKey
	 * @return
	 * @throws Exception
	 */
	public static String decryptPin(String Pan,String DesPin,String PinKey)throws PinKeyException{
		String pin = "";
		if(StringUtils.isBlank(DesPin)) return "";
		try{
			Pan=Pan.substring(Pan.length()-13, Pan.length()-1);
			int PanLen=Pan.length();
			for(int i=0;i<16-PanLen;i++){
				Pan="0"+Pan;
			}
			
			byte[] res = null;
			if(PinKey.length()==32){
				res = hexStringToByte(decode3DES(DesPin,PinKey));
			}else{
				res = decodc(hexStringToByte(DesPin),hexStringToByte(PinKey));
			}
			pin = byte2hex(TestDes.XORContent(res,hexStringToByte(Pan)));
			int len = Integer.parseInt(pin.substring(0, 2), 10);
			
			pin = pin.substring(2,2+len);
		}catch (Exception e) {
			//e.printStackTrace();
			throw new PinKeyException("密码密钥错误！",e);
		}
		return pin;
	}
	
	/**
	 * 二磁道加密
	 * @param trkKey
	 * @param track
	 * @return
	 */
	public static String encryptStanderTranck(String trkKey,String track)throws TrackKeyException{	
		log.debug("trkKey:"+trkKey);
		int trkLen=track.length();
		//track=Int2HexStr(trkLen)+track;
		int mod=trkLen%16;
		log.debug("mod:"+mod);
		for(int i=0;i<16-mod;i++)
		{
			track+="F";
		}
		
		String mTrack="";
		try {
			log.debug("track:"+track);
			mTrack=byte2hex(encode3DES(hexStringToByte(trkKey),hexStringToByte(track)));
			mTrack=Int2HexStr(trkLen)+mTrack;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new TrackKeyException("二磁道密钥错误！",e);
		}
		return mTrack;
	}
	
	/**
	 * 二磁道解密
	 * @param trkKey
	 * @param track
	 * @return
	 */
	public static String decryptStanderTranck(String trkKey,String desTtrack)throws TrackKeyException{	
		String track = "";
		try {
			int len = Integer.parseInt(desTtrack.substring(0, 2), 16);
			desTtrack = desTtrack.substring(2);
			track = decode3DES(desTtrack,trkKey);
			track = track.substring(0,len).toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
			throw new TrackKeyException("二磁道密钥错误！");
		}
		return track;
	}
	
	/**
	 * 生成8位DES密钥  转码的16位密钥
	 * @return
	 */
	public static String generate16Key(){
		KeyGenerator kg;
		try {
			kg = KeyGenerator.getInstance("DES");
			kg.init(56);
			SecretKey secretKey = kg.generateKey();
			byte[] key = secretKey.getEncoded();
			return byte2hex(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 生成8位DES密钥  转码的32位密钥
	 * @return
	 */
	public static String generate32Key(){
		return MD5.mD5ofStr(generate16Key());
	}
	
	public static void main(String[] args) throws Exception {
//		String keys = "1A2A97C5CE152CF96DD0295B8B98A750";
//		String dataS = "{\"bankUrl\":null,\"bankOrderNum\":null,\"icCardYu\":null,\"sonMerchantId\":null,\"track3Data\":null,\"track2Data\":null,\"bankPort\":null,\"pinKey\":null,\"transAmt\":50000,\"terminalId\":null,\"accountNumber\":\"6217580100000118505\",\"pin\":null,\"transId\":\"739064\",\"merchantId\":null,\"mainKey\":null,\"macKey\":null}FFFFFFFFFFFFFFF";
////		String dataS = "FFFFFFFFFFFFFFFF";
//		String jiami = byte2hex(encode3DES(hexStringToByte(keys),dataS.getBytes("UTF-8")));
////		String jiami = encode3DES(dataS,keys);
//		
//		
//		
//		String result = decode3DES(jiami, keys);
//		
//		byte[] jieBytes = hexStringToByte(result);
//		String jiemi = new String(jieBytes,"UTF-8");
//		
//		System.out.println(jiami);
//		System.out.println(jiemi);
		
//		String PinKey="E3D13ACC2EC98480039FE9F955961CC0";
//		String Pin="123456";
//		String Pan = "6226220286298871";
//		String desPin = encryptPin(Pan, Pin, PinKey);
//		log.debug("desPin = "+desPin);
//		
//		//解密
//		String jiemPin = decryptPin(Pan, desPin, PinKey);
//		log.debug("jiemPin = "+jiemPin);
//		
//		String track2Data="6222021001086720528d49121209499991227";
//		String trkKey="FCEC16E3CC524B9D380FCCC34E40F502";
//		String deStrack2Data = encryptStanderTranck(trkKey,track2Data);
//		log.debug("deStrack2Data = "+deStrack2Data);
//		
//		String jiemStrack2Data = decryptStanderTranck(trkKey, deStrack2Data);
//		log.debug("track2Data = "+track2Data);
//		log.debug("jiemStrack2Data = "+jiemStrack2Data);
		
//		String data = "D54B80E4F30EB0FEB4AFC63DDE3C09DB";
//		String data = "97FFDB257A514E0CAA9276F873E7149E";
//		String data = "E10ADC3949BA59ABBE56E057F20F883E";
//		String mainKey = "235E8FFCF30EFAA3EE95B2DC672559DA";
//		String jiamiData = encode3DES(data, mainKey);
//		log.debug(jiamiData);
//		String jiemiData = decode3DES(jiamiData, mainKey);
//		log.debug(data);
//		log.debug(jiemiData);
		
		
//		String mainKey = generate32Key();
//		String pinKey = generate32Key();
//		String trackKey = generate32Key();
//		String rollbackKey = generate32Key();
//		log.debug("mainKey:"+mainKey);
//		log.debug("pinKey:"+pinKey);
//		log.debug("trackKey:"+trackKey);
//		log.debug("rollbackKey:"+rollbackKey);
//		
//		log.debug("*********************");
//		pinKey = encode3DES(pinKey, mainKey);
//		trackKey = encode3DES(trackKey, mainKey);
//		rollbackKey = encode3DES(rollbackKey, mainKey);
//		log.debug("mainKey:"+mainKey);
//		log.debug("pinKey:"+pinKey);
//		log.debug("trackKey:"+trackKey);
//		log.debug("rollbackKey:"+rollbackKey);
		
		
//		String track2Data="32c2e9b28af451e58e9faf878eb82d5a046b94936264aac0";
//		String trkKey="46c6850577bbf523cb0bc41fe4e5badf";
//		String deStrack2Data = Des3.decode3DES(track2Data, trkKey).replaceAll("F", "").toLowerCase();
//		log.debug("deStrack2Data = "+deStrack2Data);
//		
//		String PinKey="46c6850577bbf523cb0bc41fe4e5badf";
//		String Pan = "6217000010023848024";
//		String desPin = "bf4f68b7909281c3";
//		//解密
//		String jiemPin = decryptPin(Pan, desPin, PinKey);
//		log.debug("jiemPin = "+jiemPin);
		
//		String track2Data="0F62C88E04C912239995B68B30DD11796DBFF69D0CB453D4";
//		String trkKey="A0C45C59F1E549BBA0C45C59F1E549BB";
//		String deStrack2Data = Des3.decode3DES(track2Data, trkKey).replaceAll("F", "").toLowerCase();
//		log.debug("deStrack2Data = "+deStrack2Data);
		
//		String track2Data="25F997CB58CB80B86CE5948C596F638E9DA1C784A969B4CC37";
//		String trkKey="458C9F94DDCDF2761E97A35ED116CEBA";
//		String deStrack2Data = Des3.decryptStanderTranck(trkKey,track2Data);
//		log.debug("deStrack2Data = "+deStrack2Data);
//		
//		String PinKey="B79CB767E5E5C8EF62DB8B3ADF88A3B0";
//		String Pan = "6227000011170648562";
//		String desPin = "BE04431076AF82F0";
//		//解密
//		String jiemPin = decryptPin(Pan, desPin, PinKey);
//		log.debug("jiemPin = "+jiemPin);
//		
//		String Pan1 = "123456789012345678";
//		String pin = "123456";
//		String jiamiPin = encryptPin(Pan1, pin, PinKey);
//		log.debug("jiamiPin = "+jiamiPin);
		
		
		
		//B73671875044B4425A8C97B9D0780B96 神州安付主秘钥
		//C2C0B64E5A43B19E020A0AF13BE03C6F ABC1FED7  6E9E0C354F0CFB28EB9059C0672FB461ABC1FED7
		//3AAA3C6831A42BB38AF5A2F7FF22E1E8 ABC1FED7  1A65DDEC5BF208FC357E501DB1CF4EA6ABC1FED7
//		System.out.println(encode3DES("00000000000000000000000000000000", "B73671875044B4425A8C97B9D0780B96"));
//		System.out.println(encode3DES("C2C0B64E5A43B19E020A0AF13BE03C6F","B73671875044B4425A8C97B9D0780B96")+"ABC1FED7");
//		System.out.println(encode3DES("3AAA3C6831A42BB38AF5A2F7FF22E1E8","B73671875044B4425A8C97B9D0780B96")+"ABC1FED7");
//		System.out.println(encode3DES("00000000000000000000000000000000", "C2C0B64E5A43B19E020A0AF13BE03C6F"));
//		System.out.println(encode3DES("00000000000000000000000000000000", "3AAA3C6831A42BB38AF5A2F7FF22E1E8"));
//		System.out.println(encode3DES("00000000000000000000000000000000", "B73671875044B4425A8C97B9D0780B96"));
//		System.out.println(decode3DES("1DAFBD4D7127A6C2FA9F2AC31EB9CD3D0304AC1B41C9753BA2F72C510F0A481BAF561F851FECE6EDE9388F830DBB8D7713A3B647859BC261", "C2C0B64E5A43B19E020A0AF13BE03C6F"));

//		System.out.println(decode3DES("A7A6FA2E78F0C3F831BF328F8574354390ADB1D9ABB12F37F27E73BEF4AB34E1AF561F851FECE6ED7D8DF4290EA47A488DAA753F6CA3FB57", "C2C0B64E5A43B19E020A0AF13BE03C6F"));
//		System.out.println(decode3DES("7EF532D24E3FA88C00D2C24BA4D2DFB3514546245228B993", "C2C0B64E5A43B19E020A0AF13BE03C6F"));

//		System.out.println(encode1("0200602004C020C0", "54C8FB83D9ABB58954C8FB83D9ABB589"));
		
//		System.out.println(byte2hex(encodc(hexStringToByte("E88F825B4DC6A31F"), hexStringToByte("E39E98963AECDA6C"))));
//		System.out.println(byte2hex(decodc(hexStringToByte("4E14447701516D6C"), hexStringToByte("E88F825B4DC6A31F"))));
//		System.out.println(byte2hex(encodc(hexStringToByte("0000000000000000"), hexStringToByte("E4E64011CB997607"))));

//		String shopId = "2000099";
//		Long id = 2000099L;
//		String id32 = Long.toString(id, 32);
//		System.out.println(Base64.encode(shopId));
//		System.out.println(id32);
//		String id10 = Long.parseLong("1t173", 32)+"";
//		System.out.println(id10);
//		System.out.println(Base64.encode(id32));
//		System.out.println(Encode64.long10To64(id));
//		System.out.println(Base64.encode(Encode64.long10To64(id)));
//		System.out.println(Encode64.long64To10(Encode64.long10To64(id)));
		
//		String trk2 = "4637580040772331d21041010000031200000";
//		String trkKey = "E6B8BE442EE79D126A8CA158648D20F7";
//		System.out.println(encryptStanderTranck(trkKey, trk2));
		
//		String desTtrack = "25961C3C39D2789390ADA274CE278D1F904D9C9C70AA9D7426";
//		String trkKey = "63DFE3684D18DB58E0874556792B139B";
//		String trk2 = decryptStanderTranck(trkKey, desTtrack);
//		System.out.println(trk2);
		
		String trk2 = "123456d123456";
		String trkKey = "63DFE3684D18DB58E0874556792B139B";
		System.out.println(encryptStanderTranck(trkKey, trk2));
		
//		String Pan = "6225882145895741";
//		String desPin = "B4B1218FFFE7A07B";
//		String pinKey = "56C1BDA718908A9405A1D0827494A61F";
//		String pin = decryptPin(Pan, desPin, pinKey);
//		System.out.println(pin);
	}
	
	
	
//	public static void main(String args[]){
//		String s1 = "明文数据";
//		log.debug("明文数据:"+s1);
//		try {
//			String s2 = encode(s1);
//			log.debug("密文数据:"+s2);
//			String s3 = decode(s2);
//			log.debug("解密数据:"+s3);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	/*
	 //ios
	 //
	//  DES3Util.h
	//  lx100-gz
	//
	//  Created by   13-12-30.
	//
	
		#import <Foundation/Foundation.h>
		
		
		@interface DES3Util : NSObject {
		
		}
		
		// 加密方法
		+ (NSString*)encrypt:(NSString*)plainText;
		
		// 解密方法
		+ (NSString*)decrypt:(NSString*)encryptText;
		
		@end

		//
		//  DES3Util.m
		//  lx100-gz
		//
		//  Created by  柳峰 on 12-9-17.
		//  Copyright 2012 http://blog.csdn.net/lyq8479. All rights reserved.
		//
		
		#import "DES3Util.h"
		#import <CommonCrypto/CommonCryptor.h>
		#import "GTMBase64.h"
		
		#define gkey			@"liuyunqiang@lx100$#365#$"
		#define gIv             @"01234567"
		
		@implementation DES3Util
		
		// 加密方法
		+ (NSString*)encrypt:(NSString*)plainText {
		    NSData* data = [plainText dataUsingEncoding:NSUTF8StringEncoding];
			size_t plainTextBufferSize = [data length];
			const void *vplainText = (const void *)[data bytes];
		    
		    CCCryptorStatus ccStatus;
		    uint8_t *bufferPtr = NULL;
		    size_t bufferPtrSize = 0;
		    size_t movedBytes = 0;
		    
		    bufferPtrSize = (plainTextBufferSize + kCCBlockSize3DES) & ~(kCCBlockSize3DES - 1);
		    bufferPtr = malloc( bufferPtrSize * sizeof(uint8_t));
		    memset((void *)bufferPtr, 0x0, bufferPtrSize);
		    
		    const void *vkey = (const void *) [gkey UTF8String];
		    const void *vinitVec = (const void *) [gIv UTF8String];
		    
		    ccStatus = CCCrypt(kCCEncrypt,
		                       kCCAlgorithm3DES,
		                       kCCOptionPKCS7Padding,
		                       vkey,
		                       kCCKeySize3DES,
		                       vinitVec,
		                       vplainText,
		                       plainTextBufferSize,
		                       (void *)bufferPtr,
		                       bufferPtrSize,
		                       &movedBytes);
		    
		    NSData *myData = [NSData dataWithBytes:(const void *)bufferPtr length:(NSUInteger)movedBytes];
			NSString *result = [GTMBase64 stringByEncodingData:myData];
		    return result;
		}
		
		// 解密方法
		+ (NSString*)decrypt:(NSString*)encryptText {
		    NSData *encryptData = [GTMBase64 decodeData:[encryptText dataUsingEncoding:NSUTF8StringEncoding]];
			size_t plainTextBufferSize = [encryptData length];
			const void *vplainText = [encryptData bytes];
		    
		    CCCryptorStatus ccStatus;
		    uint8_t *bufferPtr = NULL;
		    size_t bufferPtrSize = 0;
		    size_t movedBytes = 0;
		    
		    bufferPtrSize = (plainTextBufferSize + kCCBlockSize3DES) & ~(kCCBlockSize3DES - 1);
		    bufferPtr = malloc( bufferPtrSize * sizeof(uint8_t));
		    memset((void *)bufferPtr, 0x0, bufferPtrSize);
		    
		    const void *vkey = (const void *) [gkey UTF8String];
		    const void *vinitVec = (const void *) [gIv UTF8String];
		    
		    ccStatus = CCCrypt(kCCDecrypt,
		                       kCCAlgorithm3DES,
		                       kCCOptionPKCS7Padding,
		                       vkey,
		                       kCCKeySize3DES,
		                       vinitVec,
		                       vplainText,
		                       plainTextBufferSize,
		                       (void *)bufferPtr,
		                       bufferPtrSize,
		                       &movedBytes);
		    
		    NSString *result = [[[NSString alloc] initWithData:[NSData dataWithBytes:(const void *)bufferPtr 
										length:(NSUInteger)movedBytes] encoding:NSUTF8StringEncoding] autorelease];
		    return result;
		}
		
		@end
	  */
}
