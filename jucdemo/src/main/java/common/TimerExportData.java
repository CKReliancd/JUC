package common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ybs.web.dataserver.entity.bank.BankInfo;
import com.ybs.web.dataserver.entity.bank.CardBin;
import com.ybs.web.dataserver.entity.devops.Certificate;
import com.ybs.web.dataserver.entity.terminal.TerminalMer;
import com.ybs.web.dataserver.server.bank.BankInfoService;
import com.ybs.web.dataserver.server.bank.CardBinService;
import com.ybs.web.dataserver.server.devops.CertificateService;
import com.ybs.web.dataserver.server.terminal.TerminalMerService;

@Service
public class TimerExportData {
	
	/*@Autowired
	private IpsTerminalService terminalService ;*/
	
	@Autowired
	private BankInfoService bankInfoService;
	
	@Autowired
	private CardBinService cbService;
	
	@Autowired
	private TerminalMerService merService;
	
	@Autowired
	private CertificateService certificateService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 导出缴费终端  生成TXT文件
	 * 表名：TERMINAL
	 */
 /*  public void exportTerminal(){
	   Condition condition=new Condition();
	   String filepath ="D:\\logs\\web\\TERMINAL.txt";
		File file = new File(filepath);			 
		List<IpsTerminal>list=null;
		BufferedWriter output=null;
		if (file.exists()) {
			file.delete();
		}
		logger.info("export TERMINAL begin");
		try {
			file.createNewFile();
			 output = new BufferedWriter(new FileWriter(file,true));
			 int begin=0; 
			 int size=10; 
			 condition.add("size", size);
			 int total=terminalService.getTotalCount(condition);
			 int num=(total-1)/size+1;
			for(int j=1;j<=num;j++){
				 begin=(j-1)*size;
				 logger.info("begin  "+begin);
				 condition.add("begin", begin);
				 list=terminalService.findListByLimit(condition);
				for (int i=0 ;i<list.size();i++ ){
					output.write(String.valueOf(list.get(i).getTerminalId()+",  "+list.get(i).getMaster()+",   "+list.get(i).getLocation()) + "\r\n");
				}	
			}
				 
			output.close();
			logger.info("export TERMINAL end");
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
   }*/
   
   
	/**
	 * 导出消费终端  生成TXT文件
	 * 表名：TERMINAL_MER
	 */
  public void exportTerminalMer(){
	   String filepath ="D:\\logs\\web\\TERMINAL_MER.txt";
		File file = new File(filepath);			 
		List<TerminalMer>list=null;
		BufferedWriter output=null;
		if (file.exists()) {
			file.delete();
		}
		logger.info("export TERMINAL begin");
		try {
			file.createNewFile();
			 output = new BufferedWriter(new FileWriter(file,true));
				 list=merService.getAll();
				for (TerminalMer terminal:list){
					output.write(String.valueOf(terminal.getTerminalId()+"|"+terminal.getStatus()+"|"+terminal.getAddress()+"|"+terminal.getDevice()+"|"+terminal.getType()+"|"+terminal.getMaster()+"|"
							+ ""+terminal.getGroupId()+"|"+terminal.getDistrict()+"|"+terminal.getFwdMccode()+"|"+terminal.getMachId()+"|"+terminal.getLocation()+"|"+terminal.getTermVer()+"|"
							+ ""+terminal.getCapkVersion()+"|"+terminal.getTermIcVersion()+"|"+terminal.getInsertDateTime()+"|"+terminal.getDzpzFlag()+"|"+terminal.getTermDuty()) + "\r\n");
				}	
			
				 
			output.close();
			logger.info("export TERMINAL end");
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
  }
   
   
   
	/**
	 * 导出卡bin数据 生成TXT文件
	 * 表名：PPBANKDS
	 */
  public void exportBank(){
	   String filepath ="D:\\logs\\web\\PPBANKDS.txt";
		File file = new File(filepath);			 
		List<BankInfo>list=null;
		BufferedWriter output=null;
		if (file.exists()) {
			file.delete();
		}
		logger.info("export PPBANKDS begin");
		try {
			file.createNewFile();
			 output = new BufferedWriter(new FileWriter(file,true));
				list=bankInfoService.findAllData();
				for (BankInfo bank:list ){
					output.write(String.valueOf(bank.getBsbnCode()+","+bank.getBsbnDesc()+","+bank.getBsbnName()+","+bank.getBsbnDate()+","+bank.getBsbnAddr()+","+bank.getBsPeople()+","
				    +bank.getBsTelpne()+","+bank.getBsPrintfg()+","+bank.getBsInsID()+","+bank.getBsCpbCode()+","+bank.getGdsBankCode()+","+bank.getPassCode()) + "\r\n");
				}	
			output.close();
			logger.info("export PPBANKDS end");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
  }
   
  
	/**
	 * 导出卡bin数据 生成TXT文件
	 * 表名：RALT
	 */
public void exportCardBin(){
	   String filepath ="D:\\logs\\web\\RALT.txt";
		File file = new File(filepath);			 
		List<CardBin> list=null;
		BufferedWriter output=null;
		if (file.exists()) {
			file.delete();
		}
		logger.info("export RALT begin");
		try {
			file.createNewFile();
			 output = new BufferedWriter(new FileWriter(file,true));
				list=cbService.findAllData();
				for (CardBin card:list ){
					output.write(String.valueOf(card.getAltBank()+","+card.getBankNo()+","+card.getCardType()+","+card.getIfTrk3()+","+card.getCardNo()+","+card.getLen()+","
				    +card.getBegin()+","+card.getInTypeStatus()+","+card.getIssuingInstitution()+","+card.getIssuingObject()+","+card.getCardLevel()+","+card.getPartner()+","+card.getCurrency()+","
				    + ""+card.getMedium()+","+card.getFunctions()+","+card.getSpecialFuncs()+","+card.getSouthcard()) + "\r\n");
				}	
			output.close();
			logger.info("export RALT end");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
}
 
public void checkCertificate(){//curl http://ybs.dk.com:8000/ips/sms/send?modulename=ybsapp&phonenumber=18938951111&message=短信内容[深银联易办事]
	logger.info("checkCertificate begin");
	 InputStream is = this.getClass().getClassLoader().getResourceAsStream("datasource.properties");
	Properties prop = new Properties();
	try {
		prop.load(is);
		String url=prop.getProperty("message_url");          //"http://ybs.dk.com:8000/ips/sms/send";
		String json=null;
		List<Certificate>cerList=  certificateService.warmList();
		if(cerList.size()>0){
			for(Certificate c:cerList){
				StringBuilder sb=new StringBuilder("modulename=ybsapp&");
				sb.append("phonenumber=").append(c.getLinkmanPhone()).append("&");
				sb.append("message=您的证书(域名:");
				sb.append(c.getDomainName());
				sb.append(")即将到期[深银联易办事]");
				json=HttpClient.sendPost(url, sb.toString());
				//JSONObject result= JSONObject.fromObject(json) ;
				logger.info("checkCertificate result"+json);
			}
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
}


}
