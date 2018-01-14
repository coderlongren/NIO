package com.coderlongren.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.swing.ButtonGroup;

/**
* @author 作者 : coderlong
* @version 创建时间：2018年1月9日 下午1:14:59
* 类说明: 
*/
public class TestChannel {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		RandomAccessFile aFile = new RandomAccessFile("E:/Ehcache/data/nio-data.txt", "rw");
		FileChannel inChannel = aFile.getChannel();
		ByteBuffer buf = ByteBuffer.allocate(48); // 分配了48字节
		int byteRead = inChannel.read(buf);
		while (byteRead != -1){
			System.out.println();
			buf.flip();
			
			while (buf.hasRemaining()){
				System.out.print((char)buf.get());
			}
			buf.clear();
			byteRead = inChannel.read(buf);
		}
		aFile.close();
	}

}
