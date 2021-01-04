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
            try {
                if (document != null) {
                    Dispatch.call(document, "Close", new Variant(0));//word文档关闭
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


    public static void replaceImg(String wordPath,String imgPath,
                                  String imgInWord,int imgWidth,int imgHeight,
                                  String newFilePath){
//        初始化线程
        ComThread.InitSTA();
        ActiveXComponent word = new ActiveXComponent("Word.Application");
        //设置打印机名称
        // 这里Visible是控制文档打开后是可见还是不可见，若是静默打印，那么第三个参数就设为false就好了
        Dispatch.put(word, "Visible", new Variant(false));
        // 获取文档属性
        Dispatch document = word.getProperty("Documents").toDispatch();
        // 打开激活文挡
        Dispatch doc=Dispatch.call(document, "Open", wordPath).toDispatch();
        Dispatch selection = Dispatch.get(word, "Selection").toDispatch();
        try{
            Dispatch.call(selection, "HomeKey", new Variant(6));
            while (find(imgInWord,selection)){
                Dispatch picture = Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(), "AddPicture", imgPath).toDispatch();
                Dispatch.call(picture, "Select");
                Dispatch.put(picture, "Width", new Variant(imgWidth));
                Dispatch.put(picture, "Height", new Variant(imgHeight));
                Dispatch.call(selection, "HomeKey", new Variant(6));
            }
            Dispatch.call(doc, "SaveAs", newFilePath);
        }finally {
            try {
                if (doc != null) {
                    Dispatch.call(doc, "Close", new Variant(0));//word文档关闭
                }
            } catch (Exception e2) {
            }
            try {
                if (selection != null) {
                    Dispatch.call(selection, "Close", new Variant(0));//word文档关闭
                }
            } catch (Exception e2) {
            }
            //退出
            word.invoke("Quit", new Variant[0]);
            //释放资源
            ComThread.Release();
            ComThread.quitMainSTA();
        }
    }

    private static boolean find(String findText,Dispatch selection){
        if(findText == null || findText.equals("")){
            return false;
        }
        // 从selection所在位置开始查询
        Dispatch find = Dispatch.call(selection, "Find").toDispatch();
        // 设置要查找的内容
        Dispatch.put(find, "Text", findText);
        // 向前查找
        Dispatch.put(find, "Forward", "True");
        // 设置格式
        Dispatch.put(find, "Format", "True");
        // 大小写匹配
        Dispatch.put(find, "MatchCase", "True");
        // 全字匹配
        Dispatch.put(find, "MatchWholeWord", "True");
        // 查找并选中
        return Dispatch.call(find, "Execute").getBoolean();
    }
}
