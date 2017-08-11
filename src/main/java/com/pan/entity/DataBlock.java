package com.pan.entity;

/**
 * @author yp2
 * @date 2015-11-18
 * @description �������ݿ�
 */
public abstract class DataBlock {
	
	/**
	 * ָ�����ݿ���������ĳ��ȣ�4���ֽ�
	 */
	private byte[] length;
	/**
	 * ���ݿ���������ASCII��ĸ(A-Z��a-z)��ɣ�4���ֽ�
	 */
	private byte[] chunkTypeCode;
	/**
	 * ���ݿ�����
	 */
	protected byte[] data;
	/**
	 * �洢��������Ƿ��д����ѭ�������룬4���ֽ�
	 */
	private byte[] crc;
	
	public DataBlock() {
		length = new byte[4];
		chunkTypeCode = new byte[4];
		crc = new byte[4];
		data = null;
	}
	
	public byte[] getLength() {
		return length;
	}
	public void setLength(byte[] length) {
		this.length = length;
	}
	public byte[] getChunkTypeCode() {
		return chunkTypeCode;
	}
	public void setChunkTypeCode(byte[] chunkTypeCode) {
		this.chunkTypeCode = chunkTypeCode;
	}
	public byte[] getData() {
		return data;
	}
	public byte[] getCrc() {
		return crc;
	}
	public void setCrc(byte[] crc) {
		this.crc = crc;
	}
	
	public abstract void setData(byte[] data);
}
