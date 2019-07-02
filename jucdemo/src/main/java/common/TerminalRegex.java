package common;

import java.text.SimpleDateFormat;

public class TerminalRegex {
  public static String terminalID="[a-zA-Z0-9]{8}";
  public static String status="[a-zA-Z]{1}";
  public static String address=".{0,30}";
  public static String device=".{0,30}";
  public static String type="[0-9]{0,4}";
  public static String master=".{0,8}";
  public static String groupId="[0-9]{0,4}";
  public static String district="[0-9]{1,6}";
  public static String fwdMccode="[0-9]{0,18}";
  public static String machId="[0-9A-Za-z]{0,16}";
  public static String businessCode="[0-9A-Za-z]{1,20}";
  public static String loginTime="[1-9]|1\\d|2[0-4]";
  public static String location=".{0,16}";
  public static String termVer="[0-9A-Za-z_]{0,12}";
  public static String capkVersion="[0-9]{0,11}";
  public static String termIcVersion="[0-9]{0,11}";
  public static String dzpzFlag="[0-9]{1}";
  public static String termduty="[0-9]{1}";
  
  public static String agentType="[0-9]{1,4}";
  public static String elem1ConfirmTime="[0-9]{1}";
  public static String elem1Type="[A-Za-z]{1}";  
  public static String systemType="[A-Za-z]{2,20}"; 
  public static String elem1Len1="[0-9]{1,4}";
  public static String mercID="[0-9]{6}";
  public static String mccode="[0-9]{18}";
  public static String fwdinsid="[a-zA-Z0-9]{10,11}";
  public static String epos="0(31|65|32|66|35|36|68|38|67|5[0-9])[0-9a-zA-Z]{0,6}";
  
  public static String interType="[0-9]{1,2}";
  public static String amount="[0-9]{1,11}";
  public static String ONEORZERO="[0,1]{1}";
  
  public static String IDCard="^\\d{15}$|^\\d{17}[0-9Xx]$" ;
  public static  String CN_NMAE="^[\u4e00-\u9fa5]{1,6}$"; 
  
  public static  String merCode="\\d{6}|\\d{18}"; 
  public static  String IDType="01|02|03|04|05|06|07|99"; 
  
  public static String date="[0-9]{4}-[0-9]{2}-[0-9]{2}";
  
  public static String shuntFlag="[J,K]{1}";
  public static String smsFlag="[0,1]{1}";
  
  
  public static String intType="^[\u4e00-\u9fa50-9a-zA-Z/]{1,60}$";
  public static String intMan="^[\u4e00-\u9fa50-9a-zA-Z]{1,30}$";
  public static String inDate="^(?:19|20\\d{2})(?:0[1-9]|1[0-2])$";
  public static String inCnt="^[0-9]+([.]{1}[0-9]+){0,1}$";

  //普通格式：
  public static String termId="^[0-9A-Za-z]{8}$";
  public static String termId2="^[A-z0-9]{8}$";
  public static String merCodeExt="^\\d{18}||0$";
  public static String merCodeExt2="^[A-Za-z0-9]{18}$";
  public static String department="^[0-9]{1,2}$";
  public static String merchine="^[A-Za-z0-9-/]+$";
  public static String mer="^[\u4e00-\u9fa50-9a-zA-Z]{1,30}$"; 
  public static String name="^[\u4e00-\u9fa5]{1,10}$";
  public static String manager="^[\u4e00-\u9fa5]{1,4}$";
  public static String phone="[0-9]{8,11}";
  public static String area="^[\u4e00-\u9fa5]{1,5}$";
  public static String postCode="[0-9]{6}";
  public static String or="1|2|3|0";
  public static String fee="1|3|6|12";
  public static String Ifstop="0|1";
  public static String ifConnect="[Y,N]{1}";
  public static String mcc="[0-9]{4}";
  public static String zh="^\\d{19}$";
  
  //终端配件
  public static String merchineOne="^[\u4e00-\u9fa50-9a-zA-Z]{1,60}$";
  public static String pwdInputType="PP20|752|PP300|RC8100|PP60|K3306|S100R|G101";
  public static String notConnectType="R50|758B|K3306R|K3102|SP20|C650|R50M|R30|G101|S100R";
  public static String sim1Name="移动|联通|电信";
  public static String sztType="LXR-06B0|HD6350-V|KF900G9|KF900G10";//非接读头型号
  public static String apn="一类|二类";
  public static String simConnect="GPRS|3G|CDMA";
  public static String product="宏电|联通|代维|德传";
  public static String route="联通路由|电信路由";//路由类型
  public static String termCradle="TJJ|TJG";
  public static String sztBusinessName = "粤通卡充值|深圳通卡充值|万科卡充值";
  

  //校验有效日期
  public static boolean isValidDate(String s)  
  {  
      try{  
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
          dateFormat.setLenient(false);
          dateFormat.parse(s);  
          return true;  
      }catch(Exception e){  
          return false;  
      }  
  } 
  
  //校验有效日期
  public static boolean isSetupDate(String s)  
  {  
      try{  
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); 
          dateFormat.setLenient(false);
          dateFormat.parse(s);  
          return true;  
      }catch(Exception e){  
          return false;  
      }  
  } 
  
  /**
   * 通过身份证号的前17位计算出最后一位检验码
   * @param idCard
   * @return
   */
  public  static boolean validIDCard(String idCard)  {  
      try{  
    	  if(!idCard.matches(IDCard)){//IDCard="^\\d{15}$|^\\d{17}[0-9Xx]$"
    		  return false;
    	  }
    	  if(idCard.length()==15){
    		  return true;
    	  }
    	  
    	  char pszSrc[] = idCard.toCharArray(); //下面开始验证身份证的有效性
    	          int iS = 0;
    	          int iW[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
    	          char szVerCode[] = new char[] { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };
    	          int i;
    	          for (i = 0; i < 17; i++) {
    	              iS += (int) (pszSrc[i] - '0') * iW[i];
    	          }
    	          int iY = iS % 11;
                  System.out.println(szVerCode[iY]);
           if(szVerCode[iY]==pszSrc[17]){
        	   return true;
           }
           return false; 
      } catch (Exception e){  
          return false;  
      }  
  }
  
  
  /** 
   * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位 
   * @param nonCheckCodeCardId 
   * @return 
   */  
  public static boolean checkBankCardCode(String cardNO){  
      if(cardNO == null || cardNO.trim().length() == 0  
              || !cardNO.matches("\\d+")) {  
          //如果传的不是数据返回N  
          return false;  
      } 
      String nonCheckCodeCardId=cardNO.substring(0, cardNO.length() - 1);
      String checkCode= cardNO.substring(cardNO.length() - 1, cardNO.length());
      System.out.println("checkCode "+checkCode);
      char[] chs = nonCheckCodeCardId.trim().toCharArray();  
      int luhmSum = 0;  
      for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {  
          int k = chs[i] - '0';  
          if(j % 2 == 0) {  
              k *= 2;  
              k = k / 10 + k % 10;  
          }  
          luhmSum += k;             
      }  
      char code= (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');
      System.out.println("code "+code);
      if(checkCode.equals(String.valueOf(code))){
    	  return true;
      }
     return false; 
  }  
  
}
