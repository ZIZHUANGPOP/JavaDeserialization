package fanxulie;

import java.io.IOException;

public class commandinj1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Runtime.getRuntime().exec("calc.exe");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
