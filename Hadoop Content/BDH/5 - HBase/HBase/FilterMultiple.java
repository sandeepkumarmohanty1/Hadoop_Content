package com.programs.HBase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilterMultiple {

    private static void printResults(ResultScanner scanResult) {
        System.out.println();
        System.out.println("Results: ");
        for (Result res : scanResult) {
            for (Cell cell : res.listCells()) {
                String row = new String(CellUtil.cloneRow(cell));
                String family = new String(CellUtil.cloneFamily(cell));
                String column = new String(CellUtil.cloneQualifier(cell));
                String value = new String(CellUtil.cloneValue(cell));

                System.out.println(row + " " + family + " " + column + " " + value);
            }
        }
    }


    public static void main(String[] args) throws IOException {
        Configuration conf = HBaseConfiguration.create();
        conf.setInt("timeout", 60000	);
        conf.set("hbase.master", "e2e-27-153.e2enetworks.net.in,e2e-27-144.e2enetworks.net.in,e2e-27-143.e2enetworks.net.in" + ":9000");
        conf.set("hbase.zookeeper.quorum","e2e-27-153.e2enetworks.net.in,e2e-27-144.e2enetworks.net.in,e2e-27-143.e2enetworks.net.in");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        Connection connection = ConnectionFactory.createConnection(conf);

        Table table = null;
        ResultScanner scanResult = null;
        try {
            table = connection.getTable(TableName.valueOf("test_pinu"));

            SingleColumnValueFilter maritalStatusFilter = new SingleColumnValueFilter(
                    Bytes.toBytes("Personal"),
                    Bytes.toBytes("marital_status"),
                    CompareFilter.CompareOp.EQUAL,
                    new BinaryComparator(Bytes.toBytes("married")));
            maritalStatusFilter.setFilterIfMissing(true);

            SingleColumnValueFilter genderFilter = new SingleColumnValueFilter(
                    Bytes.toBytes("Personal"),
                    Bytes.toBytes("gender"),
                    CompareFilter.CompareOp.EQUAL,
                    new BinaryComparator(Bytes.toBytes("male")));
            genderFilter.setFilterIfMissing(true);

            List<Filter> filterList = new ArrayList<Filter>();
            filterList.add(maritalStatusFilter);
            filterList.add(genderFilter);

            FilterList filters = new FilterList(filterList);

            Scan userScan = new Scan();
            userScan.setFilter(filters);

            scanResult = table.getScanner(userScan);

            printResults(scanResult);
        } finally {
            connection.close();
            if (table != null) {
                table.close();
            }
            if (scanResult != null) {
                scanResult.close();
            }
        }

    }
}