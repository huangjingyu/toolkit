package jyhuang;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws Exception {
		nb();
	}

	public static void nb() throws Exception {
		ServerSocket ssocket = new ServerSocket(9999);
		while (true) {
			Socket socket = ssocket.accept();
			long cur = System.currentTimeMillis();
			Writer w = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			for (int i = 0; i < 40000000; i++) {
				w.write("asfddddddddddddddddddddddd\n");
			}
			long used = System.currentTimeMillis() - cur;
			System.out.println(used);
			w.close();
			socket.close();
			ssocket.close();
		}
	}
}
