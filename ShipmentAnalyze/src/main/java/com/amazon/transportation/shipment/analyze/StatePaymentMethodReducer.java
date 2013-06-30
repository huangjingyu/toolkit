package com.amazon.transportation.shipment.analyze;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class StatePaymentMethodReducer extends MapReduceBase implements Reducer<Text, StatePaymentRecord, Text, StatePaymentRecord> {
    StatePaymentRecord rec = new StatePaymentRecord();

    @Override
    public void reduce(Text key, Iterator<StatePaymentRecord> values, OutputCollector<Text, StatePaymentRecord> output, Reporter reporter)
            throws IOException {
        int cashNum = 0;
        int mposNum = 0;
        int prepayNum = 0;
        long cashValue = 0L;
        long mposValue = 0L;
        long prepayValue = 0L;

        while (values.hasNext()) {
            StatePaymentRecord r = values.next();
            cashNum += r.getCashNum().get();
            mposNum += r.getMposNum().get();
            prepayNum += r.getPrepayNum().get();
            cashValue += r.getCashValue().get();
            mposValue += r.getMposValue().get();
            prepayValue += r.getPrepayValue().get();
        }

        rec.set(cashNum, mposNum, prepayNum, cashValue, mposValue, prepayValue);

        output.collect(key, rec);
    }

}
