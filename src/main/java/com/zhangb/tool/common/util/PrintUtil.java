package com.zhangb.tool.common.util;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
/**
 * Created by z9104 on 2020/11/1.
 */
public class PrintUtil {

    /**
     * 打印word文档
     * @param filePath 文件路径
     * @param printerName 打印机名称
     */
    public static void printWord(String filePath, String printerName){
//        初始化线程
        ComThread.InitSTA();
        ActiveXComponent word = new ActiveXComponent("Word.Application");
        //设置打印机名称
        word.setProperty("ActivePrinter", new Variant(printerName));
        // 这里Visible是控制文档打开后是可见还是不可见，若是静默打印，那么第三个参数就设为false就好了
        Dispatch.put(word, "Visible", new Variant(false));
        // 获取文档属性
        Dispatch document = word.getProperty("Documents").toDispatch();
        // 打开激活文挡
        Dispatch doc=Dispatch.call(document, "Open", filePath).toDispatch();
        try{
            Dispatch.callN(doc, "PrintOut");
            System.out.println("打印成功！");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("打印失败");
        }finally {
            try {
                if (doc != null) {
                    Dispatch.call(doc, "Close", new Variant(0));//word文档关闭
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            //退出
            word.invoke("Quit", new Variant[0]);
            //释放资源
            ComThread.Release();
            ComThread.quitMainSTA();
        }
    }
}
