package common;

import com.ybs.util.Aes128;
import com.ybs.util.ECB3Des;
import com.ybs.util.SHA_256;

public class Test_ybs_ed {
    public static void main(String[] args) throws Exception {
    	 String msg = "a3几分!@#$%^&*()！@#￥%…1…&*（）——+=-{}【】、】】地哦啊是进佛顶山";//62152411365791131zZCVD
         System.out.println("【3des加密前】：" + msg);
         // 加密
         String encryptMsg = ECB3Des.encryptECB3Des("gfhdnsny4t3753wgjee4");
         System.out.println("【3des加密后】：" + encryptMsg);
         // 解密
         String decryptMsg = ECB3Des.decryptECB3Des(encryptMsg);
         System.out.println("【3des解密后】：" + decryptMsg);

         System.out.println("============================================");

         // 需要加密的字串
         String cSrc = "621524113657911384aes原始字符串";
         System.out.println("aes原始字符串：" + cSrc);
         // // 加密
         String enString = Aes128.encrypt(cSrc);
         System.out.println("aes加密后的字串是：" + enString);
         // 解密
         String DeString = Aes128.decrypt(enString);
         System.out.println("aes解密后的字串是：" + DeString);
         
         System.out.println("============================================");
         String sha256Str = "123456123456";
         System.out.println("SHA_256加密前原始字符串：" + sha256Str);
         System.out.println("SHA_256加密后：" + SHA_256.getSHA256Str(sha256Str));
    }
}
