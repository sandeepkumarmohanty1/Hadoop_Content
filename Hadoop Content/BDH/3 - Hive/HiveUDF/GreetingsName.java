package com.programs.HiveUDF;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;
public final class GreetingsName extends UDF {
    public Text evaluate(final Text s) {
        if (s == null) { return null; }
        return new Text("MR " + s.toString().toLowerCase());
    }
}