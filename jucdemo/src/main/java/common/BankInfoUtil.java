package common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.ybs.web.dataserver.dao.innerQuery.PpbankdsDao;

@Component
public class BankInfoUtil {
	
	private static Map<String,String> bankInfo = null;
	
	@Autowired
	private PpbankdsDao dao;
	private BankInfoUtil() {
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();  
		PpbankdsDao dao = wac.getBean(PpbankdsDao.class);
		List<Map<String,String>> bankinfoList = dao.findBankMapInfo();
		bankInfo = new HashMap<String, String>();
		for (Map<String, String> map : bankinfoList) {
			bankInfo.put(map.get("code"), map.get("name"));
		}
	}


	public static Map<String,String> getBankNameList(){
		if(bankInfo == null) {
			new BankInfoUtil();
		}
		return bankInfo;
	}
		
//			Map<String,String> map = new HashMap<String, String>();
//		
//			map.put("600403","北京银行");
//			map.put("605840","中国邮政储蓄银行");
//			map.put("607866","宁波银行");
//			map.put("627706","中国工商银行");
//			map.put("627708","中国工商银行");
//			map.put("627709","中国工商银行");
//			map.put("637897","中国农业银行");
//			map.put("637898","中国农业银行");
//			map.put("647668","中国银行总行");
//			map.put("647669","中国银行总行");
//			map.put("656702","中国建设银行" );
//			map.put("656703","中国建设银行");
//			map.put("663488","交通银行");
//			map.put("663489","交通银行");
//			map.put("665800","好易联");
//			map.put("666666","中国银联卡");
//			map.put("674410","中信银行" );
//			map.put("674411","中信银行");
//			map.put("678768","平安银行");
//			map.put("678769","平安银行");
//			map.put("680014","广发银行");
//			map.put("682012","招商银行");
//			map.put("682013","易办事");
//			map.put("682501","平安银行");
//			map.put("682502","平安银行");
//			map.put("683891","中国光大银行");
//			map.put("683892","中国光大银行");
//			map.put("684080","华夏银行");
//			map.put("685840","深圳农村商业银行");
//			map.put("685841","深圳农村商业银行");
//			map.put("687030","上海浦东发展银行");
//			map.put("687050","上海银行");
//			map.put("687400","中国民生银行");
//			map.put("690591","兴业银行总行");
//			map.put("690592","兴业银行总行");
//			map.put("960316","浙商银行");
//			map.put("960317","渤海银行");
//			map.put("960413","广州银行");
//			map.put("960423","杭州银行");
//			map.put("960425","东莞银行");
//			map.put("960448","南昌银行");
//			map.put("960479","包商银行");
//			map.put("960508","江苏银行");
//		return map;          
//	}               
}  