import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
* @author 作者 : coderlong
* @version 创建时间：2018年1月12日 下午8:47:45
* 类说明:  从键盘输入必须使用 Scanner构造system.in?
*/
public class 包装流 {
	//  这段代码出自_____<Thinking in Java>
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		InputStreamReader inputStreamReader = new InputStreamReader(System.in);
		// 再将这个inputStreamReader 包装为 bufferedreader
		BufferedReader reader = new BufferedReader(inputStreamReader);
		String line = null;
		
		while ((line = reader.readLine()) != null){
			// 读取到了 "exit" 程序安全退出
			if (line.equals("exit")){
				System.exit(1);
			}
			// 打印读取的内容
			System.out.println(line);
		}
		
	}

}
