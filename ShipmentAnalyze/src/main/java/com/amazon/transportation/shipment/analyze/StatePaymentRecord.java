package com.amazon.transportation.shipment.analyze;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.WritableComparable;

public class StatePaymentRecord implements WritableComparable<StatePaymentRecord> {
    private IntWritable _cashNum;
    private IntWritable _mposNum;
    private IntWritable _prepayNum;
    private LongWritable _cashValue;
    private LongWritable _mposValue;
    private LongWritable _prepayValue;

    public StatePaymentRecord() {
        _cashNum = new IntWritable();
        _mposNum = new IntWritable();
        _prepayNum = new IntWritable();
        _cashValue = new LongWritable();
        _mposValue = new LongWritable();
        _prepayValue = new LongWritable();
    }

    public void set(int cashNum, int mposNum, int prepayNum, long cashValue, long mposValue, long prepayValue) {
        _cashNum.set(cashNum);
        _mposNum.set(mposNum);
        _prepayNum.set(prepayNum);
        _cashValue.set(cashValue);
        _mposValue.set(mposValue);
        _prepayValue.set(prepayValue);
    }

    public IntWritable getCashNum() {
        return _cashNum;
    }

    public IntWritable getMposNum() {
        return _mposNum;
    }

    public IntWritable getPrepayNum() {
        return _prepayNum;
    }

    public LongWritable getCashValue() {
        return _cashValue;
    }

    public LongWritable getMposValue() {
        return _mposValue;
    }

    public LongWritable getPrepayValue() {
        return _prepayValue;
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        _cashNum.readFields(in);
        _mposNum.readFields(in);
        _prepayNum.readFields(in);
        _cashValue.readFields(in);
        _mposValue.readFields(in);
        _prepayValue.readFields(in);
    }

    @Override
    public void write(DataOutput out) throws IOException {
        _cashNum.write(out);
        _mposNum.write(out);
        _prepayNum.write(out);
        _cashValue.write(out);
        _mposValue.write(out);
        _prepayValue.write(out);
    }

    @Override
    public int compareTo(StatePaymentRecord another) {
        int cmp = _cashNum.compareTo(another._cashNum);
        if (cmp != 0) {
            return cmp;
        }
        cmp = _mposNum.compareTo(another._mposNum);
        if (cmp != 0) {
            return cmp;
        }
        cmp = _prepayNum.compareTo(another._prepayNum);
        if (cmp != 0) {
            return cmp;
        }
        cmp = _cashValue.compareTo(another._cashValue);
        if (cmp != 0) {
            return cmp;
        }
        cmp = _mposValue.compareTo(another._mposValue);
        if (cmp != 0) {
            return cmp;
        }
        return _prepayValue.compareTo(another._prepayValue);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((_cashNum == null) ? 0 : _cashNum.hashCode());
        result = prime * result + ((_mposNum == null) ? 0 : _mposNum.hashCode());
        result = prime * result + ((_prepayNum == null) ? 0 : _prepayNum.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StatePaymentRecord other = (StatePaymentRecord) obj;
        if (_cashNum == null) {
            if (other._cashNum != null)
                return false;
        } else if (!_cashNum.equals(other._cashNum))
            return false;
        if (_mposNum == null) {
            if (other._mposNum != null)
                return false;
        } else if (!_mposNum.equals(other._mposNum))
            return false;
        if (_prepayNum == null) {
            if (other._prepayNum != null)
                return false;
        } else if (!_prepayNum.equals(other._prepayNum))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return _cashNum + "\t" + _mposNum + "\t" + _prepayNum + "\t" + _cashValue + "\t" + _mposValue + "\t" + _prepayValue;
    }

}
