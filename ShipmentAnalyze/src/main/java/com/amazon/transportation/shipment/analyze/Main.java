package com.amazon.transportation.shipment.analyze;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

public class Main {
    public static void main(String[] args) throws Exception {
        JobConf conf = new JobConf(Main.class);
        conf.setJobName("analyze-shipments");

        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(StatePaymentRecord.class);

        conf.setMapperClass(StatePaymentMethodMapper.class);
        conf.setReducerClass(StatePaymentMethodReducer.class);

        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

        FileInputFormat.setInputPaths(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1]));

        JobClient.runJob(conf);
    }
}
