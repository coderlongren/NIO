package com.coderlongren.nio;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
/**
* @author ���� : coderlong
* @version ����ʱ�䣺2018��1��24�� ����9:25:59
* ��˵��: 
*/
public class PipeTest {
	
	@Test
	public void test() throws IOException{
		// ��ȡ�ܵ�
		Pipe pipe = Pipe.open();
		
		// �ѻ�����������д�뵽 �ܵ���
		ByteBuffer buffer =  ByteBuffer.allocate(1024);
		Pipe.SinkChannel sChannel = pipe.sink();
		buffer.put("��ϲ�����������ѧ".getBytes());
		buffer.flip();
		sChannel.write(buffer);
		
		// ��ȡ������������
		Pipe.SourceChannel sourceChannel = pipe.source();
		buffer.flip();
		sourceChannel.read(buffer);
		System.out.println(new String(buffer.array(),0,buffer.limit()));
		sourceChannel.close();
		sChannel.close();
	}
}
