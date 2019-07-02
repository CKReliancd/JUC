package common;

/**
 * 商户信息字段校验
 * 
 * @author wyj
 *
 */
public class MerchantRegex {
	// 内部商户号
	public boolean checkMimccode(String mimccode) {
		if (mimccode.matches("^\\w{1,18}$")) {
			return true;
		}
		return false;
	}

	// 商户全称
	public boolean checkMimcname(String mimcname) {
		if (mimcname.length() > 0 && mimcname.length() <= 60) {
			return true;
		}
		return false;
	}

	// 商户地址
	public boolean checkMimcaddr(String mimcaddr) {
		if (mimcaddr.equals("") || mimcaddr.length() <= 100) {
			return true;
		}
		return false;
	}

	// 商户简称
	public boolean checkMicredit(String micredit) {
		if (micredit.length() > 0 && micredit.length() <= 60) {
			return true;
		}
		return false;
	}

	// 清算账户名称
	public boolean checkMisettname(String misettname) {
		if (misettname.length() > 0 && misettname.length() <= 300) {
			return true;
		}
		return false;
	}

	// 清算账户银行名称
	public boolean checkMibankno(String mibankno) {
		if (mibankno.length() > 0 && mibankno.length() <= 200) {
			return true;
		}
		return false;
	}

	// 清算账号
	public boolean checkMiacctno(String miacctno) {
		if (miacctno.matches("^\\w{1,25}$")) {
			return true;
		}
		return false;
	}

	// 清算账户银行12位代码
	public boolean checkMibankcode(String mibankcode) {
		if (mibankcode.equals("") || mibankcode.matches("^\\w{1,12}$")) {
			return true;
		}
		return false;
	}

	// 是否撤机
	public boolean checkMiacctdt(String miacctdt) {
		if (miacctdt.equals("") || miacctdt.matches("^\\d{1,2}$")) {
			return true;
		}
		return false;
	}

	// 联系电话
	public boolean checkMitelpne(String mitelpne) {
		if (mitelpne.equals("") || mitelpne.matches("^\\w{0,11}$")) {
			return true;
		}
		return false;
	}

	// 业务
	public boolean checkMionline(String mionline) {
		if (mionline.equals("") || mionline.matches("^\\w{1}$")) {
			return true;
		}
		return false;
	}

	// 业务类型
	public boolean checkMibfcode(String mibfcode) {
		if (mibfcode.equals("") || mibfcode.matches("^\\w{1,4}$")) {
			return true;
		}
		return false;
	}

	// 对帐类型
	public boolean checkMicheck(String micheck) {
		if (micheck.equals("") || micheck.matches("^\\w{1}$")) {
			return true;
		}
		return false;
	}

	// 外部代码
	public boolean checkMiextmco(String miextmco) {
		if (miextmco.equals("") || miextmco.matches("^\\w{1,18}$")) {
			return true;
		}
		return false;
	}

	// 授权标志
	public boolean checkMiflag(String miflag) {
		if (miflag.equals("") || miflag.matches("^\\w{1,16}$")) {
			return true;
		}
		return false;
	}

	// 商户单笔最高限额
	public boolean checkMicrdtamt(String micrdtamt) {
		if (micrdtamt.equals("") || micrdtamt.matches("^\\d{1,12}$")) {
			return true;
		}
		return false;
	}

	// 商户单笔最高限额
	public boolean checkMifee(String mifee) {
		if (mifee.equals("") || mifee.matches("^\\d{1,12}$")) {
			return true;
		}
		return false;
	}

	// 商户计费方式
	public boolean checkMifeeType(String mifeeType) {
		if (mifeeType.equals("") || mifeeType.matches("^\\d{1,13}$")) {
			return true;
		}
		return false;
	}

	// 脚本编号
	public boolean checkMimctype(String mimctype) {
		if (mimctype.equals("") || mimctype.matches("^\\w{1,6}$")) {
			return true;
		}
		return false;
	}

	// 是否本地
	public boolean checkMiiflocal(String miiflocal) {
		if (miiflocal.equals("") || miiflocal.matches("^\\w{0,1}$")) {
			return true;
		}
		return false;
	}

	// 运维商户开关
	public boolean checkMistatus(String mistatus) {
		if (mistatus.equals("") || mistatus.matches("^\\w{0,2}$")) {
			return true;
		}
		return false;
	}

	// 手续费公式
	public boolean checkMifeeExp(String mifeeExp) {
		if (mifeeExp.equals("") || mifeeExp.length() <= 256) {
			return true;
		}
		return false;
	}

	// 银行账单名称
	public boolean checkMimbname(String mimbname) {
		if (mimbname.length() >= 0 && mimbname.length() <= 60) {
			return true;
		}
		return false;
	}

	// 代理内部商户号
	public boolean checkMiproxyMccode(String miproxyMccode) {
		if (miproxyMccode.equals("") || miproxyMccode.matches("^\\w{1,6}$")) {
			return true;
		}
		return false;
	}

	// 商户运行时间
	public boolean checkMiopentime(String miopentime) {
		if (miopentime.equals("") || miopentime.length() <= 256) {
			return true;
		}
		return false;
	}

	// 直联/间联
	public boolean checkMccType(String mccType) {
		if (mccType.equals("") || mccType.matches("^\\w{0,30}$")) {
			return true;
		}
		return false;
	}

	// 邮件模板
	public boolean checkMailModel(String mailModel) {
		if (mailModel.equals("") || mailModel.length() <= 30) {
			return true;
		}
		return false;
	}

	// 特殊邮件规则
	public boolean checkMailRule(String mailRule) {
		if (mailRule.equals("") || mailRule.length() <= 1000) {
			return true;
		}
		return false;
	}

	// 邮件及FTP控制
	public boolean checkDistributemode(String distributemode) {
		if (distributemode.equals("") || distributemode.length() <= 8) {
			return true;
		}
		return false;
	}

	// 邮件及FTP发送地址
	public boolean checkDistributeAddress(String distributeAddress) {
		if (distributeAddress.equals("") || distributeAddress.length() <= 2048) {
			return true;
		}
		return false;
	}

	// RTGS
	public boolean checkRtgs(String rtgs) {
		if (rtgs.equals("") || rtgs.matches("^\\w{0,2}$")) {
			return true;
		}
		return false;
	}

	// 内卡收单机构代码
	public boolean checkAcq_ins_id_cd(String acq_ins_id_cd) {
		if (acq_ins_id_cd.equals("") || acq_ins_id_cd.matches("^\\w{0,20}$")) {
			return true;
		}
		return false;
	}

	// 32域受理机构代码
	public boolean checkF32_ins_id_cd(String f32_ins_id_cd) {
		if (f32_ins_id_cd.equals("") || f32_ins_id_cd.matches("^\\w{0,20}$")) {
			return true;
		}
		return false;
	}

	// 内卡结算机构代码
	public boolean checkSettle_ins_id_cd(String settle_ins_id_cd) {
		if (settle_ins_id_cd.equals("") || settle_ins_id_cd.matches("^\\w{0,20}$")) {
			return true;
		}
		return false;
	}

	// 发展人
	public boolean checkManager(String manager) {
		if (manager.equals("") || manager.length() <= 20) {
			return true;
		}
		return false;
	}

	// 终端号
	public boolean checkMiternno(String miternno) {
		if (miternno.equals("") || miternno.matches("^\\w{0,8}$")) {
			return true;
		}
		return false;
	}

	// 对账文件模板
	public boolean checkFiletype(String filetype) {
		if (filetype.equals("") || filetype.matches("^\\w{0,4}$")) {
			return true;
		}
		return false;
	}

	// 银联手续费结算周期
	public boolean checkFee_sett(String fee_sett) {
		if (fee_sett.equals("") || fee_sett.matches("^\\w{0,2}$")) {
			return true;
		}
		return false;
	}

	// 包月回佣
	public boolean checkFee_money(String fee_money) {
		if (fee_money.equals("") || fee_money.matches("^\\d{0,12}$")) {
			return true;
		}
		return false;
	}

	// 上送MCC
	public boolean checkMer_type(String mer_type) {
		if (mer_type.equals("") || mer_type.matches("^\\d{4}$")) {
			return true;
		}
		return false;
	}

	// 本银行本代本标志
	public boolean checkMibenben(String mibenben) {
		if (mibenben.equals("") || mibenben.matches("^\\d{0,4}$")) {
			return true;
		}
		return false;
	}

	// 报表生成标志位图
	public boolean checkReport_flag(String report_flag) {
		if (report_flag.equals("") || report_flag.length() <= 100) {
			return true;
		}
		return false;
	}

	// 商户清算名称
	public boolean checkMisettname_credit(String misettname_credit) {
		if (misettname_credit.length() <= 180) {
			return true;
		}
		return false;
	}

	// 清算银行名称
	public boolean checkMiacctno_credit(String miacctno_credit) {
		if (miacctno_credit.length() <= 180) {
			return true;
		}
		return false;
	}

	// 清算帐号（信）
	public boolean checkMibankno_credit(String mibankno_credit) {
		if (mibankno_credit.equals("") || mibankno_credit.matches("^\\w{0,25}$")) {
			return true;
		}
		return false;
	}

	// 清算银行行号(信)
	public boolean checkMibankcode_credit(String mibankcode_credit) {
		if (mibankcode_credit.equals("") || mibankcode_credit.matches("^\\w{0,12}$")) {
			return true;
		}
		return false;
	}

	// 拆分清算
	public boolean checkMicardtype(String micardtype) {
		if (micardtype.equals("") || micardtype.matches("^\\w{0,2}$")) {
			return true;
		}
		return false;
	}

