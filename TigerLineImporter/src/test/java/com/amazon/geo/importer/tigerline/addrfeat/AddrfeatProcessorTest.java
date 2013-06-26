package com.amazon.geo.importer.tigerline.addrfeat;

import org.junit.Test;

import com.amazon.geo.importer.tigerline.TLShapefileRecordWriter;

public class AddrfeatProcessorTest {
    @Test
    public void test() throws Exception {
        AddrfeatProcessor p = new AddrfeatProcessor();
        TLShapefileRecordWriter writer = new AddrfeatRecordWriter("/Users/jyhuang/Documents/work/geocoding/tl_2012_06001_addrfeat.output");
        p.process("/Users/jyhuang/Documents/work/geocoding/ADDRFEAT/tl_2012_06001_addrfeat", writer);
    }
}
