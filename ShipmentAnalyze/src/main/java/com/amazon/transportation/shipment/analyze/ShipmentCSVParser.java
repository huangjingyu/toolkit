package com.amazon.transportation.shipment.analyze;

import java.io.IOException;

import au.com.bytecode.opencsv.CSVParser;

public class ShipmentCSVParser {
    CSVParser parser = new CSVParser(',', '"');

    public Shipment parse(String line) throws IOException {
        String[] arr = parser.parseLine(line);
        Shipment s = new Shipment();
        int ind = 0;
        s.setShipmentId(arr[ind++]);
        s.setAddressId(arr[ind++]);
        s.setManifestShipmentId(arr[ind++]);
        s.setCustomerOrderId(arr[ind++]);
        s.setHolderStationId(arr[ind++]);
        s.setHolderEmployeeId(arr[ind++]);
        s.setExpectedPaymentMethod(arr[ind++]);
        s.setActualPaymentMethod(arr[ind++]);
        s.setShipOption(arr[ind++]);
        s.setPromisedDeliveryDate(arr[ind++]);
        s.setScheduledDeliveryStartDate(arr[ind++]);
        s.setScheduleDeliveryEndDate(arr[ind++]);
        s.setDeliveryDoneDate(arr[ind++]);
        s.setShipDate(arr[ind++]);
        s.setValueOfGoods(arr[ind++]);
        s.setBalanceDue(arr[ind++]);
        s.setShipmentStatusId(arr[ind++]);
        s.setShipmentReasonId(arr[ind++]);
        s.setRmaId(arr[ind++]);
        s.setShipmentType(arr[ind++]);
        s.setWarehouse(arr[ind++]);
        s.setRouteSeq(arr[ind++]);
        s.setMerchantId(arr[ind++]);
        s.setExchangeOrderId(arr[ind++]);
        s.setDeliveredByEmployeeId(arr[ind++]);
        s.setAddress1(arr[ind++]);
        s.setAddress2(arr[ind++]);
        s.setAddress3(arr[ind++]);
        s.setDistrict(arr[ind++]);
        s.setCity(arr[ind++]);
        s.setState(arr[ind++]);
        s.setCountryCode(arr[ind++]);
        s.setPostalCode(arr[ind++]);
        s.setLatitude(arr[ind++]);
        s.setLongitude(arr[ind++]);
        s.setIsCommercial(arr[ind++]);
        s.setContactName(arr[ind++]);
        s.setContactEmail(arr[ind++]);
        s.setContactCellPhone(arr[ind++]);
        s.setContactWorkPhone(arr[ind++]);

        return s;
    }
}
