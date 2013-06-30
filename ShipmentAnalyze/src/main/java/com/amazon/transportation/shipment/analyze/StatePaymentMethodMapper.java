package com.amazon.transportation.shipment.analyze;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class StatePaymentMethodMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, StatePaymentRecord> {
    private ShipmentCSVParser parser = new ShipmentCSVParser();
    private StatePaymentRecord rec = new StatePaymentRecord();
    private Text text = new Text();

    @Override
    public void map(LongWritable key, Text value, OutputCollector<Text, StatePaymentRecord> output, Reporter reporter) throws IOException {
        String line = value.toString();
        if (line.startsWith("\"S")) {
            return;
        }
        Shipment shipment = null;
        try {
            shipment = parser.parse(line);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        if (shipment == null) {
            return;
        }
        if (StringUtils.isEmpty(shipment.getShipDate())) {
            return;
        }
        if (StringUtils.isEmpty(shipment.getActualPaymentMethod())) {
            if (StringUtils.isEmpty(shipment.getExpectedPaymentMethod())) {
                return;
            } else {
                shipment.setActualPaymentMethod(shipment.getExpectedPaymentMethod());
            }
        }
        if (StringUtils.isEmpty(shipment.getState())) {
            return;
        }
        if (StringUtils.isEmpty(shipment.getValueOfGoods())) {
            return;
        }

        text.set(DateUtil.normalizeToMonth(shipment.getShipDate()) + shipment.getState());
        if ("PREPAY".equals(shipment.getActualPaymentMethod())) {
            rec.set(0, 0, 1, 0L, 0L, toLong(shipment.getValueOfGoods()));
        } else if ("CASH".equals(shipment.getActualPaymentMethod())) {
            rec.set(1, 0, 0, toLong(shipment.getValueOfGoods()), 0L, 0L);
        } else if ("MPOS".equals(shipment.getActualPaymentMethod())) {
            rec.set(0, 1, 0, 0L, toLong(shipment.getValueOfGoods()), 0L);
        }

        output.collect(text, rec);
    }

    private long toLong(String s) {
        try {
            return Long.parseLong(s);
        } catch (Throwable t) {
            return 0L;
        }
    }

}
