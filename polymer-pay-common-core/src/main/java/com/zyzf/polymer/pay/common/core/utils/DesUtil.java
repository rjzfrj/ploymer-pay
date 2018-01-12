package com.zyzf.polymer.pay.common.core.utils;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.lang.StringUtils;



/**
 * 3DES加密
 * 
 * @version 1.0
 * @author
 * 
 */
public abstract class DesUtil {
	
	/**
	 * 密钥算法
	 * @version 1.0
	 * @author
	 */
	public static final String KEY_ALGORITHM = "DESede";
		
	/**
	 * 加密/解密算法/工作模式/填充方式
	 * @version 1.0
	 * @author
	 */	
	public static final String CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";
			
	/**
	 * 转换密钥
	 * @param key 二进制密�?
	 * @return key 密钥
	 * 
	 */	
	public static Key toKey(byte[] key) throws Exception{
		//实例化DES密钥材料
		DESedeKeySpec dks = new DESedeKeySpec(key);
		//实例化秘密密钥工�?
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
		//生成秘密密钥
		return keyFactory.generateSecret(dks);
	}

	/**
	 * 解密
	 * @param data 待解密数�?
	 * @param key 密钥
	 * @return byte[] 解密数据
	 */	
	public static byte[] decrypt(byte[] data, byte[] key)throws Exception{
		//还原密钥
		Key k = toKey(key);
		//"DESede/ECB/NoPadding"
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		//初始化，设置为解密模�?
		cipher.init(Cipher.DECRYPT_MODE, k);
		//执行操作
		return cipher.doFinal(data);
	}
	/**
	 * 3DES解密
	 * @param data 待解密数�?
	 * @param key 密钥
	 * @return byte[] 解密数据
	 */
	public static String decode3DES(String dataS, String keys){
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
				throw new RuntimeException("" + e);
			}
			return result;
	 }
	/**
	 * 3DES解密
	 * @param data 待解密数�?
	 * @param key 密钥
	 * @return byte[] 解密数据
	 */
	public static byte[] decode3DES(byte[] key, byte[] data){
		byte[] result=null;
		 try {
			 	//byte[] data=hexStringToByte(dataS);
			 	//byte[] key=hexStringToByte(keys);
				byte[] km = new byte[24];
				
				if (key.length == 16) {
					System.arraycopy(key, 0, km, 0, 16);
					System.arraycopy(key, 0, km, 16, 8);
				}else{
					System.arraycopy(key, 0, km, 0, 24);
				}
				Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
				DESedeKeySpec dks = new DESedeKeySpec(km);
				SecretKey k = SecretKeyFactory.getInstance("DESede")
						.generateSecret(dks);
				cipher.init(Cipher.DECRYPT_MODE, k);
				result=cipher.doFinal(data);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("" + e);
			}
			return result;
	 }
	
	 /**
     * 不是8位倍数时补足
     *
     * @param b
     * @return
     */
    public static byte[] complementZero(byte[] b) {
        int len = b.length;

        //data不足8位以0补足8位
        if (b.length % 8 != 0) {
            len = b.length - b.length % 8 + 8;
        } else {
            return b;
        }
        byte[] needData = null;
        needData = new byte[len];
        for (int i = 0; i < len; i++) {
            needData[i] = 0x00;
        }
        System.arraycopy(b, 0, needData, 0, b.length);
        return needData;
    }
	
	/**
	 * 3DES加密
	 * @param data 待加密数�?
	 * @param key 密钥
	 * @return byte[] 加密数据
	 */
	public static byte[] compute3DES(byte[] key, byte[] data) {
		try {
			byte[] km = new byte[24];
			if (key.length == 16) {
				System.arraycopy(key, 0, km, 0, 16);
				System.arraycopy(key, 0, km, 16, 8);
			}else{
				System.arraycopy(key, 0, km, 0, 24);
			}
			byte[] buff = complementZero(data);
			
			Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
			DESedeKeySpec dks = new DESedeKeySpec(km);
			SecretKey k = SecretKeyFactory.getInstance("DESede")
					.generateSecret(dks);
			cipher.init(Cipher.ENCRYPT_MODE, k);
			byte[] result = cipher.doFinal(buff);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("" + e);
		}
	}
	/**
	 * 3DES加密
	 * @param data 待加密数�?
	 * @param key 密钥
	 * @return byte[] 加密数据
	 */
	public static String encrypt3DES(String dataS, String keys){
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
				cipher.init(Cipher.ENCRYPT_MODE, k);
				result=byte2hex(cipher.doFinal(data));
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("" + e);
			}
			return result;
	 }
	//DES解密
	public static byte[] decodc(byte[] myinfo,byte[] key) {
		String Algorithm = "DES"; //DES,DESede,Blowfish
		try {
			DESKeySpec dks = new DESKeySpec(key);

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Algorithm);
			SecretKey deskey = keyFactory.generateSecret(dks);

			//System.out.println("解密前数据=[" + byte2hex(myinfo)+"]");

			Cipher c1 = Cipher.getInstance("DES/ECB/NoPadding");
			c1.init(Cipher.DECRYPT_MODE, deskey);

			byte[] cipherByte = c1.doFinal(myinfo);
			//System.out.println("解密结果=[" + byte2hex(cipherByte)+"]");
			return cipherByte;

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}
	//DES加密
	public static byte[] encodc(byte[] myinfo,byte[] key) {
		// Security.addProvider(new com.sun.crypto.provider.SunJCE());
		String Algorithm = "DES"; //DES,DESede,Blowfish
		try {
			DESKeySpec dks = new DESKeySpec(key);

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Algorithm);
			SecretKey deskey = keyFactory.generateSecret(dks);
			//System.out.println("加密前数据=[" + byte2hex(myinfo)+"]");
			Cipher c1 = Cipher.getInstance("DES/ECB/NoPadding");
			//Cipher c1 = Cipher.getInstance("DES");
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			byte[] cipherByte = c1.doFinal(myinfo);
			//System.out.println("加密结果=[" + byte2hex(cipherByte)+"]");
			return cipherByte;

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}
	//DES加密
	 public static byte[] encryptDES(byte[] strkey, byte[] strdate)
		throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(strkey);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(dks);
		Cipher encryptCipher = Cipher.getInstance("DES/ECB/NoPadding");
		encryptCipher.init(Cipher.ENCRYPT_MODE, key, sr);
		return encryptCipher.doFinal(strdate);
	}
	/**
	 * 加密
	 * @param data 待加密数�?
	 * @param key 密钥
	 * @return byte[] 加密数据
	 */	
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception{
		//还原密钥
		Key k = toKey(key);
		/**
		 * 实例�?
		 * 使用PKCS7Padding填充方式，按如下代码实现
		 * Cipher.getInstance(CIPHER_ALGORITHM,"BC");
		 */
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		//初始化，设置为解密模�?
		cipher.init(Cipher.ENCRYPT_MODE, k);
		//执行操作
		return cipher.doFinal(data);
	}
	
	/**
	 * 生成密钥
	 * 
	 * @return byte[] 二进制密�?
	 */	
	public static byte[] initKey() throws Exception{
		/**
		 * 实例�?
		 * 使用128位或192位长度密�?
		 * KeyGenerator.getInstance(KEY_ALGORITHM,"BC");
		 */
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		/**
		 * 初始�?
		 *使用128位或192位长度密钥，按如下代码实�?
		 *kg.init(128);
		 *kg.init(192);
		 */
		kg.init(168);
		//生成秘密密钥
		SecretKey secretKey = kg.generateKey();
		//获得密钥的二进制编码形式
		return secretKey.getEncoded();
	}
    //转换成十六进制字符串
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
	public static String Int2HexStr(int n)
	{
		String HexStr=Integer.toHexString(n);
		HexStr=HexStr.length()==1?"0"+HexStr:HexStr;
		return HexStr;
	}
	//MAC算法:
	public static String mac(String decKey,String orgData) throws Exception
	{
		byte[] dataByte=new byte[orgData.getBytes().length+1];
		System.arraycopy(orgData.getBytes(), 0, dataByte, 0, orgData.getBytes().length);
		dataByte[dataByte.length-1]=(byte)0x80;
						 
		byte[] addAscData=null;
						
		if(dataByte.length%8!=0){
			addAscData=new byte[(dataByte.length/8+2)*8];
			System.arraycopy(dataByte, 0, addAscData, 0, dataByte.length);
//			addAscData[dataByte.length]=(byte)0x80;
			for(int i =dataByte.length+1;i<addAscData.length;i++)
				addAscData[i]=0x00;
							
		}else{
			addAscData=new byte[dataByte.length+8];
			System.arraycopy(dataByte, 0, addAscData, 0, dataByte.length);
		}
		byte[] add={(byte)0x80,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
		System.arraycopy(add, 0, addAscData, addAscData.length-8, 8);

		byte[] initKey={0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
		String key=decKey;
		for(int i=0;i<addAscData.length/8;i++)
		{
			byte[]tmp=new byte[8];
			System.arraycopy(addAscData, i*8, tmp, 0, tmp.length);
							
			for(int j=0;j<8;j++){
				initKey[j]^=tmp[j];
			}			
			initKey=encryptDES(hexStringToByte(key),initKey);
		}
		return byte2hex(initKey).substring(0, 8);
	}

	//MAC验证:
	public static boolean macVerify(String orgMacStr,String merchantOrderTime,String macKey,String macStr) 
				throws Exception
	{
		//验MAC,过滤非法交易  主账户，个人身份标识码（密码密文），磁道2数据(密文)，商户订单号，订单金额,订单时间，交易类型码
		//String orgMacStr = accountNumber + " " + pin + " " + track2Data + " " + merchantOrderId + " " + amt + " " + merchantOrderTime + " " +  transType;
				
		//byte[] b = hexStringToByte(decode3DES(mKey,mainKey));
		//String macKey = byte2hex(b);
		byte[] desKey = compute3DES(hexStringToByte(macKey),hexStringToByte(merchantOrderTime + "80"));
		String mac=mac(byte2hex(desKey),orgMacStr).substring(0, 8);
		System.out.println("mac:"+mac);
		if(!mac.equals(macStr.toUpperCase())){
			return false;
		}
		return true;
	}

	//PIN加密
	public static String encryptPin(String orderTime,String Pin,String PinKey)
	{
		String mPin="";
		try{
			System.out.println("orderTime:"+orderTime);
			String DataKey=encrypt3DES(orderTime+"80",PinKey);
			System.out.println("DataKey:"+DataKey);
			
			Pin=Int2HexStr(Pin.length())+Pin;
			int PinLen=Pin.length();
			for(int j=0;j<16-PinLen;j++)
			{
				Pin+="F";
			}
			System.out.println("Pin:"+Pin);
			mPin=byte2hex(encryptDES(hexStringToByte(DataKey),hexStringToByte(Pin)));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mPin;
	}
	//PIN加密
	public static String newEncryptPin(String Pan,String Pin,String PinKey){
		String DesPin="";
		if(StringUtils.isBlank(Pin)) return DesPin;
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
			System.out.println("异或数据=["+Pan+"]["+Pin+"]");
			System.out.println("异或的结果=["+byte2hex(res)+"]");
			
			if(StringUtils.isBlank(PinKey)) return byte2hex(res);
			
			if(PinKey.length()==32)
			{
				System.out.println("=====加密开始========");
				System.out.println("加密数据=["+byte2hex(res)+"]["+PinKey+"]");
				byte[] res1=DesUtil.compute3DES(TestDes.getDESKey(PinKey),res);
				DesPin=byte2hex(res1);
				System.out.println("加密的结果=["+DesPin+"]");
				System.out.println("=====加密结束========");
			}else{
				System.out.println("=====加密开始========");
				System.out.println("加密数据=["+byte2hex(res)+"]["+PinKey+"]");
				byte[] res1=encodc(res,TestDes.getDESKey(PinKey));
				DesPin=byte2hex(res1);
				System.out.println("加密的结果=["+DesPin+"]");
				System.out.println("=====加密结束========");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return DesPin;
	}
	
	
	//解密密文密码
	public static String getDecPin(String pinKey,String orderTime,String encPin)throws Exception{
		//pinKey = decode3DES( pinKey,mainKey);
		orderTime = orderTime+"80";
		byte[] b = compute3DES(hexStringToByte(pinKey), hexStringToByte(orderTime));
		//byte[] bb = DESDECRYPT(b, hexStringToByte(encPin));
		byte[] bb =decodc(hexStringToByte(encPin),b);
		System.out.println(byte2hex(bb));
		return byte2hex(bb);
		}
	
	//解密密码
	public static String newGetDecPin(String Pan,String DesPin,String PinKey)throws Exception{
		String pin = "";
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
			e.printStackTrace();
		}
		return pin;
	}
	
	//明文磁道数据加密
	public static String encryptStanderTranck(String orderTime,String randomNum,String trkKey,String track)
	{	
		orderTime=orderTime+"80";
		System.out.println("orderTime:"+orderTime);
		System.out.println("randomNum:"+randomNum);
		
		int trackLen=track.length();
		int n=track.length()/16;
		int mod=track.length()%16;
		
		String modData=track.substring(trackLen-mod);
		track=track.substring(0, n*16);
		
		System.out.println("n:"+n);
		System.out.println("mod:"+mod);
		
		String mTrack="";
		try {
			String aKey = byte2hex(compute3DES(hexStringToByte(trkKey),hexStringToByte(orderTime)));
			System.out.println("aKey:"+aKey);
			String bKey=aKey+trkKey.substring(16);
			System.out.println("bKey:"+bKey);
			String cKey=byte2hex(encryptDES(hexStringToByte(bKey),hexStringToByte(randomNum)));
			System.out.println("cKey:"+cKey);
			System.out.println("track:"+track);
			//mTrack=byte2hex(encodc(hexStringToByte(track),hexStringToByte(cKey)));
			mTrack=byte2hex(encryptDES(hexStringToByte(cKey),hexStringToByte(track)));
			mTrack=trackLen+mTrack+modData;
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return mTrack;
	}
	
	public static String newEncryptStanderTranck(String trkKey,String track){	
		System.out.println("trkKey:"+trkKey);
		int trkLen=track.length();
		//track=Int2HexStr(trkLen)+track;
		int mod=trkLen%16;
		System.out.println("mod:"+mod);
		for(int i=0;i<16-mod;i++)
		{
			track+="F";
		}
		
		String mTrack="";
		try {
			System.out.println("track:"+track);
			mTrack=byte2hex(compute3DES(hexStringToByte(trkKey),hexStringToByte(track)));
			mTrack=Int2HexStr(mTrack.length())+mTrack;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mTrack;
	}
	
	public static String newDecryptStanderTranck(String trkKey,String desTtrack){	
		String track = "";
		try {
			//int len = Integer.parseInt(desTtrack.substring(0, 2), 10);
			desTtrack = desTtrack.substring(2);
			track = decode3DES(desTtrack,trkKey);
			track = track.replaceAll("F", "").toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return track;
	}
	
	
	//密文磁道数据解密
	public static String decryptStanderTranck(String tracKey,String orderTime,String randomNum,String track) throws Exception
	{
		//tracKey = decode3DES(tracKey,mainKey);
		orderTime = orderTime+"80";
		/******************调整*/
		String trackLen = track.substring(0, 2);
		System.out.println("trackLen:"+trackLen);
		int len = Integer.parseInt(trackLen);
		int n = len  / 16;
		String decTrac = track.substring(2, (n*16)+2);
		System.out.println("decTrac:"+decTrac);
		/******************end*/
		//String decTrac = track.substring(2, 34);
		byte[] tracKeyArr = hexStringToByte(tracKey);
		byte[] b = compute3DES(tracKeyArr, hexStringToByte(orderTime));
		String newTrackKey=byte2hex(b)+tracKey.substring(16);
		byte[] bb = encryptDES(hexStringToByte(newTrackKey), hexStringToByte(randomNum));
		byte[] bbb = decodc(hexStringToByte(decTrac),bb);
		decTrac = byte2hex(bbb);
		/******************调整*/
		decTrac = decTrac + track.substring((n*16)+2);
		decTrac = decTrac.replace("D", "=").replace("d", "=");
		/******************end*/
		String last = decTrac.substring(decTrac.length()-1);
		if(last.equals("f") || last.equals("F")){
			decTrac = decTrac.substring(0,decTrac.length()-1);
		}
		return decTrac;
	}
	
	//E点支付明文磁道数据加密
	public static String ideaioeEncryptStanderTranck(String trkKey,String track,String MasterKey){
		if(null != MasterKey)
			trkKey=decode3DES(trkKey,MasterKey);
		System.out.println("trkKey:"+trkKey);
		int endID=2;
		if(track.length()%2==1)
		{
			//track=track+"0";
			endID=1;
		}
		System.out.println("track:"+track);
		String mTrk=track.substring(track.length()-16-endID, track.length()-endID);
		String mTrack="";
		try {
			System.out.println("mTrk:"+mTrk);
			String mTrk2=byte2hex(compute3DES(hexStringToByte(trkKey),hexStringToByte(mTrk)));
			System.out.println("mTrk2:"+mTrk2);
			mTrack=track.replaceAll(mTrk, mTrk2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mTrack;
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
	
	//将字符串转为ASCII码 0 
	public static String HexStr2ASCII(String cnStr)
	{
		StringBuffer strBuf=new StringBuffer();
		//将字符串转换成字节序列
		byte[] bGBK=cnStr.getBytes();
		for(int i=0;i<bGBK.length;i++)
		{
			//将每个字符转换成ASCII码
			strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
			//System.out.println(Integer.toHexString(bGBK[i] & 0xff)); 
		}
		return strBuf.toString();
	}
	
	/**
	 * 获得主秘钥明文
	 * @param fl1
	 * @param fl2
	 * @param mainKey
	 * @param mainKeyCheck
	 * @return
	 * @throws Exception
	 */
	public static String getMastKey(String fl1,String fl2,String mainKey,String mainKeyCheck)throws Exception{
		String key = DesUtil.byte2hex(TestDes.XORContent(DesUtil.hexStringToByte(fl1), DesUtil.hexStringToByte(fl2)));
//		String miwen = jiemi(key, mainKey, mainKeyCheck);
		
		String mingwen=DesUtil.decode3DES(mainKey,key);
		String jy1 = DesUtil.encrypt3DES("0000000000000000", mingwen);
		if(mainKeyCheck.equals(jy1)){
			return mingwen;
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
//		String MasterKey="370A87908BB30AD71DED854E41E08C11";
//		String DesKey="AC4BF58CE035380ED46126E01DEEDB60";
//		
//		String orderTime=TimeUtils.getDateTimeToString()+"80";
//		orderTime="20120827090000";
//		String randomNum="9e75641d0d162db1";
//				
//		String trkKey="7A9BAAF69E579613D4F04047CEADCF48";
//		String track="b6212260200044981823d23092200739991037f";
//		//解密
//		//String result=DesUtil.decode3DES(DesKey, MasterKey);
//		//System.out.println(result);
//		//
//		String Pin="123456";
//		String PinKey="E3D13ACC2EC98480039FE9F955961CC0";
//		//System.out.println("PIN加密,返回:"+encryptPin(orderTime,Pin,PinKey));
//		
//		String encPin="b49c15918abfc729";
//		orderTime="20120827090000";
//		//System.out.println("PIN解密,返回:"+getDecPin(PinKey, orderTime, encPin));
//		
//		track="6222021001086720528d49121209499991227";
//		trkKey="FCEC16E3CC524B9D380FCCC34E40F502";
//		
//		System.out.println("磁道数据加密,返回:"+encryptStanderTranck(orderTime, randomNum, trkKey, track));
//		
//		track="3254C89BD907E71BB2E966BF940BBDF574";
//		track="3781DCA1E510629B07C3E3F7074CFA93837E142";
//		//System.out.println("解密磁道数据,返回:"+decryptStanderTranck(trkKey, orderTime, randomNum, track));
//		
//		//主账号
//		String accountNumber="6226220286298871";
//		//密文密码
//		String pin="b49c15918abfc729";
//		//密文磁道数据
//		String track2Data="3254C89BD907E71BB2E966BF940BBDF574";
//		//商户订单号
//		String merchantOrderId="1050000000009a1f6c08";
//		//订单金额
//		String amt="0";
//		//订单时间
//		String merchantOrderTime="20120827090000";
//		//交易类型码
//		String transType="100000";
//		
//		//验MAC,过滤非法交易  主账户，个人身份标识码（密码密文），磁道2数据(密文)，商户订单号，订单金额,订单时间，交易类型码
//		String orgMacStr = accountNumber + " " + pin + " " + track2Data + " " + merchantOrderId + " " + amt + " " + merchantOrderTime + " " +  transType;
//
//		String macKey="3666C42BDDA42A721D3146E1A4CE5765";
//		
//		//byte[] desKey = compute3DES(hexStringToByte(macKey),hexStringToByte(merchantOrderTime + "80"));
//
//		//System.out.println("mac运算,返回:"+mac(byte2hex(desKey), orgMacStr));
//		
//		String macStr="8A10310A";
//		//System.out.println("mac验证,返回:"+macVerify(orgMacStr, merchantOrderTime, macKey, macStr));
		
//		String PinKey="E3D13ACC2EC98480039FE9F955961CC0";
//		String Pin="123456";
//		String Pan = "6226220286298871";
//		String desPin = newEncryptPin(Pan, Pin, PinKey);
//		System.out.println("desPin = "+desPin);
//		
//		//解密
//		String jiemPin = newGetDecPin(Pan, desPin, PinKey);
//		System.out.println("jiemPin = "+jiemPin);
		
//		String track2Data="6222021001086720528d49121209499991227";
//		String trkKey="FCEC16E3CC524B9D380FCCC34E40F502";
//		String deStrack2Data = newEncryptStanderTranck(trkKey,track2Data);
//		System.out.println("deStrack2Data = "+deStrack2Data);
//		
//		String jiemStrack2Data = newDecryptStanderTranck(trkKey, deStrack2Data);
//		System.out.println("track2Data = "+track2Data);
//		System.out.println("jiemStrack2Data = "+jiemStrack2Data);
		System.out.println("mainKey :"+generate32Key());
		System.out.println("pinKey :"+generate32Key());
		System.out.println("trackKey :"+generate32Key());
	}
	
}
