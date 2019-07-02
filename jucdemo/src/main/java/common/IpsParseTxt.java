package common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ybs.web.constant.StringConst;
import com.ybs.web.dataserver.entity.BatchUpdateEntity;
import com.ybs.web.dataserver.entity.cds.PmBsRegister;
import com.ybs.web.dataserver.entity.cds.PosLogin;
import com.ybs.web.dataserver.entity.ips.IpsTerminal;
import com.ybs.web.dataserver.entity.ips.ScanCodeMercRouter;
import com.ybs.web.dataserver.entity.merchant.ExtMerchant;
import com.ybs.web.dataserver.entity.merchant.MerchantinfoAgent;
import com.ybs.web.dataserver.entity.riskbigdata.BlackDetail;
import com.ybs.web.dataserver.entity.riskbigdata.BlackDetailAudit;
import com.ybs.web.dataserver.entity.riskbigdata.BlackMainPending;
import com.ybs.web.dataserver.entity.riskbigdata.BlackMerchant;
import com.ybs.web.dataserver.entity.terminal.MenuFunction;
import com.ybs.web.dataserver.entity.terminal.MenuScript;
import com.ybs.web.dataserver.entity.terminal.TerminalMenuTree;
import com.ybs.web.dataserver.entity.vfwd.BizcodeConfig;
import com.ybs.web.dataserver.server.cds.PmBsRegisterService;
import com.ybs.web.dataserver.server.ips.IpsTerminalService;
import com.ybs.web.dataserver.server.ips.MercRouterService;
import com.ybs.web.dataserver.server.merchant.ExtMerchantService;
import com.ybs.web.dataserver.server.riskbigdata.BlackMerchantMerService;
import com.ybs.web.dataserver.server.riskbigdata.BlackMerchantService;
import com.ybs.web.dataserver.server.terminal.MenuFunctionService;
import com.ybs.web.dataserver.server.terminal.MenuScriptService;
import com.ybs.web.dataserver.server.vfwd.BizcodeConfigService;

@Service
public class IpsParseTxt {
	/**
	 * 批量导入终端信息 解析txt文件获取终端信息的集合
	 * 
	 * @param list
	 */
	@Autowired
	private IpsTerminalService terminalService;

	@Autowired
	private MenuFunctionService functionService;;

	@Autowired
	private MenuScriptService scriptService;

	@Autowired
	private ExtMerchantService extMerService;

	// BlackMerchantService
	@Autowired
	private BlackMerchantService blackMerchantService;

	@Autowired
	private BlackMerchantMerService merchantMerService;

	@Autowired
	private MercRouterService mercRouterService;

	@Autowired
	private BizcodeConfigService bizcodeConfigService;

	@Autowired
	private PmBsRegisterService registerService;

	public Map<String, Object> readTxtGetTerminal(InputStream is) {
		List<IpsTerminal> list = new ArrayList<IpsTerminal>();
		String lineTxt = null;
		Map<String, Object> result = new HashMap<String, Object>();
		BufferedReader bufferedReader = null;
		List<String> terNo = terminalService.findIDList();
		try {
			InputStreamReader read = new InputStreamReader(is, "GBK");
			bufferedReader = new BufferedReader(read);
			int i = 0;
			result.put("code", StringConst.FLAG_TRUE); // 默认解析状态为成功
			boolean status = true;
			StringBuilder sb = new StringBuilder();
			while ((lineTxt = bufferedReader.readLine()) != null) {
				i++;
				IpsTerminal terminal = new IpsTerminal();
				lineTxt = lineTxt.trim();
				if (lineTxt.contains("|")) {
					String[] arr = lineTxt.split("\\|"); // lineTxt.split(",");ManagerTxt.trimArr
					if (this.validateTerminalNo(terNo, arr[0])) {
						terNo.add(arr[0]);
					} else {
						sb.append(arr[0] + StringConst.TERMIALNOEXIST + ",");
					}

					if (arr[0].startsWith("0000000")) {
						String str = "文件数据第" + i + "行，终端号为[" + arr[0] + "]的数据不允许新增！";
						;
						if (i > 1) {
							str = "^" + str;
						}
						sb.append(str);
					}

					if (arr[0].matches(IpsRegex.terminalID)) {
						terminal.setTerminalId(arr[0]);
					} else {
						status = false;
					}

					if (arr[1].matches(IpsRegex.status)) {
						terminal.setStatus(arr[1]);
					} else {
						status = false;

					}
					if (arr[2].matches(IpsRegex.address)) {
						terminal.setAddress(arr[2]);
					} else {
						status = false;

					}

					if (arr[3].matches(IpsRegex.device)) {
						terminal.setDevice(arr[3]);
					} else {
						status = false;

					}

					if (arr[4].matches(IpsRegex.type)) {
						terminal.setType(arr[4]);
					} else {
						status = false;

					}

					if (arr[5].matches(IpsRegex.district)) {
						terminal.setDistrict(arr[5]);
					} else {
						status = false;

					}

					if (arr[6].matches(IpsRegex.fwdMccode)) {
						terminal.setFwdMccode(arr[6]);
					} else {
						status = false;

					}

					if (arr[7].matches(IpsRegex.master)) {
						terminal.setMaster(arr[7]);
					} else {
						status = false;

					}

					if (arr[8].matches(IpsRegex.groupId)) {
						terminal.setGroupId(arr[8]);
					} else {
						status = false;

					}

					if (arr[9].matches(IpsRegex.termduty)) {
						terminal.setTermduty(arr[9]);
					} else {
						status = false;

					}

					if (arr[10].matches(IpsRegex.dzpzFlag)) {
						terminal.setDzpzFlag(arr[10]);
					} else {
						status = false;

					}

					if (!status) {
						sb.append(StringConst.IMPORT_RULE + i + ",");
					}
					terminal.setInsertdatetime(DateUtils.currentDay());
					list.add(terminal);
				} else {
					result.put("code", StringConst.FLAG_FALSE);
					result.put("msg", StringConst.IMPORT_SPLIT);
					return result;
				}

			}

			if (sb.length() > 0) {
				result.put("code", StringConst.FLAG_FALSE);
				result.put("msg", sb);
			}

			bufferedReader.close();
			read.close();
			result.put("list", list);
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("读取文件内容出错");
			e.printStackTrace();
			return result;
		}

	}

	public boolean validateTerminalNo(List<String> list, String terNo) {
		for (String no : list) {
			if (no.equals(terNo)) { // 如果司机号已经存在 则校验不通过
				return false;
			}
		}
		return true;
	}

	public static List<String> getDriverNoByFile(InputStream is) {
		List<String> list = new ArrayList<String>();
		InputStreamReader read = new InputStreamReader(is);// 考虑到编码格式
		BufferedReader bufferedReader = new BufferedReader(read);
		String lineTxt = null;
		try {
			while ((lineTxt = bufferedReader.readLine()) != null) {
				lineTxt = lineTxt.trim();
				if (StringUtils.isNoneBlank(lineTxt)) {
					list.add(lineTxt);
				}

			}
			read.close();
			return list;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return list;
		}
	}