	// 部门
	public boolean checkDepartment(String department) {
		if (department.equals("") || department.matches("^\\w{0,20}$")) {
			return true;
		}
		return false;
	}

	// 代理商编号
	public boolean checkAgency_id(String agency_id) {
		if (agency_id.equals("") || agency_id.matches("^\\w{0,20}$")) {
			return true;
		}
		return false;
	}

	// 父节点商户号
	public boolean checkParent_mccode(String parent_mccode) {
		if (parent_mccode.equals("") || parent_mccode.matches("^\\w{1,18}$")) {
			return true;
		}
		return false;
	}

	// 平安银行清算转账类型
	public boolean checkSett_pa_type(String sett_pa_type) {
		if (sett_pa_type.equals("") || sett_pa_type.matches("^\\w{1,2}$")) {
			return true;
		}
		return false;
	}

	// 建设银行清算转账类型
	public boolean checkSett_ccb_type(String sett_ccb_type) {
		if (sett_ccb_type.equals("") || sett_ccb_type.matches("^\\w{1,2}$")) {
			return true;
		}
		return false;
	}

	// 平安银行清算转账类型
	public boolean checkSett_pa_type_old(String sett_pa_type_old) {
		if (sett_pa_type_old.equals("") || sett_pa_type_old.matches("^\\w{1,2}$")) {
			return true;
		}
		return false;
	}

	// 平安银行清算-省份
	public boolean checkSett_pa_province(String sett_pa_province) {
		if (sett_pa_province.equals("") || sett_pa_province.length() <= 20) {
			return true;
		}
		return false;
	}

	// 平安银行清算-城市
	public boolean checkSett_pa_city(String sett_pa_city) {
		if (sett_pa_city.equals("") || sett_pa_city.length() <= 20) {
			return true;
		}
		return false;
	}

	// 所属平台机构代码
	public boolean checkBelong_id_code(String belong_id_code) {
		if (belong_id_code.equals("") || belong_id_code.matches("^\\w{0,12}$")) {
			return true;
		}
		return false;
	}

	// 收单接入机构代码
	public boolean checkAcq_inter_code(String acq_inter_code) {
		if (acq_inter_code.equals("") || acq_inter_code.matches("^\\w{0,12}$")) {
			return true;
		}
		return false;
	}

	// 商户结算标志
	public boolean checkMer_sett_flag(String mer_sett_flag) {
		if (mer_sett_flag.equals("") || mer_sett_flag.matches("^\\w{0,1}$")) {
			return true;
		}
		return false;
	}

	// 时分
	public boolean checkMer_sett_datetime(String mer_sett_datetime) {
		if (mer_sett_datetime.equals("") || mer_sett_datetime.matches("^\\w{0,4}$")) {
			return true;
		}
		return false;
	}

	// 易办事与商户手续费结算周期
	public boolean checkMer_sett_fee(String mer_sett_fee) {
		if (mer_sett_fee.equals("") || mer_sett_fee.matches("^\\w{0,4}$")) {
			return true;
		}
		return false;
	}

	// 是否银联建档
	public boolean checkUnionpay_archive(String unionpay_archive) {
		if (unionpay_archive.equals("") || unionpay_archive.matches("^\\w{1}$")) {
			return true;
		}
		return false;
	}

	// 月交易限额
	public boolean checkMonth_trans_money(String month_trans_money) {
		if (month_trans_money.equals("") || month_trans_money.matches("^\\d{0,12}$")) {
			return true;
		}
		return false;
	}

	// 业务大类
	public boolean checkBusiness_bigtype(String business_bigtype) {
		if (business_bigtype.equals("") || business_bigtype.length() <= 60) {
			return true;
		}
		return false;
	}

	// 业务细类
	public boolean checkBusiness_smalltype(String business_smalltype) {
		if (business_smalltype.equals("") || business_smalltype.length() <= 60) {
			return true;
		}
		return false;
	}

	// 操作标识 I:新增 U：修改
	public boolean checkOper_in(String oper_in) {
		if (oper_in.equals("") || oper_in.matches("^\\w{1}$")) {
			return true;
		}
		return false;
	}

	// 商户服务类型
	public boolean checkMchnt_svc_tp(String mchnt_svc_tp) {
		if (mchnt_svc_tp.equals("") || mchnt_svc_tp.matches("^\\w{1,2}$")) {
			return true;
		}
		return false;
	}

	// 所属分公司代码
	public boolean checkCup_branch_ins_id_cd(String cup_branch_ins_id_cd) {
		if (cup_branch_ins_id_cd.equals("") || cup_branch_ins_id_cd.matches("^\\w{0,11}$")) {
			return true;
		}
		return false;
	}

	// 商户经营名称
	public boolean checkMchnt_cn_abbr(String mchnt_cn_abbr) {
		if (mchnt_cn_abbr.length() > 0 && mchnt_cn_abbr.length() <= 40) {
			return true;
		}
		return false;
	}

	// 受理机构代码
	public boolean checkAcpt_ins_id_cd(String acpt_ins_id_cd) {
		if (acpt_ins_id_cd.equals("") ||acpt_ins_id_cd.matches("^\\w{1,11}$")) {
			return true;
		}
		return false;
	}

	// 国家代码
	public boolean checkCntry_cd(String cntry_cd) {
		if (cntry_cd.equals("") || cntry_cd.matches("^\\w{1,3}$")) {
			return true;
		}
		return false;
	}

	// 行政区划代码
	public boolean checkGb_region_cd(String gb_region_cd) {
		if (gb_region_cd.equals("") ||gb_region_cd.matches("^\\w{1,6}$")) {
			return true;
		}
		return false;
	}

	// 受理地区代码
	public boolean checkAcq_region_cd(String acq_region_cd) {
		if (acq_region_cd.equals("") ||acq_region_cd.matches("^\\w{1,4}$")) {
			return true;
		}
		return false;
	}

	// 清算地区代码
	public boolean checkRegion_cd(String region_cd) {
		if (region_cd.equals("") || region_cd.matches("^\\w{1,4}$")) {
			return true;
		}
		return false;
	}

	// 真实商户类型
	public boolean checkReal_mchnt_tp(String real_mchnt_tp) {
		if (real_mchnt_tp.equals("") || real_mchnt_tp.matches("^\\w{0,4}$")) {
			return true;
		}
		return false;
	}

	// 商户组别
	public boolean checkMchnt_grp(String mchnt_grp) {
		if (mchnt_grp.equals("") || mchnt_grp.matches("^\\w{1,2}$")) {
			return true;
		}
		return false;
	}

	// 商户状态
	public boolean checkMchnt_st(String mchnt_st) {
		if (mchnt_st.equals("") || mchnt_st.matches("^\\w{1}$")) {
			return true;
		}
		return false;
	}

	// 所属行业类型
	public boolean checkIndustry_tp(String industry_tp) {
		if (industry_tp.equals("") || industry_tp.matches("^\\w{0,4}$")) {
			return true;
		}
		return false;
	}

	// 受卡方名称地址
	public boolean checkCard_accpt_nm_addr(String card_accpt_nm_addr) {
		if (card_accpt_nm_addr.equals("") || card_accpt_nm_addr.length() <= 40) {
			return true;
		}
		return false;
	}

	// 商户英文名称
	public boolean checkMchnt_en_nm(String mchnt_en_nm) {
		if (mchnt_en_nm.equals("") || mchnt_en_nm.length() <= 100) {
			return true;
		}
		return false;
	}

	// 商户英文简称
	public boolean checkMchnt_en_abbr(String mchnt_en_abbr) {
		if (mchnt_en_abbr.equals("") || mchnt_en_abbr.length() <= 60) {
			return true;
		}
		return false;
	}

	// 交易特殊控制
	public boolean checkResv_fld2(String resv_fld2) {
		if (resv_fld2.equals("") || resv_fld2.matches("^\\w{0,2}$")) {
			return true;
		}
		return false;
	}

	// 联接方式
	public boolean checkConn_md(String conn_md) {
		if (conn_md.equals("") || conn_md.matches("^\\w{1}$")) {
			return true;
		}
		return false;
	}

	// 是否MIS商户
	public boolean checkMis_mchnt_in(String mis_mchnt_in) {
		if (mis_mchnt_in.equals("") || mis_mchnt_in.matches("^\\w{0,1}$")) {
			return true;
		}
		return false;
	}

	// 交易币种
	public boolean checkMchnt_acpt_curr_bmp(String mchnt_acpt_curr_bmp) {
		if (mchnt_acpt_curr_bmp.equals("") || mchnt_acpt_curr_bmp.length() <= 1000) {
			return true;
		}
		return false;
	}

	// 默认交易币种
	public boolean checkMchnt_dft_curr_cd(String mchnt_dft_curr_cd) {
		if (mchnt_dft_curr_cd.equals("") || mchnt_dft_curr_cd.matches("^\\w{1,3}$")) {
			return true;
		}
		return false;
	}

	// 发证机关
	public boolean checkIss_organ(String iss_organ) {
		if (iss_organ.equals("") || iss_organ.length() <= 40) {
			return true;
		}
		return false;
	}

	// 注册地址
	public boolean checkReg_addr(String reg_addr) {
		if (reg_addr.length() > 0 && reg_addr.length() <= 90) {
			return true;
		}
		return false;
	}

	// 企业代码
	public boolean checkCorp_id(String corp_id) {
		if (corp_id.equals("") || corp_id.matches("^\\w{0,40}$")) {
			return true;
		}
		return false;
	}

	// 商户联系人通讯地址
	public boolean checkComm_addr(String comm_addr) {
		if (comm_addr.equals("") || comm_addr.length() <= 90) {
			return true;
		}
		return false;
	}

