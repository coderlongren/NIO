package com.coderlongren.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.Test;

/**
* @author ���� : coderlong
* @version ����ʱ�䣺2018��1��24�� ����8:44:47
* ��˵��: 
*/
public class blockingNIO {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	//�ͻ���
		@Test
		public void client() throws IOException{
			//1. ��ȡͨ��
			SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
			
			FileChannel inChannel = FileChannel.open(Paths.get("E:/Ehcache/data/1.jpg"), StandardOpenOption.READ);
			
			//2. ����ָ����С�Ļ�����
			ByteBuffer buf = ByteBuffer.allocate(1024);
			
			//3. ��ȡ�����ļ��������͵������
			while(inChannel.read(buf) != -1){
				buf.flip();
				sChannel.write(buf);
				buf.clear();
			}
			
			//4. �ر�ͨ��
			inChannel.close();
			sChannel.close();
		}
		
		//�����
		@Test
		public void server() throws IOException{
			//1. ��ȡͨ��
			ServerSocketChannel ssChannel = ServerSocketChannel.open();
			
			FileChannel outChannel = FileChannel.open(Paths.get("E:/Ehcache/data/2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
			
			//2. ������
			ssChannel.bind(new InetSocketAddress(9898));
			
			//3. ��ȡ�ͻ������ӵ�ͨ��
			SocketChannel sChannel = ssChannel.accept();
			
			//4. ����ָ����С�Ļ�����
			ByteBuffer buf = ByteBuffer.allocate(1024);
			
			//5. ���տͻ��˵����ݣ������浽����
			while(sChannel.read(buf) != -1){
				buf.flip();
				outChannel.write(buf);
				buf.clear();
			}
			
			//6. �ر�ͨ��
			sChannel.close();
			outChannel.close();
			ssChannel.close();
			
		}

}
