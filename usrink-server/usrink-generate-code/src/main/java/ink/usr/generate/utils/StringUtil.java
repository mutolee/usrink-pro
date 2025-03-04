package ink.usr.generate.utils;

public class StringUtil {

    /**
     * 将name转换为骆驼命名法对象
     *
     * @param name         输入字符串（如 "user_name" 或 "user-name"）
     * @return 转换后的驼峰命名字符串，如 "UserName"
     */
    public static String nameCamel(String name, String split) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        if(!name.contains(split)){
            return capitalize(name);
        }

        // 按分隔符分割字符串
        String[] parts = name.split(split);
        StringBuilder camelCaseName = new StringBuilder();

        // 将每个部分的首字母大写并拼接
        for (String part : parts) {
            if (!part.isEmpty()) {
                camelCaseName.append(part.substring(0, 1).toUpperCase())
                        .append(part.substring(1).toLowerCase());
            }
        }

        return camelCaseName.toString();
    }

    /**
     * 将字符串的第一个字符转换为大写
     */
    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 将字符串的第一个字符转换为小写
     */
    public static String uncapitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }
}