	// 邮编
	public boolean checkZip_cd(String zip_cd) {
		if (zip_cd.equals("") || zip_cd.matches("^\\w{0,6}$")) {
			return true;
		}
		return false;
	}

	// 传真
	public boolean checkFax_no(String fax_no) {
		if (fax_no.equals("") || fax_no.matches("^\\w{0,20}$")) {
			return true;
		}
		return false;
	}

	// 财务联系人
	public boolean checkCfo_nm(String cfo_nm) {
		if (cfo_nm.equals("") || cfo_nm.length() <= 40) {
			return true;
		}
		return false;
	}

	// 终端维护公司
	public boolean checkMaintn_company_tp(String maintn_company_tp) {
		if (maintn_company_tp.equals("") || maintn_company_tp.matches("^\\w{0,1}$")) {
			return true;
		}
		return false;
	}

	// 注册资本币种
	public boolean checkReg_captial_curr_cd(String reg_captial_curr_cd) {
		if (reg_captial_curr_cd.equals("") || reg_captial_curr_cd.matches("^\\w{0,3}$")) {
			return true;
		}
		return false;
	}

	// 签约标志
	public boolean checkSign_in(String sign_in) {
		if (sign_in.equals("") || sign_in.matches("^\\w{0,1}$")) {
			return true;
		}
		return false;
	}

	// 电子票据业务标志
	public boolean checkEnotes_in(String enotes_in) {
		if (enotes_in.equals("") || enotes_in.matches("^\\w{0,1}$")) {
			return true;
		}
		return false;
	}

	// 资金入账方式
	public boolean checkSettle_ins_tp(String settle_ins_tp) {
		if (settle_ins_tp.equals("") || settle_ins_tp.matches("^\\w{1}$")) {
			return true;
		}
		return false;
	}

	// 商户清算模式
	public boolean checkMchnt_settle_md(String mchnt_settle_md) {
		if (mchnt_settle_md.equals("") || mchnt_settle_md.matches("^\\w{1}$")) {
			return true;
		}
		return false;
	}

	// 入账商户代码
	public boolean checkPack_mchnt_cd(String pack_mchnt_cd) {
		if (pack_mchnt_cd.equals("") || pack_mchnt_cd.matches("^\\w{0,15}$")) {
			return true;
		}
		return false;
	}

	// 资金划付周期
	public boolean checkCycle_settle_tp(String cycle_settle_tp) {
		if (cycle_settle_tp.equals("") || cycle_settle_tp.matches("^\\w{1}$")) {
			return true;
		}
		return false;
	}

	// 垫付回补类型
	public boolean checkPrepay_return_tp(String prepay_return_tp) {
		if (prepay_return_tp.equals("") || prepay_return_tp.matches("^\\w{1}$")) {
			return true;
		}
		return false;
	}

	// 直联清算标识
	public boolean checkMchnt_settle_in(String mchnt_settle_in) {
		if (mchnt_settle_in.equals("") || mchnt_settle_in.matches("^\\w{1}$")) {
			return true;
		}
		return false;
	}

	// 开户行接收行行号
	public boolean checkMchnt_settle_bank_cd(String mchnt_settle_bank_cd) {
		if (mchnt_settle_bank_cd.matches("^\\w{0,12}$")) {
			return true;
		}
		return false;
	}

	// 商户账户币种
	public boolean checkAcct_curr_cd1(String acct_curr_cd1) {
		if (acct_curr_cd1.matches("^\\w{0,3}$")) {
			return true;
		}
		return false;
	}

	// 本金清算类型
	public boolean checkSpec_settle_in(String spec_settle_in) {
		if (spec_settle_in.matches("^\\w{0,1}$")) {
			return true;
		}
		return false;
	}

	// 不清本金发卡机构代码
	public boolean checkIss_ins_id_cd(String iss_ins_id_cd) {
		if (iss_ins_id_cd.equals("") || iss_ins_id_cd.matches("^\\w{0,11}$")) {
			return true;
		}
		return false;
	}

	// 特殊计费类型
	public boolean checkSpec_disc_tp(String spec_disc_tp) {
		if (spec_disc_tp.equals("") || spec_disc_tp.matches("^\\w{1,2}$")) {
			return true;
		}
		return false;
	}

	// 特殊计费档次
	public boolean checkSpec_disc_lvl(String spec_disc_lvl) {
		if (spec_disc_lvl.equals("") || spec_disc_lvl.matches("^\\w{1}$")) {
			return true;
		}
		return false;
	}

	// 发卡银联分润算法
	public boolean checkAllot_algo(String allot_algo) {
		if (allot_algo.equals("") || allot_algo.matches("^\\w{1,5}$")) {
			return true;
		}
		return false;
	}

	// 商户计费算法
	public boolean checkMchnt_disc_det_index(String mchnt_disc_det_index) {
		if (mchnt_disc_det_index.equals("") || mchnt_disc_det_index.matches("^\\w{1,2000}$")) {
			return true;
		}
		return false;
	}

	// 收单分润算法
	public boolean checkMcmgm_allot_item_index(String mcmgm_allot_item_index) {
		if (mcmgm_allot_item_index.equals("") || mcmgm_allot_item_index.matches("^\\w{1,2000}$")) {
			return true;
		}
		return false;
	}

	// 分润角色
	public boolean checkAllot_role(String allot_role) {
		if (allot_role.equals("") || allot_role.matches("^\\w{0,10}$")) {
			return true;
		}
		return false;
	}

	// 商户服务1
	public boolean checkAllot_ins_id_cd1(String allot_ins_id_cd1) {
		if (allot_ins_id_cd1.equals("") || allot_ins_id_cd1.matches("^\\w{0,11}$")) {
			return true;
		}
		return false;
	}

	// 商户服务2
	public boolean checkAllot_ins_id_cd2(String allot_ins_id_cd2) {
		if (allot_ins_id_cd2.equals("") || allot_ins_id_cd2.matches("^\\w{0,11}$")) {
			return true;
		}
		return false;
	}

	// 商户服务3
	public boolean checkAllot_ins_id_cd3(String allot_ins_id_cd3) {
		if (allot_ins_id_cd3.equals("") || allot_ins_id_cd3.matches("^\\w{0,11}$")) {
			return true;
		}
		return false;
	}

	// 商户服务4
	public boolean checkAllot_ins_id_cd4(String allot_ins_id_cd4) {
		if (allot_ins_id_cd4.equals("") || allot_ins_id_cd4.matches("^\\w{0,11}$")) {
			return true;
		}
		return false;
	}

	// 商户服务5
	public boolean checkAllot_ins_id_cd5(String allot_ins_id_cd5) {
		if (allot_ins_id_cd5.equals("") || allot_ins_id_cd5.matches("^\\w{0,11}$")) {
			return true;
		}
		return false;
	}

	// 商户层级
	public boolean checkMchnt_class(String mchnt_class) {
		if (mchnt_class.equals("") || mchnt_class.matches("^\\w{0,1}$")) {
			return true;
		}
		return false;
	}

	// 报表类型
	public boolean checkRpt_gen_in_bmp(String rpt_gen_in_bmp) {
		if (rpt_gen_in_bmp.equals("") || rpt_gen_in_bmp.matches("^\\w{10}$")) {
			return true;
		}
		return false;
	}

	// 收单收益帐号
	public boolean checkIns_resv7(String ins_resv7) {
		if (ins_resv7.equals("") || ins_resv7.matches("^\\w{0,32}$")) {
			return true;
		}
		return false;
	}

	// 开户行清算号
	public boolean checkBank_settle_seq(String bank_settle_seq) {
		if (bank_settle_seq.equals("") || bank_settle_seq.matches("^\\w{0,12}$")) {
			return true;
		}
		return false;
	}

	// 是否参加日间清算
	public boolean checkInday_settle_in(String inday_settle_in) {
		if (inday_settle_in.equals("") || inday_settle_in.matches("^\\w{1}$")) {
			return true;
		}
		return false;
	}

	// 商户清算附言
	public boolean checkAddn_inf(String addn_inf) {
		if (addn_inf.equals("") || addn_inf.length() <= 24) {
			return true;
		}
		return false;
	}

	// 异常时收单垫付标志
	public boolean checkIferr_acq_pay_flag(String iferr_acq_pay_flag) {
		if (iferr_acq_pay_flag.equals("") || iferr_acq_pay_flag.matches("^\\w{0,1}$")) {
			return true;
		}
		return false;
	}

	// 商户优先标志
	public boolean checkMchnt_prio_in(String mchnt_prio_in) {
		if (mchnt_prio_in.equals("") || mchnt_prio_in.matches("^\\w{0,1}$")) {
			return true;
		}
		return false;
	}

	// 日间贷记业务类型号
	public boolean checkInday_cycle_msg_no(String inday_cycle_msg_no) {
		if (inday_cycle_msg_no.equals("") || inday_cycle_msg_no.matches("^\\w{0,5}$")) {
			return true;
		}
		return false;
	}

	// 小额业务种类_日间场次
	public boolean checkInday_cycle_buss_tp(String inday_cycle_buss_tp) {
		if (inday_cycle_buss_tp.equals("") || inday_cycle_buss_tp.matches("^\\w{0,12}$")) {
			return true;
		}
		return false;
	}

	// 日终贷记业务类型号
	public boolean checkEndday_cycle_msg_no(String endday_cycle_msg_no) {
		if (endday_cycle_msg_no.equals("") || endday_cycle_msg_no.matches("^\\w{0,5}$")) {
			return true;
		}
		return false;
	}

	// 小额业务种类_日终场次
	public boolean checkEndday_cycle_buss_tp(String endday_cycle_buss_tp) {
		if (endday_cycle_buss_tp.equals("") || endday_cycle_buss_tp.matches("^\\w{0,12}$")) {
			return true;
		}
		return false;
	}

