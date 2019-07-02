package common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ybs.web.dataserver.entity.merchant.*;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ybs.web.constant.BankOpenCode;
import com.ybs.web.constant.Const;
import com.ybs.web.constant.StringConst;
import com.ybs.web.dataserver.condition.Condition;
import com.ybs.web.dataserver.entity.SubjectUtils;
import com.ybs.web.dataserver.entity.bank.BankInfo;
import com.ybs.web.dataserver.entity.bank.Test;
import com.ybs.web.dataserver.entity.cds.MerchantWhite;
import com.ybs.web.dataserver.entity.ips.TaxiDriver;
import com.ybs.web.dataserver.entity.nsec.TerminalSecret;
import com.ybs.web.dataserver.entity.nsec.TmkControl;
import com.ybs.web.dataserver.entity.nsec.TmkMerControl;
import com.ybs.web.dataserver.entity.nserver.TblSZTTerminal;
import com.ybs.web.dataserver.entity.riskbigdata.MerchantTrcPend;
import com.ybs.web.dataserver.entity.user.User;
import com.ybs.web.dataserver.server.bank.BankInfoService;
import com.ybs.web.dataserver.server.ips.TaxiDriverService;
import com.ybs.web.dataserver.web.Result;
import com.ybs.web.dataserver.web.nsec.TmkControlController;
import com.ybs.web.dataserver.web.nsec.TmkMerControlController;
import com.ybs.web.dataserver.web.riskbigdata.MerchantTrcController;

/**
 * txt文档相关的处理方法
 *
 * @author zlm
 */

@Service
public class ManagerTxt {

    @Resource
    private BankInfoService bankInfoService;

    @Resource
    private TaxiDriverService driverService;

    private static Map<String, String> regExp = new HashMap<String, String>();
    //商户大类交易参数的数据集合
    public static List<MerchantTrcPend> mtLists = new ArrayList<MerchantTrcPend>();
    //通用批改的数据集合
    public static List<Map<String, String>> comBatchList = new ArrayList<Map<String, String>>();

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public ManagerTxt() {
        regExp.put("mcc", "^\\w{4}$");
        //regExp.put("description", "^[\\u4e00-\u9fa5_a-zA-Z0-9_]{0,64}$");
        regExp.put("amountPerTime", "^\\d{0,18}$");
        regExp.put("amountPerDay", "^\\d{0,18}$");
        regExp.put("amountPerWeek", "^\\d{0,18}$");
        regExp.put("amountPerMonth", "^\\d{0,18}$");
        regExp.put("countPerDay", "^\\d{0,11}$");
        regExp.put("countPerWeek", "^\\d{0,11}$");
        regExp.put("countPerMonth", "^\\d{0,11}$");
        regExp.put("handleType", "^\\w{0,1}$");
        regExp.put("greatType", "^\\w{0,2}$");
    }

    /**
     * 解析获取的txt文档
     * 并按行读取其中的内容  返回List<TerminalSecret>集合
     *
     * @param filePath
     * @throws IOException
     */
    public List<TerminalSecret> readTxtFile(InputStream is, String secretType) {
        List<TerminalSecret> list = new ArrayList<TerminalSecret>();
        InputStreamReader read = new InputStreamReader(is);//考虑到编码格式
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = null;
        try {
            while ((lineTxt = bufferedReader.readLine()) != null) {

                TerminalSecret secret = new TerminalSecret();

                lineTxt = lineTxt.trim();

                if (lineTxt != null && !"".equals(lineTxt)) {

                    String[] arr = lineTxt.split(",");

                    secret.setTerminal(arr[1].replaceAll(" +", "").replaceAll("\"", ""));

                    //塞入org机构号
                    secret.setOrg(arr[2].replaceAll(" +", "").replaceAll("\"", ""));

                    secret.setSecretKey(arr[4].replaceAll(" +", "").replaceAll("\"", ""));

                    arr[0] = arr[0].replaceAll(" +", "").replaceAll("\"", "");

                    if (arr[0].length() == 15) {
                        arr[0] = "000" + arr[0];
                    }
                    secret.setMerchantId(arr[0]);

                    secret.setSecretType(secretType);

                    list.add(secret);
                }
            }

            read.close();
            return list;

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("文件解析异常");
            return list;
        }
    }

    /*解析txt文件  返回 List<String>集合
     * 对ext文件的格式要求只有各个数据之间用 “,”分隔 对每一行的数据个数无要求
     *
     */
    public static List<String> getListString(InputStream is) {
        List<String> list = new ArrayList<String>();
        InputStreamReader read = new InputStreamReader(is);//考虑到编码格式
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = null;
        try {
            while ((lineTxt = bufferedReader.readLine()) != null) {
                lineTxt = lineTxt.trim();
                String[] arr = lineTxt.split(",");
                for (String str : arr) {
                    if (str != null && !"".equals(str)) {
                        list.add(str.trim());
                    }
                }
            }
            read.close();
            return list;

        } catch (IOException e) {
            e.printStackTrace();
            return list;
        }
    }

