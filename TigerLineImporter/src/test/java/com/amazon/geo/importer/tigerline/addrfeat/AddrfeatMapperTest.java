package com.amazon.geo.importer.tigerline.addrfeat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;
import org.junit.Test;

import com.amazon.geo.importer.tigerline.BaseTest;

public class AddrfeatMapperTest extends BaseTest {

    @Test
    public void test() throws Exception {
        File zipFile = new File(dataDir, "tl_2012_01011_addrfeat.zip");
        byte[] bytes = new byte[(int) zipFile.length()];
        InputStream input = new FileInputStream(zipFile);
        IOUtils.readFully(input, bytes);
        IOUtils.closeQuietly(input);

        BytesWritable value = new BytesWritable();
        value.set(bytes, 0, bytes.length);

        AddrfeatMapper mapper = new AddrfeatMapper();
        mapper.map(NullWritable.get(), value, new MyOutputCollector(), null);
    }

    private static class MyOutputCollector implements OutputCollector<NullWritable, Text> {

        @Override
        public void collect(NullWritable key, Text value) throws IOException {
            System.out.println(value.toString());
        }

    }
}
