package com.pan.entity;

import java.util.LinkedList;
import java.util.List;

/**
 * @author yp2
 * @date 2015-11-18
 * @description Png��ʽ
 */
public class Png {
	
	private PngHeader pngHeader;
	/**
	 * ���ݿ鼯��
	 */
	private List<DataBlock> dataBlocks;
	
	public Png() {
		dataBlocks = new LinkedList<DataBlock>();
	}

	
	public PngHeader getPngHeader() {
		return pngHeader;
	}

	public void setPngHeader(PngHeader pngHeader) {
		this.pngHeader = pngHeader;
	}

	public List<DataBlock> getDataBlocks() {
		return dataBlocks;
	}

	public void setDataBlocks(List<DataBlock> dataBlocks) {
		this.dataBlocks = dataBlocks;
	}
	
	

}
