package com.amazon.geo.importer.tigerline.addrfeat;

import org.junit.Test;

import com.amazon.geo.importer.tigerline.BaseTest;
import com.amazon.geo.importer.tigerline.SystemOutRecordWriter;
import com.amazon.geo.importer.tigerline.TLShapefileRecordWriter;

public class AddrfeatProcessorTest extends BaseTest {
    @Test
    public void test() throws Exception {
        AddrfeatProcessor p = new AddrfeatProcessor();
        TLShapefileRecordWriter writer = new SystemOutRecordWriter();
        p.process(dataDir + "tl_2012_01011_addrfeat", writer);
    }
}
