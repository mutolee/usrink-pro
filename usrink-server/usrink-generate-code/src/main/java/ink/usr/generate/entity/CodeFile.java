package ink.usr.generate.entity;

import lombok.*;

/**
 * 代码文件信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CodeFile {

    // 代码内容
    private String content;
    // 文件所在目录
    private String fileDir;
    // 文件名称
    private String fileName;

    public static CodeFile buildCodeFile(String content, String fileDir, String fileName) {
        return CodeFile.builder().content(content).fileDir(fileDir).fileName(fileName).build();
    }

}
