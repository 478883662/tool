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
     *Visual Studio中Add reference中
     * @param filePath    文件路径
     * @param printerName 打印机名称
     */
    public static void printWord(String filePath, String printerName) {
//        初始化线程
        ComThread.InitSTA();
        ActiveXComponent word = new ActiveXComponent("Word.Application");
        Dispatch doc = null;
        try {
            //设置打印机名称
            word.setProperty("ActivePrinter", new Variant(printerName));
            // 这里Visible是控制文档打开后是可见还是不可见，若是静默打印，那么第三个参数就设为false就好了
            Dispatch.put(word, "Visible", new Variant(false));
            // 获取文档属性
            Dispatch document = word.getProperty("Documents").toDispatch();
            // 打开激活文挡
            doc = Dispatch.call(document, "Open", filePath).toDispatch();
            Dispatch.callN(doc, "PrintOut");
            System.out.println("打印成功！");
        } finally {
            closeStream(word, doc);
        }
    }


    /**
     * 替换word文档中的图片
     * @param wordPath word文档位置
     * @param imgPath 图片路径
     * @param imgInWord 要替换的关键字
     * @param imgWidth 图片路径
     * @param imgHeight 要替换的关键字
     */
    public static void replaceImg(String wordPath, String imgPath,
                                  String imgInWord, int imgWidth, int imgHeight) {
//        初始化线程
        ComThread.InitSTA();
        ActiveXComponent word = new ActiveXComponent("Word.Application");
        //设置打印机名称
        // 这里Visible是控制文档打开后是可见还是不可见，若是静默打印，那么第三个参数就设为false就好了
        Dispatch.put(word, "Visible", new Variant(false));
        // 获取文档属性
        Dispatch document = word.getProperty("Documents").toDispatch();
        // 打开激活文挡
        Dispatch doc = Dispatch.call(document, "Open", wordPath).toDispatch();
        Dispatch selection = Dispatch.get(word, "Selection").toDispatch();
        try {
            Dispatch.call(selection, "HomeKey", new Variant(6));
            while (find(imgInWord, selection)) {
                Dispatch picture = Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(), "AddPicture", imgPath).toDispatch();
                Dispatch.call(picture, "Select");
                Dispatch.put(picture, "Width", new Variant(imgWidth));
                Dispatch.put(picture, "Height", new Variant(imgHeight));
                Dispatch.call(selection, "HomeKey", new Variant(6));
            }
        } finally {
            closeStream(word, doc);
        }
    }

    /**
     * 查询word文本内容
     * @param findText
     * @param selection
     * @return
     */
    private static boolean find(String findText, Dispatch selection) {
        if (findText == null || findText.equals("")) {
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


    /**
     * 关闭流
     * @param word
     * @param doc
     */
    private static void closeStream(ActiveXComponent word, Dispatch doc) {
        Dispatch.call(doc, "Save");
        Dispatch.call(doc, "Close", new Variant(true));
        doc = null;
        //退出
        word.invoke("Quit", new Variant[0]);
        word = null;
        //释放资源
        ComThread.Release();
        ComThread.quitMainSTA();
    }
}
