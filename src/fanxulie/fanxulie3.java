package fanxulie;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.MessageFormat;
import java.io.Serializable;

class Person implements Serializable {

    /**
     * ���л�ID
     */
    private static final long serialVersionUID = -5809782578272943999L;
    
    
    private int age;
    private String name;
    private String sex;
    
    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
    
}

/**
 * <p>ClassName: SerializeAndDeserialize<p>
 * <p>Description: ���Զ�������л��ͷ�����<p>
 */
public class fanxulie3 {

    public static void main(String[] args) throws Exception {
	      Object runtime=Class.forName("java.lang.Runtime")
	              .getMethod("getRuntime",new Class[]{})
	              .invoke(null);

	      Class.forName("java.lang.Runtime")
	              .getMethod("exec", String.class)
	              .invoke(runtime,"calc.exe");
        SerializePerson();//���л�Person����
        Person p = DeserializePerson();//������Perons����
        System.out.println(MessageFormat.format("name={0},age={1},sex={2}",
                                                 p.getName(), p.getAge(), p.getSex()));
    }

    /**
     * MethodName: SerializePerson
     * Description: ���л�Person����
     */
    private static void SerializePerson() throws FileNotFoundException,
            IOException {
        Person person = new Person();
        person.setName("ssooking");
        person.setAge(20);
        person.setSex("��");
        // ObjectOutputStream �������������Person����洢��Person.txt�ļ��У���ɶ�Person��������л�����
        ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(
                new File("Person.txt")));
        oo.writeObject(person);
        System.out.println("Person�������л��ɹ���");
        oo.close();
    }

    /**
     * MethodName: DeserializePerson
     * Description: ������Perons����
     */
    private static Person DeserializePerson() throws Exception, IOException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("Person.txt")));
        /*
            FileInputStream fis = new FileInputStream("Person.txt"); 
            ObjectInputStream ois = new ObjectInputStream(fis);
        */
        Person person = (Person) ois.readObject();
        System.out.println("Person�������л��ɹ���");
        return person;
    }

}