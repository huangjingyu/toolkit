package com.amazon.geo.importer.tigerline.addrfeat;

import java.io.File;
import java.io.FilenameFilter;

import org.apache.commons.lang3.StringUtils;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.opengis.feature.simple.SimpleFeature;

import com.amazon.geo.importer.tigerline.AnsiMapping;
import com.amazon.geo.importer.tigerline.TLShapefileProcessor;
import com.amazon.geo.importer.tigerline.TLShapefileRecordWriter;
import com.amazon.geo.importer.tigerline.ZipCityMapping;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;

public class AddrfeatProcessor implements TLShapefileProcessor {

    @Override
    public void process(String inputPath, TLShapefileRecordWriter recordWriter) throws Exception {
        File dir = new File(inputPath);
        File shpFile = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".shp");
            }
        })[0];
        String[] arr = shpFile.getName().split("_");
        String state = "";
        String county = "";
        try {
            state = AnsiMapping.getInstance().getState(arr[2].substring(0, 2));
            county = AnsiMapping.getInstance().getCounty(arr[2]);
        } catch (Throwable t) {
        }
        FileDataStore store = FileDataStoreFinder.getDataStore(shpFile);
        SimpleFeatureSource featureSource = store.getFeatureSource();
        SimpleFeatureCollection features = featureSource.getFeatures();
        SimpleFeatureIterator iterator = features.features();
        while (iterator.hasNext()) {
            SimpleFeature feature = iterator.next();

            Geometry geometry = (Geometry) feature.getDefaultGeometry();
            Coordinate coord = geometry.getCoordinate();

            AddrfeatRecord rec = new AddrfeatRecord();
            rec.setState(state);
            rec.setCounty(county);
            rec.setLatitude(coord.x);
            rec.setLongitude(coord.y);
            rec.setFullname(getAttr(feature, "FULLNAME"));
            rec.setLeftFromHouseNumber(getAttr(feature, "LFROMHN"));
            rec.setLeftToHouseNumber(getAttr(feature, "LTOHN"));
            rec.setRightFromHouseNumber(getAttr(feature, "RFROMHN"));
            rec.setRightToHouseNumber(getAttr(feature, "RTOHN"));
            rec.setZipl(getAttr(feature, "ZIPL"));
            rec.setZipr(getAttr(feature, "ZIPR"));
            rec.setPlus4l(getAttr(feature, "PLUS4L"));
            rec.setPlus4r(getAttr(feature, "PLUS4R"));

            if (!StringUtils.isEmpty(rec.getZipl())) {
                rec.setCity(ZipCityMapping.getInstance().getCity(rec.getZipl()));
            } else if (!StringUtils.isEmpty(rec.getZipr())) {
                rec.setCity(ZipCityMapping.getInstance().getCity(rec.getZipr()));
            }

            recordWriter.write(rec);
        }
        store.dispose();
    }

    private String getAttr(SimpleFeature feature, String attrName) {
        return String.valueOf(feature.getAttribute(attrName));
    }

}
