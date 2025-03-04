package ink.usr.generate.utils;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;

public class FreemarkerUtil {

    private static Configuration cfg = null;


    /**
     * 根据 freemarker 文件获取内容
     *
     * @param ftlFileName
     * @param dataModel
     * @return
     */
    public static String getFtlToString(String ftlFileName, Object dataModel) throws IOException, TemplateException {
        StringWriter sw = new StringWriter();
        getCfg().getTemplate(ftlFileName + ".ftl").process(dataModel, sw);
        return sw.toString();
    }

    public static Configuration getCfg() {
        if (cfg == null) {
            synchronized (FreemarkerUtil.class) {
                if (cfg == null) {
                    cfg = new Configuration();
                    cfg.setDefaultEncoding("UTF-8");
                    cfg.setClassicCompatible(true);
                    cfg.setTemplateUpdateDelay(10);
                    // 设置模板文件所在的目录
                    cfg.setClassForTemplateLoading(FreemarkerUtil.class,
                            "/ftl/");
                }
            }
        }
        return cfg;
    }

}
