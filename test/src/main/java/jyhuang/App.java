package jyhuang;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.stringtemplate.v4.ST;

/**
 * Hello world!
 * 
 */
public class App {
	public static void main(String[] args) throws Exception {
		genSqls("temp.csv");
	}

	public static void genSqls(String filename) throws Exception {
		String template = IOUtils.toString(Thread.currentThread()
				.getContextClassLoader()
				.getResourceAsStream("batch_update_order_status"));
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
				"output")));
		try {
			List<String> list = FileUtils.readLines(new File(filename));
			for (String s : list) {
				if (!StringUtils.isEmpty(s)) {
					ST st = new ST(template);
					st.add("trackingId", s);
					pw.println(st.render());
					pw.println();
				}
			}
		} finally {
			pw.flush();
			pw.close();
		}
	}
}
