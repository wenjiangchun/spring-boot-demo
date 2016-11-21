package com.haze.springboot;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

/**
 * Freemarker生成静态页面工具类
 * Created by Sofar on 2014/7/10.
 */
public class FreemarkerUtils {

    public static void generateTemplate(String templatePath, String templateName, String fileName, Map<?, ?> root) {
        try {
            Configuration config = new Configuration(Configuration.getVersion());
            // 设置要解析的模板所在的目录，并加载模板文件
            config.setDirectoryForTemplateLoading(new File(templatePath));
            // 设置包装器，并将对象包装为数据模型
            config.setObjectWrapper(new DefaultObjectWrapperBuilder(Configuration.getVersion()).build());
            // 获取模板,并设置编码方式，这个编码必须要与页面中的编码格式一致
            Template template = config.getTemplate(templateName, "UTF-8");
            // 合并数据模型与模板
            try( FileOutputStream fos = new FileOutputStream(fileName);
                 Writer out = new OutputStreamWriter(fos, "utf-8");) {
                template.process(root, out);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}