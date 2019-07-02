package common;


import com.ybs.util.ECB3Des;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class Aes128 {
	/*
	 * �����õ�Key ������26����ĸ��������� �˴�ʹ��AES-128-CBC����ģʽ��key��ҪΪ16λ��
	 */
	private String sKey = "1234567848615840";
	private String ivParameter = "0000000000000000";
	private static Aes128 instance = null;
	private static String HEX = "0123456789ABCDEF";
	
	public static Aes128 getInstance() {
		if (instance == null)
			instance = new Aes128();
		return instance;
	}
	 
	//加密
	public String encrypt(String sSrc)  {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] raw = sKey.getBytes("GBK");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes("utf-8"));// ʹ��CBCģʽ����Ҫһ������iv�������Ӽ����㷨��ǿ��
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(sSrc.getBytes("GBK"));
			return bytesToHex(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 解密
	public String decrypt(String sSrc) {
		try {
			byte[] raw = sKey.getBytes("GBK");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes("utf-8"));
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted1 = hexToBytes(sSrc);
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original,"GBK");
			return originalString;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	private static byte[] _HexToBytes(String hex) {
		String _hex = hex.toUpperCase();
		byte[] result = new byte[hex.length() / 2];
		for (int i = 0; i < _hex.length() / 2; i++) {
			byte HH = (byte) (HEX.indexOf(_hex.substring(2 * i, 2 * i + 1)) << 4);
			byte HL = (byte) (HEX.indexOf(_hex.substring(2 * i + 1, 2 * i + 2)));
			result[i] = (byte) (HH | HL);
		}
		return result;
	}


	public static String toHex(byte one) {
		String HEX = "0123456789ABCDEF";
		byte[] bTmp = new byte[2];
		bTmp[0] = (byte) (HEX.charAt((one & 0xf0) >> 4));
		bTmp[1] = (byte) (HEX.charAt(one & 0x0f));
		String result = new String(bTmp);
		return result;
	}



	/**
	 * 将十六进制字符串改为字节数组
	 * 
	 * @param hex
	 *            16进制字符串
	 * @return 字节数组
	 */
	public static byte[] hexToBytes(String hex) {
		byte temp[] = _HexToBytes(hex);
		return temp;
	}

	/**
	 * 将字节数组改为16进制字符串
	 * 
	 * @param bytes
	 *            字节数组
	 * @return 16进制字符串
	 */
	public static String bytesToHex(byte[] bytes) {
		String ret = "";
		for (int i = 0; i < bytes.length; i++) {
			ret += toHex(bytes[i]);
		}
		return ret;
	}

	public static void main(String[] args) throws Exception {
		// ��Ҫ���ܵ��ִ�
		String cSrc = "E34318DA913A0E18A83AE0417D535A088293DCC5F2BBEF2E2FB16F46E8F218359B517421C099D3E8FEDEC40FD17DEABDF53050420CA1BCC8";
		String str = "cfca1234";
		// System.out.println(cSrc);BEF818E0BA41ED7C1949D4AB562FE2DD
		// // ����                                                                 BEF818E0BA41ED7C1949D4AB562FE2DD
		// String enString = Aes128.getInstance().encrypt(cSrc);
		// System.out.println("���ܺ���ִ��ǣ�" + enString);

		// ����
		String DeString = ECB3Des.decryptECB3Des(cSrc);
		String eString = Aes128.getInstance().encrypt(str);
		Base64 base64 = new Base64();
		String a="深圳市大兴宝腾汽车销售服务有限公司";
		String shanghu=base64.encodeToString(a.getBytes());
		System.out.println(DeString);
		System.out.println(shanghu);
	}

}
