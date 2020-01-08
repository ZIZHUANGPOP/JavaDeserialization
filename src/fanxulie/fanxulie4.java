package fanxulie;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class fanxulie4 implements Serializable {

	/**
	 * 创建一个简单的可被序列化的类，它的实例化后的对象就是可以被序列化的。 然后重写readObject方法，实现弹计算器。
	 */
	private static final long serialVersionUID = 1L;

	private int n;

	public fanxulie4(int n) {
		// 构造函数，初始化时执行

		this.n = n;
	}

	public String aa() {
		return null;
	}

	// 重写readObject方法，加入了弹计算器的执行代码的内容
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();// 调用原始的readOject方法
        Runtime.getRuntime().exec("calc.exe");
		System.out.println("test");
	}

	public static void main(String[] args) {
//		fanxulie4 x = new fanxulie4(5);// 实例一个对象
//		operation2.ser(x);// 序列化
		operation2.deser();// 反序列化
	}
}

class operation2 {
	public static void ser(Object obj) {
		// 序列化操作，写数据
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("object.obj"));
			// ObjectOutputStream能把Object输出成Byte流
			oos.writeObject(obj);// 序列化关键函数
			oos.flush(); // 缓冲流
			oos.close(); // 关闭流
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void deser() {
		// 反序列化操作，读取数据
		try {
			File file = new File("object.obj");
//			File file = new File("Person1.txt");
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			System.out.println("111");
			Object x = ois.readObject();// 反序列化的关键函数
			System.out.println("222");
			System.out.println(x.getClass());
//			Field field =  x.getClass().getDeclaredField("age");
//	        System.out.println(field);
//			Class<?> clz1 = Class.forName("fanxulie.A");
////			Class<?> clz1 = Class.forName(x.getClass().toString());
//          System.out.println(clz1.getClass());
//			Method[] GetMethods = clz1.getMethods();
//			for (Method clr : GetMethods) {
//				System.out.println(clr);
//			}
			ois.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}