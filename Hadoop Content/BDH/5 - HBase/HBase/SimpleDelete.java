package com.programs.HBase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import java.io.IOException;

public class SimpleDelete {

    private static byte[] PERSONAL_CF = Bytes.toBytes("Personal");
    private static byte[] PROFESSIONAL_CF = Bytes.toBytes("Professional");

    private static byte[] GENDER_COLUMN = Bytes.toBytes("gender");
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

            Delete delete = new Delete(Bytes.toBytes("1"));

            delete.addColumn(PERSONAL_CF, GENDER_COLUMN);
            delete.addColumn(PROFESSIONAL_CF, FIELD_COLUMN);

            table.delete(delete);
            System.out.println("Table Deleted...");
        } finally {
            connection.close();

            if (table != null) {
                table.close();
            }
        }
    }
}