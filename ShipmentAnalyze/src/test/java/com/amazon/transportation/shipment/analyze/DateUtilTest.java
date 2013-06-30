package com.amazon.transportation.shipment.analyze;

import org.junit.Assert;
import org.junit.Test;

public class DateUtilTest {

    @Test
    public void testNormalizeToMonth() throws Exception {
        Assert.assertEquals("201109", DateUtil.normalizeToMonth("08-SEP-11"));
        Assert.assertEquals("201311", DateUtil.normalizeToMonth("25-NOV-13"));
        Assert.assertEquals("201201", DateUtil.normalizeToMonth("16-JAN-12"));
    }
}
