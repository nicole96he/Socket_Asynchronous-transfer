package clienthnx_com;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class clientttt extends Thread{
	Socket socket = null;
	
	public clientttt(String host, int port) {
		try {
			socket = new Socket(host,port);
		}catch(UnknownHostException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		new sendMessThread().start();
		super.run();
		try {
			InputStream s = socket.getInputStream();
			byte[] buf = new byte[1024];
			int len = 0;
			while((len = s.read(buf)) !=-1) {
				System.out.println(new String(buf,0,len));
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	class sendMessThread extends Thread{
		public void run() {
			super.run();
			Scanner scanner = null;
			OutputStream os = null;
			try {
				scanner = new Scanner(System.in);
				os = socket.getOutputStream();
				String in="";
				do {
					in = scanner.next();
					os.write((""+in).getBytes());
					os.flush();
				} while(!in.equals("bye"));
			}catch(IOException e) {
				e.printStackTrace();
			}
			scanner.close();
			try {
				os.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		clientttt clientTest = new clientttt("127.0.0.1",1234);
		clientTest.start();
		// TODO Auto-generated method stub

	}

}

