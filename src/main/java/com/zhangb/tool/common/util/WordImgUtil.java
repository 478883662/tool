package com.zhangb.tool.common.util;

import java.io.File;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
/**
 * Created by z9104 on 2020/11/22.
 */
public class WordImgUtil {

    // word运行程序对象
    private ActiveXComponent word;
    // 所有word文档集合
    private Dispatch documents;
    // word文档
    private Dispatch doc;
    // 选定的范围或插入点
    private Dispatch selection;
    // 保存退出
    private boolean saveOnExit = true;

    /**
     * 往word文档里插入图片
     * @param wordPath word文档文件路径
     * @param imgPath 图片路径
     * @param imgInWord 图片在word文档中要替换的位置代码
     * @param imgWidth 图片展示的宽度
     * @param imgHeight 图片展示的高度
     */
    public static void insertImgToWord(String wordPath,String imgPath,
                                     String imgInWord,int imgWidth,int imgHeight,
                                         String newFilePath) {
        System.out.println("要插入到word中图片的位置:"+imgPath);
        WordImgUtil demo =null;
        try{
            demo = new WordImgUtil(false);//获取工具类对象
            demo.openDocument(wordPath);//打开word
            // 在指定位置插入指定的图片
            demo.replaceAllImage(imgInWord,imgPath,imgWidth,imgHeight);//imgInWord是指定的图片位置。后面的两个参数是指定图片的长和宽。
            demo.saveAs(newFilePath);//插入成功后生成的新word
            System.out.println("插入成功图片到word文档成功:"+wordPath);
        }finally {
            demo.closeDocument();//关闭对象。
            //释放资源
            ComThread.Release();
            ComThread.quitMainSTA();
        }
    }



    /**
     * 是否可见word程序
     * @param visible true-可见word程序，false-后台默默执行。
     */
    private WordImgUtil(boolean visible) {
        word = new ActiveXComponent("Word.Application");
        word.setProperty("Visible", new Variant(visible));
        documents = word.getProperty("Documents").toDispatch();
    }
    /**
     * 打开一个已经存在的Word文档
     * @param docPath 文件的路径
     */
    private void openDocument(String docPath) {
        doc = Dispatch.call(documents, "Open", docPath).toDispatch();
        selection = Dispatch.get(word, "Selection").toDispatch();
    }

    /**
     * 全局将指定的文本替换成图片
     * @param findText
     * @param imagePath
     */
    private void replaceAllImage(String findText, String imagePath, int width, int height){
        moveStart();
        while (find(findText)){
            Dispatch picture = Dispatch.call(Dispatch.get(getSelection(), "InLineShapes").toDispatch(), "AddPicture", imagePath).toDispatch();
            Dispatch.call(picture, "Select");
            Dispatch.put(picture, "Width", new Variant(width));
            Dispatch.put(picture, "Height", new Variant(height));
            moveStart();
        }
    }

    /**
     * 把插入点移动到文件首位置
     */
    private void moveStart(){
        Dispatch.call(getSelection(), "HomeKey", new Variant(6));
    }

    /**
     * 获取当前的选定的内容或者插入点
     * @return 当前的选定的内容或者插入点
     */
    private Dispatch getSelection(){
        if (selection == null)
            selection = Dispatch.get(word, "Selection").toDispatch();
        return selection;
    }

    /**
     * 从选定内容或插入点开始查找文本
     * @param findText 要查找的文本
     * @return boolean true-查找到并选中该文本，false-未查找到文本
     */
    private boolean find(String findText){
        if(findText == null || findText.equals("")){
            return false;
        }
        // 从selection所在位置开始查询
        Dispatch find = Dispatch.call(getSelection(), "Find").toDispatch();
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
     * 文档另存为
     * @param savePath
     */
    private void saveAs(String savePath){
        Dispatch.call(doc, "SaveAs", savePath);
    }

    /**
     * 关闭word文档
     */
    private void closeDocument(){
        if (doc != null) {
            Dispatch.call(doc, "Close", new Variant(saveOnExit));
            doc = null;
        }
    }
}
