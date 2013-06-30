package com.amazon.transportation.shipment.analyze;

import org.junit.Assert;
import org.junit.Test;

public class ShipmentCSVParserTest {

    @Test
    public void test() throws Exception {
        String line = "2628900,1848680,\"Dvr22yfRR\",\"C01-4223614-4676015\",1000200,,\"PREPAY\",\"PREPAY\",\"std-cn-d2d-met-mpos-avail\","
                + "13-SEP-11,,,,13-SEP-11,50855,0,1,1,\"\",\"Delivery\",\"PEK3\",,\"\",\"\",,\"北京市丰台区西罗园2区22号楼404\",\"\",\"\","
                + "\"丰台区\",\"北京市\",\"北京\",\"CN\",\"100077\",,,\"Y\",\"徐乃莹\",\"\",\"13426066776\",\"\"";
        ShipmentCSVParser parser = new ShipmentCSVParser();
        Shipment s = parser.parse(line);
        Assert.assertEquals("2628900", s.getShipmentId());
        Assert.assertEquals("PREPAY", s.getActualPaymentMethod());
        Assert.assertEquals("50855", s.getValueOfGoods());
        Assert.assertEquals("北京", s.getState());
    }
}
