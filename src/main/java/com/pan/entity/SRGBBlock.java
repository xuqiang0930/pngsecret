package com.pan.entity;

import com.pan.utils.ByteUtil;

/**
 * @author yp2
 * @date 2015-11-18
 * @description sRGB���ݿ�
 */
public class SRGBBlock extends DataBlock{
	
	/**
	 * Rendering intent,1���ֽ�
	 */
	private byte[] renderingIntent;
	
	public SRGBBlock() {
		super();
		renderingIntent = new byte[1];
	}

	public byte[] getRenderingIntent() {
		return renderingIntent;
	}
	public void setRenderingIntent(byte[] renderingIntent) {
		this.renderingIntent = renderingIntent;
	}

	@Override
	public void setData(byte[] data) {
		int pos = 0;
		this.renderingIntent = ByteUtil.cutByte(data, pos, this.renderingIntent.length);
		pos += this.renderingIntent.length;
		
		this.data = data;
	}

}
