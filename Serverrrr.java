package server_com;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Serverrrr extends Thread{
	ServerSocket server = null;
	Socket socket = null;
	public Serverrrr (int port) {
		try {
			server = new ServerSocket(port);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		super.run();
		try {
			System.out.println("wait client connect.......");
			socket = server.accept();
			new sendMessThread().start();
			System.out.println(socket.getInetAddress().getHostAddress()+"Success to connect.");
			InputStream in = socket.getInputStream();
			int len =0;
			byte[] buf = new byte[1024];
			while((len = in.read(buf))!=-1) {
				System.out.println("client saying:"+new String(buf,0,len));
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	class sendMessThread extends Thread{
		@Override
		public void run() {
			super.run();
			Scanner scanner = null;
			OutputStream out = null;
			try {
				if(socket!=null) {
					scanner = new Scanner(System.in);
					out = socket.getOutputStream();
					String in = "";
					do {
						in = scanner.next();
						out.write(("Server saying:"+in).getBytes());
						out.flush();
					}while(!in.equals("q"));
					scanner.close();
					try {
						out.close();
					}catch (IOException e) {
						e.printStackTrace();
					}
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Serverrrr server = new Serverrrr(1234);
		server.start();
	}
}

