package com.amazon.jyhuang;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class CompGcrsSqlGenMain {
	private static final Charset UTF8 = Charset.forName("UTF-8");
	
	public void gen(String input, String output) throws Exception {
		int inLimit = 1000;
		List<String> unionList = new ArrayList<String>();
		List<String> addrIdList = new ArrayList<String>(inLimit);
		String sqlPrefix = "select geo.address_id as address_id,geo.latitude as latitude,geo.longitude as longtitude," +
				" mapp.routing_code as route_code from geo_addresses geo,route_mappings mapp" +
				" where geo.address_id=mapp.address_id and geo.address_id in (";
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(input), UTF8));
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), UTF8));
			
			br.readLine(); // skip header
			String line = null;			
			while((line = br.readLine()) != null) {
				int firstCommaInd = line.indexOf(",");
				int secondCommaInd = line.indexOf(",", firstCommaInd + 1);
				String addressId = line.substring(firstCommaInd + 1, secondCommaInd);
				addrIdList.add(addressId);
				if(addrIdList.size() >= inLimit) {
					String sql = sqlPrefix + join(addrIdList, ",") + ")";
					unionList.add(sql);
					addrIdList.clear();
				}
			}
			if(addrIdList.size() > 0) {
				String sql = sqlPrefix + join(addrIdList, ",") + ")";
				unionList.add(sql);
				addrIdList.clear();
			}
			if(unionList.size() > 0) {
				bw.write(unionList.get(0));
				for(int i=1;i<unionList.size();i++) {
					bw.write("\n union all \n");
					bw.write(unionList.get(i));
				}
			}
			bw.write(";");
			bw.flush();
		} finally {
			close(br);
			close(bw);
		}
	}
	
	private void close(Closeable c) {
		try {
			c.close();
		} catch(Throwable t) {}
	}
	
	private String join(List<String> list, String delim) {
		StringBuilder sb = new StringBuilder();
		sb.append(list.get(0));
		for(int i=1;i<list.size();i++) {
			sb.append(delim);
			sb.append(list.get(i));
		}
		return sb.toString();
	}
	
	public static void main(String[] args) throws Exception {
		String input = args[0];
		String output = args[1];
		CompGcrsSqlGenMain t = new CompGcrsSqlGenMain();
		t.gen(input, output);
	}
		
}
