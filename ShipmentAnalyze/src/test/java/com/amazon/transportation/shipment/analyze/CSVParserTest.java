package com.amazon.transportation.shipment.analyze;

import org.junit.Assert;
import org.junit.Test;

import au.com.bytecode.opencsv.CSVParser;

public class CSVParserTest extends BaseTest {

    @Test
    public void test() throws Exception {
        String s = "\"abc\",1,\"a \"\" b\",\"def\"";
        CSVParser parser = new CSVParser(',', '"');
        String[] arr = parser.parseLine(s);
        Assert.assertEquals(4, arr.length);
        Assert.assertEquals("abc", arr[0]);
        Assert.assertEquals("1", arr[1]);
        Assert.assertEquals("a \" b", arr[2]);
        Assert.assertEquals("def", arr[3]);
    }
}
