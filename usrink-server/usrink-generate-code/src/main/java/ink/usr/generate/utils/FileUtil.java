package ink.usr.generate.utils;

import ink.usr.generate.entity.CodeFile;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.Charset;

public class FileUtil {


    /**
     * 写文件
     *
     * @param codeFile
     * @throws Exception
     */
    public static void writeFile(CodeFile codeFile) throws Exception {
        File file = new File(codeFile.getFileDir(), codeFile.getFileName());
        FileUtils.writeStringToFile(file, codeFile.getContent(), Charset.defaultCharset());
    }

}
