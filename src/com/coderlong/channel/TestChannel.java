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
* @author 作者 : coderlong
* @version 创建时间：2018年1月14日 下午7:47:49
* 类说明: 
*/
public class TestChannel {
	
	
	// 测试字符集
	@Test
	public void test5() {
		Map<String, Charset> charMap = Charset.availableCharsets();
		for (Entry<String, Charset> entry : charMap.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
	}
	
	
	
	// 分散读取，
	@Test
	public void test4() throws IOException {
		RandomAccessFile raFile = new RandomAccessFile("E:/Ehcache/1.txt","rw");
		// 获取通道
		FileChannel channel1 = raFile.getChannel();
		// 分配缓冲区
		ByteBuffer buf1 = ByteBuffer.allocate(1);
		ByteBuffer buf2 = ByteBuffer.allocate(1024);
		
		// 分散读取, 缓冲区数组
		ByteBuffer[] buffers = {buf1,buf2};
		channel1.read(buffers);
		for (ByteBuffer buffer : buffers) {
			buffer.flip();/// 为每个缓冲区转换读写模式
		}
		System.out.println(new String(buffers[0].array(),0,buffers[0].limit()));
		System.out.println(new String(buffers[1].array(), 0, buffers[1].limit()));
		System.out.println("complete");
		
		// 聚集写入
		RandomAccessFile raf2 = new RandomAccessFile("E:/Ehcache/2.txt", "rw");
		FileChannel channel2 = raf2.getChannel();
		
		channel2.write(buffers);
	}
	
	
	
	
	// 通道之间的数据通信
	@Test
	public void test3() throws IOException{
		FileChannel inChannel = FileChannel.open(Paths.get("E:/Ehcache/1.avi"), StandardOpenOption.READ);
		FileChannel outChannel = FileChannel.open(Paths.get("E:/Ehcache/2.avi"), StandardOpenOption.READ,StandardOpenOption.WRITE,StandardOpenOption.CREATE);
		
		inChannel.transferTo(0, inChannel.size(), outChannel);
		inChannel.close();
		outChannel.close();
	}
	
	// 利用直接缓冲区来完成文件的复制，（内存映射文件） 19M  消耗时间为:41
	@Test
	public void test2(){
		// 开始时间戳
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
		
		// 直接缓冲区的操作 
		try {
			// 输入内存映射文件
			MappedByteBuffer inMappedBuf = inChannel.map(MapMode.READ_ONLY, 0, inChannel.size());
			// 输出内存映射文件
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
		// 结束时间戳
		long end = System.currentTimeMillis();
		System.out.println("消耗时间为:" + (end - start));
	}
	
	
	
	
	/// 利用通道来完成文件的读写操作
	@Test
	public void test1() throws FileNotFoundException{
		FileInputStream fis = new FileInputStream("E:/Ehcache/1.jpg");
		FileOutputStream fos = new FileOutputStream("E:/Ehcache/2.jpg");
		
		// 获得通道
		FileChannel inChannel = fis.getChannel();
		FileChannel outChannel = fos.getChannel();
		
		// 分配缓冲区
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		
		// 读取数据 
		
		try {
			while (inChannel.read(buffer) != -1){
				buffer.flip(); // 转换读写模式
				outChannel.write(buffer); 
				buffer.clear();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 最后需要关闭这些 fis ois inChannel outChannel 
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
	//字符集
		@Test
		public void test6() throws IOException{
			Charset cs1 = Charset.forName("GBK");
			
			//获取编码器
			CharsetEncoder ce = cs1.newEncoder();
			
			//获取解码器
			CharsetDecoder cd = cs1.newDecoder();
			
			CharBuffer cBuf = CharBuffer.allocate(1024);
			cBuf.put("CharSetEncoder test......");
			cBuf.flip();
			
			//编码
			ByteBuffer bBuf = ce.encode(cBuf);
			
			for (int i = 0; i < 12; i++) {
				System.out.println(bBuf.get());
			}
			
			//解码
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
