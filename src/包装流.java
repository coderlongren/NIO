import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
* @author ���� : coderlong
* @version ����ʱ�䣺2018��1��12�� ����8:47:45
* ��˵��:  �Ӽ����������ʹ�� Scanner����system.in?
*/
public class ��װ�� {
	//  ��δ������_____<Thinking in Java>
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		InputStreamReader inputStreamReader = new InputStreamReader(System.in);
		// �ٽ����inputStreamReader ��װΪ bufferedreader
		BufferedReader reader = new BufferedReader(inputStreamReader);
		String line = null;
		
		while ((line = reader.readLine()) != null){
			// ��ȡ���� "exit" ����ȫ�˳�
			if (line.equals("exit")){
				System.exit(1);
			}
			// ��ӡ��ȡ������
			System.out.println(line);
		}
		
	}

}
