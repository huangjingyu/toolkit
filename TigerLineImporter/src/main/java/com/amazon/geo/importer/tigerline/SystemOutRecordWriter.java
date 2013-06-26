package com.amazon.geo.importer.tigerline;

import java.io.IOException;
import java.util.List;

public class SystemOutRecordWriter implements TLShapefileRecordWriter {

    @Override
    public void close() throws IOException {
    }

    @Override
    public void write(TLShapefileRecord record) {
        System.out.println(record.toString());
    }

    @Override
    public void write(List<TLShapefileRecord> records) {
        for (TLShapefileRecord r : records) {
            write(r);
        }
    }

}