	/**
	 * 解析文件过程中主要有一行的数据出现格式校验不通过便拒绝整个文件的导入 分行解析过程中只要有字段校验不通过就记录行号
	 * 
	 * @param is
	 * @return
	 */
	public Map<String, Object> readTxtGetMenuFunction(InputStream is) {
		List<MenuFunction> list = new ArrayList<MenuFunction>();
		String lineTxt = null;
		Map<String, Object> result = new HashMap<String, Object>();
		BufferedReader bufferedReader = null;
		List<MenuFunction> errorList = new ArrayList<MenuFunction>();
		try {
			InputStreamReader read = new InputStreamReader(is, "GBK");
			bufferedReader = new BufferedReader(read);
			// int i=0;
			result.put("code", StringConst.FLAG_TRUE); // 默认解析状态为成功
			// boolean fileStatus=true;
			StringBuilder sb = new StringBuilder();
			List<String> agentList = functionService.getAgentTypeList();
			while ((lineTxt = bufferedReader.readLine()) != null) {
				// i++;
				/*
				 * boolean status=true; boolean agent=true;
				 */
				MenuFunction function = new MenuFunction();
				StringBuilder msg = new StringBuilder();
				lineTxt = lineTxt.trim();
				if (lineTxt.contains("|")) {
					String[] arr = lineTxt.split("\\|"); // lineTxt.split(",");ManagerTxt.trimArr

					if (IpsParseTxt.validateString(agentList, arr[0])) {
						msg.append(StringConst.DATAEXIST + " ");

					}

					if (arr[0].matches(IpsRegex.agentType)) {
						function.setAgentType(arr[0]);
					} else {
						msg.append(StringConst.AGENT_TYPE + " ");
					}

					if (StringUtils.isNotBlank(arr[1])) {
						function.setMenuName(arr[1]);
					} else {
						msg.append(StringConst.MENU_NAME + " ");
					}
					if (StringUtils.isNotBlank(arr[2])) {// ELEM1_NAME
						function.setElem1Name(arr[2]);
					} else {
						msg.append(StringConst.ELEM1_NAME + " ");

					}

					if (arr[3].matches(IpsRegex.elem1ConfirmTime)) {
						function.setElem1ConfirmTime(arr[3]);
					} else {
						msg.append(StringConst.ELEM1_TIME + " ");

					}

					if (arr[4].matches(IpsRegex.elem1Type)) {
						function.setElem1Type(arr[4]);
					} else {

						msg.append(StringConst.ELEM1_TYPE + " ");
					}

					if (arr[5].matches(IpsRegex.elem1Len1)) {
						function.setElem1Len1(arr[5]);
					} else {
						msg.append(StringConst.ELEM1_LEN1 + " ");

					}

					if (arr[6].matches(IpsRegex.elem1Len1)) {
						function.setElem1Len2(arr[6]);
					} else {
						msg.append(StringConst.ELEM1_LEN2 + " ");

					}
					function.setElem2Name(arr[7]);
					/*
					 * if(StringUtils.isNotBlank(arr[7])){
					 * function.setElem2Name(arr[7]); }else{ status=false;
					 * 
					 * }
					 */

					if (arr[8].matches(IpsRegex.elem1ConfirmTime)) {
						function.setElem2ConfirmTime(arr[8]);
					} else {
						msg.append(StringConst.ELEM1_TIME + " ");

					}

					if (arr[9].matches(IpsRegex.elem1Type)) {
						function.setElem2Type(arr[9]);
					} else {
						msg.append(StringConst.ELEM1_TYPE + " ");

					}

					if (arr[10].matches(IpsRegex.elem1Len1)) {
						function.setElem2Len1(arr[10]);
					} else {
						msg.append(StringConst.ELEM1_LEN1 + " ");

					}

					if (arr[11].matches(IpsRegex.elem1Len1)) {
						function.setElem2Len2(arr[11]);
					} else {
						msg.append(StringConst.ELEM1_LEN2 + " ");

					}

					/* function.setRemark(arr[12]); */
					if (msg.length() > 0) {
						function.setMsg(msg.toString());
						errorList.add(function);
					} else {
						list.add(function);
					}

				} else {
					result.put("code", StringConst.FLAG_FALSE);
					result.put("msg", StringConst.IMPORT_SPLIT);
					return result;
				}

			}

			bufferedReader.close();
			read.close();

			if (errorList.size() > 0) { // 是否存在格式不通过的 数据
				result.put("code", StringConst.FLAG_FALSE);
				result.put("errorList", errorList);
			} else {
				result.put("code", StringConst.FLAG_TRUE);
				result.put("list", list);
			}
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("读取文件内容出错");
			e.printStackTrace();
			return result;
		}

	}

