package com.amazon.geo.importer.tigerline.addrfeat;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

import com.amazon.geo.importer.tigerline.TLShapefileRecord;
import com.amazon.geo.importer.tigerline.TLShapefileRecordWriter;
import com.amazon.geo.importer.tigerline.util.ZipUtil;

public class AddrfeatStateMapper extends MapReduceBase implements Mapper<NullWritable, BytesWritable, Text, Text> {

    private Text key = new Text();
    private Text value = new Text();

    @Override
    public void map(NullWritable key, BytesWritable value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
        File tmpDir = new File(System.getProperty("java.io.tmpdir"));
        File shpDir = new File(tmpDir, UUID.randomUUID().toString());
        shpDir.mkdir();
        try {
            ZipUtil.unzip(new ByteArrayInputStream(value.getBytes()), shpDir);
            new AddrfeatProcessor().process(shpDir.getAbsolutePath(), new RecordWriter(output));
        } catch (Exception e) {
            throw new IOException(e);
        } finally {
            FileUtils.deleteQuietly(shpDir);
        }
    }

    private class RecordWriter implements TLShapefileRecordWriter {
        private static final String DELIMITER = ",";
        private OutputCollector<Text, Text> output;

        public RecordWriter(OutputCollector<Text, Text> output) {
            this.output = output;
        }

        @Override
        public void close() throws IOException {
        }

        @Override
        public void write(TLShapefileRecord record) throws IOException {
            AddrfeatRecord rec = (AddrfeatRecord) record;
            if (!StringUtils.isEmpty(rec.getLeftFromHouseNumber())) {
                StringBuilder sb = new StringBuilder();
                build(sb, rec.getLeftFromHouseNumber(), rec.getLeftToHouseNumber(), rec);
                key.set(rec.getState());
                value.set(sb.toString());
                output.collect(key, value);
            }
            if (!StringUtils.isEmpty(rec.getRightFromHouseNumber())) {
                StringBuilder sb = new StringBuilder();
                build(sb, rec.getRightFromHouseNumber(), rec.getRightToHouseNumber(), rec);
                key.set(rec.getState());
                value.set(sb.toString());
                output.collect(key, value);
            }
        }

        @Override
        public void write(List<TLShapefileRecord> records) throws IOException {
        }

        private void build(StringBuilder sb, String fromHouse, String toHouse, AddrfeatRecord rec) {
            sb.append(fromHouse).append("-").append(toHouse).append(DELIMITER);
            sb.append(rec.getFullname()).append(DELIMITER);
            sb.append(rec.getCity()).append(DELIMITER);
            sb.append(rec.getCounty()).append(DELIMITER);
            sb.append(rec.getState()).append(DELIMITER);
            sb.append(rec.getLatitude()).append(DELIMITER);
            sb.append(rec.getLongitude()).append(DELIMITER);
            sb.append(rec.getZipl()).append(DELIMITER);
            sb.append(rec.getZipr()).append(DELIMITER);
            sb.append(rec.getPlus4l()).append(DELIMITER);
            sb.append(rec.getPlus4r());
        }

    }

}
