package com.programs.HBase;
import java.io.IOException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

public class CreateTable 
{
    public static void main( String[] args ) throws IOException
    {
        Configuration conf = HBaseConfiguration.create();
        conf.setInt("timeout", 120000	);
        conf.set("hbase.master", "e2e-27-153.e2enetworks.net.in,e2e-27-144.e2enetworks.net.in,e2e-27-143.e2enetworks.net.in" + ":9000");
        conf.set("hbase.zookeeper.quorum","e2e-27-153.e2enetworks.net.in,e2e-27-144.e2enetworks.net.in,e2e-27-143.e2enetworks.net.in");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        //conf.set("hbase.master", "192.168.152.141" + ":16000");
        //conf.set("hbase.zookeeper.quorum","192.168.152.141");
       // conf.set("hbase.zookeeper.property.clientPort", "2181");
        Connection connection = ConnectionFactory.createConnection(conf);
   
        try {
			Admin admin = connection.getAdmin();
			HTableDescriptor tableName = new HTableDescriptor(TableName.valueOf("test_pinu1"));
			
			tableName.addFamily(new HColumnDescriptor("Personal"));
			tableName.addFamily(new HColumnDescriptor("Professional"));
			admin.createTable(tableName);
			if(!admin.tableExists(tableName.getTableName()))
			{
				System.out.println("Crating the table test...");
				admin.createTable(tableName);
				System.out.println("test table created...");
			}
			else
			{
				System.out.println("Table already exist...");
			}
		} 
        catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
        finally {
			connection.close();
		}
    }
}
