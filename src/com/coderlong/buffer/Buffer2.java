package com.coderlong.buffer;

import java.nio.ByteBuffer;

import org.junit.Test;

/**
* @author ���� : coderlong
* @version ����ʱ�䣺2018��1��12�� ����7:34:01
* ��˵��: 
*/
public class Buffer2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	@Test
	public void test1(){
		String str = "abcde";
		
		//1. ����һ��ָ����С�Ļ�����
		ByteBuffer buf = ByteBuffer.allocate(48);
		
		System.out.println("-----------------allocate()----------------");
		System.out.println(buf.position());
		System.out.println(buf.limit());
		System.out.println(buf.capacity());
		
//		//2. ���� put() �������ݵ���������
		buf.put(str.getBytes());
		
		System.out.println("-----------------put()----------------");
		System.out.println(buf.position());
		System.out.println(buf.limit());
		System.out.println(buf.capacity());
		
//		//3. �л���ȡ����ģʽ
		buf.flip();
		
		System.out.println("-----------------flip()----------------");
		System.out.println(buf.position());
		System.out.println(buf.limit());
		System.out.println(buf.capacity());
//		
//		//4. ���� get() ��ȡ�������е�����
		byte[] dst = new byte[buf.limit()];
		buf.get(dst);
		System.out.println(new String(dst, 0, dst.length));
		
		System.out.println("-----------------get()----------------");
		System.out.println(buf.position());
		System.out.println(buf.limit());
		System.out.println(buf.capacity());
//		
		//5. rewind() : ���ظ���
		buf.rewind();
		
		System.out.println("-----------------rewind()----------------");
		System.out.println(buf.position());
		System.out.println(buf.limit());
		System.out.println(buf.capacity());
//		
		//6. clear() : ��ջ�����. ���ǻ������е�������Ȼ���ڣ����Ǵ��ڡ���������״̬
		buf.clear();
		
		System.out.println("-----------------clear()----------------");
		System.out.println(buf.position());
		System.out.println(buf.limit());
		System.out.println(buf.capacity());
		
		byte[] byt = new byte[3];
//		System.out.println((char)buf.get(byt));
		buf.get(byt);
		System.out.println(byt);
		System.out.println(new String(byt,0,byt.length));
	}
	@Test
	public void test2(){
		String str = "abcde";
		
		ByteBuffer buf = ByteBuffer.allocate(1024);
		
		buf.put(str.getBytes());
		
		buf.flip();
		
		byte[] dst = new byte[buf.limit()];
		// offset ��ƫ������length �Ƕ�ȡ���ֽ���
		buf.get(dst, 1, 2);
		System.out.println(new String(dst, 0, 2));
		System.out.println(buf.position());
		
		//mark() : ���
		buf.mark();
		
		buf.get(dst, 2, 2);
		System.out.println(new String(dst, 2, 2));
		System.out.println(buf.position());
		
		//reset() : �ָ��� mark ��λ��
		buf.reset();
		System.out.println(buf.position());
		
		//�жϻ��������Ƿ���ʣ������ hasRemaining()
		if(buf.hasRemaining()){
			
			//��ȡ�������п��Բ���������
			System.out.println(buf.remaining());
		}
	}
	// �������������� ��̬��������
	@Test
	public void test3(){
		ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
		ByteBuffer buffer2 = ByteBuffer.allocate(1024);
		System.out.println(buffer.isDirect()); // ֱ�ӻ����� 
		System.out.println(buffer2.isDirect());// ��ֱ�ӻ�����
	}
	

}
