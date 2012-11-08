package jyhuang;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class Test {
	public static void main(String[] args) throws Exception {
		System.out.println(UUID.randomUUID().toString());
	}

	public static long used() {
		long total = Runtime.getRuntime().totalMemory();
		long free = Runtime.getRuntime().freeMemory();
		return total - free;
	}

	public static void socket() throws Exception {
		Socket socket = new Socket("127.0.0.1", 9999);
		long cur = System.currentTimeMillis();
		InputStream in = socket.getInputStream();
		File file = new File(FileUtils.getTempDirectory(), UUID.randomUUID()
				.toString());
		try {
			IOUtils.copy(in, new FileOutputStream(file));
		} finally {
			FileUtils.deleteQuietly(file);
			long used = System.currentTimeMillis() - cur;
			System.out.println("client" + used);
		}
	}
}
