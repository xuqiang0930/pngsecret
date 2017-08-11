package com.pan.utils;

/**
 * @author yp2
 * @date 2015-11-18
 * @description �ֽڲ�������
 */
public class ByteUtil {

	/**
	 * ��byte����ת��Ϊ16�����ַ���
	 * <br/>
	 * ʵ��˼·��
	 * �Ƚ�byteת����int,��ʹ��Integer.toHexString(int)
	 * @param data	byte����
	 * @return
	 */
	public static String byteToHex(byte[] data, int start, int end) {
		StringBuilder builder = new StringBuilder();
		for(int i = start; i < end; i++) {
			int tmp = data[i] & 0xff;
			String hv = Integer.toHexString(tmp);
			if(hv.length() < 2) {
				builder.append("0");
			}
			builder.append(hv);
			/*builder.append(" ");*/
			if(i % 16 == 15) {
				/*builder.append("\n");*/
			}
		}
		return builder.toString();
	}
	
	/**
	 * ��byte����ת��Ϊ16�����ַ���(���ַ�������鿴)
	 * �����Ϣ�棺16���ֽ�һ����ʾ
	 * @param data
	 * @param start
	 * @param end
	 * @return
	 */
	public static String byteToHexforPrint(byte[] data, int start, int end) {
		StringBuilder builder = new StringBuilder();
		for(int i = start; i < end; i++) {
			int tmp = data[i] & 0xff;
			String hv = Integer.toHexString(tmp);
			if(hv.length() < 2) {
				builder.append("0");
			}
			builder.append(hv);
			builder.append(" ");
			if(i % 16 == 15) {
				builder.append("\n");
			}
		}
		return builder.toString();
	}
	
	/**
	 * ʮ�������ַ���ת��Ϊ�ֽ�����
	 * @param hexStr	ʮ�������ַ���
	 * @return			�ֽ�����
	 */
	public static byte[] hexToByte(String hexStr) {
		byte[] datas = new byte[(hexStr.length() - 1) / 2 + 1];
		hexStr = hexStr.toUpperCase();
		int pos = 0;
		for(int i = 0; i < hexStr.length(); i+=2) {
			if(i + 1 < hexStr.length()) {
				datas[pos] = (byte) ((indexOf(hexStr.charAt(i)+"") << 4) + indexOf(hexStr.charAt(i+1)+""));
			}
			pos++;
		}
		return datas;
	}
	
	/**
	 * ����ָ���ַ���������Ҫ�����ַ�����16��������ʾ������
	 * @param str
	 * @return
	 */
	public static int indexOf(String str) {
		return "0123456789ABCDEF".indexOf(str);
	}
	
	/**
	 * ����byte��������ʾ��ֵ���ֽ������ֵ��С�˱�ʾ����λ�ڵ������ϣ���λ�ڸ�����
	 * <br/>
	 * ����data = {1,2},��ô���Ϊ: 2 << 8 + 1 = 513
	 * @param data	byte����
	 * @return		�������ֵ
	 */
	public static long lowByteToLong(byte[] data) {
		long sum = 0;
		for(int i = 0; i < data.length; i++) {
			long value = ((data[i] & 0xff) << (8 * i));
			sum += value;
		}
		return sum;
	}
	
	/**
	 * ����byte��������ʾ��ֵ���ֽ������ֵ�Դ�˱�ʾ����λ�ڸ������ϣ���λ�ڵ�����
	 * <br/>
	 * ����data = {1,2},��ô���Ϊ: 1 << 8 + 2 = 258
	 * @param data	byte����
	 * @return		�������ֵ
	 */
	public static long highByteToLong(byte[] data) {
		long sum = 0;
		for(int i = 0; i < data.length; i++) {
			long value = ((data[i] & 0xff) << (8 * (data.length - i - 1)));
			sum += value;
		}
		return sum;
	}
	
