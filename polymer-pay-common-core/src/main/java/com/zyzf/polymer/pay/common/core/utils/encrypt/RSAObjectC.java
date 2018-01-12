package com.zyzf.polymer.pay.common.core.utils.encrypt;
import it.sauronsoftware.base64.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 原来在用mac 系统中自带的openssl生成的密钥对文件是X509编码格式的。而我们JAVA所需的私钥文件是PKCS#8编码格式的。。所以要将在mac 系统中生成的私钥文件转下码就行了
 * 而安卓需要 添加Cipher cipher = Cipher.getInstance("RSA", "BC");
 * 
 * 三个平台使用
 * @author Administrator
 *
 */
public class RSAObjectC {
	private static final Log log = LogFactory.getLog(RSAObjectC.class);
	private static final String DEFAULT_PUBLIC_KEY= 
		"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCq5Tm4BeEmV0k/z+uu8ok27isv" + "\r" +
		 "7bRhyeoCsdJyNlYFkXCjrsMuPTSyrOd0APHpbA3VRd0DeCIfa5u6MVIWgSeC26LB" + "\r" +
		 "ZKw+UExt3rbVT7EdsUO27LIA4rPZD7VtxFOL7NHzq84xH9YgFh9vDUv0deNfiqoL" + "\r" +
		 "pZzBT3QUmenndlyXXwIDAQAB" + "\r";
	
	private static final String DEFAULT_PRIVATE_KEY=
		"MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKrlObgF4SZXST/P" + "\r" +
		"667yiTbuKy/ttGHJ6gKx0nI2VgWRcKOuwy49NLKs53QA8elsDdVF3QN4Ih9rm7ox" + "\r" +
		"UhaBJ4LbosFkrD5QTG3ettVPsR2xQ7bssgDis9kPtW3EU4vs0fOrzjEf1iAWH28N" + "\r" +
		"S/R141+KqgulnMFPdBSZ6ed2XJdfAgMBAAECgYAqE5ZKgOgWkJcwZvkYcg7mv21S" + "\r" +
		"t6waB6TeVPoDtO0pIc4h/v1/fQJkm73BChqF5lzhjfoEyKdeJRiKl2omsFYRsrer" + "\r" +
		"Xb1JjVjbNbqmTD1e1TFTBuqwCGuqEKKr8BIW0tohbpVHvy8rgxn2iCnAjhBP3tUo" + "\r" +
		"2IilCWcM6sh/nAkjgQJBAONE3szDo/CuXLnh68I1w85tPuGv/nGvbcjo4H9d6bA9" + "\r" +
		"zdAkoMx1x7qI68Bri7iuS5OB4p09MJi52KAbcM4PgocCQQDAf+4vMDKjGe7lySvz" + "\r" +
		"NuRRALrtN7a/RNsGoPIAU5Ksgg6ewrh9Jo2g7U0SPAjqoaDcjPDX3aL1PBnujTAk" + "\r" +
		"7QJpAkEAs8fwQ6Tj0+nqejTobfpgCbTL0HaLXl07CHqH1NlymjNZ0zSWawck1z+m" + "\r" +
		"1tF8drySFn7hyCb2gWpKneT3KFqGkwJBALu5RmcAQrFp7d5qs5CIfFk+NAx346Bd" + "\r" +
		"Hpc1RyroVUYjwuwyCkYJ3d4M7LcWkr91hXDvhUOOIYez57oKN+QaudkCQQDQH6FE" + "\r" +
		"jjOwIuuO7AjbU8r17QS4LwjpJPFeVrnINW1vU9n7dBBEN3+n5Q7OT7r3kBfSiYzV" + "\r" +
		"x0100MA8KvTjCPvJ"+ "\r";



	/**
	 * 私钥
	 */
	private RSAPrivateKey privateKey;

	/**
	 * 公钥
	 */
	private RSAPublicKey publicKey;
	
