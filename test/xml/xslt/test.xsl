<?xml version="1.0" encoding="UTF-8"?>

<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">

    <fo:layout-master-set>
        <fo:simple-page-master master-name="A4" page-height="29.7cm" page-width="21cm" margin-top="1cm"
            margin-bottom="1cm" margin-left="1cm" margin-right="1cm">
            <fo:region-body />
            <fo:region-before />
            <fo:region-after extent="20mm"/>
        </fo:simple-page-master>
    </fo:layout-master-set>

    <fo:page-sequence master-reference="A4">
        <fo:static-content flow-name="xsl-region-after">
            <fo:block text-align="end" font-family="SimSun">
                <fo:page-number />
                /
                <fo:page-number-citation ref-id="last-page" />
            </fo:block>
        </fo:static-content>
        <fo:flow flow-name="xsl-region-body">
            <fo:block>
                <fo:table width="100%" table-layout="fixed">
                    <fo:table-column column-width="6cm" />
                    <fo:table-column column-width="6cm" />
                    <fo:table-column column-width="proportional-column-width(1)" />
                    <fo:table-body>
                        <fo:table-row>
                            <fo:table-cell>
                                <fo:block font-family="SimSun">上海市</fo:block>
                            </fo:table-cell>
                            <fo:table-cell>
                                <fo:block font-family="SimSun">包裹跟踪号:61111610032163</fo:block>
                            </fo:table-cell>
                            <fo:table-cell number-rows-spanned="3">
                                <fo:block font-family="SimSun">退货授权号</fo:block>
                                <fo:block>
                                    <fo:instream-foreign-object>
                                        <barcode:barcode xmlns:barcode="http://barcode4j.krysalis.org/ns"
                                            message="123456789">
                                            <barcode:code128>
                                                <barcode:height>8mm</barcode:height>
                                            </barcode:code128>
                                        </barcode:barcode>
                                    </fo:instream-foreign-object>
                                </fo:block>
                            </fo:table-cell>
                        </fo:table-row>
                        <fo:table-row>
                            <fo:table-cell>
                                <fo:block font-family="SimSun">原始订单号:C01-1038308-8808017</fo:block>
                            </fo:table-cell>
                            <fo:table-cell>
                                <fo:block font-family="SimSun">退货授权号:DzSvvNPMRRMA</fo:block>
                            </fo:table-cell>
                        </fo:table-row>
                        <fo:table-row>
                            <fo:table-cell>
                                <fo:block font-family="SimSun">发货日期:2012/11/15 01:32:13</fo:block>
                            </fo:table-cell>
                            <fo:table-cell>
                                <fo:block font-family="SimSun">退货库房:SHA2</fo:block>
                            </fo:table-cell>
                        </fo:table-row>
                    </fo:table-body>
                </fo:table>
            </fo:block>
            <fo:block id="last-page" />
        </fo:flow>
    </fo:page-sequence>

</fo:root> 