	// 支持的借记业务类型号
	public boolean checkAny_cycle_dbit_msg_no(String any_cycle_dbit_msg_no) {
		if (any_cycle_dbit_msg_no.equals("") || any_cycle_dbit_msg_no.matches("^\\w{0,5}$")) {
			return true;
		}
		return false;
	}

	// 支持的借记业务种类
	public boolean checkAny_cycle_dbit_buss_tp(String any_cycle_dbit_buss_tp) {
		if (any_cycle_dbit_buss_tp.equals("") || any_cycle_dbit_buss_tp.matches("^\\w{0,12}$")) {
			return true;
		}
		return false;
	}

	// 商户属性位图
	public boolean checkMchnt_attr_bmp(String mchnt_attr_bmp) {
		if (mchnt_attr_bmp.equals("") || mchnt_attr_bmp.matches("^\\w{1,10}$")) {
			return true;
		}
		return false;
	}

	// 费用入账方式
	public boolean checkFee_settle_ins_tp(String fee_settle_ins_tp) {
		if (fee_settle_ins_tp.equals("") || fee_settle_ins_tp.matches("^\\w{0,1}$")) {
			return true;
		}
		return false;
	}

	// 商户费用开户行名称
	public boolean checkMchnt_fee_bank_nm(String mchnt_fee_bank_nm) {
		if (mchnt_fee_bank_nm.equals("") || mchnt_fee_bank_nm.length() <= 60) {
			return true;
		}
		return false;
	}

	// 商户费用账号开户行行号
	public boolean checkMchnt_fee_acct_bank_cd(String mchnt_fee_acct_bank_cd) {
		if (mchnt_fee_acct_bank_cd.equals("") || mchnt_fee_acct_bank_cd.matches("^\\w{0,12}$")) {
			return true;
		}
		return false;
	}

	// 商户费用账号
	public boolean checkMchnt_fee_acct(String mchnt_fee_acct) {
		if (mchnt_fee_acct.equals("") || mchnt_fee_acct.matches("^\\w{0,32}$")) {
			return true;
		}
		return false;
	}

	// 商户费用账户名称
	public boolean checkMchnt_fee_acct_nm(String mchnt_fee_acct_nm) {
		if (mchnt_fee_acct_nm.equals("") || mchnt_fee_acct_nm.length() <= 60) {
			return true;
		}
		return false;
	}

	// 商户费用开户行清算号
	public boolean checkMchnt_fee_settle_bank_cd(String mchnt_fee_settle_bank_cd) {
		if (mchnt_fee_settle_bank_cd.equals("") || mchnt_fee_settle_bank_cd.matches("^\\w{0,12}$")) {
			return true;
		}
		return false;
	}

	// 商户费用结算行机构代码
	public boolean checkMchnt_fee_acct_ins_id_cd(String mchnt_fee_acct_ins_id_cd) {
		if (mchnt_fee_acct_ins_id_cd.equals("") || mchnt_fee_acct_ins_id_cd.matches("^\\w{0,11}$")) {
			return true;
		}
		return false;
	}

	// sp机构代码
	public boolean checkSp_ins_id_cd(String sp_ins_id_cd) {
		if (sp_ins_id_cd.equals("") || sp_ins_id_cd.matches("^\\w{0,11}$")) {
			return true;
		}
		return false;
	}

	// 行业机构代码
	public boolean checkIndustry_ins_id_cd(String industry_ins_id_cd) {
		if (industry_ins_id_cd.equals("") || industry_ins_id_cd.matches("^\\w{0,11}$")) {
			return true;
		}
		return false;
	}

	// 商户业务处理方式
	public boolean checkMchnt_buss_proc_tp(String mchnt_buss_proc_tp) {
		if (mchnt_buss_proc_tp.equals("") || mchnt_buss_proc_tp.matches("^\\w{0,2}$")) {
			return true;
		}
		return false;
	}

	// 托管应用类型
	public boolean checkTrust_app_tp(String trust_app_tp) {
		if (trust_app_tp.equals("") || trust_app_tp.matches("^\\w{0,2}$")) {
			return true;
		}
		return false;
	}

	// 滞纳金计算标志
	public boolean checkLate_fee_calc_in(String late_fee_calc_in) {
		if (late_fee_calc_in.equals("") || late_fee_calc_in.matches("^\\w{0,1}$")) {
			return true;
		}
		return false;
	}

	// 滞纳金计算模式
	public boolean checkLate_fee_calc_md(String late_fee_calc_md) {
		if (late_fee_calc_md.equals("") || late_fee_calc_md.matches("^\\w{0,1}$")) {
			return true;
		}
		return false;
	}

	// 滞纳金用户类型
	public boolean checkLate_fee_usr_tp(String late_fee_usr_tp) {
		if (late_fee_usr_tp.equals("") || late_fee_usr_tp.matches("^\\w{0,1}$")) {
			return true;
		}
		return false;
	}

	// 最大票据打印次数
	public boolean checkMax_bill_print_num(String max_bill_print_num) {
		if (max_bill_print_num.equals("") || max_bill_print_num.matches("^\\w{0,9}$")) {
			return true;
		}
		return false;
	}

	// 固定金额
	public boolean checkFix_at(String fix_at) {
		if (fix_at.equals("") || fix_at.matches("^\\w{0,12}$")) {
			return true;
		}
		return false;
	}

	// 最低限额
	public boolean checkLate_fee_lower_limit(String late_fee_lower_limit) {
		if (late_fee_lower_limit.equals("") || late_fee_lower_limit.matches("^\\w{0,12}$")) {
			return true;
		}
		return false;
	}

	// 最高限额
	public boolean checkLate_fee_higher_limit(String late_fee_higher_limit) {
		if (late_fee_higher_limit.equals("") || late_fee_higher_limit.matches("^\\w{0,12}$")) {
			return true;
		}
		return false;
	}

	// 基准利率
	public boolean checkLate_fee_base_rt(String late_fee_base_rt) {
		if (late_fee_base_rt.equals("") || late_fee_base_rt.matches("^\\w{0,5}$")) {
			return true;
		}
		return false;
	}

	// 跨年利率
	public boolean checkLate_fee_over_year_rt(String late_fee_over_year_rt) {
		if (late_fee_over_year_rt.equals("") || late_fee_over_year_rt.matches("^\\w{0,5}$")) {
			return true;
		}
		return false;
	}

	// 商户结算性质
	public boolean checkMchnt_settle_attr(String mchnt_settle_attr) {
		if (mchnt_settle_attr.equals("") || mchnt_settle_attr.matches("^\\w{0,3}$")) {
			return true;
		}
		return false;
	}

	// 委托关系是否允许覆盖
	public boolean checkEntrust_relation_in(String entrust_relation_in) {
		if (entrust_relation_in.equals("") || entrust_relation_in.matches("^\\w{0,1}$")) {
			return true;
		}
		return false;
	}

	// 增加主动委托关系标志
	public boolean checkAdd_act_entrust_in(String add_act_entrust_in) {
		if (add_act_entrust_in.equals("") || add_act_entrust_in.matches("^\\w{0,1}$")) {
			return true;
		}
		return false;
	}

	// 税务类别
	public boolean checkTax_tp(String tax_tp) {
		if (tax_tp.equals("") || tax_tp.matches("^\\w{0,2}$")) {
			return true;
		}
		return false;
	}

	// 清算国库行号/征收机关代码
	public boolean checkSettle_tre_bank_no(String settle_tre_bank_no) {
		if (settle_tre_bank_no.equals("") || settle_tre_bank_no.matches("^\\w{0,12}$")) {
			return true;
		}
		return false;
	}

	// 密码加密类型
	public boolean checkPwd_enc_tp(String pwd_enc_tp) {
		if (pwd_enc_tp.equals("") || pwd_enc_tp.matches("^\\w{0,1}$")) {
			return true;
		}
		return false;
	}

	// 密码加密索引
	public boolean checkPwd_enc_index(String pwd_enc_index) {
		if (pwd_enc_index.equals("") || pwd_enc_index.matches("^\\w{1,4}$")) {
			return true;
		}
		return false;
	}

	// 渠道接入商代码
	public boolean checkChnl_mchnt_cd(String chnl_mchnt_cd) {
		if (chnl_mchnt_cd.equals("") || chnl_mchnt_cd.matches("^\\w{0,15}$")) {
			return true;
		}
		return false;
	}

	// 代收授权标志
	public boolean checkSpec_charge_in(String spec_charge_in) {
		if (spec_charge_in.equals("") || spec_charge_in.matches("^\\w{0,1}$")) {
			return true;
		}
		return false;
	}

	// 新业务规则启用标志
	public boolean checkAllot_in(String allot_in) {
		if (allot_in.equals("") || allot_in.matches("^\\w{0,1}$")) {
			return true;
		}
		return false;
	}

	// 商户网址
	public boolean checkSvc_net_url(String svc_net_url) {
		if (svc_net_url.equals("") || svc_net_url.length() <= 255) {
			return true;
		}
		return false;
	}

	// 商户网站名称
	public boolean checkMchnt_web_site_nm(String mchnt_web_site_nm) {
		if (mchnt_web_site_nm.equals("") || mchnt_web_site_nm.length() <= 128) {
			return true;
		}
		return false;
	}

	// 业务类型
	public boolean checkBuss_tp(String buss_tp) {
		if (buss_tp.equals("") || buss_tp.matches("^\\w{0,2}$")) {
			return true;
		}
		return false;
	}

	// 产品功能套餐
	public boolean checkProd_func(String prod_func) {
		if (prod_func.equals("") || prod_func.matches("^\\w{0,4}$")) {
			return true;
		}
		return false;
	}

