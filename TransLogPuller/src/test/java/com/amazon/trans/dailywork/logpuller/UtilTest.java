package com.amazon.trans.dailywork.logpuller;

import java.util.regex.Pattern;

import junit.framework.Assert;

import org.junit.Test;

public class UtilTest {
    @Test
    public void testGetLogTimes() throws Exception {
        String since = "2012-11-16-10";
        String until = "2012-11-17-23";
        String[] logTimes = Util.getLogTimes(since, until, "");
        Assert.assertEquals(38, logTimes.length);
        String[] expected = new String[38];
        int ind = 0;
        for (int i = 10; i <= 23; i++) {
            expected[ind++] = "2012-11-16-" + i;
        }
        for (int i = 0; i <= 23; i++) {
            expected[ind++] = "2012-11-17-" + (i < 10 ? ("0" + i) : i);
        }
        Assert.assertEquals(38, expected.length);
        for (int i = 0; i < 38; i++) {
            Assert.assertEquals(expected[i], logTimes[i]);
        }
    }

    @Test
    public void testPat() throws Exception {
        Pattern pat = Pattern.compile("[/\\\\]");
        String s = "abc/def\\hij";
        String[] arr = pat.split(s);
        Assert.assertEquals(3, arr.length);
        Assert.assertEquals("hij", arr[2]);
    }
}
