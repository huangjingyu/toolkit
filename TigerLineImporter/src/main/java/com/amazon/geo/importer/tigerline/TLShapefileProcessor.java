package com.amazon.geo.importer.tigerline;

public interface TLShapefileProcessor {

    public void process(String inputPath, TLShapefileRecordWriter recordWriter) throws Exception;

}
