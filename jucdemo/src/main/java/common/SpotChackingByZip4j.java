package common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class SpotChackingByZip4j {
	 /**
     * 根据给定密码压缩文件(s)到指定目录
     * 
     * @param destFileName 压缩文件存放绝对路径 e.g.:D:/upload/zip/demo.zip
     * @param passwd 密码(可为空)
     * @param files 单个文件或文件数组(需压缩文件路径)
     * @return 最终的压缩文件存放的绝对路径,如果为null则说明压缩失败.
     */
    public static String compress(String destFileName, String passwd, File... files) {
        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // 压缩方式
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); // 压缩级别
        if (!"".equals(passwd)&& passwd!=null) {
            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD); // 加密方式
            parameters.setPassword(passwd.toCharArray());
        }
        try {
            ZipFile zipFile = new ZipFile(destFileName);
            for (File file : files) {
                zipFile.addFile(file, parameters);
            }
            return destFileName;
        } catch (ZipException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 根据给定密码压缩文件(s)到指定位置
     * 
     * @param destFileName 压缩文件存放绝对路径 e.g.:D:/upload/zip/demo.zip
     * @param passwd 密码(可为空)
     * @param filePaths 单个文件路径或文件路径数组（需压缩文件路径）
     * @return 最终的压缩文件存放的绝对路径,如果为null则说明压缩失败.
     */
    public static String compress(String destFileName, String passwd, String... filePaths) {
    	File zipfile = new File(destFileName);
    	//判断压缩文件如果存在就删除
    	if(zipfile.exists()){
    		zipfile.delete();
    	}
        int size = filePaths.length;
        File[] files = new File[size];
        for (int i = 0; i < size; i++) {
            files[i] = new File(filePaths[i]);
        }
        return compress(destFileName, passwd, files);
    }
    public static String newdateZippassword(){
    	Date d = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	return sdf.format(d)+"000000";
    }
}