	/**
	 * 字节数据转字符串专用集合
	 */
	private static final char[] HEX_CHAR= {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	

	/**
	 * 获取私钥
	 * @return 当前的私钥对象
	 */
	public RSAPrivateKey getPrivateKey() {
		return privateKey;
	}

	/**
	 * 获取公钥
	 * @return 当前的公钥对象
	 */
	public RSAPublicKey getPublicKey() {
		return publicKey;
	}

	/**
	 * 随机生成密钥对
	 */
	public void genKeyPair(){
		KeyPairGenerator keyPairGen= null;
		try {
			keyPairGen= KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		keyPairGen.initialize(1024, new SecureRandom());
		KeyPair keyPair= keyPairGen.generateKeyPair();
		this.privateKey= (RSAPrivateKey) keyPair.getPrivate();
		this.publicKey= (RSAPublicKey) keyPair.getPublic();
	}

	/**
	 * 从文件中输入流中加载公钥
	 * @param in 公钥输入流
	 * @throws Exception 加载公钥时产生的异常
	 */
	public void loadPublicKey(InputStream in) throws Exception{
		try {
			BufferedReader br= new BufferedReader(new InputStreamReader(in));
			String readLine= null;
			StringBuilder sb= new StringBuilder();
			while((readLine= br.readLine())!=null){
				if(readLine.charAt(0)=='-'){
					continue;
				}else{
					sb.append(readLine);
					sb.append('\r');
				}
			}
			loadPublicKey(sb.toString());
		} catch (IOException e) {
			throw new Exception("公钥数据流读取错误");
		} catch (NullPointerException e) {
			throw new Exception("公钥输入流为空");
		}
	}


	/**
	 * 从字符串中加载公钥
	 * @param publicKeyStr 公钥数据字符串
	 * @throws Exception 加载公钥时产生的异常
	 */
	public void loadPublicKey(String publicKeyStr) throws Exception{
		try {
			log.debug("公钥：#"+new String(Base64.encode(publicKeyStr.getBytes("UTF-8")),"UTF-8"));
//			BASE64Decoder base64Decoder= new BASE64Decoder();
//			byte[] buffer= base64Decoder.decodeBuffer(publicKeyStr);
			byte[] buffer = Base64.decode(publicKeyStr.getBytes("UTF-8"));
			KeyFactory keyFactory= KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec= new X509EncodedKeySpec(buffer);
			publicKey= (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("公钥非法");
		} catch (IOException e) {
			throw new Exception("公钥数据内容读取错误");
		} catch (NullPointerException e) {
			throw new Exception("公钥数据为空");
		}
	}

	/**
	 * 从文件中加载私钥
	 * @param keyFileName 私钥文件名
	 * @return 是否成功
	 * @throws Exception 
	 */
	public void loadPrivateKey(InputStream in) throws Exception{
		try {
			BufferedReader br= new BufferedReader(new InputStreamReader(in));
			String readLine= null;
			StringBuilder sb= new StringBuilder();
			while((readLine= br.readLine())!=null){
				if(readLine.charAt(0)=='-'){
					continue;
				}else{
					sb.append(readLine);
					sb.append('\r');
				}
			}
			loadPrivateKey(sb.toString());
		} catch (IOException e) {
			throw new Exception("私钥数据读取错误");
		} catch (NullPointerException e) {
			throw new Exception("私钥输入流为空");
		}
	}

	public void loadPrivateKey(String privateKeyStr) throws Exception{
		try {
			log.debug("私钥：#"+new String(Base64.encode(privateKeyStr.getBytes("UTF-8")),"UTF-8"));
//			BASE64Decoder base64Decoder= new BASE64Decoder();
//			byte[] buffer= base64Decoder.decodeBuffer(privateKeyStr);
			byte[] buffer = Base64.decode(privateKeyStr.getBytes("UTF-8"));
			PKCS8EncodedKeySpec keySpec= new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory= KeyFactory.getInstance("RSA");
			privateKey= (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("私钥非法");
		} catch (IOException e) {
			throw new Exception("私钥数据内容读取错误");
		} catch (NullPointerException e) {
			throw new Exception("私钥数据为空");
		}
	}

	/**
	 * 加密过程
	 * @param publicKey 公钥
	 * @param plainTextData 明文数据
	 * @return
	 * @throws Exception 加密过程中的异常信息
	 */
	public byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception{
		if(publicKey== null){
			throw new Exception("加密公钥为空, 请设置");
		}
		Cipher cipher= null;
		try {
			cipher= Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] output= cipher.doFinal(plainTextData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此加密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		}catch (InvalidKeyException e) {
			throw new Exception("加密公钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("明文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("明文数据已损坏");
		}
	}

	/**
	 * 解密过程
	 * @param privateKey 私钥
	 * @param cipherData 密文数据
	 * @return 明文
	 * @throws Exception 解密过程中的异常信息
	 */
	public byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception{
		if (privateKey== null){
			throw new Exception("解密私钥为空, 请设置");
		}
		Cipher cipher= null;
		try {
			cipher= Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] output= cipher.doFinal(cipherData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此解密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		}catch (InvalidKeyException e) {
			throw new Exception("解密私钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("密文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("密文数据已损坏");
		}		
	}
	
	/**
	 * 字节数据转十六进制字符串
	 * @param data 输入数据
	 * @return 十六进制内容
	 */
	public static String byteArrayToString(byte[] data){
		StringBuilder stringBuilder= new StringBuilder();
		for (int i=0; i<data.length; i++){
			//取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移
			stringBuilder.append(HEX_CHAR[(data[i] & 0xf0)>>> 4]);
			//取出字节的低四位 作为索引得到相应的十六进制标识符
			stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);
			if (i<data.length-1){
				stringBuilder.append(' ');
			}
		}
		return stringBuilder.toString();
	}

//	public static void main(String[] args){
//		try {
//			FileInputStream in1 = new FileInputStream(new File("D:\\miyao\\public_key.pem"));
//			FileInputStream in2 = new FileInputStream(new File("D:\\miyao\\private_key.pem"));
//		
//			loadPublicKey(in1);
//			loadPrivateKey(in2);
//		
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	private RSAObjectC(){
		
	}
	public static RSAObjectC getInstance(){
		RSAObjectC rsaEncrypt= new RSAObjectC();
		//rsaEncrypt.genKeyPair();

		//加载公钥
		try {
			rsaEncrypt.loadPublicKey(RSAObjectC.DEFAULT_PUBLIC_KEY);
			log.debug("加载公钥成功");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.err.println("加载公钥失败");
		}

		//加载私钥
		try {
			rsaEncrypt.loadPrivateKey(RSAObjectC.DEFAULT_PRIVATE_KEY);
			log.debug("加载私钥成功");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.err.println("加载私钥失败");
		}
		return rsaEncrypt;
	}
	
	public static RSAObjectC getInstance(String publicKey,String privateKey){
		RSAObjectC rsaEncrypt= new RSAObjectC();
		//rsaEncrypt.genKeyPair();

		//加载公钥
		try {
			rsaEncrypt.loadPublicKey(publicKey);
			log.debug("加载公钥成功");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.err.println("加载公钥失败");
		}

		//加载私钥
		try {
			rsaEncrypt.loadPrivateKey(privateKey);
			log.debug("加载私钥成功");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.err.println("加载私钥失败");
		}
		return rsaEncrypt;
	}
	
	
	public static void main(String[] args){
		RSAObjectC rsaEncrypt = getInstance();

		//测试字符串
		String encryptStr= "Test String chaijunkun";

		try {
			//加密
			byte[] cipher = rsaEncrypt.encrypt(rsaEncrypt.getPublicKey(), encryptStr.getBytes());
			//解密
			byte[] plainText = rsaEncrypt.decrypt(rsaEncrypt.getPrivateKey(), cipher);
			log.debug("密文长度:"+ cipher.length);
			log.debug(RSAObjectC.byteArrayToString(cipher));
			log.debug("明文长度:"+ plainText.length);
			log.debug(RSAObjectC.byteArrayToString(plainText));
			log.debug(new String(plainText));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
