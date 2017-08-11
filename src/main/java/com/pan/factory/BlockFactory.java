package com.pan.factory;

import java.io.IOException;
import java.io.InputStream;

import com.pan.entity.DataBlock;
import com.pan.entity.IDATBlock;
import com.pan.entity.IENDBlock;
import com.pan.entity.IHDRBlock;
import com.pan.entity.PHYSBlock;
import com.pan.entity.PLTEBlock;
import com.pan.entity.Png;
import com.pan.entity.SRGBBlock;
import com.pan.entity.TEXTBlock;
import com.pan.entity.TRNSBlock;
import com.pan.utils.BlockUtil;
import com.pan.utils.ByteUtil;

public class BlockFactory {
	
	public static DataBlock readBlock(InputStream in, Png png, DataBlock dataBlock) throws IOException {
		String hexCode = ByteUtil.byteToHex(dataBlock.getChunkTypeCode(), 
								0, dataBlock.getChunkTypeCode().length);
		hexCode = hexCode.toUpperCase();
		DataBlock realDataBlock = null;
		if(BlockUtil.isIHDR(hexCode)) {
			//IHDR���ݿ�
			realDataBlock = new IHDRBlock();
		} else if(BlockUtil.isPLTE(hexCode)) {
			//PLTE���ݿ�
			realDataBlock = new PLTEBlock();
		} else if(BlockUtil.isIDAT(hexCode)) {
			//IDAT���ݿ�
			realDataBlock = new IDATBlock();
		} else if(BlockUtil.isIEND(hexCode)) {
			//IEND���ݿ�
			realDataBlock = new IENDBlock();
		} else if(BlockUtil.isSRGB(hexCode)) {
			//sRGB���ݿ�
			realDataBlock = new SRGBBlock();
		} else if(BlockUtil.istEXt(hexCode)) {
			//tEXt���ݿ�
			realDataBlock = new TEXTBlock();
		} else if(BlockUtil.isPHYS(hexCode)) {
			//pHYs���ݿ�
			realDataBlock = new PHYSBlock();
		} else if(BlockUtil.istRNS(hexCode)) {
			//tRNS���ݿ�
			realDataBlock = new TRNSBlock();
		} else {
			//�������ݿ�
			realDataBlock = dataBlock;
		}
		realDataBlock.setLength(dataBlock.getLength());
		realDataBlock.setChunkTypeCode(dataBlock.getChunkTypeCode());
		//��ȡ����,����Ĳ��԰������ǣ� ���������ݶ�ȡ���ڴ���
		int len = -1;
		byte[] data = new byte[8096];
		len = in.read(data, 0, ByteUtil.highByteToInt(dataBlock.getLength()));
		realDataBlock.setData(ByteUtil.cutByte(data, 0, len));
		return realDataBlock;
	}
	
}
