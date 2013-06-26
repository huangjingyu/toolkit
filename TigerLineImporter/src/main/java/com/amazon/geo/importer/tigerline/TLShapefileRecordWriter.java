package com.amazon.geo.importer.tigerline;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

public interface TLShapefileRecordWriter extends Closeable {
    void write(TLShapefileRecord record) throws IOException;

    void write(List<TLShapefileRecord> records) throws IOException;
}
