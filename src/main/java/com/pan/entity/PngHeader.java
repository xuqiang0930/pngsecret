package com.pan.entity;

/**
 * @author yp2
 * @date 2015-11-18
 * description pngͷ����Ϣ
 */
public class PngHeader {
	
	/**
	 * png�ļ�ͷ����Ϣ���̶�,8���ֽ�
	 */
	private byte[] flag;
	
	public PngHeader() {
		flag = new byte[8];
	}

	public byte[] getFlag() {
		return flag;
	}
	public void setFlag(byte[] flag) {
		this.flag = flag;
	}
	

}
