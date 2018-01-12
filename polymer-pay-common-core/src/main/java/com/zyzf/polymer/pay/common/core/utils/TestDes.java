package com.zyzf.polymer.pay.common.core.utils;

import java.nio.ByteBuffer;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.lang.StringUtils;

public class TestDes {
	//主密钥
//	static String MasterKey="0123456789ABCDEF0123456789ABCDEF";
//	public static String debug = ConConfig.getAttribute("ANXINYB_DAISHOU_DEBUG_STATUS");
//	static{
//		if("1".equals(debug)){
//			MasterKey="B9D9434F73681383C8F4E01CFB7A0867";
//		}
//	}
	
	/**
	 * pin加密
	 * 
	 * @param Pan
	 * 			主账号
	 * @param Pin
	 * 			明文密码
	 * @param PinKey
	 * 			明文PIN密钥
	 * @param MasterKey
	 * 			主密钥
	 */
	public static String MakeDesPin(String Pan,String Pin,String PinKey, String MasterKey)
	{
		PinKey=DesUtil.decode3DES(PinKey,MasterKey);
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
			byte[] res=XORContent(hexStringToByte(Pin),hexStringToByte(Pan));
			System.out.println("异或数据=["+Pan+"]["+Pin+"]");
			System.out.println("异或的结果=["+byte2hex(res)+"]");
			if(PinKey.length()==32)
			{
				System.out.println("=====加密开始========");
				System.out.println("加密数据=["+byte2hex(res)+"]["+PinKey+"]");
				byte[] res1=DesUtil.compute3DES(getDESKey(PinKey),res);
				DesPin=byte2hex(res1);
				System.out.println("加密的结果=["+DesPin+"]");
				System.out.println("=====加密结束========");
			}else{
				System.out.println("=====加密开始========");
				System.out.println("加密数据=["+byte2hex(res)+"]["+PinKey+"]");
				byte[] res1=encodc(res,getDESKey(PinKey));
				DesPin=byte2hex(res1);
				System.out.println("加密的结果=["+DesPin+"]");
				System.out.println("=====加密结束========");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return DesPin;
	}
	/**
	 * EBC MAC算法
	 * 
	 * @param MacKey
	 * 			明文MAC密钥
	 * @param macBlock
	 * 			加密数据
	 * @param MasterKey
	 * 			主密钥
	 */
	public static String MakeECBMac(String MacKey,String macBlock,String MasterKey)
	{
		System.out.println("主密钥:"+MasterKey);
		System.out.println("密文MAC:"+MacKey);
		if(null != MasterKey)
			MacKey=DesUtil.decode3DES(MacKey,MasterKey);
		System.out.println("明文MAC:"+MacKey);
		String mac="";
		try{
			ByteBuffer buffer = ByteBuffer.allocate(2048);
			System.out.println("原始数据=["+macBlock+"]");
			byte [] message=hexStringToByte(macBlock);
	
			buffer.put(message);
			//若原始数据不是8的倍数，则右补齐0x00
			if(buffer.position()%8!=0){
				int j=buffer.remaining()%8;
				for(int k=0;k<j;k++){
					buffer.put((byte) 0x00);
				}
			}
			buffer.flip();
			message=new byte[buffer.remaining()];
			buffer.get(message,0,message.length);
			buffer.flip();
			System.out.println("补齐后的数据=["+byte2hex(message)+"]");
	
			byte [] tmp=new byte[8];
			byte [] str_init={0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
			for(int i=0;i<buffer.limit()/8;i++){
				buffer.get(tmp);
				byte[] res=XORContent(str_init,tmp);
				System.out.println("=====第"+(i+1)+"次异或开始========");
				System.out.println("异或数据=["+byte2hex(str_init)+"]["+byte2hex(tmp)+"]");
				System.out.println("异或的结果=["+byte2hex(res)+"]");
				str_init=res;
				System.out.println("=====第"+(i+1)+"次异或结束========");
				System.out.println("");
			}
			String ASC=HexStr2ASCII(byte2hex(str_init));
			System.out.println("=====转换ASC开始========");
			System.out.println("转换为16个字节的ASC码=["+byte2hex(str_init)+"]");
			System.out.println("转换为ASC码的结果=["+ASC+"]");
			System.out.println("=====转换ASC结束========");
			System.out.println("");
			System.out.println("=====取前8个字节做DES加密开始========");
			String ASCq8=ASC.substring(0, 16);
			byte[] res1=DesUtil.compute3DES(getDESKey(MacKey),hexStringToByte(ASCq8));
			
			System.out.println("加密数据=["+ASCq8+"]["+MacKey+"]");
			System.out.println("加密的结果=["+byte2hex(res1)+"]");
			System.out.println("=====加密结束========");
			System.out.println("=====ASC前8个字节密文与ASC后8字节异或开始========");
			String ASCh8=ASC.substring(16);
			System.out.println("异或数据=["+byte2hex(res1)+"]["+ASCh8+"]");
			byte[] res2=XORContent(res1,hexStringToByte(ASCh8));
			System.out.println("异或的结果=["+byte2hex(res2)+"]");
			System.out.println("=====异或结束========");
			System.out.println("=====异或结果DES加密开始========");
			byte[] res3=DesUtil.compute3DES(getDESKey(MacKey),res2);

			System.out.println("加密数据=["+byte2hex(res2)+"]["+MacKey+"]");
			System.out.println("加密的结果=["+byte2hex(res3)+"]");
			System.out.println("=====加密结束========");
			String ASC2=HexStr2ASCII(byte2hex(res3));
			System.out.println("=====转换ASC开始========");
			System.out.println("转换为16个字节的ASC码=["+byte2hex(res3)+"]");
			System.out.println("转换为ASC码的结果=["+ASC2+"]");
			System.out.println("=====转换ASC结束========");
			mac=ASC2.substring(0,16);
			System.out.println("UBC运算结果=["+mac+"]");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mac;
	}
	
	/**
	 * 弘达上业MAC算法
	 * 
	 * @param MacKey
	 * 			明文MAC密钥
	 * @param macBlock
	 * 			加密数据
	 */
	public static String MakeMac(String MacKey,String macBlock,String MasterKey){
		System.out.println("主密钥:"+MasterKey);
		System.out.println("密文MAC:"+MacKey);
		MacKey=DesUtil.decode3DES(MacKey,MasterKey);
		System.out.println("明文MAC:"+MacKey);
		String mac="";
		try{
			ByteBuffer buffer = ByteBuffer.allocate(2048);
			System.out.println("原始数据=["+macBlock+"]");
			byte [] message=hexStringToByte(macBlock);
	
			buffer.put(message);
			//若原始数据不是8的倍数，则右补齐0x00
			if(buffer.position()%8!=0){
				int j=buffer.remaining()%8;
				for(int k=0;k<j;k++){
					buffer.put((byte) 0x00);
				}
			}
			buffer.flip();
			message=new byte[buffer.remaining()];
			buffer.get(message,0,message.length);
			buffer.flip();
			System.out.println("补齐后的数据=["+byte2hex(message)+"]");
	
			byte [] tmp=new byte[8];
			byte [] str_init={0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
			for(int i=0;i<buffer.limit()/8;i++){
				buffer.get(tmp);
				byte[] res=XORContent(str_init,tmp);
				System.out.println("=====第"+(i+1)+"次异或开始========");
				System.out.println("异或数据=["+byte2hex(str_init)+"]["+byte2hex(tmp)+"]");
				System.out.println("异或的结果=["+byte2hex(res)+"]");
				str_init=res;
				System.out.println("=====第"+(i+1)+"次异或结束========");
				System.out.println("");
			}
			
			byte[] res1=DesUtil.compute3DES(getDESKey(MacKey),str_init);
			mac=byte2hex(res1);
			
			System.out.println("mac运算结果=["+mac+"]");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mac;
	}
	
	/**
	 * ANSI 9.9 MAC算法
	 * 
	 * @param MacKey
	 * 			明文MAC密钥
	 * @param macBlock
	 * 			加密数据
	 */
	public static String MakeANSI99Mac(String MacKey,String macBlock,String MasterKey,byte[] initByte){
		System.out.println("主密钥:"+MasterKey);
		System.out.println("密文MAC:"+MacKey);
		if(null != MasterKey)
			MacKey=DesUtil.decode3DES(MacKey,MasterKey);
		System.out.println("明文MAC:"+MacKey);
		String mac="";
		try{
			ByteBuffer buffer = ByteBuffer.allocate(2048);
			System.out.println("原始数据=["+macBlock+"]");
			byte [] message=hexStringToByte(macBlock);
	
			buffer.put(message);
			//若原始数据不是8的倍数，则右补齐0x00
			if(buffer.position()%8!=0){
				int j=buffer.remaining()%8;
				for(int k=0;k<j;k++){
					buffer.put((byte) 0x00);
				}
			}
			buffer.flip();
			message=new byte[buffer.remaining()];
			buffer.get(message,0,message.length);
			buffer.flip();
			System.out.println("补齐后的数据=["+byte2hex(message)+"]");
	
			byte [] tmp=new byte[8];
			byte [] str_init={0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
			System.arraycopy(initByte, 0, str_init, 0, 8);
			for(int i=0;i<buffer.limit()/8;i++){
				buffer.get(tmp);
				byte[] res=XORContent(str_init,tmp);
				System.out.println("=====第"+(i+1)+"次异或开始========");
				System.out.println("异或数据=["+byte2hex(str_init)+"]["+byte2hex(tmp)+"]");
				System.out.println("异或的结果=["+byte2hex(res)+"]");
//				byte[] res1=DesUtil.encrypt(res, MacKey.substring(0, 16).getBytes("UTF-8"));
				byte[] res1=DesUtil.compute3DES(getDESKey(MacKey),res);
				System.out.println("加密的结果=["+byte2hex(res1)+"]");
				str_init=res1;
				System.out.println("=====第"+(i+1)+"次异或结束========");
				System.out.println("");
			}
			
			//byte[] res1=DesUtil.compute3DES(getDESKey(MacKey),str_init);
			mac=byte2hex(str_init);
			
			System.out.println("mac运算结果=["+mac+"]");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mac;
	}
	
	/**
	 * ANSI 9.9 MAC算法
	 * 
	 * @param MacKey
	 * 			明文MAC密钥
	 * @param macBlock
	 * 			加密数据
	 */
	public static String MakeANSI99Mac(String MacKey,String macBlock,String MasterKey){
		byte [] initByte={0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
		return MakeANSI99Mac(MacKey, macBlock, MasterKey, initByte);
	}
	
	/**
	 * ANSI 9.9 MAC算法
	 * 
	 * @param MacKey
	 * 			明文MAC密钥
	 * @param macBlock
	 * 			加密数据
	 */
	public static String MakeANSI99Mac_JNHX(String MacKey,String macBlock,String MasterKey){
		MacKey=DesUtil.decode3DES(MacKey,MasterKey);
		byte [] initByte={0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11};
		return MakeANSI99Mac(MacKey+MacKey, macBlock, null, initByte);
	}

	//异或
	public static byte[] XORContent( byte[] content1,byte[] content2 )
	{
	   int i=0;
	   int len = content1.length;
	   //byte []sOutMAC =new byte[8];
	   byte[] res=new byte[len];
	   for( i=0; i<len; i++){
		   res[i]=(byte) (content1[i]^content2[i]);
	   }
	   return res;
	   
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
	//DES解密
	public static byte[] decodc(byte[] myinfo,byte[] key) {
		String Algorithm = "DES"; //DES,DESede,Blowfish
		try {
			DESKeySpec dks = new DESKeySpec(key);

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Algorithm);
			SecretKey deskey = keyFactory.generateSecret(dks);

			System.out.println("解密前数据=[" + byte2hex(myinfo)+"]");

			Cipher c1 = Cipher.getInstance("DES/ECB/NoPadding");
			c1.init(Cipher.DECRYPT_MODE, deskey);

			byte[] cipherByte = c1.doFinal(myinfo);
			System.out.println("解密结果=[" + byte2hex(cipherByte)+"]");
			return cipherByte;

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}
	//DES加密
	public static byte[] encodc2(byte[] myinfo,byte[] key) {
		// Security.addProvider(new com.sun.crypto.provider.SunJCE());
		String Algorithm = "DESede"; //DES,DESede,Blowfish
		try {
			DESKeySpec dks = new DESKeySpec(key);

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Algorithm);
			SecretKey deskey = keyFactory.generateSecret(dks);
			//System.out.println("加密前数据=[" + byte2hex(myinfo)+"]");
			Cipher c1 = Cipher.getInstance("DESede/ECB/NoPadding");
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
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}
	public static byte[] getDESKey(String hexKey){
		String strKey=hexKey.toUpperCase();
		return hexStringToByte(strKey);
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
	 * pin加密 不带主账号信息
	 * @param Pan 主账号
	 * @param Pin 明文密码
	 * @param PinKey 明文PIN密钥
	 * @param MasterKey 主密钥
	 */
	public static String ZYTLPospEncryptPin(String bit53,String Pin,String PinKey, String MasterKey,Date date){
		if(null != MasterKey)
			PinKey=DesUtil.decode3DES(PinKey,MasterKey);
		String DesPin="";
		if(StringUtils.isBlank(Pin)) return DesPin;
		try{//Pin格式
			Pin=Int2HexStr(Pin.length())+Pin;
			int PinLen=Pin.length();
			for(int j=0;j<16-PinLen;j++)
			{
				Pin+="F";
			}
			//生成新的pinKey
			String nowTime = TimeUtils.getDateTime(date,"yyyyMM");
			String temp = bit53.substring(0, 8);//获取53域前8位
			String newPin = nowTime + temp + "80";//生成变种key
//			newPin = "2015080714065380";
			//做3DES加密
			byte[] newKey = DesUtil.compute3DES(hexStringToByte(PinKey),hexStringToByte(newPin));
//			System.out.println("变种秘钥=["+byte2hex(newKey)+"     "+"22A57CA3E8842D63".equals(byte2hex(newKey))+"]");
			//将pin格式转换为字节数组
			byte[] pinByte = hexStringToByte(Pin);
			//新的pinKey对pin格式的数据进行单des加密
			byte[] res = encodc(pinByte, newKey);
			DesPin = byte2hex(res);
//			System.out.println("密码=["+DesPin+"     "+"9AED59306EDBEEC7".equals(DesPin)+"]");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return DesPin;
	}
	
	/**
	 * 大POS磁道信息加密
	 * @param trkKey
	 * @param track
	 * @return
	 */
	public static String ZYTLPospEncryptTrack(String bit53, String trkKey,String MasterKey,String track,Date date) throws Exception {
		System.out.println("***********bit53="+bit53);
		if(null != MasterKey)
			trkKey=DesUtil.decode3DES(trkKey,MasterKey);
		
		//生成新的pinKey
		String nowTime = TimeUtils.getDateTime(date,"yyyyMM");
		String temp = bit53.substring(0, 8);//获取53域前8位
		String newPin = nowTime + temp + "80";//生成变种key
		
//		newPin = "2015080714065380";//###########3
		
		byte[] tempKey = DesUtil.compute3DES(hexStringToByte(trkKey), hexStringToByte(newPin));
//		System.out.println("变种秘钥=["+byte2hex(tempKey)+"     "+"6A2985F6DB7C5ED2".equals(byte2hex(tempKey))+"]");
		//获取53域后8位
		String temp2 = bit53.substring(8).toUpperCase();
//		System.out.println("***********temp2="+temp2);
		String dongtaiKey = HexStr2ASCII(temp2);
//		System.out.println("***********dongtaiKey="+dongtaiKey + "   " +"3030303232323932".equals(dongtaiKey) );
//		System.out.println("***********tempKey="+tempKey.length);
		byte [] geneKey = TestDes.encodc(hexStringToByte(dongtaiKey), tempKey);
//		System.out.println("新变种秘钥=["+byte2hex(geneKey)+"     "+"08CDDD2DDB485C5E".equals(byte2hex(geneKey))+"]");
		
		//将磁道数据按8位分组用磁道密钥加密，不足8位的无需加密。
		StringBuilder sb = new StringBuilder("");
		int count = track.length() / 16;
		String DN1 = "";
		byte[] bytes;
		if (track.length()%16 != 0){
			DN1 = track.substring(count*16);
		}
		String tem = "";
		for (int i=0; i<count; i++){
			String strTrack = track.substring(i * 16, (i + 1) * 16);
			bytes = TestDes.encodc(hexStringToByte(strTrack),geneKey);
			tem = byte2hex(bytes);
			sb.append(tem);
		}
		String miTrk = sb.toString() + DN1;
//		System.out.println("新变种秘钥=["+miTrk+"     "+"713E5833DC03C38F2F5FC4D85D2E6A1220000".equals(miTrk)+"]");
		return miTrk;
	}
	
	/**
	 * 大pos MAC算法
	 *
	 * @param MacKey
	 * 			明文MAC密钥
	 * @param macBlock
	 * 			加密数据
	 * @param MasterKey
	 * 			主密钥
	 */
	public static String ZYTLPospMakeMac(String bit53, String MacKey,String macBlock,String MasterKey,Date date){
		System.out.println("主密钥:"+MasterKey);
		System.out.println("密文MAC:"+MacKey);
		if(null != MasterKey)
			MacKey=DesUtil.decode3DES(MacKey,MasterKey);
		System.out.println("明文MAC:"+MacKey);
		String mac="";
		try{
			//生成新的macKey
			String nowTime = TimeUtils.getDateTime(date,"yyyyMM");
			String temp = bit53.substring(0, 8);//获取53域前8位时间
			String newMac = nowTime + temp + "80";
			
//			newMac = "2015110714065380";//##########
//			newMac = "2014060610252180";//##########
			
			byte[] newMacs = DesUtil.compute3DES(hexStringToByte(MacKey),hexStringToByte(newMac));
			System.out.println("变种秘钥=["+byte2hex(newMacs)+"  "+"E7C33EF188E5A750".equals(byte2hex(newMacs))+"]");
			
			ByteBuffer buffer = ByteBuffer.allocate(2048);
			System.out.println("原始数据=["+macBlock+"]");
			macBlock += "80";
			if(macBlock.length() % 2 == 1)macBlock += "0";
			while(macBlock.length() % 16 != 0){
				macBlock += "00";
			}
//			macBlock += "8000000000000000";
			byte [] message=hexStringToByte(macBlock);
			System.out.println("message 长度=["+message.length+"]");
			buffer.put(message);
			buffer.flip();
			System.out.println("补齐后的数据=["+byte2hex(message)+"]");

			byte [] tmp=new byte[8];
			byte [] str_init={0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
			byte [] str_end={(byte) 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
			for(int i=0;i<buffer.limit()/8;i++){
				buffer.get(tmp);
				byte[] res=XORContent(str_init,tmp);
//				System.out.println("=====第"+(i+1)+"次异或开始========");
				System.out.println("异或数据=["+byte2hex(str_init)+"]["+byte2hex(tmp)+"]");
				System.out.println("异或的结果=["+byte2hex(res)+"]");
				//使用变种macKey对数据进行单des加密
				byte[] res1=encodc(res, newMacs);
				System.out.println("异或并加密结果=["+byte2hex(res1)+"]");
				str_init = res1;
//				System.out.println("=====第"+(i+1)+"次异或结束========");
			}
			//最终的加密结果与0x8000000000000000异或，异或与Mac置换密钥加密，加密结果转16进制字符串，然后取前8位
			byte[] res=XORContent(str_init,str_end);
			System.out.println("异或数据=["+byte2hex(str_init)+"][byte2hex(str_end)]");
			System.out.println("异或的结果=["+byte2hex(res)+"]");
			str_init=encodc(res, newMacs);

			String ASC=byte2hex(str_init);
			System.out.println("UBC运算结果=["+ASC+"]");
			mac = HexStr2ASCII(ASC.substring(0, 8));
			//mac = byte2hex(ASC.substring(0,8).getBytes());//取前8位
			System.out.println("UBC运算结果=["+mac+ "   "+ "DC091A23".equals(mac)+"]");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mac;
	}
	
	/**
	 * 对特殊域加密 2域,61域,62域
	 * @param bit53
	 * @param area
	 * @param sexKey
	 * @return
	 */
	public static String ZYTLPospSpecial(String bit53, String area, String sexKey,String MasterKey,Date date) throws Exception {
		if(null != MasterKey)
			sexKey=DesUtil.decode3DES(sexKey,MasterKey);
		//取53域的前8位
		String nowTime = TimeUtils.getDateTime(date,"yyyyMM");;//得到交易时间
		String temp = bit53.substring(0, 8);//获取53域前8位
		String newPin = nowTime + temp + "80";
		
//		newPin = "2015102321182380";
		
		String length = NumberUtil.supplyAll(String.valueOf(area.length()), 4);
		String P = HexStr2ASCII(area);
		String D = length + P;
		while(D.length()%16 != 0 ){
			D += "0";
		}
		
		System.out.println("敏感数据秘钥=["+D+"]    "+"9CE138B5CF7CCC01".equals(D));

		//生成变种key
		byte[] geneKey = DesUtil.compute3DES(hexStringToByte(sexKey),hexStringToByte(newPin));
		System.out.println("变种秘钥=["+byte2hex(geneKey)+"]    "+"9CE138B5CF7CCC01".equals(byte2hex(geneKey)));
		
		ByteBuffer buffer = ByteBuffer.allocate(2048);
		byte [] message=hexStringToByte(D);
		System.out.println("message 长度=["+message.length+"]");
		buffer.put(message);
		buffer.flip();
		byte [] tmp=new byte[8];
		StringBuilder sb = new StringBuilder();
		byte [] str_init=null;
		for(int i=0;i<buffer.limit()/8;i++){
			buffer.get(tmp);
			str_init=encodc(tmp, geneKey);
			System.out.println("敏感信息=["+byte2hex(tmp)+"]  ["+byte2hex(str_init)+"]");
			sb.append(byte2hex(str_init));
		}
		
		String sex = sb.toString();//byte2hex(geneKey);
		System.out.println("sex=["+sex+"]");
		return sex;
	}


public static void main(String[] args) throws Exception {
		
//		//密文密码密钥
//		String PinKey="4251D1FA52CE5C83013A5077CD4F019D";
//		//密文报文密钥
//		String MacKey="13E9C5C80E0DE0A160606F846F92C4F0";
//		String macBlock="02002020048230C0101131000000000102100037393320202020202020336225882003327217D000010120805209700101996226300116634824D156156000000000000000000000011414144912DD000000000000D000000000000D0000000000000000313030313130303538323631313030383931313130303142D11AE746D8AFDD000801000001";
//		//System.out.println("标准MAC运算,返回:"+MakeBaseMac(MacKey,macBlock));
//		//System.out.println("UBC MAC运算,返回:"+MakeUBCMac(MacKey,macBlock));
//		
//		String Pan="6225882003327217";
//		String Pin="123456";
//		//System.out.println("Pin加密,返回:"+MakeDesPin(Pan,Pin,PinKey));
//		
//		//String key="HBNetBCN";
//		//String myinfo="634AE604ECF32AFE1987FBE884B1E7F2B4035F7ABB5480C4";
//		//System.out.println(byte2hex(decodc(hexStringToByte(myinfo), key.getBytes())));
//		MasterKey="0123456789ABCDEF0123456789ABCDEF";
//		//MacKey="AC4BF58CE035380ED46126E01DEEDB60";
//		MacKey=DesUtil.decode3DES(MacKey,MasterKey);
//		System.out.println("MacKey:"+MacKey);
		
//		String tc2 = "1234567890123456789=05082017819991683FFFFFFFFFFF";
//		String tc3 = "1234567890123456789=156000000000000000000378199921600000508000000000000000000000=000000000003=00000000";
//		String str = tc2 + tc3 + "FFFFFFFFFF";
//		
//		String s = PackageUtil.byte2hex(PackageUtil.ASCII2BCD(str.getBytes("UTF-8")));
//		System.out.println(s);
		
//		String key = "0177ADC5A2240926EBD3F8C877BD6320";
//		String src = "1CF6442054EA935AA5AD866C7646570C9C4C3750DF62032D"+"FFFFFFFFFFFFFFFF";
//		System.out.println(PackageUtil.hexStringToByte(src).length);
//		//String ss = new String(ThreeDES.decryptMode(key.getBytes(), src.getBytes()));
//		String ss = PackageUtil.byte2hex(ThreeDES.decryptMode(key.getBytes(), PackageUtil.hexStringToByte(src)));
//		System.out.println(ss);
		
//		System.out.println(TimeUtils.getDateTime(new Date(),"ddHHmmss"));
//		System.out.println(TimeUtils.getDateTime(new Date(),"DDHHMMSS"));
//		byte[] a = {(byte) 0x80};
//		System.out.println(new String(hexStringToByte("80")));
		
	//mac
//		String macBlock = "02002020048020C2981331000016034602100037B4956FAB9664F7F4A9C75B9330A6997E2000003943424243363044313733363635363833393634383734005453493D31337C534E3D3030303053445030303030317C54503D303030305038305443507C43543D317C52563D53585858313230343130313536622BA85FFB70541107140653000222920011010000010000000431323032";
//		String macKey = "9B625C4B41DC10A3DB5B727050FDC707";
//		System.out.println(MakePOSMac("2015110714065380", macKey,macBlock,null,new Date()));
		String macBlock = "0200202004C020C298113100000000020210001237826B28EB1CCB4C7E1D011DB75CD344DC5000003943424243363044313733363635363833393634383734003953493D31337C534E3D36313131323031353233323231317C54503D53434C383330307C43543D323135366C09A8D80038420B16180000776468D8000801000001";
		String macKey = "9B625C4B41DC10A3DB5B727050FDC707";
		System.out.println(ZYTLPospMakeMac("16180000776468D8", macKey,macBlock,null,new Date()));
//		String macBlock = "02002024048020C218113100000001793010021000248C6747915C3B9633D30102063943424243363044313733363635363833393634383734004653493D31337C53533D38393836303031313031313335303035333539317C42493D31323334353637383930313233ECDF0D590EE521ED051503240000000000110000000100003737454337464234";
//		String macKey = "9B625C4B41DC10A3DB5B727050FDC707";
//		System.out.println(MakePOSMac("15032400000000", macKey,macBlock,null,new Date()));
		
	
		//pin
//		String bit53 = "0714065300022292";
//		ZYTLPospEncryptPin(bit53, "123456", "853883621347F13A6B32D519196E997A", null, new Date());

		//磁道
//	    String track = "6227003112310611234D43035200101020000";
//		String trkKey = "91B5F212C0014F46DDE67BEDEA09DC82";
//		System.out.println(ZYTLPospEncryptTrack("0714065300022292", trkKey, null, track, new Date()));
	
		//异或
//		byte[] res=XORContent(hexStringToByte("E3D33A695883318A"),hexStringToByte("9813310000160346"));
//		byte[] res1=XORContent(hexStringToByte("9813310000160346"),hexStringToByte("E3D33A695883318A"));
//		System.out.println("异或数据=["+byte2hex(res)+"]");
//		System.out.println("异或数据=["+byte2hex(res1)+"]");
		
		//sex
//		String area = "000001000456";
//		String sexKey = "46A22B4B4E0268BACA5B4068F66F5A45";
//		System.out.println(encryptSpecial("21182300040026", area, sexKey, new Date()));
		
	}

}