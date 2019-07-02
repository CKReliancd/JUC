package common;

import java.text.SimpleDateFormat;

public class IpsRegex {
  public static String terminalID="[a-zA-Z0-9]{8}";
  public static String MID="[a-zA-Z0-9]{0,18}";
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
  public static String TAXIMCCODE="[0-9]{0,10}";
  public static String ACCTYPE="[0-9]{0,10}";
  
  public static String IDCard="^\\d{15}$|^\\d{17}[0-9Xx]$" ;
  public static String CN_NMAE="^[\u4e00-\u9fa5]{1,6}$"; 
  public static String ISAUTH="[Y,N]{1}";
  
  public static  String merCode="\\d{6}|\\d{18}"; 
  public static  String IDType="01|02|03|04|05|06|07|99"; 
  public static  String phone="[0-9]{11}";
  public static  String bankId="^\\s*\\w{0,12}\\s*$";
  public static  String licNo = "^\\s*\\w{0,70}\\s*$";
  
  public static String miacctn="^\\d{0,30}$";
  
  public static String date="[0-9]{4}-[0-9]{2}-[0-9]{2}";
  
  public static String shuntFlag="[J,K]{1}";
  public static String smsFlag="[0,1]{1}";
  public static String fundsTransferFlag="[B,F,M]{1}";
  
  
  public static String COMPANYNO="[0-9]{4}";
  public static String INSID="[0-9]{1,4}";
  public static String INSID_DRIVER="[0-9]{1,11}";
  public static String COMPANY_NMAE="^[\u4e00-\u9fa5]{2,20}$";				   
  
  public  static boolean isValidDate(String s)  
  {  
      try  
      {  
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
          dateFormat.setLenient(false);
          dateFormat.parse(s);  
          return true;  
      }  
      catch (Exception e)  
      {  
          // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对  
    	 // e.printStackTrace();
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
  
  
  public static void main (String[]args){//(\\d{15})|(\\d{18})|(\\d{17}(\\d|X|x))
	  String str="6230582000049750410";//[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]
	            //6227007200120897790
	   String id="张华雄";
	   if(id.matches(CN_NMAE)){
		   System.out.println("true");
	   }
	 /* boolean flag= checkBankCardCode(str);
	 System.out.println(flag);*/
  }
}