    public static Map<String, Object> getTermSeekFeeControllerListString(InputStream is) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> list = new ArrayList<String>();
        InputStreamReader read = new InputStreamReader(is);//考虑到编码格式
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = null;
        boolean flag = true;
        try {
            String filePath = TmkMerControlController.class.getResource("/").getFile().toString() + FileUtils.tmkMerFileName + ".txt";
            File file = new File(filePath);
            FileWriter write = new FileWriter(filePath, true);
            int lineNum = 1;
            String errMsg = "";
            while ((lineTxt = bufferedReader.readLine()) != null) {
                errMsg = "";
                lineTxt = lineTxt.trim();
                String[] arr = lineTxt.split("\\|");
                if (arr.length == 3) {
                    if (!checkLid(arr[0])) {
                        errMsg += "[记录序号]=" + arr[0] + "参数格式错误#";
                    }
                    if (!checkTid(arr[1])) {
                        errMsg += "[终端号]=" + arr[1] + "参数格式错误#";
                    }
                    if (!checkMid(arr[2])) {
                        errMsg += "[外部代码]=" + arr[2] + "参数格式错误#";
                    }
                } else {
                    errMsg += "数据量不正确，数据量应为三个#";
                }
                if (errMsg.equals("")) {
                    for (String str : arr) {
                        if (str != null && !"".equals(str)) {
                            list.add(str.trim());
                        }
                    }
                } else {
                    flag = false;
                    errMsg = "第" + lineNum + "行 " + errMsg.substring(0, errMsg.length() - 1) + "\r\n";
                    write.write(errMsg);
                    write.close();
                    map.put("listMap", list);
                    map.put("regRes", flag);
                    if (errMsg != null || !"".equals(errMsg)) {
                        map.put("errMsg", errMsg);
                    }
                    if (flag) {
                        file.delete();
                    }
                    read.close();
                    return map;
                }
                lineNum++;
            }
            write.close();
            map.put("listMap", list);
            map.put("regRes", flag);
            if (errMsg != null || !"".equals(errMsg)) {
                map.put("errMsg", errMsg);
            }
            if (flag) {
                file.delete();
            }
            read.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Map<String, Object> getQueryDateControlListString(InputStream is) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> list = new ArrayList<String>();
        InputStreamReader read = new InputStreamReader(is);//考虑到编码格式
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = null;
        boolean flag = true;
        try {
            String filePath = TmkMerControlController.class.getResource("/").getFile().toString() + FileUtils.tmkMerFileName + ".txt";
            File file = new File(filePath);
            FileWriter write = new FileWriter(filePath, true);
            int lineNum = 1;
            String errMsg = "";
            while ((lineTxt = bufferedReader.readLine()) != null) {
                errMsg = "";
                lineTxt = lineTxt.trim();
                String[] arr = lineTxt.split("#");
                if (arr.length == 2) {
                    if (!checkTid(arr[0])) {
                        errMsg += "[终端号]=" + arr[0] + "参数格式错误#";
                    }
                    if (!checkMid(arr[1])) {
                        errMsg += "[受理机构标识码 ]=" + arr[1] + "参数格式错误#";
                    }
                } else {
                    errMsg += "数据量不正确，数据量应为两个且用'#'隔开#";
                }
                if (errMsg.equals("")) {
                    for (String str : arr) {
                        if (str != null && !"".equals(str)) {
                            list.add(str.trim());
                        }
                    }
                } else {
                    flag = false;
                    errMsg = "第" + lineNum + "行 " + errMsg.substring(0, errMsg.length() - 1) + "\r\n";
                    write.write(errMsg);
                    write.close();
                    map.put("listMap", list);
                    map.put("regRes", flag);
                    if (errMsg != null || !"".equals(errMsg)) {
                        map.put("errMsg", errMsg);
                    }
                    if (flag) {
                        file.delete();
                    }
                    read.close();
                    return map;
                }
                lineNum++;
            }
            write.close();
            map.put("listMap", list);
            map.put("regRes", flag);
            if (errMsg != null || !"".equals(errMsg)) {
                map.put("errMsg", errMsg);
            }
            if (flag) {
                file.delete();
            }
            read.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Map<String, Object> getTmkMerControlListString(InputStream is) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> list = new ArrayList<String>();
        InputStreamReader read = new InputStreamReader(is);//考虑到编码格式
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = null;
        boolean flag = true;
        try {
            String filePath = TmkMerControlController.class.getResource("/").getFile().toString() + FileUtils.tmkMerFileName + ".txt";
            File file = new File(filePath);
            FileWriter write = new FileWriter(filePath, true);
            int lineNum = 1;
            String errMsg = "";
            while ((lineTxt = bufferedReader.readLine()) != null) {
                errMsg = "";
                lineTxt = lineTxt.trim();
                String[] arr = lineTxt.split("#");
                if (arr.length == 2) {
                    if (!checkTid(arr[0])) {
                        errMsg += "[终端号]=" + arr[0] + "参数格式错误#";
                    }
                    if (!checkMid(arr[1])) {
                        errMsg += "[商户号]=" + arr[1] + "参数格式错误#";
                    }
                } else {
                    errMsg += "数据量不正确，数据量应为两个#";
                }
                if (errMsg.equals("")) {
                    for (String str : arr) {
                        if (str != null && !"".equals(str)) {
                            list.add(str.trim());
                        }
                    }
                } else {
                    flag = false;
                    errMsg = "第" + lineNum + "行 " + errMsg.substring(0, errMsg.length() - 1) + "\r\n";
                    write.write(errMsg);
                }
                lineNum++;
            }
            write.close();
            map.put("listMap", list);
            map.put("regRes", flag);
            if (flag) {
                file.delete();
            }
            read.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /*
     * 缴费终端批量查询
     *
     */
    public static Map<String, Object> getTmkControlListString(InputStream is) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> list = new ArrayList<String>();
        InputStreamReader read = new InputStreamReader(is);//考虑到编码格式
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = null;
        boolean flag = true;
        try {
            String filePath = TmkControlController.class.getResource("/").getFile().toString() + FileUtils.tmkFileName + ".txt";
            File file = new File(filePath);
            FileWriter write = new FileWriter(filePath, true);
            int lineNum = 1;
            String errMsg = "";
            while ((lineTxt = bufferedReader.readLine()) != null) {
                errMsg = "";
                lineTxt = lineTxt.trim();
                String[] arr = lineTxt.split(",");
                if (arr.length == 1) {
                    if (!checkTid(arr[0])) {
                        errMsg += "[终端号]=" + arr[0] + "参数格式错误#";
                    }
                } else {
                    errMsg += "数据量不正确";
                }
                if (errMsg.equals("")) {
                    for (String str : arr) {
                        if (str != null && !"".equals(str)) {
                            list.add(str.trim());
                        }
                    }
                } else {
                    flag = false;
                    errMsg = "第" + lineNum + "行 " + errMsg.substring(0, errMsg.length() - 1) + "\r\n";
                    write.write(errMsg);
                }
                lineNum++;
            }
            write.close();
            map.put("listMap", list);
            map.put("regRes", flag);
            if (flag) {
                file.delete();
            }
            read.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Boolean checkTid(String tid) {

        if (tid != null && !"".equals(tid)) {
            String regEx = "^[0-9a-zA-Z]{8}$";
            return tid.matches(regEx);
        }
        return false;
    }

    public static boolean checkMid(String mid) {
        if ("0".equals(mid)) {
            return true;
        }
        if (mid != null && !"".equals(mid)) {
            String regEx = "^[0-9a-zA-Z]{18}$";
            return mid.matches(regEx);
        }
        return false;
    }

    public static boolean checkLid(String mid) {
        if (mid != null && !"".equals(mid)) {
            String regEx = "^[0-9a-zA-Z]{16}$";
            return mid.matches(regEx);
        }
        return false;
    }

    /*
     * 批量删除
     */
    public static Map<String, Object> deleteSas2String(InputStream is) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<SasMercodeRoute2> list = new ArrayList<SasMercodeRoute2>();
        try {
            DataCheck dc = new DataCheck();
            InputStreamReader read = new InputStreamReader(is);//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            String errMsgs = "";
            String msg = "";
            int lineNum = 1;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                lineTxt = lineTxt.trim();
                msg = "";
                if (lineTxt.contains("#")) {
                    String[] arr = lineTxt.split("#");
                    if (arr.length != 2) {
                        msg += "参数数量有误;";
                    } else {
                        if (!dc.checkExtmercode(arr[0])) {
                            msg += "1列[POS上送商户代码]、";
                        }
                        if (!dc.checkExtmercode(arr[1])) {
                            msg += "2列[卡类型]、";
                        }
                        if (!msg.equals("")) {
                            msg = msg.substring(0, msg.length() - 1) + "参数格式错误;";
                        }
                    }
                    if (!msg.equals("")) {
                        errMsgs += "第" + lineNum + "行" + msg + "<br/>";
                    } else {
                        SasMercodeRoute2 s = new SasMercodeRoute2();
                        s.setExtmercode(arr[0].trim());
                        s.setCardtype(arr[1].trim());
                        list.add(s);
                    }
                } else {
                    msg += "分隔符有误;";
                }
                lineNum++;
            }
            read.close();
            if (!errMsgs.equals("")) {
                map.put("msg", errMsgs);
            } else {
                map.put("listData", list);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /*
     * 批量修改二代路由
     */
    public static Map<String, Object> updateSas2String(InputStream is) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<SasMercodeRoute2> list = new ArrayList<SasMercodeRoute2>();
        try {
            InputStreamReader read = new InputStreamReader(is, "GBK");
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = "";
            String errMsgs = "";
            String msg = "";
            int lineNum = 1;
            DataCheck dc = new DataCheck();
            while ((lineTxt = bufferedReader.readLine()) != null) {
                msg = "";
                lineTxt = lineTxt.trim();
                if (lineTxt.contains("#")) {
                    String[] arr = lineTxt.split("#");
                    if (arr.length != 12) {
                        msg += "参数数量有误;";
                    } else {
                        if (!dc.checkExtmercode(arr[0])) {
                            msg += "1列[外部商户号]、";
                        }
                        if (!dc.checkMimccode(arr[1])) {
                            msg += "2列[内部代码]、";
                        }
                        if (!dc.checkRouteid(arr[2])) {
                            msg += "3列[交易路由]、";
                        }
                        if (!dc.checkFwdcode(arr[3])) {
                            msg += "4列[转发机构号]、";
                        }
                        if (!dc.checkF18(arr[4])) {
                            msg += "5列[18域上送MCC]、";
                        }
                        if (!dc.checkF6031(arr[5])) {
                            msg += "6列[F60_3_1特殊计费类型]、";
                        }
                        if (!dc.checkF6032(arr[6])) {
                            msg += "7列[F60_3_2特殊计费档次]、";
                        }
                        if (!dc.checkCardtype(arr[7])) {
                            msg += "8列[卡类型]、";
                        }
                        if (!dc.checkMername(arr[8])) {
                            msg += "9列[上送F43域商户名称]、";
                        }
                        if (!dc.checkF42(arr[9])) {
                            msg += "10列[上送F42商户代码]、";
                        }
                        if (!dc.checkF41(arr[10])) {
                            msg += "11列[上送F41终端代码]、";
                        }
                        if (!dc.checkMerStatus(arr[11])) {
                            msg += "12列[商户开关]、";
                        }
                        if (!msg.equals("")) {
                            msg = msg.substring(0, msg.length() - 1) + "参数格式错误;";
                        }
                    }
                    if (!msg.equals("")) {
                        errMsgs += "第" + lineNum + "行" + msg + "<br/>";
                    } else {
                        SasMercodeRoute2 s = new SasMercodeRoute2();
                        s.setMerStatus("");
                        s.setExtmercode(arr[0].trim());
                        s.setMimccode(arr[1].trim());
                        s.setRouteid(arr[2].trim());
                        s.setFwdcode(arr[3].trim());
                        s.setF18(arr[4].trim());
                        s.setF6031(arr[5].trim());
                        s.setF6032(arr[6].trim());
                        s.setCardtype(arr[7].trim());
                        s.setMername(arr[8].trim());
                        s.setF42(arr[9].trim());
                        s.setF41(arr[10].trim());
                        s.setMerStatus(arr[11].trim());
                        list.add(s);
                    }
                } else {
                    msg += "分隔符有误;";
                }
                lineNum++;
            }
            read.close();
            if (!errMsgs.equals("")) {
                map.put("msg", errMsgs);
            } else {
                map.put("listData", list);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /*
     * 批量修改一代路由
     */
    public static Map<String, Object> updateSasString(InputStream is) {
        List<SasMercodeRoute> list = new ArrayList<SasMercodeRoute>();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            InputStreamReader read = new InputStreamReader(is, "GBK");
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            String errMsgs = "";
            String msg = "";
            int lineNum = 1;
            DataCheck dc = new DataCheck();
            while ((lineTxt = bufferedReader.readLine()) != null) {
                SasMercodeRoute s = new SasMercodeRoute();
                lineTxt = lineTxt.trim();
                msg = "";
                if (lineTxt.contains("#")) {
                    String[] arr = lineTxt.split("#");
                    if (arr.length != 7) {
                        msg += "参数数量有误;";
                    } else {
                        if (!dc.checkExtmercode(arr[0])) {
                            msg += "1列[POS上送商户代码]、";
                        }
                        if (!dc.checkMimccode(arr[1])) {
                            msg += "2列[内部商户号]、";
                        }
                        if (!dc.checkRouteid(arr[2])) {
                            msg += "3列[交易路由]、";
                        }
                        if (!dc.checkFwdcode(arr[3])) {
                            msg += "4列[转发机构号]、";
                        }
                        if (!dc.checkF18(arr[4])) {
                            msg += "5列[18域上送MCC]、";
                        }
                        if (!dc.checkF6031(arr[5])) {
                            msg += "6列[F60_3_1特殊计费类型]、";
                        }
                        if (!dc.checkF6032(arr[6])) {
                            msg += "7列[F60_3_2特殊计费档次]、";
                        }
                        if (!msg.equals("")) {
                            msg = msg.substring(0, msg.length() - 1) + "参数格式错误;";
                        }
                    }
                    if (msg.equals("")) {
                        s.setExtmercode(arr[0].trim());
                        s.setMimccode(arr[1].trim());
                        s.setRouteid(arr[2].trim());
                        s.setFwdcode(arr[3].trim());
                        s.setF18(arr[4].trim());
                        s.setF6031(arr[5].trim());
                        s.setF6032(arr[6].trim());
                        list.add(s);
                    } else {
                        errMsgs += "第" + lineNum + "行" + msg + "<br/>";
                    }
                } else {
                    msg += "分隔符有误;";
                }
                lineNum++;
            }
            read.close();
            if (errMsgs.equals("")) {
                map.put("listData", list);
            } else {
                map.put("msg", errMsgs);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 消费终端主密钥批量修改
     *
     * @param is
     * @return
     */
    public static Map<String, Object> updateTmkString(InputStream is) {
        List<TmkMerControl> list = new ArrayList<TmkMerControl>();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            InputStreamReader read = new InputStreamReader(is, "GBK");
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            String errMsgs = "";
            String msg = "";
            int lineNum = 1;
            DataCheck dc = new DataCheck();
            while ((lineTxt = bufferedReader.readLine()) != null) {
                TmkMerControl s = new TmkMerControl();
                lineTxt = lineTxt.trim();
                msg = "";
                if (lineTxt.contains("#")) {
                    String[] arr = lineTxt.split("#");
                    if (arr.length != 3) {
                        msg += "参数数量有误;";
                    } else {
                        if (!dc.checkTid(arr[0])) {
                            msg += "1列[终端号]、";
                        }
                        if (!dc.checkMid(arr[1])) {
                            msg += "2列[商户号]、";
                        }
                        if (!dc.checkFlag(arr[2])) {
                            msg += "3列[flag]、";
                        }
                        if (!msg.equals("")) {
                            msg = msg.substring(0, msg.length() - 1) + "参数格式错误;";
                        }
                    }
                    if (msg.equals("")) {
                        s.setTid(arr[0].trim());
                        s.setMid(arr[1].trim());
                        s.setFlag(arr[2].trim());
                        list.add(s);
                    } else {
                        errMsgs += "第" + lineNum + "行" + msg + "<br/>";
                    }
                } else {
                    msg += "分隔符有误;";
                }
                lineNum++;
            }
            read.close();
            if (errMsgs.equals("")) {
                map.put("listData", list);
            } else {
                map.put("msg", errMsgs);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 缴费终端主密钥批量修改
     *
     * @param is
     * @return
     */
    public static Map<String, Object> updateTmkControllerString(InputStream is) {
        List<TmkControl> list = new ArrayList<TmkControl>();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            InputStreamReader read = new InputStreamReader(is, "GBK");
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            String errMsgs = "";
            String msg = "";
            int lineNum = 1;
            DataCheck dc = new DataCheck();
            while ((lineTxt = bufferedReader.readLine()) != null) {
                TmkControl s = new TmkControl();
                lineTxt = lineTxt.trim();
                msg = "";
                if (lineTxt.contains("#")) {
                    String[] arr = lineTxt.split("#");
                    if (arr.length != 2) {
                        msg += "参数数量有误;";
                    } else {
                        if (!dc.checkTid(arr[0])) {
                            msg += "1列[终端号]、";
                        }
                        if (!dc.checkMid(arr[1])) {
                            msg += "2列[flag]、";
                        }
                        if (!msg.equals("")) {
                            msg = msg.substring(0, msg.length() - 1) + "参数格式错误;";
                        }
                    }
                    if (msg.equals("")) {
                        s.setTid(arr[0].trim());
                        s.setFlag(arr[1].trim());
                        list.add(s);
                    } else {
                        errMsgs += "第" + lineNum + "行" + msg + "<br/>";
                    }
                } else {
                    msg += "分隔符有误;";
                }
                lineNum++;
            }
            read.close();
            if (errMsgs.equals("")) {
                map.put("listData", list);
            } else {
                map.put("msg", errMsgs);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 删除文件或目录  file如果是一个文件则删除文件
     * 如果是一个目录则递归删除目录下所有的文件以及目录
     */
    public static void del(File file) {
        File[] files = file.listFiles();
        if (files != null)
            for (File f : files)
                del(f);
        file.delete();
    }

    public static List<TmkControl> readTxtFile2(InputStream is) {
        List<TmkControl> list = new ArrayList<TmkControl>();
        try {

            InputStreamReader read = new InputStreamReader(
                    is);//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                TmkControl tc = new TmkControl();
                lineTxt = lineTxt.trim();
                /* String []arr=lineTxt.split(" ");*/
                tc.setTid(lineTxt);
                tc.setFlag("1");
                list.add(tc);
            }
            read.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("读取文件内容出错");
            e.printStackTrace();
            return list;
        }

    }

    public static List<String> readTerTxtFile(InputStream is) {
        List<String> list = new ArrayList<String>();
        try {

            InputStreamReader read = new InputStreamReader(
                    is);//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                lineTxt = lineTxt.trim();
                list.add(lineTxt);
            }
            read.close();
            return list;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("读取文件内容出错");
            return list;
        }

    }

    public static List<TmkMerControl> readTxtFile3(InputStream is) {
        List<TmkMerControl> list = new ArrayList<TmkMerControl>();
        Result result = new Result();
        try {

            InputStreamReader read = new InputStreamReader(
                    is);//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                TmkMerControl tmc = new TmkMerControl();
                lineTxt = lineTxt.trim();
                if (lineTxt.contains("#")) {
                    String[] arr = lineTxt.split("#");
                    tmc.setTid(arr[0].trim());
                    tmc.setMid(arr[1].trim());
                    tmc.setFlag("1");
                    list.add(tmc);
                } else {
                    result.setMessage("导入数据格式错误");
                }
            }
            read.close();
            return list;

        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
            return list;
        }
    }

    public static List<String> readTxtFile4(InputStream is) {
        List<String> list = new ArrayList<String>();
        try {
            InputStreamReader read = new InputStreamReader(
                    is);//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                lineTxt = lineTxt.trim();
                list.add(lineTxt);
            }
            read.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("读取文件内容出错");
            e.printStackTrace();
            return list;
        }
    }

    //导入深圳通格式

    public static List<TblSZTTerminal> readSztTxt(InputStream is) {
        List<TblSZTTerminal> list = new ArrayList<TblSZTTerminal>();
        Result result = new Result();
        try {
            InputStreamReader read = new InputStreamReader(
                    is, "GBK");//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                TblSZTTerminal szt = new TblSZTTerminal();
                lineTxt = lineTxt.trim();
                if (lineTxt.contains("#")) {
                    String[] arr = lineTxt.split("#");
                    szt.setSztTermMk("");
                    szt.setSztTerminalID(arr[0].trim());
                    szt.setSztSamID(arr[1].trim());
                    szt.setStatus(arr[2].trim());
                    szt.setAddress(arr[3].trim());
                    szt.setDevice(arr[4].trim());
                    szt.setMaster(arr[5].trim());
                    szt.setMerchantId(arr[6].trim());
                    szt.setFwdmccode(arr[7].trim());
                    szt.setDistrict(arr[8].trim());
                    list.add(szt);
                } else {
                    result.setMessage("导入数据格式错误");
                }
            }
            read.close();
            return list;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("读取文件内容出错");
            e.printStackTrace();
            return list;
        }

    }

    //sas路由一代
    public static List<SasMercodeRoute> readSasTxt(InputStream is) {
        List<SasMercodeRoute> list = new ArrayList<SasMercodeRoute>();
        Result result = new Result();
        try {
            InputStreamReader read = new InputStreamReader(is, "GBK");//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                SasMercodeRoute sas = new SasMercodeRoute();
                lineTxt = lineTxt.trim();
                if (lineTxt.contains("#")) {
                    String[] arr = lineTxt.split("#");
                    sas.setExtmercode(arr[0].trim());
                    sas.setMimccode(arr[1].trim());
                    sas.setRouteid(arr[2].trim());
                    sas.setFwdcode(arr[3].trim());
                    sas.setMername(arr[4].trim());
                    sas.setF18(arr[5].trim());
                    sas.setF6031(arr[6].trim());
                    sas.setF6032(arr[7].trim());
                    list.add(sas);
                } else {
                    result.setMessage("导入数据格式错误");
                }
            }
            read.close();
            return list;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("读取文件内容出错");
            e.printStackTrace();
            return list;
        }

    }

    //sas路由二代
    public static List<SasMercodeRoute2> readSas2Txt(InputStream is) {
        List<SasMercodeRoute2> list = new ArrayList<SasMercodeRoute2>();
        Result result = new Result();
        try {
            InputStreamReader read = new InputStreamReader(
                    is, "GBK");//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                SasMercodeRoute2 sas = new SasMercodeRoute2();
                lineTxt = lineTxt.trim();
                if (lineTxt.contains("#")) {
                    String[] arr = lineTxt.split("#");
                    sas.setMerStatus("");
                    sas.setExtmercode(arr[0].trim());
                    sas.setMimccode(arr[1].trim());
                    sas.setRouteid(arr[2].trim());
                    sas.setFwdcode(arr[3].trim());
                    sas.setMername(arr[4].trim());
                    sas.setF18(arr[5].trim());
                    sas.setF6031(arr[6].trim());
                    sas.setF6032(arr[7].trim());
                    sas.setCardtype(arr[8].trim());
                    sas.setF42(arr[9].trim());
                    sas.setF41(arr[10].trim());
                    sas.setDescription(arr[11].trim());
                    sas.setUseF42Flag(arr[12].trim());
                    sas.setUseF41Flag(arr[13].trim());
                    sas.setMerStatus("1");
                    list.add(sas);
                } else {
                    result.setMessage("导入数据格式错误");
                }
            }
            read.close();
            return list;

        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }

    }

    public static List<MerchantWhite> readTxtmWFile(InputStream is) {
        List<MerchantWhite> list = new ArrayList<MerchantWhite>();
        Result result = new Result();
        String zn = null;
        try {
            User user = (User) RequestUtils.getRequest().getSession().getAttribute(Const.SESSION_USER);
            zn = user.getZN();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        String lineTxt = null;
        try {
            InputStreamReader read = new InputStreamReader(is);//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            while ((lineTxt = bufferedReader.readLine()) != null) {
                MerchantWhite mw = new MerchantWhite();
                lineTxt = lineTxt.trim();
                String[] arr = lineTxt.split("#");
                String substring = arr[0].substring(0, 3);
                if (substring.equals("000")) {
                    mw.setExmercno(arr[0].trim());
                    mw.setStatus(arr[1].trim());
                    mw.setInsertTime(format);
                    mw.setDescription(arr[2].trim());
                    mw.setOperator(zn);
                    list.add(mw);
                } else {
                    result.setSuccess(false);
                    result.setMessage("导入数据格式错误");
                }
            }
            read.close();
            return list;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("读取文件内容出错");
            e.printStackTrace();
            return list;
        }

    }

    /**
     * 风控大数据
     *
     * @param list
     */
    public static boolean checkFileMt(InputStream is) {
        boolean flag = true;
        String lineTxt = null;
        InputStreamReader read = null;
        BufferedReader bufferedReader = null;
        mtLists.clear();
        try {
            Map<String, String> mccMap = new HashMap<String, String>();
            Map<String, String> repData = new HashMap<String, String>(); //重复行
            int lineNum = 1;
            int dataNum = 5;
            String mcc = "";
            String filePath = MerchantTrcController.class.getResource("/").getFile().toString() + FileUtils.merTrcFileName + ".txt";
            File file = new File(filePath);
            FileWriter write = new FileWriter(file);
            read = new InputStreamReader(is, "GBK");//考虑到编码格式
            bufferedReader = new BufferedReader(read);
            while ((lineTxt = bufferedReader.readLine()) != null) {
                String[] arr = lineTxt.split("#");
                if (arr.length != dataNum) {
                    write.write("第" + lineNum + "行 " + lineTxt + "#数据量应为" + dataNum + "\r\n");
                    flag = false;
                } else {
                    if (!regExpMt(arr)) {
                        write.write("第" + lineNum + "行 " + lineTxt + "#数据格式不正确\r\n");
                        flag = false;
                    } else {
                        MerchantTrcPend mw = new MerchantTrcPend();
                        mcc = arr[0].trim();
                        if (!mcc.equals("")) {
                            if (mccMap.containsKey(mcc)) {
                                repData.put(mcc, mccMap.get(mcc) + "、" + lineNum);
                                mccMap.put(mcc, mccMap.get(mcc) + "、" + lineNum);
                            } else {
                                mccMap.put(mcc, String.valueOf(lineNum));
                            }
                            mw.setOperationType("1");
                            mw.setCheckStatus("0");
                            mw.setMcc(mcc.equals("") ? null : mcc);
                            mw.setDescription(arr[1].trim().equals("") ? "0" : arr[1].trim());
                            mw.setAmountPerTime(arr[2].trim().equals("") ? "0" : arr[2].trim());
							/*mw.setAmountPerDay( arr[3].trim().equals("")?"0":arr[3].trim() );
							mw.setAmountPerWeek( arr[4].trim().equals("")?"0":arr[4].trim() );
							mw.setAmountPerMonth( arr[5].trim().equals("")?"0":arr[5].trim() );*/
                            mw.setHandleType(arr[3].trim().equals("") ? null : arr[3].trim());
                            mw.setGreatType(arr[4].trim().equals("") ? null : arr[4].trim());
                            //							mw.setCountPerDay( arr[6].trim().equals("")?null:Integer.valueOf( arr[6].trim() ) );
                            //							mw.setCountPerWeek( arr[7].trim().equals("")?null:Integer.valueOf( arr[7].trim() ) );
                            //							mw.setCountPerMonth( arr[8].trim().equals("")?null:Integer.valueOf( arr[8].trim() ) );
                            //							mw.setHandleType( arr[9].trim().equals("")?null:arr[9].trim() );
                            //							mw.setGreatType( arr[10].trim().equals("")?null:arr[10].trim() );
                            User user = SubjectUtils.getSubject();
                            if (user != null) {
                                mw.setOperator(user.getZN());
                                mw.setCreator(user.getZN());
                            }
                            mtLists.add(mw);
                        }
                    }
                }
                lineNum++;
            }
            for (String key : repData.keySet()) {
                write.write("第" + repData.get(key) + "行 商户类别号(" + key + ")重复\r\n");
                flag = false;
            }
            write.close();
            read.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (read != null) {
                    read.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    private static boolean regExpMt(String[] arr) {
        boolean flag = true;
        try {
            if (!arr[0].trim().matches(regExp.get("mcc"))) {
                flag = false;
            }
            if (arr[1].trim().length() > 32) {
                flag = false;
            }
            if (!arr[2].trim().matches(regExp.get("amountPerTime"))) {
                flag = false;
            }
			/*if( !arr[3].trim().matches( regExp.get("amountPerDay") ) ){
				flag = false;
			}
			if( !arr[4].trim().matches( regExp.get("amountPerWeek") ) ){
				flag = false;
			}
			if( !arr[5].trim().matches( regExp.get("amountPerMonth") ) ){
				flag = false;
			}*/
            if (!arr[3].trim().matches(regExp.get("handleType"))) {
                flag = false;
            }
            if (!arr[4].trim().matches(regExp.get("greatType"))) {
                flag = false;
            }
			/*if( !arr[6].trim().matches( regExp.get("countPerDay") ) ){
				flag = false;
			}
			if( !arr[7].trim().matches( regExp.get("countPerWeek") ) ){
				flag = false;
			}
			if( !arr[8].trim().matches( regExp.get("countPerMonth") ) ){
				flag = false;
			}
			if( !arr[9].trim().matches( regExp.get("handleType") ) ){
				flag = false;
			}
			if( !arr[10].trim().matches( regExp.get("greatType") ) ){
				flag = false;
			}*/
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    //导入外部商户资料格式
    public static List<ExtMerchant> readExtMerTxt(InputStream is) {
        List<ExtMerchant> list = new ArrayList<ExtMerchant>();
        Result result = new Result();
        try {
            InputStreamReader read = new InputStreamReader(
                    is, "GBK");//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                ExtMerchant extMer = new ExtMerchant();
                lineTxt = lineTxt.trim();
                if (lineTxt.contains("#")) {
                    String arr[] = lineTxt.split("#");
                    extMer.setExtMerCode(arr[0].trim());
                    extMer.setExtMerName(arr[1].trim());
                    extMer.setFwdInsCode(arr[2].trim());
                    extMer.setValidFlag(arr[3].trim());
                    extMer.setSagentFlag(arr[4].trim());
                    list.add(extMer);
                } else {
                    result.setMessage("导入数据格式错误");
                }
            }
            read.close();
            return list;

        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }

    }

    //导入外部终端号资料格式
    public static List<ExtTerminal> readExtTermTxt(InputStream is) {
        List<ExtTerminal> list = new ArrayList<ExtTerminal>();
        Result result = new Result();
        try {
            InputStreamReader read = new InputStreamReader(
                    is, "GBK");//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                ExtTerminal extTerm = new ExtTerminal();
                lineTxt = lineTxt.trim();
                if (lineTxt.contains("#")) {
                    String arr[] = lineTxt.split("#");
                    extTerm.setExtTermId(arr[0].trim());
                    extTerm.setExtMerCode(arr[1].trim());
                    extTerm.setExtMerName(arr[2].trim());
                    list.add(extTerm);
                } else {
                    result.setMessage("导入数据格式错误");
                }
            }
            read.close();
            return list;

        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }

    }

    /**
     * 批量导入司机信息   解析txt文件获取司机信息的集合
     *
     * @param list bankInfoService
     */

    public Map<String, Object> readTxtToTaxiDriver(InputStream is) {
        List<TaxiDriver> list = new ArrayList<TaxiDriver>();
        List<TaxiDriver> errorList = new ArrayList<TaxiDriver>();
        Map<String, Object> result = new HashMap<String, Object>();
        String lineTxt = null;
        Condition condition = new Condition();
        BufferedReader bufferedReader = null;
        try {
            InputStreamReader read = new InputStreamReader(is, "GBK");
            bufferedReader = new BufferedReader(read);
            List<String> company = driverService.getCompanyNoList();    //获取所有的出租车公司编号
            logger.info("company " + company.toString());
            List<String> driverNo = driverService.getDriverNoList(condition);    //获取所有的司机号
            logger.info("begin  parse txt ");
            while ((lineTxt = bufferedReader.readLine()) != null) {
                //i++;
                TaxiDriver driver = new TaxiDriver();
                StringBuilder sb = new StringBuilder();
                lineTxt = lineTxt.trim();
                //System.out.println("file "+lineTxt);
                if (StringUtils.isBlank(lineTxt)) {
                    continue;
                }
                if (lineTxt.contains("#")) {
                    String[] arr = trimArr(lineTxt.split("#")); //lineTxt.split(",");
                    if (arr.length < 22) {
                        driver.setMsg(StringConst.DRIVERINFO);
                        if (arr.length > 1) {
                            driver.setDriverNo(arr[0]);
                        } else {
                            driver.setDriverNo(StringConst.EMPTYTEXT);
                        }
						/*if(StringUtils.validateDriverNoFormat(arr[7])){ //验证本行内容第7个字段是否为司机号
							driver.setDriverNo(arr[7]);
						}else{
							driver.setDriverNo(arr[1]);  //如果第7个字段不是司机号，则使用司机姓名作为提示信息
						}*/

                        errorList.add(driver);
                        continue;
                    }
                    //driver.setName(arr[0]);
                    if (arr[0].matches(IpsRegex.CN_NMAE)) {
                        driver.setName(arr[0]);
                    } else {
                        sb.append(StringConst.CNNAME + " ");
                    }
                    if (IpsRegex.checkBankCardCode(arr[1])) { //校验卡号的有效性
                        driver.setAccNo(arr[1]);
                        List<BankInfo> bank = bankInfoService.findListByCardNo(arr[1]);
                        if (bank.size() <= 0) {
                            sb.append(StringConst.CARDBIN + " ");
                        } else {
                            String openBank = findOpenCode(bank.get(0));  //通过卡号 集合卡bin 获取银行编号  再通过银行编号获取开户行行号
                            if (openBank == null || "".equals(openBank)) {
                                sb.append(StringConst.OPENBANKCODE + " ");
                            }
                            driver.setOpenBankCode(openBank);
                        }
                    } else {
                        sb.append(StringConst.BANKCODEERROR + " ");
                    }
                    driver.setOpenBankName(arr[2]);
                    //System.out.println(bank.toString());
                    if (arr[4].matches(IpsRegex.ONEORZERO)) {
                        driver.setFeeFlag(arr[4]);
                    } else {
                        sb.append(StringConst.FEEFLAG + " ");
                    }
                    if (arr[5].matches(IpsRegex.phone)) {
                        driver.setPhoneNo(arr[5]);
                    } else {
                        sb.append(StringConst.PHONE + " ");
                    }

                    if (this.validateCompany(arr[6], company)) {
                        driver.setCompanyNo(arr[6]);
                    } else {
                        sb.append(StringConst.COMPANYNO + " ");
                    }

                    if (StringUtils.validateDriverNoFormat(arr[7])) {  //验证司机号的格式
                        if (this.validateDriverNo(arr[7], driverNo)) { //如果司机号不存在 数据库中 允许添加
                            driverNo.add(arr[7]);    //把司机号添加到集合中    防止同一个文件导入相同的司机号
                        } else {
                            sb.append(StringConst.DRIVERNOEXIST + " ");
                        }
                    } else {
                        sb.append(StringConst.DRIVERNOFORMAT + " ");
                    }


                    driver.setDriverNo(arr[7]);
                    if (IpsRegex.isValidDate(arr[8])) {
                        driver.setDriverNoDate(arr[8]);
                    } else {
                        sb.append("发证日期格式有误    ");
                    }

                    if (arr[9].matches(IpsRegex.IDType)) {
                        driver.setIdType(arr[9]);
                    } else {
                        sb.append(StringConst.IDTYPE + " ");
                    }
                    if (arr[10].matches(IpsRegex.IDCard)) {// 验证身份证号码格式
                        if (IpsRegex.validIDCard(arr[10])) { //校验身份证号码的有效性
                            driver.setIdNo(arr[10]);
                        } else {
                            sb.append(StringConst.IDNOERROR + " ");
                        }

                    } else {
                        sb.append(StringConst.IDNO + " ");
                    }
                    //driver.setIdNo(arr[10]);
                    if (IpsRegex.isValidDate(arr[11])) {
                        driver.setIdNoDateBegin(arr[11]);
                    } else {
                        sb.append("起始日期格式有误 ");
                    }
                    if (IpsRegex.isValidDate(arr[12])) {
                        driver.setIdNoDateEnd(arr[12]);
                    } else {
                        sb.append("结束日期格式有误 ");
                    }
                    driver.setTaxiNo(arr[13]);
                    if (arr[14].matches(IpsRegex.ONEORZERO)) {
                        driver.setSettFlag(arr[14]);
                    } else {
                        sb.append(StringConst.SettFlag + " ");
                    }


					/*driver.setQqNo(arr[14]);
	                  		driver.setWechat(arr[15]);
	                  		driver.setMail(arr[16]);*/
                    if (arr[18].matches(IpsRegex.status)) {
                        /*driver.setSettFlag(arr[14]);*/
                        driver.setStatus(arr[18]);
                    } else {
                        sb.append(StringConst.ISSTATE + " ");
                    }
                    //校验是否需要鉴权
                    if (arr[19].matches(IpsRegex.ISAUTH)) {
                        driver.setIsAuth(arr[19]);
                    } else {
                        sb.append(StringConst.ISAUTH);
                    }
                    /*driver.setStatus(arr[18]);*/
					/*String shunt = arr[20];
					String sms = arr[21];*/
                    if (arr[20].matches(IpsRegex.shuntFlag)) {
                        driver.setShuntFlag(arr[20]);
                    } else {
                        sb.append(StringConst.SHUNTFLAG);
                    }
                    if (arr[21].matches(IpsRegex.smsFlag)) {
                        driver.setSmsFlag(arr[21]);
                    } else {
                        sb.append(StringConst.SMSFLAG);
                    }
                    driver.setInsDate(DateUtils.currentDay());
                    if (sb.length() > 0) {
                        driver.setMsg(sb.toString());
                        errorList.add(driver);
                    } else {
                        list.add(driver);
                    }

                } else {
                    result.put("code", StringConst.FLAG_FALSE);
                    result.put("msg", StringConst.IMPORT_SPLIT);
                    logger.info("manager result " + result);
                    return result;
                }
            }


            bufferedReader.close();
            read.close();

            if (errorList.size() > 0) {  //验证格式不通过的 司机集合
                result.put("code", StringConst.FLAG_FALSE);
                result.put("errorList", errorList);
            } else {
                result.put("code", StringConst.FLAG_TRUE);
                result.put("list", list);
            }

            logger.info("manager result " + result);
            return result;


        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("读取文件内容出错");
            e.printStackTrace();
            logger.info("manager result " + result);
            return result;
        }

    }


    /**
     * 批量导入司机信息   解析txt文件获取司机信息的集合
     *
     * @param list bankInfoService
     */

    public Map<String, Object> batchAccont(InputStream is) {
        List<TaxiDriver> list = new ArrayList<TaxiDriver>();
        List<TaxiDriver> errorList = new ArrayList<TaxiDriver>();
        Map<String, Object> result = new HashMap<String, Object>();
        String lineTxt = null;
        Condition condition = new Condition();
        BufferedReader bufferedReader = null;
        try {
            InputStreamReader read = new InputStreamReader(is, "GBK");
            bufferedReader = new BufferedReader(read);
            List<String> driverNo = driverService.getDriverNoList(condition);    //获取所有的司机号
            logger.info("begin  parse txt ");
            while ((lineTxt = bufferedReader.readLine()) != null) {
                //i++;
                TaxiDriver driver = new TaxiDriver();
                StringBuilder sb = new StringBuilder();
                lineTxt = lineTxt.trim();
                //System.out.println("file "+lineTxt);
                if (StringUtils.isBlank(lineTxt)) {
                    continue;
                }
                if (lineTxt.contains("#")) {
                    String[] arr = trimArr(lineTxt.split("#")); //lineTxt.split(",");
                    if (arr.length < 6) { //若字段缺少
                        driver.setMsg(StringConst.DRIVERINFO);
                        if (arr.length > 1) {
                            driver.setDriverNo(arr[0]);
                        } else {
                            driver.setDriverNo(StringConst.EMPTYTEXT);
                        }
                        errorList.add(driver);
                        continue;
                    }
                    //driver.setName(arr[0]);
                    if (arr[0].matches(IpsRegex.CN_NMAE)) {
                        driver.setName(arr[0]);
                    } else {
                        sb.append(StringConst.CNNAME + " ");
                    }
                    if (IpsRegex.checkBankCardCode(arr[1])) { //校验卡号的有效性
                        driver.setAccNo(arr[1]);
                        List<BankInfo> bank = bankInfoService.findListByCardNo(arr[1]);
                        if (bank.size() <= 0) {
                            sb.append(StringConst.CARDBIN + " ");
                        } else {
                            String openBank = findOpenCode(bank.get(0));  //通过卡号 集合卡bin 获取银行编号  再通过银行编号获取开户行行号
                            if (openBank == null || "".equals(openBank)) {
                                sb.append(StringConst.OPENBANKCODE + " ");
                            }
                            driver.setOpenBankCode(openBank);
                        }
                    } else {
                        sb.append(StringConst.BANKCODEERROR + " ");
                    }

                    if (arr[2].matches(IpsRegex.phone)) {
                        driver.setPhoneNo(arr[2]);
                    } else {
                        sb.append(StringConst.PHONE + " ");
                    }


                    if (StringUtils.validateDriverNoFormat(arr[3])) {  //验证司机号的格式
                        if (this.validateDriverNo(arr[3], driverNo)) { //如果司机号不存在 数据库中 允许添加
                            sb.append(StringConst.NONOTEXIST + " ");
                        }
                    } else {
                        sb.append(StringConst.DRIVERNOFORMAT + " ");
                    }
                    driver.setDriverNo(arr[3]);

                    if (arr[4].matches(IpsRegex.IDCard)) {// 验证身份证号码格式
                        if (IpsRegex.validIDCard(arr[4])) { //校验身份证号码的有效性
                            driver.setIdNo(arr[4]);
                        } else {
                            sb.append(StringConst.IDNOERROR + " ");
                        }

                    } else {
                        sb.append(StringConst.IDNO + " ");
                    }

                    if (arr[5].matches(IpsRegex.TAXIMCCODE)) {
                        driver.setAccType(arr[5]);
                    } else {
                        sb.append(StringConst.ACCTYPE + " ");
                    }


                    if (sb.length() > 0) {
                        driver.setMsg(sb.toString());
                        errorList.add(driver);
                    } else {
                        list.add(driver);
                    }

                } else {
                    result.put("code", StringConst.FLAG_FALSE);
                    result.put("msg", StringConst.IMPORT_SPLIT);
                    logger.info("manager result " + result);
                    return result;
                }
            }


            bufferedReader.close();
            read.close();

            if (errorList.size() > 0) {  //验证格式不通过的 司机集合
                result.put("code", StringConst.FLAG_FALSE);
                result.put("errorList", errorList);
            } else {
                result.put("code", StringConst.FLAG_TRUE);
                result.put("list", list);
            }

            logger.info("manager result " + result);
            return result;


        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("读取文件内容出错");
            e.printStackTrace();
            logger.info("manager result " + result);
            return result;
        }

    }


    /**
     * 校验公司号是否存在
     *
     * @param companyNo
     * @param list
     * @return
     */
    public boolean validateCompany(String companyNo, List<String> list) {
        for (String no : list) {
            if (no.equals(companyNo)) { //公司号存在则校验通过
                return true;
            }
        }
        return false;
    }

    /*
     *校验司机号是否重复
     */
    public boolean validateDriverNo(String driverNo, List<String> list) {
        for (String no : list) {
            if (no.equals(driverNo)) { //如果司机号已经存在 则校验不通过
                return false;
            }
        }
        return true;
    }

    /**
     * 生成txt文件
     *
     * @param list
     */
    public static String creatFile(List<TerminalSecret> list, String path, String fileName) {
        String filepath = path + fileName;
        File file = new File(filepath);

        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            BufferedWriter output = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < list.size(); i++) {
                output.write(String.valueOf(list.get(i).getTerminal() + ",  " + list.get(i).getMerchantId() + ",   " + list.get(i).getSecretKey() + ",   " + list.get(i).getMsg()) + "\r\n");
            }
            output.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return filepath;
    }

    /**
     * 生成txt文件
     *
     * @param list
     */
    public static String creatFile2(List<TmkControl> list, String path, String fileName) {
        String filepath = path + fileName;
        File file = new File(filepath);
        if (file.exists()) {

            file.delete();

        }

        try {
            file.createNewFile();
            BufferedWriter output = new BufferedWriter(new FileWriter(file));


            for (int i = 0; i < list.size(); i++) {

                output.write(String.valueOf(list.get(i).getTid() + ",  " + list.get(i).getFlag() + ",   " + list.get(i).getInsertTime() + ",   " + list.get(i).getUpdateTime()) + "\r\n");

            }
            output.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return filepath;
    }

    public static String creatFile3(List<TmkMerControl> list, String path, String fileName) {
        String filepath = path + fileName;
        File file = new File(filepath);
        if (file.exists()) {

            file.delete();

        }

        try {
            file.createNewFile();
            BufferedWriter output = new BufferedWriter(new FileWriter(file));


            for (int i = 0; i < list.size(); i++) {

                output.write(String.valueOf(list.get(i).getTid() + ",  " + list.get(i).getMid() + ",   " + list.get(i).getFlag() + ",   " + list.get(i).getInsertTime() + ",   " + list.get(i).getUpdateTime()) + "\r\n");

            }
            output.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return filepath;
    }

    public static String creatTdiFile(List<Document> list, String path, String fileName) {
        String filepath = path + fileName;
        File file = new File(filepath);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            BufferedWriter output = new BufferedWriter(new FileWriter(file));

            for (int i = 0; i < list.size(); i++) {
                output.write(String.valueOf(list.get(i).toJson() + "\r\n"));
            }
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filepath;
    }

    /***
     * 去掉字符串前后的空格，中间的空格保留
     * @param str
     * @return
     */
    public static String trimInnerSpaceStr(String str) {

        while (str.startsWith(" ")) {
            str = str.substring(1, str.length()).trim();
        }
        while (str.endsWith(" ")) {
            str = str.substring(0, str.length() - 1).trim();
        }

        return str;
    }

    /***
     * 去掉字符串数组中的每一个元素的前后空格
     * @param str
     * @return
     */
    public static String[] trimArr(String[] arr) {
        for (String str : arr) {
            str.trim();
        }

        return arr;
    }

    public String findOpenCode(BankInfo param) {
        List<Test> list = BankOpenCode.getOpenCode();
        for (Test t : list) {
            if (param.getBsbnCode().equals(t.getBankCode())) {
                return t.getOpenCode();
            }
        }
        return null;
    }

    /**
     * 通用批改功能的文件校验
     *
     * @param is
     * @param types   字段对应列名
     * @param types   字段对应类型
     * @param strLens 字段对应长度
     * @param mustLen 条件字段数
     * @return
     */
    public static Map<String, String> checkFile(InputStream is, String[] colNames, String[] types, int[] strLens, int mustLen) {
        Map<String, String> map = new HashMap<String, String>();
        InputStreamReader isr = null;
        BufferedReader br = null;
        Map<String, String> colData = new HashMap<String, String>();
        Map<String, String> repData = new HashMap<String, String>(); //重复行
        String strLen = "";//数据数量不正确
        String strFor = "";//数据格式不正确
        String repDatas = "";
        String[] strs = null;
        String mapKey = "";
        int lineNum = 1;
        try {
            comBatchList.clear();
            isr = new InputStreamReader(is, "GBK");//考虑到编码格式
            br = new BufferedReader(isr);
            String line = "";
            while ((line = br.readLine()) != null) {
                strs = line.split("#");
                mapKey = "";
                if (strs.length != types.length) {
                    strLen += String.valueOf(lineNum) + "、";
                } else {
                    for (int i = 0; i < mustLen; i++) {
                        mapKey += strs[i] + "#";
                    }
                    Map<String, String> datas = new HashMap<String, String>();
                    for (int i = 0; i < types.length; i++) {
                        if (types[i].equals("int")) {
                            if (!strs[i].matches("^\\d{0," + (strLens[i] > 0 ? strLens[i] : "") + "}$")) {
                                strFor += String.valueOf(lineNum) + "行" + (i + 1) + "列、";
                            }
                        } else if (types[i].equals("varchar")) {
                            if (strs[i].length() > strLens[i]) {
                                strFor += String.valueOf(lineNum) + "行" + (i + 1) + "列、";
                            }
                        }
                        datas.put(colNames[i], strs[i]);
                    }
                    if (colData.containsKey(mapKey)) {
                        repData.put(mapKey, colData.get(mapKey) + String.valueOf(lineNum) + "、");
                        colData.put(mapKey, colData.get(mapKey) + String.valueOf(lineNum) + "、");
                    } else {
                        colData.put(mapKey, String.valueOf(lineNum) + "、");
                    }
                    comBatchList.add(datas);
                }
                lineNum++;
            }
            for (String key : repData.keySet()) {
                repDatas += repData.get(key).substring(0, repData.get(key).length() - 1) + ",&nbsp;&nbsp;";
            }
            if (!strLen.equals("")) {
                map.put("strLen", strLen.substring(0, strLen.length() - 1));
            }
            if (!strFor.equals("")) {
                map.put("strFor", strFor.substring(0, strFor.length() - 1));
            }
            if (!repDatas.equals("")) {
                map.put("repDatas", repDatas.substring(0, repDatas.length() - 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 批量商户开户
     *
     * @param is
     * @return
     */
    public Map<String, Object> merchantBatchAccont(InputStream is) {

        List<SimpleMerchantInfo> list = new ArrayList<SimpleMerchantInfo>();
        List<SimpleMerchantInfo> errorList = new ArrayList<SimpleMerchantInfo>();
        Map<String, Object> result = new HashMap<String, Object>();
        String lineTxt = null;
        //Condition condition=new Condition();
        BufferedReader bufferedReader = null;
        try {
            InputStreamReader read = new InputStreamReader(is, "GBK");
            bufferedReader = new BufferedReader(read);
            StringBuilder sb = new StringBuilder();
            logger.info("begin  parse txt ");
            int num = 1;//记录当前读取行数
            while ((lineTxt = bufferedReader.readLine()) != null) {
                SimpleMerchantInfo merchant = new SimpleMerchantInfo();
                lineTxt = lineTxt.trim();
                if (StringUtils.isBlank(lineTxt)) {
                    num++;
                    continue;
                }
                if (lineTxt.contains("#")) {
                    String[] arr = trimArr(lineTxt.split("#"));
                    if (arr.length < 8) { //若字段缺少
                        sb.append(num + ",");
                    } else {
                        merchant.setMimccode(arr[0]);

                        merchant.setRealmccode(arr[0]);

                        merchant.setSettno(arr[1]);

                        merchant.setArtif(arr[2]);

                        merchant.setArtifId(arr[3]);

                        merchant.setTelphone(arr[4]);

                        merchant.setQueryMimcname(arr[5]);

                        merchant.setLicNo(arr[6]);

                        merchant.setLicType(arr[7]);

						/*if(arr[2].matches(IpsRegex.phone)){
							driver.setPhoneNo(arr[2]);
						}else{
							sb.append(StringConst.PHONE+" ");
						}*/

                        list.add(merchant);
                    }
                } else {
                    result.put("code", StringConst.FLAG_FALSE);
                    result.put("msg", StringConst.IMPORT_SPLIT);
                    logger.info("manager result " + result);
                    return result;
                }
                num++;
            }

            bufferedReader.close();
            read.close();

            if (sb.length() > 0) {  //验证格式不通过的 商户集合
                result.put("code", StringConst.FLAG_FALSE);
                result.put("msg", "第[" + sb.toString() + "]条数据格式不对，请核对！");
            } else {
                result.put("code", StringConst.FLAG_TRUE);
                result.put("list", list);
            }

            logger.info("manager result " + result);
            return result;


        } catch (Exception e) {
            result.put("code", StringConst.FLAG_FALSE);
            result.put("msg", "读取商户批量开户文件过程中发生异常：" + e.getMessage());
            logger.info("manager result " + result);
            return result;
        }
    }

    /**
     * 批量特约商户关注配置
     *
     * @param is
     * @return
     */
    public Map<String, Object> batchFocusConfiguration(InputStream is) {

        List<UnionpayWechat> list = new ArrayList<UnionpayWechat>();
        List<UnionpayWechat> errorList = new ArrayList<UnionpayWechat>();
        Map<String, Object> result = new HashMap<String, Object>();
        String lineTxt = null;
        //Condition condition=new Condition();
        BufferedReader bufferedReader = null;
        try {
            InputStreamReader read = new InputStreamReader(is, "GBK");
            bufferedReader = new BufferedReader(read);
            StringBuilder sb = new StringBuilder();
            logger.info("begin  parse txt ");
            int num = 1;//记录当前读取行数
            while ((lineTxt = bufferedReader.readLine()) != null) {
                UnionpayWechat unionpayWechat = new UnionpayWechat();
                lineTxt = lineTxt.trim();
                if (StringUtils.isBlank(lineTxt)) {
                    num++;
                    continue;
                }
                if (lineTxt.contains("#")) {
                    String[] arr = trimArr(lineTxt.split("#"));
                    if (arr.length < 5 || arr.length > 6) { //若字段缺少
                        sb.append(num + ",");
                    } else {
                        if (arr.length == 5) {
                            unionpayWechat.setReceiptAppid("");
                        } else {
                            unionpayWechat.setReceiptAppid(arr[5]);
                        }
                        unionpayWechat.setMiextmco(arr[0]);
                        unionpayWechat.setMchId(arr[1]);
                        unionpayWechat.setSubMchId(arr[2]);
                        unionpayWechat.setSubAppid(arr[3]);
                        unionpayWechat.setForins("0880158514");
                        unionpayWechat.setSubscribeAppid(arr[4]);

                        list.add(unionpayWechat);
                    }
                } else {
                    result.put("code", StringConst.FLAG_FALSE);
                    result.put("msg", StringConst.IMPORT_SPLIT);
                    logger.info("manager result " + result);
                    return result;
                }
                num++;
            }

            bufferedReader.close();
            read.close();

            if (sb.length() > 0) {  //验证格式不通过的 商户集合
                result.put("code", StringConst.FLAG_FALSE);
                result.put("msg", "第[" + sb.toString() + "]条数据格式不对，请核对！");
            } else {
                result.put("code", StringConst.FLAG_TRUE);
                result.put("list", list);
            }

            logger.info("manager result " + result);
            return result;


        } catch (Exception e) {
            result.put("code", StringConst.FLAG_FALSE);
            result.put("msg", "读取商户批量开户文件过程中发生异常：" + e.getMessage());
            logger.info("manager result " + result);
            return result;
        }
    }
}
