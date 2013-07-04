package com.amazon.geo.importer.tigerline.util;

import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import com.vividsolutions.jts.geom.Coordinate;

public class GeocodingUtil {
    private static CoordinateReferenceSystem crs;
    static {
        try {
            crs = CRS.parseWKT("GEOGCS[\"GCS_North_American_1983\",DATUM[\"D_North_American_1983\","
                    + "SPHEROID[\"GRS_1980\",6378137,298.257222101]],PRIMEM[\"Greenwich\",0],"
                    + "UNIT[\"Degree\",0.017453292519943295]]c82a144495f9:tl_2012_06001_addrfeat");
        } catch (FactoryException e) {
            e.printStackTrace();
        }
    }

    public static String getDistance(double x1, double y1, double x2, double y2) throws Exception {
        double distance = JTS.orthodromicDistance(new Coordinate(x1, y1), new Coordinate(x2, y2), crs);
        int totalmeters = (int) distance;
        int km = totalmeters / 1000;
        int meters = totalmeters - (km * 1000);
        float remaining_cm = (float) (distance - totalmeters) * 10000;
        remaining_cm = Math.round(remaining_cm);
        float cm = remaining_cm / 100;

        return km + "km " + meters + "m " + cm + "cm";
    }

    public static void main(String[] args) throws Exception {
        String s = GeocodingUtil.getDistance(-118.472043, 33.999137, -118.47118198871613, 33.999556079506874);
        System.out.println(s);
    }
}
