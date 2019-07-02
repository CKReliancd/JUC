package common;

/**
 * @author LiJun
 * @since 2018/9/29 10:47
 */
public class ImportException extends RuntimeException {

    public static String E01 = "文件格式错误，请重新检查TXT文件";
    public static String E02 = "字段缺少，请重新检查TXT文件";
    public static String E03 = "外部商户代码只能为18位数字或为0";
    public static String E04 = "终端号只能为8位数字或字母";
    public static String E05 = "机身号仅支持中文字母数字,最多30个字符";
    public static String E06 = "归属只能为1位或2位数字";

    public ImportException(String message) {
        super(message);
    }

    public ImportException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImportException(Throwable cause) {
        super(cause);
    }
}


