package com.coderlong.channel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;

import org.ietf.jgss.Oid;
import org.junit.Test;

/**
* @author ���� : coderlong
* @version ����ʱ�䣺2018��1��14�� ����7:47:49
* ��˵��: 
*/
public class TestChannel {
	
	
	// �����ַ���
	@Test
	public void test5() {
		Map<String, Charset> charMap = Charset.availableCharsets();
		for (Entry<String, Charset> entry : charMap.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
	}
	
	
	
	// ��ɢ��ȡ��
	@Test
	public void test4() throws IOException {
		RandomAccessFile raFile = new RandomAccessFile("E:/Ehcache/1.txt","rw");
		// ��ȡͨ��
		FileChannel channel1 = raFile.getChannel();
		// ���仺����
		ByteBuffer buf1 = ByteBuffer.allocate(1);
		ByteBuffer buf2 = ByteBuffer.allocate(1024);
		
		// ��ɢ��ȡ, ����������
		ByteBuffer[] buffers = {buf1,buf2};
		channel1.read(buffers);
		for (ByteBuffer buffer : buffers) {
			buffer.flip();/// Ϊÿ��������ת����дģʽ
		}
		System.out.println(new String(buffers[0].array(),0,buffers[0].limit()));
		System.out.println(new String(buffers[1].array(), 0, buffers[1].limit()));
		System.out.println("complete");
		
		// �ۼ�д��
		RandomAccessFile raf2 = new RandomAccessFile("E:/Ehcache/2.txt", "rw");
		FileChannel channel2 = raf2.getChannel();
		
		channel2.write(buffers);
	}
	
	
	
	
	// ͨ��֮�������ͨ��
	@Test
	public void test3() throws IOException{
		FileChannel inChannel = FileChannel.open(Paths.get("E:/Ehcache/1.avi"), StandardOpenOption.READ);
		FileChannel outChannel = FileChannel.open(Paths.get("E:/Ehcache/2.avi"), StandardOpenOption.READ,StandardOpenOption.WRITE,StandardOpenOption.CREATE);
		
		inChannel.transferTo(0, inChannel.size(), outChannel);
		inChannel.close();
		outChannel.close();
	}
	
	// ����ֱ�ӻ�����������ļ��ĸ��ƣ����ڴ�ӳ���ļ��� 19M  ����ʱ��Ϊ:41
	@Test
	public void test2(){
		// ��ʼʱ���
		long start = System.currentTimeMillis();
		
		FileChannel inChannel = null;
		FileChannel outChannel = null;
		try {
			inChannel = FileChannel.open(Paths.get("E:/Ehcache/1.avi"), StandardOpenOption.READ);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			outChannel = FileChannel.open(Paths.get("E:/Ehcache/2.avi"), StandardOpenOption.READ,StandardOpenOption.WRITE,StandardOpenOption.CREATE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// ֱ�ӻ������Ĳ��� 
		try {
			// �����ڴ�ӳ���ļ�
			MappedByteBuffer inMappedBuf = inChannel.map(MapMode.READ_ONLY, 0, inChannel.size());
			// ����ڴ�ӳ���ļ�
			MappedByteBuffer outMapedBuf = outChannel.map(MapMode.READ_WRITE, 0, inChannel.size());
			byte[] dst = new byte[inMappedBuf.limit()];
			inMappedBuf.get(dst);
			outMapedBuf.put(dst);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				inChannel.close();
				outChannel.close();
			} catch (Exception e2) {
				// TODO: handle exception
				System.out.println(e2.getMessage());
			}
		}
		// ����ʱ���
		long end = System.currentTimeMillis();
		System.out.println("����ʱ��Ϊ:" + (end - start));
	}
	
	
	
	
	/// ����ͨ��������ļ��Ķ�д����
	@Test
	public void test1() throws FileNotFoundException{
		FileInputStream fis = new FileInputStream("E:/Ehcache/1.jpg");
		FileOutputStream fos = new FileOutputStream("E:/Ehcache/2.jpg");
		
		// ���ͨ��
		FileChannel inChannel = fis.getChannel();
		FileChannel outChannel = fos.getChannel();
		
		// ���仺����
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		
		// ��ȡ���� 
		
		try {
			while (inChannel.read(buffer) != -1){
				buffer.flip(); // ת����дģʽ
				outChannel.write(buffer); 
				buffer.clear();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// �����Ҫ�ر���Щ fis ois inChannel outChannel 
		try {
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//�ַ���
		@Test
		public void test6() throws IOException{
			Charset cs1 = Charset.forName("GBK");
			
			//��ȡ������
			CharsetEncoder ce = cs1.newEncoder();
			
			//��ȡ������
			CharsetDecoder cd = cs1.newDecoder();
			
			CharBuffer cBuf = CharBuffer.allocate(1024);
			cBuf.put("CharSetEncoder test......");
			cBuf.flip();
			
			//����
			ByteBuffer bBuf = ce.encode(cBuf);
			
			for (int i = 0; i < 12; i++) {
				System.out.println(bBuf.get());
			}
			
			//����
			bBuf.flip();
			CharBuffer cBuf2 = cd.decode(bBuf);
			System.out.println(cBuf2.toString());
			
			System.out.println("------------------------------------------------------");
			
			Charset cs2 = Charset.forName("GBK");
			bBuf.flip();
			CharBuffer cBuf3 = cs2.decode(bBuf);
			System.out.println(cBuf3.toString());
		}
		

}
