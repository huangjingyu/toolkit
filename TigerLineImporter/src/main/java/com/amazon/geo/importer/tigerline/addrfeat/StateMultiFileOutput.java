package com.amazon.geo.importer.tigerline.addrfeat;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.lib.MultipleTextOutputFormat;

public class StateMultiFileOutput extends MultipleTextOutputFormat<Text, Text> {

    @Override
    protected String generateFileNameForKeyValue(Text key, Text value, String name) {
        return key.toString();
    }

}
