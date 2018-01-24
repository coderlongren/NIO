package com.coderlongren.nio;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
/**
* @author 作者 : coderlong
* @version 创建时间：2018年1月24日 下午9:25:59
* 类说明: 
*/
public class PipeTest {
	
	@Test
	public void test() throws IOException{
		// 获取管道
		Pipe pipe = Pipe.open();
		
		// 把缓冲区的数据写入到 管道中
		ByteBuffer buffer =  ByteBuffer.allocate(1024);
		Pipe.SinkChannel sChannel = pipe.sink();
		buffer.put("我喜欢看，尼采哲学".getBytes());
		buffer.flip();
		sChannel.write(buffer);
		
		// 读取缓冲区的数据
		Pipe.SourceChannel sourceChannel = pipe.source();
		buffer.flip();
		sourceChannel.read(buffer);
		System.out.println(new String(buffer.array(),0,buffer.limit()));
		sourceChannel.close();
		sChannel.close();
	}
}
