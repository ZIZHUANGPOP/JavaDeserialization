package fanxulie;

import java.io.IOException;

public class A {
	public void foo(String name) throws IOException {
		Runtime.getRuntime().exec("calc.exe");
		System.out.println("Hello, " + name);
	}
}
