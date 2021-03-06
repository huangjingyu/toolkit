package com.amazon.geo.importer.tigerline;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.lib.IdentityReducer;

import com.amazon.geo.importer.tigerline.addrfeat.AddrfeatStateMapper;
import com.amazon.geo.importer.tigerline.addrfeat.StateMultiFileOutput;
import com.amazon.geo.importer.tigerline.util.WholeFileInputFormat;

public class Main {
    public static void main(String[] args) throws Exception {
        JobConf conf = new JobConf(Main.class);
        conf.setJobName("tiger-line-import");

        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(Text.class);

        conf.setMapperClass(AddrfeatStateMapper.class);
        conf.setReducerClass(IdentityReducer.class);

        conf.setInputFormat(WholeFileInputFormat.class);
        conf.setOutputFormat(StateMultiFileOutput.class);

        FileInputFormat.setInputPaths(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1]));

        //conf.setNumReduceTasks(0);

        JobClient.runJob(conf);
    }
}