	// 卡种
	public boolean checkEbuss_tp(String ebuss_tp) {
		if (ebuss_tp.equals("") || ebuss_tp.matches("^\\w{0,1}$")) {
			return true;
		}
		return false;
	}

	// 共享证书主商户号
	public boolean checkProd_func_desc(String prod_func_desc) {
		if (prod_func_desc.equals("") || prod_func_desc.matches("^\\w{0,15}$")) {
			return true;
		}
		return false;
	}

	// 单笔限额
	public boolean checkSingle_at_limit(String single_at_limit) {
		if (single_at_limit.equals("") || single_at_limit.matches("^\\w{0,12}$")) {
			return true;
		}
		return false;
	}

	// 单笔限额说明
	public boolean checkSingle_at_limit_desc(String single_at_limit_desc) {
		if (single_at_limit_desc.equals("") || single_at_limit_desc.length() <= 12) {
			return true;
		}
		return false;
	}

	// 单卡单日累计限额
	public boolean checkSingle_card_day_at_limit(String single_card_day_at_limit) {
		if (single_card_day_at_limit.equals("") || single_card_day_at_limit.matches("^\\w{0,2}$")) {
			return true;
		}
		return false;
	}

	// 单卡单日累计限额说明
	public boolean checkSingle_card_day_at_limit_desc(String single_card_day_at_limit_desc) {
		if (single_card_day_at_limit_desc.equals("") || single_card_day_at_limit_desc.length() <= 12) {
			return true;
		}
		return false;
	}

	// 业务联系人
	public boolean checkBuss_cont1(String buss_cont1) {
		if (buss_cont1.equals("") || buss_cont1.length() <= 64) {
			return true;
		}
		return false;
	}

	// 业务联系人固话
	public boolean checkBuss_cont1_tel(String buss_cont1_tel) {
		if (buss_cont1_tel.equals("") || buss_cont1_tel.matches("^\\w{0,40}$")) {
			return true;
		}
		return false;
	}

	// 业务联系人移动电话
	public boolean checkBuss_cont1_cell(String buss_cont1_cell) {
		if (buss_cont1_cell.equals("") || buss_cont1_cell.matches("^\\w{0,40}$")) {
			return true;
		}
		return false;
	}

	// 业务联系人邮箱
	public boolean checkBuss_cont1_email(String buss_cont1_email) {
		if (buss_cont1_email.equals("") || buss_cont1_email.matches("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$")) {
			return true;
		}
		return false;
	}

	// 技术联系人
	public boolean checkBuss_cont2(String buss_cont2) {
		if (buss_cont2.equals("") || buss_cont2.length() <= 64) {
			return true;
		}
		return false;
	}

	// 技术联系人固话
	public boolean checkBuss_cont2_tel(String buss_cont2_tel) {
		if (buss_cont2_tel.equals("") || buss_cont2_tel.matches("^\\w{0,40}$")) {
			return true;
		}
		return false;
	}

	// 技术联系人移动电话
	public boolean checkBuss_cont2_cell(String buss_cont2_cell) {
		if (buss_cont2_cell.equals("") || buss_cont2_cell.matches("^\\w{0,50}$")) {
			return true;
		}
		return false;
	}

	// 技术联系人邮箱
	public boolean checkBuss_cont2_email(String buss_cont2_email) {
		if (buss_cont2_email.equals("") || buss_cont2_email.matches("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$")) {
			return true;
		}
		return false;
	}

	// 语音业务类型支持位图
	public boolean checkCup_branch_resv10(String cup_branch_resv10) {
		if (cup_branch_resv10.equals("") || cup_branch_resv10.matches("^\\w{0,10}$")) {
			return true;
		}
		return false;
	}

	// 语音支付合作机构
	public boolean checkCup_branch_resv9(String cup_branch_resv9) {
		if (cup_branch_resv9.equals("") || cup_branch_resv9.matches("^\\w{0,11}$")) {
			return true;
		}
		return false;
	}

	// MAC校验和交易加密标志
	public boolean checkCycle_mchnt_in(String cycle_mchnt_in) {
		if (cycle_mchnt_in.equals("") || cycle_mchnt_in.matches("^\\w{0,1}$")) {
			return true;
		}
		return false;
	}

	// 商户交易种类
	public boolean checkMchnt_trans_tp(String mchnt_trans_tp) {
		if (mchnt_trans_tp.equals("") || mchnt_trans_tp.matches("^\\w{0,1}$")) {
			return true;
		}
		return false;
	}

	// 外卡收单行代码
	public boolean checkFrn_acq_ins_id_cd(String frn_acq_ins_id_cd) {
		if (frn_acq_ins_id_cd.equals("") || frn_acq_ins_id_cd.matches("^\\w{0,11}$")) {
			return true;
		}
		return false;
	}

	// 第三方服务机构代码
	public boolean checkIns_resv8(String ins_resv8) {
		if (ins_resv8.equals("") || ins_resv8.matches("^\\w{0,11}$")) {
			return true;
		}
		return false;
	}

	// 是否向商户收取服务费
	public boolean checkSvc_fee_in(String svc_fee_in) {
		if (svc_fee_in.equals("") || svc_fee_in.matches("^\\w{0,1}$")) {
			return true;
		}
		return false;
	}

	// 是否开展分期付款
	public boolean checkInstl_in(String instl_in) {
		if (instl_in.equals("") || instl_in.matches("^\\w{0,1}$")) {
			return true;
		}
		return false;
	}

	// 客服电话
	public boolean checkCall_center_phone(String call_center_phone) {
		if (call_center_phone.equals("") || call_center_phone.matches("^\\w{0,50}$")) {
			return true;
		}
		return false;
	}

	// 商户等级
	public boolean checkMchnt_lvl(String mchnt_lvl) {
		if (mchnt_lvl.equals("") || mchnt_lvl.matches("^\\w{0,2}$")) {
			return true;
		}
		return false;
	}

	// POS台数
	public boolean checkPos_num(String pos_num) {
		if (pos_num.equals("") || pos_num.matches("^\\d{0,4}$")) {
			return true;
		}
		return false;
	}

	// 收银台数量
	public boolean checkCasher_num(String casher_num) {
		if (casher_num.equals("") || casher_num.matches("^\\d{0,4}$")) {
			return true;
		}
		return false;
	}

	// 开业经营日期
	public boolean checkMchnt_open_dt(String mchnt_open_dt) {
		if (mchnt_open_dt.equals("") || mchnt_open_dt.length() > 0 && mchnt_open_dt.matches("^\\d{8}$")) {
			return true;
		}
		return false;
	}

	// 强制退出时间
	public boolean checkConstr_quit_dt(String constr_quit_dt) {
		if (constr_quit_dt.equals("") || constr_quit_dt.length() > 0 && constr_quit_dt.matches("^\\d{8}$")) {
			return true;
		}
		return false;
	}

	// 商户交易渠道
	public boolean checkTrans_chnl(String trans_chnl) {
		if (trans_chnl.equals("") || trans_chnl.matches("^\\w{0,2}$")) {
			return true;
		}
		return false;
	}

	// 强制退出原因码
	public boolean checkConstr_quit_rsn_cd(String constr_quit_rsn_cd) {
		if (constr_quit_rsn_cd.equals("") || constr_quit_rsn_cd.matches("^\\w{0,2}$")) {
			return true;
		}
		return false;
	}

	// 备注
	public boolean checkRemark(String remark) {
		if (remark.equals("") || remark.length() <= 100) {
			return true;
		}
		return false;
	}

	// 分公司特殊字段
	public boolean checkCup_branch_spec_fld(String cup_branch_spec_fld) {
		if (cup_branch_spec_fld.equals("") || cup_branch_spec_fld.length() <= 100) {
			return true;
		}
		return false;
	}

	// 机构保留字段1
	public boolean checkIns_resv1(String ins_resv1) {
		if (ins_resv1.equals("") || ins_resv1.length() <= 100) {
			return true;
		}
		return false;
	}

	// 机构保留字段2
	public boolean checkIns_resv2(String ins_resv2) {
		if (ins_resv2.equals("") || ins_resv2.length() <= 100) {
			return true;
		}
		return false;
	}

	// 机构保留字段3
	public boolean checkIns_resv3(String ins_resv3) {
		if (ins_resv3.equals("") || ins_resv3.length() <= 100) {
			return true;
		}
		return false;
	}

	// 机构保留字段4
	public boolean checkIns_resv4(String ins_resv4) {
		if (ins_resv4.equals("") || ins_resv4.length() <= 100) {
			return true;
		}
		return false;
	}

	// 机构保留字段5
	public boolean checkIns_resv5(String ins_resv5) {
		if (ins_resv5.equals("") || ins_resv5.length() <= 100) {
			return true;
		}
		return false;
	}

	// 机构保留字段6
	public boolean checkIns_resv6(String ins_resv6) {
		if (ins_resv6.equals("") || ins_resv6.length() <= 100) {
			return true;
		}
		return false;
	}

	// 机构保留字段9
	public boolean checkIns_resv9(String ins_resv9) {
		if (ins_resv9.equals("") || ins_resv9.length() <= 100) {
			return true;
		}
		return false;
	}

	// 机构保留字段10
	public boolean checkIns_resv10(String ins_resv10) {
		if (ins_resv10.equals("") || ins_resv10.length() <= 100) {
			return true;
		}
		return false;
	}

	// 分公司保留字段1
	public boolean checkCup_branch_resv1(String cup_branch_resv1) {
		if (cup_branch_resv1.equals("") || cup_branch_resv1.length() <= 100) {
			return true;
		}
		return false;
	}

