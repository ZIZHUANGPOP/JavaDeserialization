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
	 * ����һ���򵥵Ŀɱ����л����࣬����ʵ������Ķ�����ǿ��Ա����л��ġ� Ȼ����дreadObject������ʵ�ֵ���������
	 */
	private static final long serialVersionUID = 1L;

	private int n;

	public fanxulie4(int n) {
		// ���캯������ʼ��ʱִ��

		this.n = n;
	}

	public String aa() {
		return null;
	}

	// ��дreadObject�����������˵���������ִ�д��������
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();// ����ԭʼ��readOject����
        Runtime.getRuntime().exec("calc.exe");
		System.out.println("test");
	}

	public static void main(String[] args) {
//		fanxulie4 x = new fanxulie4(5);// ʵ��һ������
//		operation2.ser(x);// ���л�
		operation2.deser();// �����л�
	}
}

class operation2 {
	public static void ser(Object obj) {
		// ���л�������д����
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("object.obj"));
			// ObjectOutputStream�ܰ�Object�����Byte��
			oos.writeObject(obj);// ���л��ؼ�����
			oos.flush(); // ������
			oos.close(); // �ر���
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void deser() {
		// �����л���������ȡ����
		try {
			File file = new File("object.obj");
//			File file = new File("Person1.txt");
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			System.out.println("111");
			Object x = ois.readObject();// �����л��Ĺؼ�����
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