package com.coderlong.buffer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
* @author ���� : coderlong
* @version ����ʱ�䣺2018��1��12�� ����4:33:40
* ��˵��: 
*/
public class Buffer1 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		RandomAccessFile aFile = new RandomAccessFile("E:/Ehcache/data/nio-data.txt", "rw");
		FileChannel inChannel = aFile.getChannel();
		// ����һ������48�ֽڵ�buffer
		ByteBuffer buf = ByteBuffer.allocate(48);
		
		int byteRead = inChannel.read(buf);// �����ݶ���buffer
		while (byteRead != -1){
			buf.flip();// ��buffer�Ӷ�ģʽת����writeģʽ
			while (buf.hasRemaining()) {
				System.out.print((char)buf.get()); // read from the buffer
			}
			buf.clear();// make buffer ready for writing
			byteRead = inChannel.read(buf);
		}
		aFile.close();
	}

}
