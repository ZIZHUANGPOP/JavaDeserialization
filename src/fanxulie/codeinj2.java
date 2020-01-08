package fanxulie;

import java.lang.reflect.Field;
import java.lang.reflect.Method;



public class codeinj2 {
	public static void main(String[] args) throws Exception {
        Class<?> clz = Class.forName(A.class.getName());
        Object o = clz.newInstance();
        Method m = clz.getMethod("foo", String.class);
        for (int i = 0; i < 16; i++) {
            if(i==15){
                m.invoke(o, Integer.toString(i));
            }else{
                m.invoke(o, Integer.toString(i));
            }
        }
        Object runtime=Class.forName("java.lang.Runtime")
	              .getMethod("getRuntime",new Class[]{})
	              .invoke(null);
//	      Class.forName("java.lang.Runtime")
//          .getMethod("exec", String.class)
//          .invoke(runtime,"calc.exe");	      
        Class<?> clz1 = Class.forName("java.lang.Runtime");
//        System.out.println(clz1.getClass());
        Method[] GetMethods = clz1.getMethods();
        for (Method clr:GetMethods) {
            System.out.println(clr);
        }
        Method method = clz1.getMethod("exec", String.class);
        method.invoke(runtime,"calc.exe");
    }
}