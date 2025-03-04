package com.programs.HBase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleGet {

    private static byte[] PERSONAL_CF = Bytes.toBytes("Personal");
    private static byte[] PROFESSIONAL_CF = Bytes.toBytes("Professional");

    private static byte[] NAME_COLUMN = Bytes.toBytes("name");
    private static byte[] FIELD_COLUMN = Bytes.toBytes("field");

    public static void main(String[] args) throws IOException {
    	Configuration conf = HBaseConfiguration.create();
    	conf.setInt("timeout", 60000	);
    	conf.set("hbase.master", "e2e-27-153.e2enetworks.net.in,e2e-27-144.e2enetworks.net.in,e2e-27-143.e2enetworks.net.in" + ":9000");
    	conf.set("hbase.zookeeper.quorum","e2e-27-153.e2enetworks.net.in,e2e-27-144.e2enetworks.net.in,e2e-27-143.e2enetworks.net.in");
    	conf.set("hbase.zookeeper.property.clientPort", "2181");
        Connection connection = ConnectionFactory.createConnection(conf);

        Table table = null;
        try {
            table = connection.getTable(TableName.valueOf("test_pinu"));

            Get get = new Get(Bytes.toBytes("1"));

            get.addColumn(PERSONAL_CF, NAME_COLUMN);
            get.addColumn(PROFESSIONAL_CF, FIELD_COLUMN);

            Result result = table.get(get);

            byte[] nameValue = result.getValue(PERSONAL_CF, NAME_COLUMN);
            System.out.println("Name: " + Bytes.toString(nameValue));

            byte[] fieldValue = result.getValue(PROFESSIONAL_CF, FIELD_COLUMN);
            System.out.println("Field: " + Bytes.toString(fieldValue));

            System.out.println();
            System.out.println("SimpleGet multiple results in one go:");

            List<Get> getList = new ArrayList<Get>();
            Get get1 = new Get(Bytes.toBytes("2"));
            get1.addColumn(PERSONAL_CF, NAME_COLUMN);

            Get get2 = new Get(Bytes.toBytes("3"));
            get1.addColumn(PERSONAL_CF, NAME_COLUMN);

            getList.add(get1);
            getList.add(get2);

            Result[] results = table.get(getList);

            for (Result res : results) {
                nameValue = res.getValue(PERSONAL_CF, NAME_COLUMN);
                System.out.println("Name: " + Bytes.toString(nameValue));
            }
        } finally {
            connection.close();
            if (table != null) {
                table.close();
            }
        }
    }

}