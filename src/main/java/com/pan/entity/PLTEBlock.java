package com.pan.entity;

import com.pan.utils.ByteUtil;

/**
 * @author yp2
 * @date 2015-11-18
 * @description PLTE���ݿ�
 */
public class PLTEBlock extends DataBlock {
	
	/**
	 * ��ɫ����Ϣ��ÿһ����ɫ����Ϣ��3���ֽڹ��ɣ��ֱ�ΪRGB
	 * �ܵ���Ϣ��λ1-256��
	 */
	private byte[][] palettes;

	public byte[][] getPalettes() {
		return palettes;
	}

	public void setPalettes(byte[][] palettes) {
		this.palettes = palettes;
	}

	@Override
	public void setData(byte[] data) {
		int length = ByteUtil.highByteToInt(this.getLength());
		int col = 3;
		int row = length / col;
		palettes = new byte[row][col];
		for(int i = 0; i < col; i++) {
			for(int j = 0; j < row; j++) {
				palettes[j][i] = data[j * col + i];
			}
		}
		
		this.data = data;
	}
	
	

}
