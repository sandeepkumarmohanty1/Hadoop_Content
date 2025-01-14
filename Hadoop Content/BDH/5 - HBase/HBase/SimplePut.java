package com.programs.HBase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimplePut {

    private static byte[] PERSONAL_CF = Bytes.toBytes("Personal");
    private static byte[] PROFESSIONAL_CF = Bytes.toBytes("Professional");

    private static byte[] NAME_COLUMN = Bytes.toBytes("name");
    private static byte[] GENDER_COLUMN = Bytes.toBytes("gender");
    private static byte[] MARITAL_STATUS_COLUMN = Bytes.toBytes("marital_status");

    private static byte[] EMPLOYED_COLUMN = Bytes.toBytes("employed");
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

            Put put1 = new Put(Bytes.toBytes("1"));

            put1.addColumn(PERSONAL_CF, NAME_COLUMN, Bytes.toBytes("Mike Jones"));
            put1.addColumn(PERSONAL_CF, GENDER_COLUMN, Bytes.toBytes("male"));
            put1.addColumn(PERSONAL_CF, MARITAL_STATUS_COLUMN, Bytes.toBytes("unmarried"));
            put1.addColumn(PROFESSIONAL_CF, EMPLOYED_COLUMN, Bytes.toBytes("yes"));
            put1.addColumn(PROFESSIONAL_CF, FIELD_COLUMN, Bytes.toBytes("construction"));

            table.put(put1);

            System.out.println("Inserted row for Mike Jones");

            Put put2 = new Put(Bytes.toBytes("2"));

            put2.addColumn(PERSONAL_CF, NAME_COLUMN, Bytes.toBytes("Hillary Clinton"));
            put2.addColumn(PERSONAL_CF, GENDER_COLUMN, Bytes.toBytes("female"));
            put2.addColumn(PERSONAL_CF, MARITAL_STATUS_COLUMN, Bytes.toBytes("married"));
            put2.addColumn(PROFESSIONAL_CF, FIELD_COLUMN, Bytes.toBytes("politics"));

            Put put3 = new Put(Bytes.toBytes("3"));

            put3.addColumn(PERSONAL_CF, NAME_COLUMN, Bytes.toBytes("Donald Trump"));
            put3.addColumn(PERSONAL_CF, GENDER_COLUMN, Bytes.toBytes("male"));
            put3.addColumn(PROFESSIONAL_CF, FIELD_COLUMN, Bytes.toBytes("politics, real estate"));

            Put put4 = new Put(Bytes.toBytes("4"));

            put4.addColumn(PERSONAL_CF, NAME_COLUMN, Bytes.toBytes("Bill"));
            put4.addColumn(PERSONAL_CF, GENDER_COLUMN, Bytes.toBytes("male"));
            put4.addColumn(PERSONAL_CF, MARITAL_STATUS_COLUMN, Bytes.toBytes("married"));
            put4.addColumn(PROFESSIONAL_CF, FIELD_COLUMN, Bytes.toBytes("politics retired"));
            
            List<Put> putList = new ArrayList<Put>();
            putList.add(put2);
            putList.add(put3);
            putList.add(put4);
            table.put(putList);

            System.out.println("Inserted rows for Bill, Hillary Clinton and Donald Trump");
        } 
        finally {
            connection.close();
            if (table != null) {
                table.close();
            }
        }
    }
}