	// 分公司保留字段2
	public boolean checkCup_branch_resv2(String cup_branch_resv2) {
		if (cup_branch_resv2.equals("") || cup_branch_resv2.length() <= 100) {
			return true;
		}
		return false;
	}

	// 分公司保留字段3
	public boolean checkCup_branch_resv3(String cup_branch_resv3) {
		if (cup_branch_resv3.equals("") || cup_branch_resv3.length() <= 100) {
			return true;
		}
		return false;
	}

	// 分公司保留字段4
	public boolean checkCup_branch_resv4(String cup_branch_resv4) {
		if (cup_branch_resv4.equals("") || cup_branch_resv4.length() <= 100) {
			return true;
		}
		return false;
	}

	// 分公司保留字段5
	public boolean checkCup_branch_resv5(String cup_branch_resv5) {
		if (cup_branch_resv5.equals("") || cup_branch_resv5.length() <= 100) {
			return true;
		}
		return false;
	}

	// 分公司保留字段6
	public boolean checkCup_branch_resv6(String cup_branch_resv6) {
		if (cup_branch_resv6.equals("") || cup_branch_resv6.length() <= 100) {
			return true;
		}
		return false;
	}

	// 分公司保留字段7
	public boolean checkCup_branch_resv7(String cup_branch_resv7) {
		if (cup_branch_resv7.equals("") || cup_branch_resv7.length() <= 100) {
			return true;
		}
		return false;
	}

	// 分公司保留字段8
	public boolean checkCup_branch_resv8(String cup_branch_resv8) {
		if (cup_branch_resv8.equals("") || cup_branch_resv8.length() <= 100) {
			return true;
		}
		return false;
	}

	// 总公司保留字段1
	public boolean checkCup_hdqrs_resv1(String cup_hdqrs_resv1) {
		if (cup_hdqrs_resv1.equals("") || cup_hdqrs_resv1.length() <= 100) {
			return true;
		}
		return false;
	}

	// 总公司保留字段2
	public boolean checkCup_hdqrs_resv2(String cup_hdqrs_resv2) {
		if (cup_hdqrs_resv2.equals("") || cup_hdqrs_resv2.length() <= 100) {
			return true;
		}
		return false;
	}

	// 总公司保留字段3
	public boolean checkCup_hdqrs_resv3(String cup_hdqrs_resv3) {
		if (cup_hdqrs_resv3.equals("") || cup_hdqrs_resv3.length() <= 100) {
			return true;
		}
		return false;
	}

	// 总公司保留字段4
	public boolean checkCup_hdqrs_resv4(String cup_hdqrs_resv4) {
		if (cup_hdqrs_resv4.equals("") || cup_hdqrs_resv4.length() <= 100) {
			return true;
		}
		return false;
	}

	// 商户是否有不良记录
	public boolean checkMchnt_neg_act_in(String mchnt_neg_act_in) {
		if (mchnt_neg_act_in.equals("") || mchnt_neg_act_in.matches("^\\w{0,1}$")) {
			return true;
		}
		return false;
	}

	// 法人是否列入风险商户法人名单
	public boolean checkRisk_artif_in(String risk_artif_in) {
		if (risk_artif_in.equals("") || risk_artif_in.matches("^\\w{0,1}$")) {
			return true;
		}
		return false;
	}

	// 商户是否列入风险商户名单
	public boolean checkRisk_mchnt_in(String risk_mchnt_in) {
		if (risk_mchnt_in.equals("") || risk_mchnt_in.matches("^\\w{0,1}$")) {
			return true;
		}
		return false;
	}

	// 商户是否列入可疑商户名单
	public boolean checkSus_mchnt_in(String sus_mchnt_in) {
		if (sus_mchnt_in.equals("") || sus_mchnt_in.matches("^\\w{0,1}$")) {
			return true;
		}
		return false;
	}

	// 总分店标志
	public boolean checkHdqrs_branch_in(String hdqrs_branch_in) {
		if (hdqrs_branch_in.equals("") || hdqrs_branch_in.matches("^\\w{0,1}$")) {
			return true;
		}
		return false;
	}

	// 总店商户代码
	public boolean checkHdqrs_mchnt_cd(String hdqrs_mchnt_cd) {
		if (hdqrs_mchnt_cd.equals("") || hdqrs_mchnt_cd.matches("^\\w{0,15}$")) {
			return true;
		}
		return false;
	}

	// 商户拓展方式
	public boolean checkRecncl_tp(String recncl_tp) {
		if (recncl_tp.equals("") || recncl_tp.matches("^\\w{1}$")) {
			return true;
		}
		return false;
	}

	// 营业证明文件类型
	public boolean checkNet_mchnt_svc_tp(String net_mchnt_svc_tp) {
		if (net_mchnt_svc_tp.equals("") || net_mchnt_svc_tp.matches("^\\w{2}$")) {
			return true;
		}
		return false;
	}

	// 收单外包服务机构
	public boolean checkPrincipal_nm(String principal_nm) {
		if (principal_nm.equals("") || principal_nm.length() <= 40) {
			return true;
		}
		return false;
	}

	// 网络商户类型
	public boolean checkSubmchnt_in(String submchnt_in) {
		if (submchnt_in.equals("") || submchnt_in.matches("^\\w{0,1}$")) {
			return true;
		}
		return false;
	}

	// ICP许可证编号
	public boolean checkSvc_ins_nm(String svc_ins_nm) {
		if (svc_ins_nm.equals("") || svc_ins_nm.length() <= 70) {
			return true;
		}
		return false;
	}

	// 商户开票开户银行名称
	public boolean checkCooking(String cooking) {
		if (cooking.equals("") || cooking.length() <= 100) {
			return true;
		}
		return false;
	}

	// 商户开票账号
	public boolean checkMchnt_icp(String mchnt_icp) {
		if (mchnt_icp.equals("") || mchnt_icp.length() <= 40) {
			return true;
		}
		return false;
	}

	// 商户开票账户名称
	public boolean checkTraffic_line(String traffic_line) {
		if (traffic_line.equals("") || traffic_line.length() <= 100) {
			return true;
		}
		return false;
	}

	// 是否申请非标准价格
	public boolean checkDirect_acq_settle_in(String direct_acq_settle_in) {
		if (direct_acq_settle_in.equals("") || direct_acq_settle_in.matches("^\\w{0,1}$")) {
			return true;
		}
		return false;
	}

	// 商户现场注册标识码
	public boolean checkNm_addr(String nm_addr) {
		if (nm_addr.equals("") || nm_addr.matches("^\\w{0,1}$")) {
			return true;
		}
		return false;
	}

	// 贷记卡发卡银联分润算法
	public boolean checkAllot_cd(String allot_cd) {
		if (allot_cd.equals("") || allot_cd.matches("^\\w{0,5}$")) {
			return true;
		}
		return false;
	}

	// 是否开通免密免签
	public boolean checkMcc_appl_rule(String mcc_appl_rule) {
		if (mcc_appl_rule.equals("") || mcc_appl_rule.matches("^\\w{0,2}$")) {
			return true;
		}
		return false;
	}

	// 品牌
	public boolean checkMaster_pwd(String master_pwd) {
		if (master_pwd.equals("") || master_pwd.length() <= 40) {
			return true;
		}
		return false;
	}

	// 业务扩展预留字段1
	public boolean checkMcc_appl_rsn_cd(String mcc_appl_rsn_cd) {
		if (mcc_appl_rsn_cd.equals("") || mcc_appl_rsn_cd.matches("^\\w{0,4}$")) {
			return true;
		}
		return false;
	}

	// 业务扩展预留字段2
	public boolean checkSpec_fee_std_desc(String spec_fee_std_desc) {
		if (spec_fee_std_desc.equals("") || spec_fee_std_desc.length() <= 20) {
			return true;
		}
		return false;
	}

	// 业务扩展预留字段3
	public boolean checkNm_addr1(String nm_addr1) {
		if (nm_addr1.equals("") || nm_addr1.length() <= 32) {
			return true;
		}
		return false;
	}

	// 统一社会信用代码
	public boolean checkCredit_code(String credit_code) {
		if (credit_code.equals("") || credit_code.length() <= 256) {
			return true;
		}
		return false;
	}

	// 法人身份证生效日期
	public boolean checkId_effective(String id_effective) {
		if (id_effective.equals("")
				|| id_effective.matches("^([1-9][0-9]{3})((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$")) {
			return true;
		}
		return false;
	}

	// 法人身份证失效日期
	public boolean checkId_invalid(String id_invalid) {
		if (id_invalid.equals("")
				|| id_invalid.matches("^([1-9][0-9]{3})((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$")) {
			return true;
		}
		return false;
	}

	// 组织机构代码证照编号
	public boolean checkCode_no(String code_no) {
		if (StringUtils.isEmpty(code_no) || code_no.matches("^[a-zA-Z0-9]{1,10}[-]{0,1}[a-zA-Z0-9]{1,10}$")) {
			return true;
		}
		return false;
	}

	// 组织机构代码证生效日期
	public boolean checkCode_effective(String code_effective) {
		if (code_effective.equals("")
				|| code_effective.matches("^([1-9][0-9]{3})((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$")) {
			return true;
		}
		return false;
	}

	// 组织机构代码证失效日期
	public boolean checkCode_invalid(String code_invalid) {
		if (code_invalid.equals("")
				|| code_invalid.matches("^([1-9][0-9]{3})((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$")) {
			return true;
		}
		return false;
	}

	// 是否提供商户调查表
	public boolean checkIs_mccode_table(String is_mccode_table) {
		if (is_mccode_table.equals("") || is_mccode_table.matches("^\\w{0,4}$")) {
			return true;
		}
		return false;
	}

