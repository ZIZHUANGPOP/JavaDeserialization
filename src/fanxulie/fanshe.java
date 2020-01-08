package fanxulie;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.MessageFormat;

public class fanshe {

	public static void main(String[] args) throws Exception {

	      Object runtime=Class.forName("java.lang.Runtime")
	              .getMethod("getRuntime",new Class[]{})
	              .invoke(null);

	      Class.forName("java.lang.Runtime")
	              .getMethod("exec", String.class)
	              .invoke(runtime,"calc.exe");
	      

	 
	      }


}
