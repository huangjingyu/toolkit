package com.amazon.geo.importer.tigerline.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;

public class ZipUtil {
    public static void unzip(InputStream in, File destDir) throws IOException {
        byte[] buffer = new byte[4096];
        ZipInputStream zipIn = null;
        try {
            zipIn = new ZipInputStream(in);
            ZipEntry entry;
            while ((entry = zipIn.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    File dir = new File(destDir, entry.getName());
                    dir.mkdir();
                } else {
                    FileOutputStream output = null;
                    try {
                        output = new FileOutputStream(new File(destDir, entry.getName()));
                        int len = 0;
                        while ((len = zipIn.read(buffer)) > 0) {
                            output.write(buffer, 0, len);
                        }
                    } finally {
                        IOUtils.closeQuietly(output);
                    }
                }
            }
        } finally {
            IOUtils.closeQuietly(zipIn);
        }
    }
}