	// 法人代表姓名
	public boolean checkArtif(String artif) {
		if (artif.equals("") || artif.length() <= 300) {
			return true;
		}
		return false;
	}

	// 法人联系电话
	public boolean checkArtif_phone(String artif_phone) {
		if (artif_phone.equals("") || artif_phone.matches("^\\w{0,15}$")) {
			return true;
		}
		return false;
	}

	// 法人身份证 匹配身份证、香港身份证、港澳通行证、台湾通行证、台湾身份证
	public boolean checkArtif_id(String artif_id) {
		if (artif_id.equals("") || IpsRegex.validIDCard(artif_id)
				|| artif_id.matches("^((\\s?[A-Za-z])|([A-Za-z]{2}))\\d{6}(([0−9aA])|([0-9aA]))$")
				|| artif_id.matches("^[HMhm]{1}([0-9]{10}|[0-9]{8})$")||artif_id.matches("[A-Z][0-9]{6}\\([0-9A]\\)")||artif_id.matches("[0-9]{8}")||artif_id.matches("[a-zA-Z0-9]{10}")) {
			return true;
		}
		return false;
	}

	// 营业执照号
	public boolean checkLic_no(String lic_no) {
		if (lic_no.equals("") || lic_no.matches("^\\w{0,40}$")) {
			return true;
		}
		return false;
	}

	// 年报情况
	public boolean checkCompany_year_check(String company_year_check) {
		if (company_year_check.equals("") || company_year_check.length() <= 100) {
			return true;
		}
		return false;
	}

	// 营业执照号开始日期
	public boolean checkCompany_license_startdate(String company_license_startdate) {
		if (company_license_startdate.equals("") || company_license_startdate
				.matches("^([1-9][0-9]{3})((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$")) {
			return true;
		}
		return false;
	}

	// 执照有效期
	public boolean checkCompany_license_enddate(String company_license_enddate) {
		if (company_license_enddate.equals("") || company_license_enddate
				.matches("^([1-9][0-9]{3})((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$")) {
			return true;
		}
		return false;
	}

	// 组织机构代码
	public boolean checkForm_code(String form_code) {
		if (form_code.equals("") || form_code.length() <= 100) {
			return true;
		}
		return false;
	}

	// 税务登记证
	public boolean checkTaxation_code(String taxation_code) {
		if (taxation_code.equals("") || taxation_code.matches("^\\w{0,100}$")) {
			return true;
		}
		return false;
	}

	// 公司类型
	public boolean checkCompany_type(String company_type) {
		if (company_type.length() <= 100) {
			return true;
		}
		return false;
	}

	// 企业住所
	public boolean checkArtif_address(String artif_address) {
		if (artif_address.equals("") || artif_address.length() <= 400) {
			return true;
		}
		return false;
	}

	// 注册资本
	public boolean checkCompany_set_money(String company_set_money) {
		if (company_set_money.equals("") || company_set_money.matches("^(\\d{0,18})(\\.\\d{1,2})?")
				|| company_set_money.matches("^(\\d{0,18})")) {
			return true;
		}
		return false;
	}

	// 实收资本
	public boolean checkCompany_real_money(String company_real_money) {
		if (company_real_money.equals("") || company_real_money.matches("^(\\d{0,18})(\\.\\d{1,2})?")
				|| company_real_money.matches("^(\\d{0,18})")) {
			return true;
		}
		return false;
	}

	// 经营范围以及方式
	public boolean checkCompany_area_or_method(String company_area_or_method) {
		if (company_area_or_method.equals("") || company_area_or_method.length() <= 150) {
			return true;
		}
		return false;
	}

	// 总店区域
	public boolean checkMother_area(String mother_area) {
		if (mother_area.equals("") || mother_area.length() <= 150) {
			return true;
		}
		return false;
	}

	// 入网资料状态
	public boolean checkInfomation_status(String infomation_status) {
		if (infomation_status.equals("") || infomation_status.length() <= 30) {
			return true;
		}
		return false;
	}

	// 资料修改日期
	public boolean checkUpdate_date(String update_date) {
		if (update_date.equals("")
				|| update_date.matches("^([1-9][0-9]{3})((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$")) {
			return true;
		}
		return false;
	}

	// 联系人
	public boolean checkMipeople(String mipeople) {
		if (mipeople.equals("") || mipeople.length() <= 30) {
			return true;
		}
		return false;
	}

	// 联系人手机
	public boolean checkExtendMitelpne(String mitelpne) {
		if (mitelpne.equals("") || mitelpne.matches("^\\w{0,11}$")) {
			return true;
		}
		return false;
	}

	// 联系人座机
	public boolean checkMipeopletel(String mipeopletel) {
		if (mipeopletel.equals("") || mipeopletel.length() < 16) {
			return true;
		}
		return false;
	}

	// 联系人微信
	public boolean checkMipeoplewx(String mipeoplewx) {
		if (mipeoplewx.equals("") || mipeoplewx.matches("^\\w{0,30}$")) {
			return true;
		}
		return false;
	}

	// 联系人QQ
	public boolean checkMipeopleqq(String mipeopleqq) {
		if (mipeopleqq.equals("") || mipeopleqq.matches("^\\w{0,30}$")) {
			return true;
		}
		return false;
	}

	// 联系人邮箱
	public boolean checkMipeopleemail(String mipeopleemail) {
		if (mipeopleemail.equals("") || mipeopleemail
				.matches("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$")) {
			return true;
		}
		return false;
	}

	// 实际经营地址
	public boolean checkMisjaddr(String misjaddr) {
		if (misjaddr.equals("") || misjaddr.length() <= 300) {
			return true;
		}
		return false;
	}

	// 信用代码生效日期
	public boolean checkCredit_effective(String credit_effective) {
		if (credit_effective.equals("")
				|| credit_effective.matches("^([1-9][0-9]{3})((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$")) {
			return true;
		}
		return false;
	}

	// 信用代码失效日期
	public boolean checkCredit_invalid(String credit_invalid) {
		if (credit_invalid.equals("")
				|| credit_invalid.matches("^([1-9][0-9]{3})((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$")) {
			return true;
		}
		return false;
	}

	// 核准日期
	public boolean checkCheck_date(String check_date) {
		if (check_date.equals("")
				|| check_date.matches("^([1-9][0-9]{3})((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$")) {
			return true;
		}
		return false;
	}

	// 年检情况
	public boolean checkYear_check(String year_check) {
		if (year_check.equals("") || year_check.length() <= 100) {
			return true;
		}
		return false;
	}

	// 异常情况说明
	public boolean checkErr_remark(String err_remark) {
		if (err_remark.equals("") || err_remark.length() <= 150) {
			return true;
		}
		return false;
	}

	// 最后修订人
	public boolean checkUpdate_people(String update_people) {
		if (update_people.equals("") || update_people.length() <= 40) {
			return true;
		}
		return false;
	}

	// 合同号
	public boolean checkContract_no(String contract_no) {
		if (contract_no.equals("") || contract_no.length() <= 60) {
			return true;
		}
		return false;
	}

	// 经营状态
	public boolean checkMiaddr_status(String miaddr_status) {
		if (miaddr_status.equals("") || miaddr_status.matches("^\\w{0,1}$")) {
			return true;
		}
		return false;
	}

	// 单位个人标识
	public boolean checkPeople_flag(String people_flag) {
		if (people_flag.equals("") || people_flag.matches("^\\w{0,2}$")) {
			return true;
		}
		return false;
	}

	// 特约商户境内外标识
	public boolean checkMerchantinfo_flag(String merchantinfo_flag) {
		if (merchantinfo_flag.equals("") || merchantinfo_flag.matches("^\\w{0,2}$")) {
			return true;
		}
		return false;
	}

	// 成立日期yyyymmdd
	public boolean checkEffective(String effective) {
		if (effective.equals("")
				|| effective.matches("^([1-9][0-9]{3})((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$")) {
			return true;
		}
		return false;
	}

	// 商户信息调查表
	public boolean checkMerchantinfo_check_flag(String merchantinfo_check_flag) {
		if (merchantinfo_check_flag.equals("") || merchantinfo_check_flag.matches("^\\w{0,1}$")) {
			return true;
		}
		return false;
	}

	// 特约商户证照名称
	public boolean checkMerchant_name(String merchant_name) {
		if (merchant_name.length() <= 100) {
			return true;
		}
		return false;
	}

	// 撤销（终止业务关系）日期
	public boolean checkInvalid(String invalid) {
		if (invalid.equals("")
				|| invalid.matches("^([1-9][0-9]{3})((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$")) {
			return true;
		}
		return false;
	}

	// 法人代表性别
	public boolean checkArtif_sex(String artif_sex) {
		if (artif_sex.length() <= 4) {
			return true;
		}
		return false;
	}

	// 控股股东或实际控制人
	public boolean checkControl_people(String control_people) {
		if (control_people.length() <= 256) {
			return true;
		}
		return false;
	}

	// 控股股东或实际控制人证件种类
	public boolean checkControl_no_type(String control_no_type) {
		if (control_no_type.length() <= 2) {
			return true;
		}
		return false;
	}

	// 控股股东或实际控制人证件号码
	public boolean checkControl_no(String control_no) {
		/*if (control_no.length() <= 40) {
			return true;
		}
		return false;*/
		
		if (control_no.equals("") || IpsRegex.validIDCard(control_no)
				|| control_no.matches("^((\\s?[A-Za-z])|([A-Za-z]{2}))\\d{6}(([0−9aA])|([0-9aA]))$")
				|| control_no.matches("^[HMhm]{1}([0-9]{10}|[0-9]{8})$")||control_no.matches("[A-Z][0-9]{6}\\([0-9A]\\)")||control_no.matches("[0-9]{8}")||control_no.matches("[a-zA-Z0-9]{10}")) {
			return true;
		}
		return false;
	}

