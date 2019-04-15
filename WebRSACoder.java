package com.wodan.platform.foundation.util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.lang.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

/**
 * WEB版本的RSA算法加密/解密工具类。用于JS
 * 
 * @author Administrator
 * 
 */
public class WebRSACoder {

	/** 算法名称 */
	private static final String ALGORITHOM = "RSA";
	/** 密钥大小 */
	private static final int KEY_SIZE = 1024;
	/** 默认的安全服务提供者 */
	private static final Provider DEFAULT_PROVIDER = new BouncyCastleProvider();

	private static final String PUBLIC_KEY = "RSAPublicKey";
	private static final String PRIVATE_KEY = "RSAPrivateKey";

	/**
	 * 生成并返回RSA密钥对。
	 */
	public static KeyPair generateKeyPair() {
		try {
			// 随机生成密码种子
			String radamKey = Float.toString(new Date().getTime() / 1000F);
			// 初始化密钥生成器
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
			keyPairGen.initialize(KEY_SIZE, new SecureRandom(radamKey.getBytes()));
			KeyPair oneKeyPair = keyPairGen.generateKeyPair();
			return oneKeyPair;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成并返回RSA密钥对。
	 */
	public static Map<String, Object> initKeyInfo() {
		KeyPair keyPair = generateKeyPair();
		// 公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		// 私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		Map<String, Object> keyMap = new HashMap<>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}

	/**
	 * 
	 * @Description:
	 * @param initKeyMap
	 * @return
	 */
	public static String getModulus(Map<String, Object> initKeyMap) {
		String modulus = null;
		Object pubKeyObj = initKeyMap.get(PUBLIC_KEY);
		if (pubKeyObj instanceof RSAPublicKey) {
			RSAPublicKey key = (RSAPublicKey) pubKeyObj;
			modulus = new String(Hex.encode(key.getModulus().toByteArray()));
		}
		return modulus;
	}

	/**
	 * 
	 * @Description:
	 * @param initKeyMap
	 * @return
	 */
	public static String getPublicExponent(Map<String, Object> initKeyMap) {
		String publicExponent = null;
		Object pubKeyObj = initKeyMap.get(PUBLIC_KEY);
		if (pubKeyObj instanceof RSAPublicKey) {
			RSAPublicKey key = (RSAPublicKey) pubKeyObj;
			publicExponent = new String(Hex.encode(key.getPublicExponent().toByteArray()));
		}
		return publicExponent;
	}

	/**
	 * 取得私钥
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static byte[] getPrivateKey(Map<String, Object> keyMap) {
		byte[] prilicKeyByteArray = null;
		Object priKeyObj = keyMap.get(PRIVATE_KEY);
		if (priKeyObj instanceof Key) {
			Key key = (Key) priKeyObj;
			prilicKeyByteArray = key.getEncoded();
		}
		return prilicKeyByteArray;
	}

	/**
	 * 取得私钥
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getHexPrivateKey(Map<String, Object> keyMap) {
		return HexUtil.bytesToHexString(getPrivateKey(keyMap));
	}

	/**
	 * 取得公钥
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static byte[] getPublicKey(Map<String, Object> keyMap) {
		byte[] publicKeyByteArray = null;
		Object pubKeyObj = keyMap.get(PUBLIC_KEY);
		if (pubKeyObj instanceof Key) {
			Key key = (Key) pubKeyObj;
			publicKeyByteArray = key.getEncoded();
		}
		return publicKeyByteArray;
	}

	/**
	 * 取得公钥
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getHexPublicKey(Map<String, Object> keyMap) throws Exception {
		return HexUtil.bytesToHexString(getPublicKey(keyMap));
	}

	/**
	 * 使用指定的私钥解密数据。
	 * 
	 * @param privateKey
	 *            给定的私钥。
	 * @param data
	 *            要解密的数据。
	 * @return 原数据。
	 */
	public static byte[] decrypt(PrivateKey privateKey, byte[] data) {
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			return cipher.doFinal(data);
		} catch (Exception e) {
			return new byte[] {};
		}
	}

	/**
	 * 使用私钥解密给定的字符串。
	 * 
	 * @param encrypttext
	 *            密文。
	 * @return 原文字符串。
	 */
	public static String decryptByPriKey(String encrypttext, String priKey) {
		if (StringUtils.isBlank(encrypttext)) {
			return null;
		}
		try {
			// 解密由base64编码的私钥
			byte[] keyBytes = HexUtil.hexStringToBytes(priKey);
			// 构造PKCS8EncodedKeySpec对象
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			// KEY_ALGORITHM 指定的加密算法
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHOM);
			// 取私钥匙对象
			RSAPrivateKey rsaPriKey = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);

			byte[] en_data = Hex.decode(encrypttext);
			byte[] data = decrypt(rsaPriKey, en_data);
			return new String(data);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 使用默认的私钥解密由JS加密（使用此类提供的公钥加密）的字符串。
	 * 
	 * @param encrypttext
	 *            密文。
	 * @return {@code encrypttext} 的原文字符串。
	 */
	public static String decryptStringByJs(String encrypttext, String priKey) {
		String text = decryptByPriKey(encrypttext, priKey);
		if (text == null) {
			return null;
		}
		return StringUtils.reverse(text);
	}

	public static void main(String[] args) {
		Map<String, Object> keyInfo = initKeyInfo();
		String modulus = getModulus(keyInfo);
		String publicExponent = getPublicExponent(keyInfo);
		String priKey = getHexPrivateKey(keyInfo);
		// 公钥-系数(n)
		System.out.println("public key modulus:" + modulus);
		// 公钥-指数(e1)
		System.out.println("public key exponent:" + publicExponent);

		System.out.println("priKey:" + priKey);

		// JS加密后的字符串
		String pppp = "2974491244ca308c881c501df0547aade1e03372acc80e382bd4010d00d6444a232f83c88475371e28292c2a30de5d7b3329e47b2690c530ad6a4b70c5214fce1f1f30280b1536c3cf842a491964e657f64d3de189fffab70197a31a710749b42c5c3594f143a113d2dd1e1eb58affd934b1f760d5774f5a314152cf95c1f8a7";
		// 解密后的字符串
		String kkkk = WebRSACoder.decryptStringByJs(pppp,
				"30820276020100300D06092A864886F70D0101010500048202603082025C02010002818100BFD8F901152158E8DA178CDF2F52D37FF629D7A7809741008854911EC911D3B8F30F1B5CB0B8ED94F59C9F108563C7D317D926F19201BF544E2765041356C1A28CC5A720D5BFD05E5C5E72B7273516E9CEB4BDAB84991DA0FD7DD60E5EAA6BF91A3639167927CD4E6625AD5C0A4FEEE358D2B76684C1B0D8EC2397AE31676C1F020301000102818027355EDE9BC4EEF8D7E9CC3EA8DB52A26A5AEEDA2AEBBEBB2F21A96F1344F3726F2A7D3B1A5B11234A7732031C5FE22D0048BF2ECBDA5392154EF3D43B6795B71A5C3745E9B245D9CEEC7C4B3E6E21F6F491116912A54E11DB091AFE8B76B8B34AFECB4BB331201DE084E363752B0A0F7B611CF2ACFBAA70C36730C66956FB11024100DE0BD1AA12BF32D704697C0DF58AD96A4E710642020EDA0EDC6EB6755B4DBAB15D30F3DDEE66B4AC464E974F6AB8637770E31CDF13DFD802CE63CD4F25E44B53024100DD2F00F674DD612B0D10FF88D745B2B7742F4414D68DC45ED8CB1E61939A62E0D6EFAAC731405367E4D7BCDC7C73E4F9A8B2DB2C634467A40B04F0256FA34E8502407FB988B72DE571972160940D6B8A9D197B59A461DFD12F39912ED621978D94B0658CBD1A9201DE37A2919E93FE2D6679D423AFFDD9EECED07E6C06335AF681250241008F5EF57FCBD6E83250C0F0F2D19FC32DB9691DF767EFF3170BEDF5DE881FEDF21240BE7169D2697E11CF6A037DC45DEB1AD7FCCBF3F89639B64ED81F5C12FE4502400BE81C38340EB86718D621F1B25347FBB09F75909AB012A3E9A9C55C98B533DE09739EC791D1A3B69E0BBF1EECF30C7DC99B10B325FCB3AEC1D1A720F2F08A5F");
		System.out.println("解密后文字：" + kkkk);
	}
}
