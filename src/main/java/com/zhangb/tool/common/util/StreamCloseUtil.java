package com.zhangb.tool.common.util;

import java.io.OutputStream;
import java.io.Writer;

public class StreamCloseUtil {

    public static void closeOutputStream(OutputStream outputStream) {
        try{
            if (outputStream!=null){
                outputStream.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void closeWriter(Writer writer) {
        try{
            if (writer!=null){
                writer.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