	/**
	 * ����byte��������ʾ��ֵ���ֽ������ֵ��С�˱�ʾ����λ�ڵ������ϣ���λ�ڸ�����
	 * <br/>
	 * ����data = {1,2},��ô���Ϊ: 2 << 8 + 1 = 513
	 * @param data	byte����
	 * @return		�������ֵ
	 */
	public static int lowByteToInt(byte[] data) {
		int sum = 0;
		for(int i = 0; i < data.length; i++) {
			long value = ((data[i] & 0xff) << (8 * i));
			sum += value;
		}
		return sum;
	}
	
	/**
	 * ����byte��������ʾ��ֵ���ֽ������ֵ�Դ�˱�ʾ����λ�ڸ������ϣ���λ�ڵ�����
	 * <br/>
	 * ����data = {1,2},��ô���Ϊ: 1 << 8 + 2 = 258
	 * @param data	byte����
	 * @return		�������ֵ
	 */
	public static int highByteToInt(byte[] data) {
		int sum = 0;
		for(int i = 0; i < data.length; i++) {
			long value = ((data[i] & 0xff) << (8 * (data.length - i - 1)));
			sum += value;
		}
		return sum;
	}
	
	/**
	 * longֵת��Ϊָ�����ȵ�С���ֽ�����
	 * @param data		longֵ
	 * @param len		����
	 * @return			�ֽ�����,С����ʽչʾ
	 */
	public static byte[] longToLowByte(long data, int len) {
		byte[] value = new byte[len];
		for(int i = 0; i < len; i++) {
			value[i] = (byte) ((data >> (8 * i )) & 0xff);
		}
		return value;
	}
	
	/**
	 * longֵת��Ϊָ�����ȵĴ���ֽ�����
	 * @param data		longֵ
	 * @param len		����
	 * @return			�ֽ�����,�����ʽչʾ
	 */
	public static byte[] longToHighByte(long data, int len) {
		byte[] value = new byte[len];
		for(int i = 0; i < len; i++) {
			value[i] = (byte) ((data >> (8 * (len - 1 - i) )) & 0xff);
		}
		return value;
	}
	
	/**
	 * intֵת��Ϊָ�����ȵ�С���ֽ�����
	 * @param data		intֵ
	 * @param len		����
	 * @return			�ֽ�����,С����ʽչʾ
	 */
	public static byte[] intToLowByte(int data, int len) {
		byte[] value = new byte[len];
		for(int i = 0; i < len; i++) {
			value[i] = (byte) ((data >> (8 * i )) & 0xff);
		}
		return value;
	}
	
	/**
	 * intֵת��Ϊָ�����ȵĴ���ֽ�����
	 * @param data		intֵ
	 * @param len		����
	 * @return			�ֽ�����,�����ʽչʾ
	 */
	public static byte[] intToHighByte(int data, int len) {
		byte[] value = new byte[len];
		for(int i = 0; i < len; i++) {
			value[i] = (byte) ((data >> (8 * (len - 1 - i) )) & 0xff);
		}
		return value;
	}
	
	/**
	 * ����base��exponent�η�
	 * @param base  	����
	 * @param exponent	ָ��
	 * @return
	 */
	public static long power(int base, int exponent) {
		long sum = 1;
		for(int i = 0; i < exponent; i++) {
			sum *= base;
		}
		return sum;
	}
	
	/**
	 * �ü��ֽ����ݣ���ȡָ����ʼλ�ã�0��ʼ����ĵڸ�len�ֽ�
	 * @param data		ԭ�����ֽ�����
	 * @param start		��ʼλ��
	 * @param len		����
	 * @return			�ü�����ֽ�����
	 */
	public static byte[] cutByte(byte[] data, int start, int len) {
		byte[] value = null;
		do {
			if(len + start > data.length || start < 0 || len <= 0) {
				break;
			}
			value = new byte[len];
			for(int i = 0; i < len; i++) {
				value[i] = data[start + i];
			}
		} while (false);
		
		return value;
	}
	
	public static void main(String[] args) {
		byte[] data = new byte[]{1,2};
		System.out.println(highByteToInt(data));
		System.out.println(lowByteToInt(data));
		System.out.println(byteToHex(intToHighByte(258, 4), 0, 4));
		System.out.println(byteToHex(intToLowByte(258, 4), 0, 4));
	}
}