	// 控股股东或实际控制人证件生效日期
	public boolean checkControl_effective(String control_effective) {
		if (control_effective.equals("")
				|| control_effective.matches("^([1-9][0-9]{3})((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$")) {
			return true;
		}
		return false;
	}

	// 控股股东或实际控制人证件有效期截止日
	public boolean checkControl_invalid(String control_invalid) {
		if (control_invalid.equals("")
				|| control_invalid.matches("^([1-9][0-9]{3})((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$")) {
			return true;
		}
		return false;
	}

	// 法定代表人或负责人证件种类
	public boolean checkArtif_db_lcno_type(String artif_db_lcno_type) {
		if (artif_db_lcno_type.equals("") || artif_db_lcno_type.matches("^\\w{0,4}$")) {
			return true;
		}
		return false;
	}

	// 授权办理业务人员姓名
	public boolean checkAuthorize_name(String authorize_name) {
		if (authorize_name.length() <= 40) {
			return true;
		}
		return false;
	}

	// 授权办理业务人员证件种类
	public boolean checkAuthorize_no_type(String authorize_no_type) {
		if (authorize_no_type.equals("") || authorize_no_type.matches("^\\w{0,2}$")) {
			return true;
		}
		return false;
	}

	// 授权办理业务人员证件号码
	public boolean checkAuthorize_no(String authorize_no) {
		if (authorize_no.equals("") || IpsRegex.validIDCard(authorize_no)
				|| authorize_no.matches("^((\\s?[A-Za-z])|([A-Za-z]{2}))\\d{6}(([0−9aA])|([0-9aA]))$")
				|| authorize_no.matches("^[HMhm]{1}([0-9]{10}|[0-9]{8})$")||authorize_no.matches("[A-Z][0-9]{6}\\([0-9A]\\)")||authorize_no.matches("[0-9]{8}")||authorize_no.matches("[a-zA-Z0-9]{10}")) {
			return true;
		}
		return false;
	}

	// 授权办理业务人员证件有效期截止日YYYYMMDD
	public boolean checkAuthorize_effective(String authorize_effective) {
		if (authorize_effective.equals("") || authorize_effective
				.matches("^([1-9][0-9]{3})((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$")) {
			return true;
		}
		return false;
	}

	// 主联系人证件类型商户库
	public boolean checkMipeople_lcno_type(String mipeople_lcno_type) {
		if (mipeople_lcno_type.equals("") || mipeople_lcno_type.matches("^\\w{0,2}$")) {
			return true;
		}
		return false;
	}

	// 主联系人证件号码
	public boolean checkMipeople_no(String mipeople_no) {
		if (mipeople_no.equals("") || IpsRegex.validIDCard(mipeople_no)
				|| mipeople_no.matches("^((\\s?[A-Za-z])|([A-Za-z]{2}))\\d{6}(([0−9aA])|([0-9aA]))$")
				|| mipeople_no.matches("^[HMhm]{1}([0-9]{10}|[0-9]{8})$")||mipeople_no.matches("[A-Z][0-9]{6}\\([0-9A]\\)")||mipeople_no.matches("[0-9]{8}")||mipeople_no.matches("[a-zA-Z0-9]{10}")) {
			return true;
		}
		return false;
	}

	// 主联系人证件生效日期yyyymmdd
	public boolean checkMipeople_effective(String mipeople_effective) {
		if (mipeople_effective.equals("")
				|| mipeople_effective.matches("^([1-9][0-9]{3})((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$")) {
			return true;
		}
		return false;
	}

	// 主联系人证件有效期截止日yyyymmdd
	public boolean checkMipeople_invalid(String mipeople_invalid) {
		if (mipeople_invalid.equals("")
				|| mipeople_invalid.matches("^([1-9][0-9]{3})((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$")) {
			return true;
		}
		return false;
	}

	// 平均月营业额
	public boolean checkMonth_business(String month_business) {
		if (StringUtils.isEmpty(month_business) || month_business.matches("^(\\d{0,18})(\\.\\d{1,2})?")
				|| month_business.matches("^(\\d{0,18})")) {
			return true;
		}
		return false;
	}

	// 法定代表人或负责人是否提供开户视频或音频
	public boolean checkArtif_video_flag(String artif_video_flag) {
		if (artif_video_flag.equals("") || artif_video_flag.matches("^\\w{0,1}$")) {
			return true;
		}
		return false;
	}

	// 股东1
	public boolean checkControl_people1(String control_people1) {
		if (control_people1.length() <= 40) {
			return true;
		}
		return false;
	}

	// 股东1证件种类
	public boolean checkControl_no_type1(String control_no_type1) {
		if (control_no_type1.equals("") || control_no_type1.matches("^\\w{0,2}$")) {
			return true;
		}
		return false;
	}

	// 股东1证件号码
	public boolean checkControl_no1(String control_no1) {
		if (control_no1.equals("") || control_no1.matches("^\\w{0,25}$")) {
			return true;
		}
		return false;
	}

	// 股东1证件生效日期
	public boolean checkControl_effective1(String control_effective1) {
		if (control_effective1.equals("")
				|| control_effective1.matches("^([1-9][0-9]{3})((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$")) {
			return true;
		}
		return false;
	}

	// 股东1证件截止日期
	public boolean checkControl_invalid1(String control_invalid1) {
		if (control_invalid1.equals("")
				|| control_invalid1.matches("^([1-9][0-9]{3})((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$")) {
			return true;
		}
		return false;
	}

	// 股东2
	public boolean checkControl_people2(String control_people2) {
		if (control_people2.length() <= 40) {
			return true;
		}
		return false;
	}

	// 股东2证件种类
	public boolean checkControl_no_type2(String control_no_type2) {
		if (control_no_type2.equals("") || control_no_type2.matches("^\\w{0,2}$")) {
			return true;
		}
		return false;
	}

	// 股东2证件号码
	public boolean checkControl_no2(String control_no2) {
		if (control_no2.equals("") || control_no2.matches("^\\w{0,25}$")) {
			return true;
		}
		return false;
	}

	// 股东2证件生效日期
	public boolean checkControl_effective2(String control_effective2) {
		if (control_effective2.equals("")
				|| control_effective2.matches("^([1-9][0-9]{3})((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$")) {
			return true;
		}
		return false;
	}

	// 股东2证件截止日期
	public boolean checkControl_invalid2(String control_invalid2) {
		if (control_invalid2.equals("")
				|| control_invalid2.matches("^([1-9][0-9]{3})((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$")) {
			return true;
		}
		return false;
	}

	// 出账人姓名
	public boolean checkBiller(String biller) {
		if (biller.length() <= 40) {
			return true;
		}
		return false;
	}

	// 出账人国籍
	public boolean checkBiller_nationality(String biller_nationality) {
		if (biller_nationality.length() <= 3) {
			return true;
		}
		return false;
	}

	// 出账人证件种类
	public boolean checkBiller_no_type(String biller_no_type) {
		if (biller_no_type.equals("") || biller_no_type.matches("^\\w{0,2}$")) {
			return true;
		}
		return false;
	}

	// 出账人证件号码
	public boolean checkBiller_no1(String biller_no1) { 
		if (biller_no1.equals("") || IpsRegex.validIDCard(biller_no1)
				|| biller_no1.matches("^((\\s?[A-Za-z])|([A-Za-z]{2}))\\d{6}(([0−9aA])|([0-9aA]))$")
				|| biller_no1.matches("^[HMhm]{1}([0-9]{10}|[0-9]{8})$")||biller_no1.matches("[A-Z][0-9]{6}\\([0-9A]\\)")||biller_no1.matches("[0-9]{8}")||biller_no1.matches("[a-zA-Z0-9]{10}")) {
			return true;
		}
		return false;
	}

	// 出账人证件生效日期
	public boolean checkBiller_effective1(String biller_effective1) {
		if (biller_effective1.equals("")
				|| biller_effective1.matches("^([1-9][0-9]{3})((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$")) {
			return true;
		}
		return false;
	}

	// 出账人证件失效日期
	public boolean checkBiller_invalid1(String biller_invalid1) {
		if (biller_invalid1.equals("")
				|| biller_invalid1.matches("^([1-9][0-9]{3})((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$")) {
			return true;
		}
		return false;
	}

	// 出账人联系方式
	public boolean checkBiller_tel(String biller_tel) {
		if (biller_tel.length() < 20) {
			return true;
		}
		return false;
	}

	// 业务人员证件有效期截止日期
	public boolean checkAuthorize_invalid(String authorize_invalid) {
		if (authorize_invalid.equals("")
				|| authorize_invalid.matches("^([1-9][0-9]{3})((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$")) {
			return true;
		}
		return false;
	}

	// 注册制度
	public boolean checkCompany_rule(String company_rule) {
		if (company_rule.length() <= 20) {
			return true;
		}
		return false;
	}

	// 一般时间校验 支持的格式为“YYYY-MM-DD HH:mm:ss”和“YYYY-MM-DD”。
	public boolean checkDate_datetime(String time) {
		if (time.equals("") || time.matches(
				"^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1][0-9])|([2][0-4]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$")) {
			return true;
		}
		return false;
	}

	// 一般时间校验 支持的格式为“YYYY-MM-DD”。
	public boolean checkDate_date(String time) {
		if (time.equals("") || time.matches(
				"^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))?$")) {
			return true;
		}
		return false;
	}

	// 一般时间校验 支持的格式为“YYYY-MM-DD”。
	public boolean checkAmount(String amount) {
		if (amount.equals("") || amount.matches("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$")) {
			return true;
		}
		return false;
	}
	
}