	/**
	 * 校验字符串type是否在list 中存在
	 * 
	 * @param list
	 * @param type
	 * @return
	 */
	public static boolean validateString(List<String> list, String type) {
		for (String no : list) {
			if (no.equals(type)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 解析txt文件 获取终端菜单脚本配置信息集合
	 *
	 * @param is
	 * @return
	 */
	public Map<String, Object> readTxtGetMenuScript(InputStream is) {
		List<MenuScript> list = new ArrayList<MenuScript>();
		String lineTxt = null;
		Map<String, Object> result = new HashMap<String, Object>();
		BufferedReader bufferedReader = null;
		List<MenuScript> errorList = new ArrayList<MenuScript>();
		try {
			List<String> agentType = scriptService.getAgentList();
			InputStreamReader read = new InputStreamReader(is, "GBK");
			bufferedReader = new BufferedReader(read);
			int i = 0;
			result.put("code", StringConst.FLAG_TRUE); // 默认解析状态为成功
			// boolean fileStatus=true;
			StringBuilder sb = new StringBuilder();
			while ((lineTxt = bufferedReader.readLine()) != null) {
				i++;
				boolean status = true;
				boolean agent = true;
				MenuScript script = new MenuScript();
				StringBuilder msg = new StringBuilder();
				lineTxt = lineTxt.trim();
				if (lineTxt.contains("|")) {
					String[] arr = lineTxt.split("\\|"); // lineTxt.split(",");ManagerTxt.trimArr
					if (IpsParseTxt.validateString(agentType, arr[0])) {
						msg.append(StringConst.DATAEXIST + " ");
					}

					if (arr[0].matches(IpsRegex.agentType)) {
						script.setAgentType(arr[0]);
					} else {
						msg.append(StringConst.AGENT_TYPE + " ");

					}

					if (arr[1].matches(IpsRegex.mercID)) {
						script.setMercId(arr[1]);
					} else {
						msg.append(StringConst.MERC_ID + " ");

					}

					if (StringUtils.isNoneBlank(arr[2])) {
						script.setMercName(arr[2]);
					} else {
						msg.append(StringConst.MERC_NAME + " ");

					}

					if (arr[3].matches(IpsRegex.agentType)) { // 功能码为4位数字
						script.setFuncCode(arr[3]);
					} else {
						msg.append(StringConst.FUNC_CODE + " ");

					}

					if (StringUtils.isNotBlank(arr[4])) {
						script.setScript(arr[4]);
					} else {
						msg.append(StringConst.SCRIPT + " ");

					}

					if (arr[5].matches(IpsRegex.mercID)) { // 6位数字
						script.setIsState(arr[5]);
					} else {
						msg.append(StringConst.ISSTATE + " ");

					}

					if (arr[6].matches(IpsRegex.termduty)) { // 1位数字
						script.setSignFlag(arr[6]);
					} else {
						msg.append(StringConst.SIGN_FLAG + " ");

					}

					if (arr.length < 8) {
						script.setScriptContent(null);
					} else {
						script.setScriptContent(arr[6]);

					}
					if (msg.length() > 0) {
						script.setMsg(msg.toString());
						errorList.add(script);
					} else {
						list.add(script);
					}
				} else {
					result.put("code", StringConst.FLAG_FALSE);
					result.put("msg", StringConst.IMPORT_SPLIT);
					return result;
				}

			}

			bufferedReader.close();
			read.close();
			if (errorList.size() > 0) { // 是否存在格式不通过的 数据
				result.put("code", StringConst.FLAG_FALSE);
				result.put("errorList", errorList);
			} else {
				result.put("code", StringConst.FLAG_TRUE);
				result.put("list", list);
			}
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("读取文件内容出错");
			e.printStackTrace();
			result.put("code", StringConst.FLAG_FALSE);
			return result;
		}

	}

	/**
	 * 解析txt文件 获取终端菜单脚本配置信息集合
	 *
	 * @param is
	 * @return
	 */
	public Map<String, Object> readTxtGetMenuTree(InputStream is) {
		List<TerminalMenuTree> list = new ArrayList<TerminalMenuTree>();
		String lineTxt = null;
		Map<String, Object> result = new HashMap<String, Object>();
		BufferedReader bufferedReader = null;
		try {
			List<String> agentType = scriptService.getAgentList();
			InputStreamReader read = new InputStreamReader(is, "GBK");
			bufferedReader = new BufferedReader(read);
			int i = 0;
			result.put("code", StringConst.FLAG_TRUE); // 默认解析状态为成功
			// boolean fileStatus=true;
			StringBuilder sb = new StringBuilder();
			while ((lineTxt = bufferedReader.readLine()) != null) {

				TerminalMenuTree tree = new TerminalMenuTree();
				lineTxt = lineTxt.trim();
				if (lineTxt.contains(",")) {
					String[] arr = lineTxt.split(","); // lineTxt.split(",");ManagerTxt.trimArr
					tree.setType("135");
					tree.setGrp("1");
					tree.setSortNo("1");
					tree.setId(arr[0]);
					tree.setAgentType(arr[1]);
					tree.setMenuName(arr[2]);
					tree.setFather(arr[3]);

					list.add(tree);

				} else {
					result.put("code", StringConst.FLAG_FALSE);
					result.put("msg", StringConst.IMPORT_SPLIT);
					return result;
				}

			}

			if (sb.length() > 0) {
				result.put("code", StringConst.FLAG_FALSE);
				result.put("msg", StringConst.IMPORT_RULE + sb);
			}
			bufferedReader.close();
			read.close();
			result.put("list", list);
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("读取文件内容出错");
			e.printStackTrace();
			result.put("code", StringConst.FLAG_FALSE);
			return result;
		}

	}

	/**
	 * 解析txt文件 获取终端菜单脚本配置信息集合
	 *
	 * @param is
	 * @return
	 */
	public Map<String, Object> readTxtGetExtMerchant(InputStream is) {
		List<ExtMerchant> list = new ArrayList<ExtMerchant>();
		String lineTxt = null;
		List<String> mccode = null;
		Map<String, Object> result = new HashMap<String, Object>();
		BufferedReader bufferedReader = null;
		try {
			// List<String>agentType=scriptService.getAgentList();
			InputStreamReader read = new InputStreamReader(is, "GBK");
			bufferedReader = new BufferedReader(read);
			int i = 0;
			result.put("code", StringConst.FLAG_TRUE); // 默认解析状态为成功
			mccode = extMerService.getMccodeList();
			StringBuilder sb = new StringBuilder();
			while ((lineTxt = bufferedReader.readLine()) != null) {
				i++;
				boolean status = true;
				boolean codeStatus = true;
				ExtMerchant extMerchant = new ExtMerchant();
				lineTxt = lineTxt.trim();
				if (lineTxt.contains("|")) {
					String[] arr = lineTxt.split("\\|"); // lineTxt.split(",");ManagerTxt.trimArr
					if (arr[0].matches(IpsRegex.mccode)) {
						// String str=arr[0])+arr[2]
						if (IpsParseTxt.validateString(mccode, arr[0] + arr[2])) {
							codeStatus = false;
							status = false;
							// extMerchant.setExtMerCode(arr[0]);
						} else {
							extMerchant.setExtMerCode(arr[0]);
							mccode.add(arr[0] + arr[2]); // 每读取一个商户号均保存在集合中，避免文件中存在重复的商户号。
						}
					} else {
						status = false;
					}

					extMerchant.setExtMerName(arr[1]);

					if (arr[2].matches(IpsRegex.fwdinsid)) {
						extMerchant.setFwdInsCode(arr[2]);
						;
					} else {
						status = false;
					}

					if (arr[3].matches(IpsRegex.dzpzFlag)) {// 1~9 一位
						extMerchant.setValidFlag(arr[3]);
					} else {
						status = false;
					}
					if (arr[4].matches(IpsRegex.dzpzFlag)) {// 1~9 一位
						extMerchant.setSagentFlag(arr[4]);
					} else {
						status = false;
					}
					// list.add(extMerchant);
					if (!status) {
						if (!codeStatus) {
							sb.append(i + "商户号已存在,");
						} else {
							sb.append(i + ",");
						}

					} else {
						list.add(extMerchant);
					}

				} else {
					result.put("code", StringConst.FLAG_FALSE);
					result.put("msg", StringConst.IMPORT_SPLIT);
					return result;
				}

			}

			if (sb.length() > 0) { // 判断错误信息的长度，如果为0表示文件校验通过，否则需要组装错误提示信息
				result.put("code", StringConst.FLAG_FALSE);
				result.put("msg", StringConst.IMPORT_RULE + sb);
			}
			bufferedReader.close();
			read.close();
			result.put("list", list);
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("读取文件内容出错");
			e.printStackTrace();
			result.put("code", StringConst.FLAG_FALSE);
			return result;
		}

	}

	/**
	 * 解析txt文件 获取外部商户号 以及所属的机构号
	 *
	 * @param is
	 * @return
	 */
	public Map<String, Object> readTxtGetMccode(InputStream is) {
		String lineTxt = null;
		Map<String, Object> result = new HashMap<String, Object>();
		BufferedReader bufferedReader = null;
		try {
			// List<String>agentType=scriptService.getAgentList();
			InputStreamReader read = new InputStreamReader(is, "GBK");
			bufferedReader = new BufferedReader(read);
			int i = 0;
			result.put("code", StringConst.FLAG_TRUE); // 默认解析状态为成功
			// boolean fileStatus=true;
			StringBuilder sb = new StringBuilder();
			List<String> codeList = new ArrayList<String>();
			List<String> fwdList = new ArrayList<String>();
			Set<String> set = new HashSet<String>();
			while ((lineTxt = bufferedReader.readLine()) != null) {
				i++;
				boolean status = true;
				lineTxt = lineTxt.trim();
				if (lineTxt.contains("|")) {
					String[] arr = lineTxt.split("\\|"); // lineTxt.split(",");ManagerTxt.trimArr
					if (arr[0].matches(IpsRegex.mccode)) {
						codeList.add(arr[0]);
					} else {
						status = false;
					}

					if (arr[1].matches(IpsRegex.fwdinsid)) {
						set.add(arr[1]); // 由于机构号可能存在大量重复的可能，所以使用set保存，避免打通相同的机构号用于查询，使sql出现超长
					} else {
						status = false;
					}
					if (!status) {
						sb.append(i + ",");
					}

				} else {
					result.put("code", StringConst.FLAG_FALSE);
					result.put("msg", StringConst.IMPORT_SPLIT);
					return result;
				}

			}
			if (sb.length() > 0) {
				result.put("code", StringConst.FLAG_FALSE);
				result.put("msg", StringConst.IMPORT_RULE + sb);
				return result;
			}
			bufferedReader.close();
			read.close();
			Iterator<String> iterator = set.iterator(); // 遍历hashset
														// 把数据取出来，保存到list中，方便查询的时候
														// mybaties自动遍历
			while (iterator.hasNext()) {
				fwdList.add(iterator.next());
			}

			result.put("codeList", codeList);
			result.put("fwdList", fwdList);
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("读取文件内容出错");
			e.printStackTrace();
			result.put("code", StringConst.FLAG_FALSE);
			return result;
		}

	}

	/**
	 * 解析txt文件 获取外部商户号 用于批量查询
	 *
	 * @param is
	 * @return
	 */
	public Map<String, Object> parseTxtGetExt(InputStream is) {
		String lineTxt = null;
		Map<String, Object> result = new HashMap<String, Object>();
		BufferedReader bufferedReader = null;
		try {
			InputStreamReader read = new InputStreamReader(is, "GBK");
			bufferedReader = new BufferedReader(read);
			int i = 0;
			result.put("code", StringConst.FLAG_TRUE); // 默认解析状态为成功
			// boolean fileStatus=true;
			StringBuilder sb = new StringBuilder();
			List<String> codeList = new ArrayList<String>();
			Set<String> set = new HashSet<String>();
			while ((lineTxt = bufferedReader.readLine()) != null) {
				i++;
				boolean status = true;
				lineTxt = lineTxt.trim();
				if (lineTxt.matches(IpsRegex.mccode)) {
					set.add(lineTxt); // 由于机构号可能存在大量重复的可能，所以使用set保存，避免出现相同的机构号用于查询，使sql出现超长
				} else {
					status = false;
				}
				if (!status) {
					sb.append(i + ",");
				}
			}
			if (sb.length() > 0) {
				result.put("code", StringConst.FLAG_FALSE);
				result.put("msg", StringConst.IMPORT_RULE + sb);
				return result;
			}
			bufferedReader.close();
			read.close();
			Iterator<String> iterator = set.iterator(); // 遍历hashset
														// 把数据取出来，保存到list中，方便查询的时候
														// mybaties自动遍历
			while (iterator.hasNext()) {
				codeList.add(iterator.next());
			}
			result.put("codeList", codeList);
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("读取文件内容出错");
			e.printStackTrace();
			result.put("code", StringConst.FLAG_FALSE);
			return result;
		}

	}

	/**
	 * 解析txt文件 获取外部商户号 用于批量查询
	 *
	 * @param is
	 * @return
	 */
	public Map<String, Object> parseTxtGetBlackMain(InputStream is) {
		String lineTxt = null;
		Map<String, Object> result = new HashMap<String, Object>();
		BufferedReader bufferedReader = null;
		try {
			InputStreamReader read = new InputStreamReader(is, "GBK");
			bufferedReader = new BufferedReader(read);
			// int i=0;
			result.put("code", StringConst.FLAG_TRUE); // 默认解析状态为成功
			// boolean fileStatus=true;
			// StringBuilder sb=new StringBuilder();
			List<BlackMainPending> mainList = new ArrayList<BlackMainPending>();
			while ((lineTxt = bufferedReader.readLine()) != null) {
				// i++;
				// boolean status=true;
				lineTxt = lineTxt.trim();
				if (StringUtils.isNotBlank(lineTxt)) {
					BlackMainPending black = new BlackMainPending();
					black.setUserName(lineTxt);
					mainList.add(black);
				}

			}
			/*
			 * if(sb.length()>0){ result.put("code", StringConst.FLAG_FALSE);
			 * result.put("msg", StringConst.IMPORT_RULE+sb); return result; }
			 */
			bufferedReader.close();
			read.close();

			result.put("list", mainList);
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("读取文件内容出错");
			e.printStackTrace();
			result.put("code", StringConst.FLAG_FALSE);
			return result;
		}

	}

	/**
	 * 解析txt文件 获取黑名单人信息 用于批量新增
	 *
	 * @param is
	 * @return
	 */
	public Map<String, Object> readTxtGetblackDetail(InputStream is) {
		String lineTxt = null;
		Map<String, Object> result = new HashMap<String, Object>();
		BufferedReader bufferedReader = null;
		try {
			// List<String>agentType=scriptService.getAgentList();
			InputStreamReader read = new InputStreamReader(is, "GBK");
			bufferedReader = new BufferedReader(read);
			int i = 0;
			result.put("code", StringConst.FLAG_TRUE); // 默认解析状态为成功
			// boolean fileStatus=true;
			StringBuilder sb = new StringBuilder();
			List<BlackDetail> detailList = new ArrayList<BlackDetail>();
			while ((lineTxt = bufferedReader.readLine()) != null) {
				i++;
				boolean status = true;
				lineTxt = lineTxt.trim();
				if (lineTxt.contains("#")) {
					String[] arr = lineTxt.split("#");
					BlackDetail black = new BlackDetail();
					if (StringUtils.isNotBlank(arr[0])) {
						black.setUserName(arr[0]);
					} else {
						status = false;
					}

					if (StringUtils.isNotBlank(arr[1])) {
						black.setNameUsedBefore(arr[1]);
					} else {
						status = false;
					}
					if (arr[2].matches(IpsRegex.IDCard)) {
						black.setIdentifyCard(arr[2]);
						black.setBirthday(StringUtils.getBirthDay(arr[2]));
					}

					black.setPassport(arr[3]);
					black.setGender(arr[4]);
					black.setFamilyGroup(arr[5]);
					black.setNational(arr[6]);
					black.setAllegedCrimes(arr[7]);
					black.setHandleType(arr[8]);
					black.setStatus(arr[9]);

					if (!status) {
						sb.append(i + ",");
					} else {
						detailList.add(black);

					}

				} else {
					result.put("code", StringConst.FLAG_FALSE);
					result.put("msg", StringConst.IMPORT_SPLIT);
					return result;
				}

			}
			if (sb.length() > 0) {
				result.put("code", StringConst.FLAG_FALSE);
				result.put("msg", StringConst.IMPORT_RULE + sb);
				return result;
			}
			bufferedReader.close();
			read.close();
			result.put("list", detailList);
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("读取文件内容出错");
			e.printStackTrace();
			result.put("code", StringConst.FLAG_FALSE);
			return result;
		}

	}

	/**
	 * 解析txt文件 获取黑名单商户名称 用于批量查询
	 *
	 * @param is
	 * @return
	 */
	public Map<String, Object> readTxtGetMerchant(InputStream is) {
		String lineTxt = null;
		Map<String, Object> result = new HashMap<String, Object>();
		BufferedReader bufferedReader = null;
		try {
			List<String> merName = blackMerchantService.findMerNameList();
			InputStreamReader read = new InputStreamReader(is, "GBK");
			bufferedReader = new BufferedReader(read);
			int i = 0;
			result.put("code", StringConst.FLAG_TRUE); // 默认解析状态为成功
			boolean exist = false;
			StringBuilder sb = new StringBuilder();
			List<BlackMerchant> merList = new ArrayList<BlackMerchant>();
			while ((lineTxt = bufferedReader.readLine()) != null) {
				i++;
				boolean status = true;
				lineTxt = lineTxt.trim();
				if (lineTxt.contains("|")) {
					String[] arr = lineTxt.split("\\|");
					BlackMerchant merchant = new BlackMerchant();
					if (StringUtils.isNotBlank(arr[0])) {
						if (IpsParseTxt.validateString(merName, arr[0])) {
							exist = true;
							status = false;
						} else {
							merchant.setMername(arr[0]);
						}
					} else {
						status = false;
					}

					if (StringUtils.isNotBlank(arr[1])) {
						merchant.setListType(arr[1]);
					} else {
						status = false;
					}

					if (StringUtils.isNotBlank(arr[2])) {
						merchant.setStatus(arr[2]);
					} else {
						status = false;
					}

					if (StringUtils.isNotBlank(arr[3])) {
						merchant.setComeFrom(arr[3]);
					} else {
						status = false;
					}
					merchant.setRemark(arr[4]);

					if (!status) {
						if (exist) {
							sb.append(i).append("该商户名称已存在");
						} else {
							sb.append(i + ",");
						}

					} else {
						merchant.setCheckStatus(StringConst.TYPE_ZERO);
						// merchant.setCreator(creator);
						merList.add(merchant);

					}

				} else {
					result.put("code", StringConst.FLAG_FALSE);
					result.put("msg", StringConst.IMPORT_SPLIT);
					return result;
				}

			}
			if (sb.length() > 0) {
				result.put("code", StringConst.FLAG_FALSE);
				result.put("msg", StringConst.IMPORT_RULE + sb);
				return result;
			}
			bufferedReader.close();
			read.close();
			result.put("list", merList);
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("读取文件内容出错");
			e.printStackTrace();
			result.put("code", StringConst.FLAG_FALSE);
			return result;
		}

	}

	/**
	 * 解析txt文件 获取黑名单商户名称 用于批量查询
	 *
	 * @param is
	 * @return
	 */
	public Map<String, Object> readTxtGetMerCode(InputStream is, String user) {
		String lineTxt = null;
		Map<String, Object> result = new HashMap<String, Object>();
		BufferedReader bufferedReader = null;
		try {
			List<String> merCode = merchantMerService.findMerCodeList();
			InputStreamReader read = new InputStreamReader(is, "GBK");
			bufferedReader = new BufferedReader(read);
			int i = 0;
			result.put("code", StringConst.FLAG_TRUE); // 默认解析状态为成功
			boolean exist = false;
			StringBuilder sb = new StringBuilder();
			List<BlackMerchant> merList = new ArrayList<BlackMerchant>();
			while ((lineTxt = bufferedReader.readLine()) != null) {
				i++;
				boolean status = true;
				lineTxt = lineTxt.trim();
				if (lineTxt.contains("|")) {
					String[] arr = lineTxt.split("\\|");
					BlackMerchant merchant = new BlackMerchant();
					merchant.setOperationType("1");
					merchant.setOperator(user);
					merchant.setCreator(user);
					if (StringUtils.isNotBlank(arr[0])) {
						if (IpsParseTxt.validateString(merCode, arr[0])) {
							exist = true;
							status = false;
						} else {
							merchant.setMercode(arr[0]);
						}
					} else {
						status = false;
					}

					if (StringUtils.isNotBlank(arr[1])) {
						merchant.setMername(arr[1]);
					} else {
						status = false;
					}

					if (StringUtils.isNotBlank(arr[2])) {
						merchant.setListType(arr[2]);
					} else {
						status = false;
					}

					if (StringUtils.isNotBlank(arr[3])) {
						merchant.setStatus(arr[3]);
					} else {
						status = false;
					}

					if (StringUtils.isNotBlank(arr[4])) {
						merchant.setComeFrom(arr[4]);
					} else {
						status = false;
					}
					merchant.setRemark(arr[5]);

					if (!status) {
						if (exist) {
							sb.append(i).append("该商户号已存在");
						} else {
							sb.append(i + ",");
						}

					} else {
						merList.add(merchant);

					}

				} else {
					result.put("code", StringConst.FLAG_FALSE);
					result.put("msg", StringConst.IMPORT_SPLIT);
					return result;
				}

			}
			if (sb.length() > 0) {
				result.put("code", StringConst.FLAG_FALSE);
				result.put("msg", StringConst.IMPORT_RULE + sb);
				return result;
			}
			bufferedReader.close();
			read.close();
			result.put("list", merList);
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("读取文件内容出错");
			e.printStackTrace();
			result.put("code", StringConst.FLAG_FALSE);
			return result;
		}

	}

	/**
	 * 解析txt文件 获取黑名单商户名称 用于批量查询
	 *
	 * @param is
	 * @return
	 */
	public Map<String, Object> readTxtGetScanCodeMercRouter(InputStream is) {
		String lineTxt = null;
		Map<String, Object> result = new HashMap<String, Object>();
		BufferedReader bufferedReader = null;
		try {
			List<String> ybsCode = mercRouterService.getPrimaryKeyList();
			InputStreamReader read = new InputStreamReader(is, "GBK");
			bufferedReader = new BufferedReader(read);
			result.put("code", StringConst.FLAG_TRUE); // 默认解析状态为成功

			List<ScanCodeMercRouter> merList = new ArrayList<ScanCodeMercRouter>();
			List<ScanCodeMercRouter> errorList = new ArrayList<ScanCodeMercRouter>();
			while ((lineTxt = bufferedReader.readLine()) != null) {
				lineTxt = lineTxt.trim();
				StringBuilder msg = new StringBuilder();
				if (lineTxt.contains("|")) {
					String[] arr = lineTxt.split("\\|");
					ScanCodeMercRouter mercRouter = new ScanCodeMercRouter();
					if (arr.length < 11) {
						mercRouter.setMsg(StringConst.DRIVERINFO);
						mercRouter.setYbsMerchantID(arr[0]);
						errorList.add(mercRouter);
						continue;

					}
					if (arr[0].matches(IpsRegex.mccode)) {
						if (IpsParseTxt.validateString(ybsCode, arr[0] + arr[4] + arr[6] + arr[9] + arr[10])) {
							msg.append(StringConst.DATAEXIST);
						}
						mercRouter.setYbsMerchantID(arr[0]);

					} else {
						msg.append(StringConst.MCCODEFORMAT);
					}

					if (StringUtils.isNotBlank(arr[1])) {
						mercRouter.setExtMerName(arr[1]);
					} else {
						msg.append(StringConst.MERC_NAME);
					}

					if (arr[2].matches(IpsRegex.systemType)) {
						mercRouter.setBackEndSystem(arr[2]);

					} else {
						msg.append(StringConst.BACK_SYSTEM);
					}

					if (arr[3].matches(IpsRegex.fwdinsid)) {
						mercRouter.setForins(arr[3]);
					} else {
						msg.append(StringConst.FWDINSID);
					}

					if (arr[4].matches(IpsRegex.systemType)) {
						mercRouter.setOrgType(arr[4]);
					} else {
						msg.append(StringConst.ORG_TYPE);
					}
					if (arr[5].matches(IpsRegex.fwdMccode)) {
						mercRouter.setExtMerchantID(arr[5]);
					} else {
						msg.append(StringConst.MCCODEFORMAT);
					}

					if (arr[6].matches(IpsRegex.machId)) {
						mercRouter.setServiceType(arr[6]);
					} else {
						msg.append(StringConst.SERVICE_TYPE);
					}
					if (arr[7].matches(IpsRegex.interType)) {
						mercRouter.setInType(arr[7]);
					} else {
						msg.append(StringConst.INTER_TYPE);
					}
					mercRouter.setvBankCode(arr[8]);
					if (arr[9].matches(IpsRegex.amount)) {
						mercRouter.setMinAmount(arr[9]);
					} else {
						msg.append(StringConst.MIN_AMOUNT);
					}
					if (arr[10].matches(IpsRegex.amount)) {
						mercRouter.setMaxAmount(arr[10]);
					} else {
						msg.append(StringConst.MAX_AMOUNT);
					}
					if (msg.length() > 0) {
						mercRouter.setMsg(msg.toString());
						errorList.add(mercRouter);
					} else {
						merList.add(mercRouter);
					}

				} else {
					result.put("code", StringConst.FLAG_FALSE);
					result.put("msg", StringConst.IMPORT_SPLIT);
					return result;
				}

			}

			bufferedReader.close();
			read.close();
			if (errorList.size() > 0) { // 是否存在格式不通过的 数据
				result.put("code", StringConst.FLAG_FALSE);
				result.put("errorList", errorList);
			} else {
				result.put("code", StringConst.FLAG_TRUE);
				result.put("list", merList);
			}
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("读取文件内容出错");
			e.printStackTrace();
			result.put("code", StringConst.FLAG_FALSE);
			return result;
		}

	}

	/**
	 * 解析文件获取司机号和需要修改的字段的值 方法用于批量修改司机信息-解析文件
	 * 
	 * @param is
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static Map<String, Object> getDriverByFile(InputStream is, String column) throws UnsupportedEncodingException {
		List<BatchUpdateEntity> list = new ArrayList<BatchUpdateEntity>();
		InputStreamReader read = new InputStreamReader(is,"GBK");// 考虑到编码格式
		BufferedReader bufferedReader = new BufferedReader(read);
		Map<String, Object> result = new HashMap<String, Object>();
		String lineTxt = null;
		StringBuilder sb = new StringBuilder();
		int i = 1;
		try {
			while ((lineTxt = bufferedReader.readLine()) != null) {
				lineTxt = lineTxt.trim();
				boolean status = true;
				boolean accNoStatus = true;
				if (lineTxt.contains("#")) {
					String[] arr = lineTxt.split("#");
					BatchUpdateEntity updateEntity = new BatchUpdateEntity();
					if (StringUtils.validateDriverNoFormat(arr[0])) {
						updateEntity.setPrimaryKey(arr[0]);
						updateEntity.setColumnValue(arr[1]);
					} else {
						status = false;
					}
					if("accNo".equals(column)){
						if(IpsRegex.checkBankCardCode(arr[1])){
							updateEntity.setColumnValue(arr[1]);
						}else{
							accNoStatus = false;
						}
					}
					updateEntity.setColumnName(column);
					if (!status) {
						sb.append(i).append("该司机号格式 有误");
					} else if(!accNoStatus){
						sb.append(i).append("银行卡格式有误");
					}else {
						list.add(updateEntity);
					}

				} else {
					result.put("code", StringConst.FLAG_FALSE);
					if (StringUtils.isBlank(lineTxt)) {
						result.put("msg", StringConst.EMPTYTEXT);
						return result;
					}
					result.put("msg", StringConst.IMPORT_SPLIT);
					return result;
				}
				i++;    //每解析一行行数+1，当有错误时提示错误的行数
			}
			if (sb.length() > 0) {
				result.put("code", StringConst.FLAG_FALSE);
				result.put("msg", StringConst.IMPORT_RULE + sb);
				return result;
			}
			bufferedReader.close();
			read.close();
			result.put("code", StringConst.FLAG_TRUE);
			result.put("list", list);
			return result;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return result;
		}
	}
	
	/*public static Map<String, Object> getDriverByFile(InputStream is, String column) throws UnsupportedEncodingException {
		List<BatchUpdateEntity> list = new ArrayList<BatchUpdateEntity>();
		InputStreamReader read = new InputStreamReader(is,"GBK");// 考虑到编码格式
		BufferedReader bufferedReader = new BufferedReader(read);
		Map<String, Object> result = new HashMap<String, Object>();
		String lineTxt = null;
		StringBuilder sb = new StringBuilder();
		int i = 0;
		try {
			while ((lineTxt = bufferedReader.readLine()) != null) {
				lineTxt = lineTxt.trim();
				boolean status = true;
				if (lineTxt.contains("#")) {
					//文件格式：记录序号#司机号#修改字段值
					String[] arr = lineTxt.split("#");
					BatchUpdateEntity updateEntity = new BatchUpdateEntity();
					if (StringUtils.validateDriverNoFormat(arr[0])) {
						updateEntity.setPrimaryKey(arr[0]);
					} else {
						status = false;
					}
					if (StringUtils.validateDriverNoFormat(arr[1])) {
						updateEntity.setPrimaryKeyTwo(arr[1]);
					} else {
						status = false;
					}
					
					
					updateEntity.setColumnName(column);
					updateEntity.setColumnValue(arr[1]);
					if (!status) {
						sb.append(i).append("该司机号格式 有误");
					} else {
						list.add(updateEntity);
					}

				} else {
					result.put("code", StringConst.FLAG_FALSE);
					if (StringUtils.isBlank(lineTxt)) {
						result.put("msg", StringConst.EMPTYTEXT);
						return result;
					}
					result.put("msg", StringConst.IMPORT_SPLIT);
					return result;
				}
			}
			if (sb.length() > 0) {
				result.put("code", StringConst.FLAG_FALSE);
				result.put("msg", StringConst.IMPORT_RULE + sb);
				return result;
			}
			bufferedReader.close();
			read.close();
			result.put("code", StringConst.FLAG_TRUE);
			result.put("list", list);
			return result;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return result;
		}
	}*/
	

	/**
	 * 解析文件获取司机号和需要修改的字段的值 方法用于批量修改司机信息-解析文件
	 * 
	 * @param is
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static Map<String, Object> getMerchantinfoByFile(InputStream is, String column) throws UnsupportedEncodingException {
		List<BatchUpdateEntity> list = new ArrayList<BatchUpdateEntity>();
		InputStreamReader read = new InputStreamReader(is,"GBK");// 考虑到编码格式
		BufferedReader bufferedReader = new BufferedReader(read);
		Map<String, Object> result = new HashMap<String, Object>();
		String lineTxt = null;
		StringBuilder sb = new StringBuilder();
		int i = 0;
		try {
			while ((lineTxt = bufferedReader.readLine()) != null) {
				lineTxt = lineTxt.trim();
				boolean status = true;
				if (lineTxt.contains("#")) {
					String[] arr = lineTxt.split("#");
					BatchUpdateEntity updateEntity = new BatchUpdateEntity();
					if (arr[0].matches(IpsRegex.merCode)) {
						updateEntity.setPrimaryKey(arr[0]);
						i++;
					} else {
						status = false;
					}
					updateEntity.setColumnName(column);
					updateEntity.setColumnValue(arr[1]);
					if (!status) {
						sb.append(i).append("该商户号格式 有误");
					} else {
						list.add(updateEntity);
					}

				} else {
					result.put("code", StringConst.FLAG_FALSE);
					if (StringUtils.isBlank(lineTxt)) {
						result.put("msg", StringConst.EMPTYTEXT);
						return result;
					}
					result.put("msg", StringConst.IMPORT_SPLIT);
					return result;
				}
			}
			if (sb.length() > 0) {
				result.put("code", StringConst.FLAG_FALSE);
				result.put("msg", StringConst.IMPORT_RULE + sb);
				return result;
			}
			bufferedReader.close();
			read.close();
			result.put("code", StringConst.FLAG_TRUE);
			result.put("list", list);
			return result;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return result;
		}
	}

	public static Map<String, Object> getPosloginByFile(InputStream is) {
		List<PosLogin> list = new ArrayList<PosLogin>();
		InputStreamReader read = new InputStreamReader(is);// 考虑到编码格式
		BufferedReader bufferedReader = new BufferedReader(read);
		Map<String, Object> result = new HashMap<String, Object>();
		String lineTxt = null;
		StringBuilder sb = new StringBuilder();
		int i = 0;
		try {
			while ((lineTxt = bufferedReader.readLine()) != null) {
				lineTxt = lineTxt.trim();
				i++;
				boolean status = true;
				if (lineTxt.contains("#")) {
					String[] arr = lineTxt.split("#");
					PosLogin posLogin = new PosLogin();
					if (!arr[0].matches(IpsRegex.amount)) {
						status = false;
						sb.append("第" + i + "行转发机构号:" + arr[0] + "数据格式错误");
					}
					if (!arr[1].matches(IpsRegex.mccode)) {
						status = false;
						sb.append("第" + i + "行商户号:" + arr[1] + "数据格式错误");
					}
					if (!arr[2].matches(IpsRegex.businessCode)) {
						status = false;
						sb.append("第" + i + "行终端号:" + arr[2] + "数据格式错误");
					}
					if (!arr[3].matches(IpsRegex.loginTime)) {
						status = false;
						sb.append("第" + i + "行签到时间:" + arr[3] + "数据格式错误");
					}
					if (!arr[4].matches(IpsRegex.termduty)) {
						status = false;
						sb.append("第" + i + "行状态:" + arr[4] + "数据格式错误");
					}
					if (!status) {
						sb.append(",");
					} else {
						posLogin.setForins(arr[0]);
						posLogin.setMerchantID(arr[1]);
						posLogin.setPosID(arr[2]);
						posLogin.setLoginTime(arr[3]);
						posLogin.setStatus(arr[4]);
						list.add(posLogin);
					}

				} else {
					result.put("code", StringConst.FLAG_FALSE);
					result.put("msg", StringConst.IMPORT_SPLIT);
					return result;
				}
			}
			if (sb.length() > 0) {
				result.put("code", StringConst.FLAG_FALSE);
				result.put("msg", /* StringConst.IMPORT_RULE+ */sb);
				return result;
			}
			bufferedReader.close();
			read.close();
			result.put("code", StringConst.FLAG_TRUE);
			result.put("list", list);
			return result;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return result;
		}
	}

	/**
	 * 解析txt文件 获取业务类型码信息集合
	 *
	 * @param is
	 * @return
	 */
	public Map<String, Object> readTxtGetBizcodeConfig(InputStream is) {
		List<BizcodeConfig> list = new ArrayList<BizcodeConfig>();
		String lineTxt = null;
		Map<String, Object> result = new HashMap<String, Object>();
		BufferedReader bufferedReader = null;
		List<BizcodeConfig> errorList = new ArrayList<BizcodeConfig>();
		try {
			List<String> primary = bizcodeConfigService.getPrimaryList();
			InputStreamReader read = new InputStreamReader(is, "GBK");
			bufferedReader = new BufferedReader(read);
			// int i=0;
			result.put("code", StringConst.FLAG_TRUE); // 默认解析状态为成功
			// boolean fileStatus=true;
			// StringBuilder sb=new StringBuilder();
			while ((lineTxt = bufferedReader.readLine()) != null) {
				BizcodeConfig bizcodeConfig = new BizcodeConfig();
				StringBuilder msg = new StringBuilder();
				lineTxt = lineTxt.trim();
				if (lineTxt.contains("|")) {
					String[] arr = lineTxt.split("\\|"); // lineTxt.split(",");ManagerTxt.trimArr
					if (IpsParseTxt.validateString(primary, arr[0] + arr[1])) {
						msg.append(StringConst.DATAEXIST + " ");
					}

					if (arr[0].matches(IpsRegex.fwdinsid)) {
						bizcodeConfig.setFwdinsID(arr[0]);
					} else {
						msg.append(StringConst.FWDINSID + " ");

					}

					if (arr[1].matches(IpsRegex.mercID)) {
						bizcodeConfig.setMccode(arr[1]);
						;
					} else {
						msg.append(StringConst.MERC_ID + " ");

					}

					if (arr[2].matches(IpsRegex.businessCode)) {
						bizcodeConfig.setBusinessCode(arr[2]);
					} else {
						msg.append(StringConst.BUSINESSCODE + " ");

					}

					if (arr[3].matches(IpsRegex.termduty)) { // 功能码为4位数字
						bizcodeConfig.setStatus(arr[3]);
					} else {
						msg.append(StringConst.ISSTATE + " ");

					}
					if (arr.length >= 5) {
						bizcodeConfig.setDescription(arr[4]);
					}

					if (msg.length() > 0) {
						bizcodeConfig.setMsg(msg.toString());
						errorList.add(bizcodeConfig);
					} else {
						list.add(bizcodeConfig);
					}
				} else {
					result.put("code", StringConst.FLAG_FALSE);
					result.put("msg", StringConst.IMPORT_SPLIT);
					return result;
				}

			}

			bufferedReader.close();
			read.close();
			if (errorList.size() > 0) { // 是否存在格式不通过的 数据
				result.put("code", StringConst.FLAG_FALSE);
				result.put("errorList", errorList);
			} else {
				result.put("code", StringConst.FLAG_TRUE);
				result.put("list", list);
			}
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("读取文件内容出错");
			e.printStackTrace();
			result.put("code", StringConst.FLAG_FALSE);
			return result;
		}

	}

	/**
	 * 解析txt文件 获取业务类型码信息集合
	 *
	 * @param is
	 * @return
	 */
	public Map<String, Object> readTxtGetPmBsRegister(InputStream is) {
		List<PmBsRegister> list = new ArrayList<PmBsRegister>();
		String lineTxt = null;
		Map<String, Object> result = new HashMap<String, Object>();
		BufferedReader bufferedReader = null;
		List<PmBsRegister> errorList = new ArrayList<PmBsRegister>();
		try {
			// List<String>primary=registerService.getPrimaryList();
			List<String> primary = new ArrayList<>();
			InputStreamReader read = new InputStreamReader(is, "GBK");
			bufferedReader = new BufferedReader(read);
			int i = 0;
			result.put("code", StringConst.FLAG_TRUE); // 默认解析状态为成功
			// boolean fileStatus=true;
			String loadBatch = DateUtils.getTime();
			while ((lineTxt = bufferedReader.readLine()) != null) {
				i++;
				if (i <= 1) { // 默认不读取第一行的数据
					continue;
				}
				PmBsRegister pmBsRegister = new PmBsRegister();
				StringBuilder msg = new StringBuilder();
				StringBuilder attribute = new StringBuilder();
				lineTxt = lineTxt.trim();
				if (lineTxt.contains("#")) {
					String[] arr = lineTxt.split("#"); // lineTxt.split(",");ManagerTxt.trimArr
					if (arr.length < 8) {
						pmBsRegister.setMsg(StringConst.MISSDATA);
						if (arr.length > 1) {
							pmBsRegister.setPayID(arr[0]);
						} else {
							pmBsRegister.setPayID(StringConst.EMPTYTEXT);
						}
						errorList.add(pmBsRegister);
						continue;
					}
					if (IpsParseTxt.validateString(primary, arr[0])) {
						msg.append(StringConst.DATAEXIST + " ");
					} else {
						primary.add(arr[0]);
					}

					if (arr[0].matches(IpsRegex.mercID)) {
						pmBsRegister.setPayID(arr[0]);
						pmBsRegister.setIdx(arr[0]);
						attribute.append(arr[0]).append("+");
					} else {
						msg.append(StringConst.PAY_FLAG + " ");

					}

					if (StringUtils.isNoneBlank(arr[2])) {
						attribute.append(arr[2]).append("+");
					} else {
						msg.append(StringConst.SCHOOL_NAME + " ");

					}

					if (StringUtils.isNoneBlank(arr[1])) {
						attribute.append(arr[1]).append("+");
					} else {
						msg.append(StringConst.STUDENT_NAME + " ");

					}

					if (arr[4].matches(IpsRegex.amount)) {
						pmBsRegister.setPrice(arr[4]);
					} else {
						msg.append(StringConst.STUDENT_AMOUNT + " ");

					}

					if (StringUtils.isNoneBlank(arr[5])) {
						attribute.append(arr[5]).append("+");
					} else {
						msg.append(StringConst.STUDENT_REMARK + " ");

					}
					if (arr[6].matches(IpsRegex.TAXIMCCODE)) {
						attribute.append(arr[6]).append("+");
					} else {
						msg.append(StringConst.BEGINDATE + " ");

					}
					if (arr[7].matches(IpsRegex.TAXIMCCODE)) {
						attribute.append(arr[7]);
					} else {
						msg.append(StringConst.ENDDATE + " ");

					}

					if (msg.length() > 0) {
						pmBsRegister.setMsg(msg.toString());
						errorList.add(pmBsRegister);
					} else {
						pmBsRegister.setMccode("000496");
						pmBsRegister.setProductType("000496");
						pmBsRegister.setLoadBatch(loadBatch);
						pmBsRegister.setDescription(StringConst.ZhongGang);
						pmBsRegister.setAttribute(attribute.toString());
						list.add(pmBsRegister);
					}
				} else {
					result.put("code", StringConst.FLAG_FALSE);
					result.put("msg", StringConst.IMPORT_SPLIT);
					return result;
				}

			}
			bufferedReader.close();
			read.close();
			if (errorList.size() > 0) { // 是否存在格式不通过的 数据
				result.put("code", StringConst.FLAG_FALSE);
				result.put("errorList", errorList);
			} else {
				result.put("code", StringConst.FLAG_TRUE);
				result.put("list", list);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("读取文件内容出错");
			e.printStackTrace();
			result.put("code", StringConst.FLAG_FALSE);
			return result;
		}

	}

	/**
	 * 解析文件获取司机号和需要修改的字段的值 方法用于批量修改司机信息-解析文件
	 * 
	 * @param is
	 * @return
	 */
	public static Map<String, Object> parseTxt(InputStreamReader read) {
		// InputStreamReader read = new InputStreamReader(is);//考虑到编码格式
		BufferedReader bufferedReader = new BufferedReader(read);
		Map<String, Object> result = new HashMap<String, Object>();
		String lineTxt = null;
		StringBuilder sb = new StringBuilder();
		int i = 0;
		try {
			while ((lineTxt = bufferedReader.readLine()) != null) {
				lineTxt = lineTxt.trim();
				i++;
				if (lineTxt.contains("=")) {
					String[] arr = lineTxt.split("=");
					result.put(arr[0], arr[1]);

				} else {
					result.put("code", StringConst.FLAG_FALSE);
					result.put("msg", StringConst.IMPORT_SPLIT);
					return result;
				}
			}

			return result;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return result;
		}
	}

	public static String creatFile(Map<String, Object> param, String path, String fileName) {
		String filepath = path + fileName;
		File file = new File(filepath);

		if (file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			Iterator<Entry<String, Object>> iterator = param.entrySet().iterator();
			while (iterator.hasNext()) {
				// i++;
				Entry<String, Object> entry = iterator.next();
				System.out.println(entry.getKey() + "=" + entry.getValue());
				output.write(String.valueOf(entry.getKey() + ",  " + entry.getValue() + "\r\n"));
			}

			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filepath;
	}

	/**
	 * 解析txt文件，获取要查询的商户信息的内部商户号
	 * 
	 * @param is
	 * @return
	 */
	public static Map<String, Object> readTxtGetMimccodes(InputStream is) {
		List<String> mimccodeList = new ArrayList<>();
		Map<String, Object> result = new HashMap<String, Object>();
		BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(is, "GBK"));

			String lineTxt = null;
			while ((lineTxt = bufferedReader.readLine()) != null) {
				if (StringUtils.isNotEmpty(lineTxt)) {
					mimccodeList.add(lineTxt.trim());
				}
			}
			result.put("success", true);
			result.put("mimccodeList", mimccodeList);
		} catch (UnsupportedEncodingException e) {
			result.put("success", false);
			result.put("msg", e.getLocalizedMessage());
			e.printStackTrace();
		} catch (IOException e) {
			result.put("success", false);
			result.put("msg", e.getLocalizedMessage());
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
			String a="62284809200243955154";
			boolean checkBankCardCode = IpsRegex.checkBankCardCode(a);
			System.out.println(checkBankCardCode);
	}

}
