package com.programs.HBase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

public class DeleteTable {

    public static void main(String[] args) throws IOException {

        Configuration conf = HBaseConfiguration.create();
    	conf.setInt("timeout", 60000	);
    	conf.set("hbase.master", "e2e-27-153.e2enetworks.net.in,e2e-27-144.e2enetworks.net.in,e2e-27-143.e2enetworks.net.in" + ":9000");
    	conf.set("hbase.zookeeper.quorum","e2e-27-153.e2enetworks.net.in,e2e-27-144.e2enetworks.net.in,e2e-27-143.e2enetworks.net.in");
    	conf.set("hbase.zookeeper.property.clientPort", "2181");
        Connection connection = ConnectionFactory.createConnection(conf);

        try {
            Admin admin = connection.getAdmin();
            TableName tableName = TableName.valueOf("test_pinu");

            if (admin.tableExists(tableName)) {
                System.out.print("Table exists, Deleting.. ");

                admin.disableTable(tableName);
                admin.deleteTable(tableName);

                System.out.println(" Table Delted...");
            } else {
                System.out.println("Table does not exist.");
            }
        } finally {
            connection.close();
        }
    }

}