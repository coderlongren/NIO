package com.coderlong.buffer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
* @author 作者 : coderlong
* @version 创建时间：2018年1月12日 下午4:33:40
* 类说明: 
*/
public class Buffer1 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		RandomAccessFile aFile = new RandomAccessFile("E:/Ehcache/data/nio-data.txt", "rw");
		FileChannel inChannel = aFile.getChannel();
		// 创建一个容量48字节的buffer
		ByteBuffer buf = ByteBuffer.allocate(48);
		
		int byteRead = inChannel.read(buf);// 把数据读入buffer
		while (byteRead != -1){
			buf.flip();// 将buffer从读模式转换到write模式
			while (buf.hasRemaining()) {
				System.out.print((char)buf.get()); // read from the buffer
			}
			buf.clear();// make buffer ready for writing
			byteRead = inChannel.read(buf);
		}
		aFile.close();
	}

}
