package com.zhangb.tool.common.util;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.db.Entity;
import com.zhangb.tool.common.annotation.DbFeildAnnotation;
import com.zhangb.tool.common.annotation.ExportFeildAnnotation;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by z9104 on 2020/10/31.
 */
public class ExportWordUtil {

    /**
     * 导出word文档到filePath
     *
     * @param t
     * @param filePath
     * @param <T>
     */
    public static <T> void exportWord(T t, String filePath) {
        OutputStream outputStream = null;
        Writer writer = null;
        Writer out = null;
        try {
            Map<String, Object> dataMap = new HashMap<>();
            Field[] fields = t.getClass().getDeclaredFields();
            for (Field field : fields) {
                String exportFieldName = AnnotationUtil.getAnnotationValue(field, ExportFeildAnnotation.class);
                dataMap.put(exportFieldName, ReflectUtil.getFieldValue(t, field));
            }
            Configuration configuration = new Configuration();
            configuration.setDefaultEncoding("utf-8");
            //获取模版文件夹的绝对路径

            File targetFile = FileUtil.getWebRoot();
            String parent = targetFile.getParentFile().getAbsolutePath() + "/";
            System.out.println(parent);
            //读取jar中文件的方式
            configuration.setDirectoryForTemplateLoading(FileUtil.newFile(parent));
            //输出文档路径为项目的路径下
            File outFile = FileUtil.newFile(filePath);
            if (!FileUtil.exist(outFile)) {
                FileUtil.mkParentDirs(outFile);
            }
            //以utf-8的编码读取模版文件 ftl文件
            Template template = configuration.getTemplate("print_template.ftl", "utf-8");
             outputStream = new FileOutputStream(outFile);
             writer = new OutputStreamWriter(outputStream, "utf-8");
             out = new BufferedWriter(writer, 10240);
            template.process(dataMap, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StreamCloseUtil.closeWriter(out);
            StreamCloseUtil.closeWriter(writer);
            StreamCloseUtil.closeOutputStream(outputStream);
        }

    }
}
