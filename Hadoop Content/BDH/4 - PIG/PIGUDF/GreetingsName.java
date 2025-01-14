package com.programs.PIGUDF;
import java.io.IOException;
import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;

public class GreetingsName extends EvalFunc<String>
{
    public String exec(Tuple input) throws IOException {
        if (input == null || input.size() == 0)
            return null;
        try{
            String str = (String)input.get(0);
            return "MR " + str.toUpperCase();
        }
        catch(Exception e){
            throw new IOException("Caught exception processing input row ", e);
        }
    }
}
