package common;

import cn.tksspApi.TCSSP;
import cn.tksspApi.TResultSet;
import cn.tksspApi.exception.TKSSPAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;


@Controller
public class TcsspUtil {
    private  final  int type=0;
    private  static  TCSSP tcssp=null;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public static TCSSP getInstance() throws TKSSPAPIException {
        if(tcssp==null){
            tcssp = TCSSP.getInstance();
        }
        return tcssp;
    }


    public String decrypt(String authReqData,String encryptData)throws Exception{
        String data="";
        TCSSP tcssp=getInstance();
        TResultSet rs=tcssp.authenForTSDK(authReqData);
        if (rs.getErrCode() != 0) {
            logger.error("authenForTSDK errcode = " + rs.getErrCode());
            return data;
        }
        String workkey = rs.getWorkKeyByLMK();
        String mackey = rs.getMacKeyByLMK();
        TResultSet resultSet = tcssp.decryptDataWithSK(type,workkey,mackey,encryptData,"");
        if (resultSet.getErrCode() != 0) {
            logger.error("decryptDataWithSK errcode = " + rs.getErrCode());
            return data;
        }
        data=resultSet.getPlaintext();
        return  data;
    }
}
