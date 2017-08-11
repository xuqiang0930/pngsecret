package com.pan.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.pan.entity.CommonBlock;
import com.pan.entity.DataBlock;
import com.pan.entity.Png;
import com.pan.entity.PngHeader;
import com.pan.factory.BlockFactory;

/**
 * @author yp2
 * @date 2015-11-19
 * @decription �����ļ����ݵ�png��ʽͼƬ��
 */
public class PngUtil {
	
	/**
	 * ��ȡָ��png�ļ�����Ϣ
	 * @param pngFileName
	 * @return
	 * @throws IOException 
	 */
	private static Png readPng(String pngFileName) throws IOException {
		Png png = new Png();
		File pngFile = new File(pngFileName);
		InputStream pngIn = null;
		//��¼��������ȡλ��(�ֽ�Ϊ��λ)
		long pos = 0;
		try {
			pngIn = new FileInputStream(pngFile);
			//��ȡͷ����Ϣ
			PngHeader pngHeader = new PngHeader();
			pngIn.read(pngHeader.getFlag());
			png.setPngHeader(pngHeader);
			pos += pngHeader.getFlag().length;
			
			while(pos < pngFile.length()) {
				DataBlock realDataBlock = null;
				//��ȡ���ݿ�
				DataBlock dataBlock = new CommonBlock();
				//�ȶ�ȡ���ȣ�4���ֽ�
				pngIn.read(dataBlock.getLength());
				pos += dataBlock.getLength().length;
				//�ٶ�ȡ�����룬4���ֽ�
				pngIn.read(dataBlock.getChunkTypeCode());
				pos += dataBlock.getChunkTypeCode().length;
				//����������ٶ�ȡ����
				//��ȡ����
				realDataBlock = BlockFactory.readBlock(pngIn, png, dataBlock);
				pos += ByteUtil.highByteToInt(dataBlock.getLength());
				//��ȡcrc��4���ֽ�
				pngIn.read(realDataBlock.getCrc());
				//��Ӷ�ȡ�������ݿ�
				png.getDataBlocks().add(realDataBlock);
				pos += realDataBlock.getCrc().length;
				dataBlock = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if(pngIn != null) {
					pngIn.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			}
		}
		return png;
	}
	
	/**
	 * ����ȡ�����ļ���Ϣд�뵽ָ��png���ļ��У���ָ������ļ�
	 * @param png				Png��Ϣ����
	 * @param pngFileName		png�ļ���
	 * @param inputFileName		Ҫ���ص��ļ���
	 * @param outFileName		����ļ��������ݰ���png���ݺ�Ҫ�����ļ�����Ϣ
	 * @throws IOException 
	 */
	private static void wirteFileToPng(Png png, String pngFileName, String inputFileName, String outFileName) throws IOException {
		File pngFile = new File(pngFileName);
		File inputFile = new File(inputFileName);
		File outFile = new File(outFileName);
		InputStream pngIn = null;
		InputStream inputIn = null;
		OutputStream out = null;
		int len = -1;
		byte[] buf = new byte[1024];
		try {
			if(!outFile.exists()) {
				outFile.createNewFile();
			}
			pngIn = new FileInputStream(pngFile);
			inputIn = new FileInputStream(inputFile);
			out = new FileOutputStream(outFile);
			//��ȡ���һ�����ݿ飬��IEND���ݿ�
			DataBlock iendBlock = png.getDataBlocks().get(png.getDataBlocks().size() - 1);
			//�޸�IEND���ݿ����ݳ��ȣ�ԭ���ĳ���+Ҫ�����ļ��ĳ���
			long iendLength = ByteUtil.highByteToLong(iendBlock.getLength());
			iendLength += inputFile.length();
			iendBlock.setLength(ByteUtil.longToHighByte(iendLength, iendBlock.getLength().length));
			//�޸�IEND crc��Ϣ�����������ļ��Ĵ�С���ֽڣ�����������ȡpngʱ�ҵ��ļ����ݵ�λ�ã�����ȡ
			iendBlock.setCrc(ByteUtil.longToHighByte(inputFile.length(), iendBlock.getCrc().length));
			//д���ļ�ͷ����Ϣ
			out.write(png.getPngHeader().getFlag());
			//д�����ݿ���Ϣ
			String hexCode = null;
			for(int i = 0; i < png.getDataBlocks().size(); i++) {
				DataBlock dataBlock = png.getDataBlocks().get(i);
				hexCode = ByteUtil.byteToHex(dataBlock.getChunkTypeCode(), 
						0, dataBlock.getChunkTypeCode().length);
				hexCode = hexCode.toUpperCase();
				out.write(dataBlock.getLength());
				out.write(dataBlock.getChunkTypeCode());
				//д���ݿ�����
				if(BlockUtil.isIEND(hexCode)) {
					//дԭ��IEND���ݿ������
					if(dataBlock.getData() != null) {
						out.write(dataBlock.getData());
					}
					//�����IEND���ݿ飬��ô���ļ�����д��IEND���ݿ��������ȥ
					len = -1;
					while((len = inputIn.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
				} else {
					out.write(dataBlock.getData());
				}
				out.write(dataBlock.getCrc());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if(pngIn != null) {
					pngIn.close();
				}
				if(inputIn != null) {
					inputIn.close();
				}
				if(out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			}
		}
		
	}
	
	/**
	 * ��ָ�����ļ���Ϣд�뵽png�ļ��У��������ָ�����ļ���
	 * @param pngFileName			png�ļ���
	 * @param inputFileName			Ҫ���ص��ļ���
	 * @param outFileName			����ļ���
	 * @throws IOException 
	 */
	public static void writeFileToPng(String pngFileName, String inputFileName, String outFileName) throws IOException {
		Png png = readPng(pngFileName);
		wirteFileToPng(png, pngFileName, inputFileName, outFileName);
	}
	
	/**
	 * ��ȡpng�ļ��д洢����Ϣ����д�뵽ָ��ָ������ļ���
	 * @param pngFileName		png�ļ���
	 * @param outFileName		ָ������ļ���
	 * @throws IOException 
	 */
	public static void readFileFromPng(String pngFileName, String outFileName) throws IOException {
		File pngFile = new File(pngFileName);
		File outFile = new File(outFileName);
		InputStream pngIn = null;
		OutputStream out = null;
		//��¼��������ȡλ��
		long pos = 0;
		int len = -1;
		byte[] buf = new byte[1024];
		try {
			if(!outFile.exists()) {
				outFile.createNewFile();
			}
			pngIn = new BufferedInputStream(new FileInputStream(pngFile));
			out = new FileOutputStream(outFile);
			DataBlock dataBlock = new CommonBlock();
			//��ȡcrc�ĳ�����Ϣ����Ϊ����д�������Զ����ȡһ��
			int crcLength = dataBlock.getCrc().length;
			byte[] fileLengthByte = new byte[crcLength];
			pngIn.mark(0);
			//��λ��IEND���ݿ��crc��Ϣλ�ã���Ϊд���ʱ��������crcд����������ļ��Ĵ�С��Ϣ
			pngIn.skip(pngFile.length() - crcLength);
			//��ȡcrc��Ϣ
			pngIn.read(fileLengthByte);
			//��ȡ�������ļ��Ĵ�С���ֽڣ�
			int fileLength = ByteUtil.highByteToInt(fileLengthByte);
			//���¶�λ����ʼ���֡�
			pngIn.reset();
			//��λ�������ļ��ĵ�һ���ֽ�
			pngIn.skip(pngFile.length() - fileLength - crcLength);
			pos = pngFile.length() - fileLength - crcLength;
			//��ȡ�����ļ�����
			while((len = pngIn.read(buf)) > 0) {
				if( (pos + len) > (pngFile.length() - crcLength) ) {
					out.write(buf, 0, (int) (pngFile.length() - crcLength - pos));
					break;
				} else {
					out.write(buf, 0, len);
				}
				pos += len;
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if(pngIn != null) {
					pngIn.close();
				}
				if(out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		String filePath = PngUtil.class.getClassLoader().getResource("resource/sound_wav.png").getPath();
		Png png = readPng(filePath);
		wirteFileToPng(png, filePath, PngUtil.class.getClassLoader().getResource("resource/").getPath() + "screct.txt",
				PngUtil.class.getClassLoader().getResource("resource/").getPath() + "sound_wavout.png");
		readFileFromPng(PngUtil.class.getClassLoader().getResource("resource/").getPath() + "sound_wavout.png",
				PngUtil.class.getClassLoader().getResource("resource/").getPath() + "sound_wavscrect.txt");
		System.out.println(ByteUtil.byteToHexforPrint(png.getPngHeader().getFlag(), 
									0, png.getPngHeader().getFlag().length));
		for(DataBlock dataBlock : png.getDataBlocks()) {
			System.out.println(ByteUtil.byteToHexforPrint(dataBlock.getLength(), 
									0, dataBlock.getLength().length));
			System.out.println(ByteUtil.byteToHexforPrint(dataBlock.getChunkTypeCode(), 
					0, dataBlock.getChunkTypeCode().length));
			if(dataBlock.getData() != null) {
				System.out.println(ByteUtil.byteToHexforPrint(dataBlock.getData(), 
						0, dataBlock.getData().length));
			}
			System.out.println(ByteUtil.byteToHexforPrint(dataBlock.getCrc(), 
					0, dataBlock.getCrc().length));
		}
		System.out.println();
	}

}
