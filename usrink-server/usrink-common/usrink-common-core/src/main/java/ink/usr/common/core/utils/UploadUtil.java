package ink.usr.common.core.utils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * 上传工具类
 */
public class UploadUtil {


    /**
     * 将Base64字符串解码并保存为图片文件
     *
     * @param base64Image Base64字符串
     * @param uploadDir 上传目录
     */
    public static String uploadBase64Image(String base64Image, String uploadDir, String fileName) throws IOException {
        // 创建上传目录
        File uploadDirectory = new File(uploadDir);
        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();
        }

        // 构造文件名
        if(StringUtil.isEmpty(fileName)){
            // 获取文件名后缀
            String extension = getExtensionFromBase64(base64Image);
            fileName = System.currentTimeMillis() + "." + extension;
        }

        // 解码Base64并保存图片文件
        String[] parts = base64Image.split(",");
        // 去掉Base64字符串中的前缀
        base64Image = parts[1];
        byte[] decodedBytes = Base64.getDecoder().decode(base64Image);
        File targetFile = new File(uploadDir + fileName);
        try (FileOutputStream fos = new FileOutputStream(targetFile)) {
            fos.write(decodedBytes);
        }

        // 返回保存的文件路径
        return uploadDir + fileName;
    }

    /**
     * 从Base64字符串中获取文件后缀
     *
     * @param base64Image Base64字符串
     * @return 文件后缀
     */
    private static String getExtensionFromBase64(String base64Image) {
        String[] parts = base64Image.split(",");
        String extension;
        switch (parts[0]) {
            case "data:image/jpeg;base64":
                extension = "jpg";
                break;
            case "data:image/png;base64":
                extension = "png";
                break;
            case "data:image/gif;base64":
                extension = "gif";
                break;
            default:
                extension = "jpg"; // 默认为jpg格式
                break;
        }
        return extension;
    }

}
