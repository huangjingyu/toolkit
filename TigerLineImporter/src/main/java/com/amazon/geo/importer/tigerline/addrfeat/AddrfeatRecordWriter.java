package com.amazon.geo.importer.tigerline.addrfeat;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.amazon.geo.importer.tigerline.TLShapefileRecord;
import com.amazon.geo.importer.tigerline.TLShapefileRecordWriter;

public class AddrfeatRecordWriter implements TLShapefileRecordWriter {

    private BufferedWriter bw;

    public AddrfeatRecordWriter() {
    }

    public AddrfeatRecordWriter(String outputFile) throws IOException {
        bw = new BufferedWriter(new FileWriter(outputFile));
    }

    @Override
    public void close() throws IOException {
        if (bw != null) {
            bw.close();
        }
    }

    @Override
    public void write(TLShapefileRecord record) throws IOException {
        bw.write(record.toString());
        bw.write("\n");
    }

    @Override
    public void write(List<TLShapefileRecord> records) throws IOException {
        // TODO Auto-generated method stub

    }

}
