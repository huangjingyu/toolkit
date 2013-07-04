package com.amazon.geo.importer.tigerline.util;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class GetAnsiCountyMapping {
    private static final String[] PAGE_NAME_ARR = new String[] { "01000", "02000", "04000", "05000", "06000", "08000", "09000", "10000",
            "11000", "12000", "13000", "15000", "16000", "17000", "18000", "19000", "20000", "21000", "22000", "23000", "24000", "25000",
            "26000", "27000", "28000", "29000", "30000", "31000", "32000", "33000", "34000", "35000", "36000", "37000", "38000", "39000",
            "40000", "41000", "42000", "44000", "45000", "46000", "47000", "48000", "49000", "50000", "51000", "53000", "54000", "55000",
            "56000", "72000", "99000" };

    public void crawl(String outputFile) throws Exception {
        System.setProperty("socksProxyHost", "127.0.0.1");
        System.setProperty("socksProxyPort", "7070");
        URL url = new URL("http://www.census.gov/geo/www/ansi/data/01000.html");
        InputStream in = url.openStream();
        List<String> list = IOUtils.readLines(in);
        IOUtils.closeQuietly(in);
    }

    public static void main(String[] args) throws Exception {
        GetAnsiCountyMapping t = new GetAnsiCountyMapping();
        t.crawl("");
    }
}
