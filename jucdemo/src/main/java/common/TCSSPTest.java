package common;

import cn.tksspApi.TCSSP;
import cn.tksspApi.TResultSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TCSSPTest implements Runnable {

    @Override
    public void run() {
        final Logger logger = LoggerFactory.getLogger("com.ybs.web.commons");
        try {
            TCSSP tcssp = TCSSP.getInstance();
            TResultSet rs=tcssp.anthenReqForLocalKMS();
            String authReqData=rs.getDatetime()+rs.getAuthReqData();
            String data=  HttpClient.sendGet("http://127.0.0.1:8080/authenForTSDK","authReqData="+authReqData);
            String[] result=data.split("|");

            TResultSet rs1= tcssp.anthenAnsForLocalKMS(result[0],result[1]);
            String workkey = rs1.getWorkKeyByLMK();
            String mackey = rs1.getMacKeyByLMK();
            String decryptText ="11111111";
            TResultSet  resultSet =tcssp.encryptDataWithSK(0,workkey,mackey,decryptText);
            if (resultSet.getErrCode() != 0) {
                logger.error("decryptDataWithSK errcode = " + resultSet.getErrCode());

            }else {
                logger.info("加密成功 = " + decryptText);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void  main(String args[]){
        final Logger logger = LoggerFactory.getLogger("com.ybs.web.commons");
        final int trueNumber=0;
        final int flaseNumber=0;
        final int number=1;
        logger.info("任务开始："+ DateUtils.currentTime());
        for (int i=0;i<number;i++){
            new Thread( new TCSSPTest()).start();
        }
        logger.error("任务结束：{},解密成功次数：{}，解密失败次数：{}",DateUtils.currentTime(),trueNumber,flaseNumber);
    }
}
