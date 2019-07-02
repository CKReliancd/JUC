package common;

import com.ybs.web.dataserver.entity.merchant.UnionpayMerchant;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.List;

/**
 * 导出txt文件
 *
 * @author luyang
 */
public class TxtUtil {

    /**
     * 生成导出附件中文名。应对导出文件中文乱码
     * <p>
     * response.addHeader("Content-Disposition", "attachment; filename=" + cnName);
     *
     * @param cnName
     * @param defaultName
     * @return
     * @author luyang
     */
    public static String genAttachmentFileName(String cnName, String defaultName) {
        try {
//            fileName = URLEncoder.encode(fileName, "UTF-8");
            cnName = new String(cnName.getBytes("gb2312"), "ISO8859-1");
            /*
            if (fileName.length() > 150) {
                fileName = new String( fileName.getBytes("gb2312"), "ISO8859-1" );
            }
            */
        } catch (Exception e) {
            cnName = defaultName;
        }
        return cnName;
    }

    /**
     * 导出文本文件
     *
     * @param response
     * @param jsonString
     * @param fileName
     */
    public static void writeToTxt(HttpServletResponse response, String fileContent) {//设置响应的字符集
        response.setCharacterEncoding("GBK");
        //设置响应内容的类型
        response.setContentType("text/plain");
        //设置文件的名称和格式
        response.addHeader("Content-Disposition", "attachment; filename=mchnt_"
                + MessageFormat.format("{0,date,yyyyMMdd}", new Object[]{Calendar.getInstance().getTime()})
                + ".txt");//通过后缀可以下载不同的文件格式
        BufferedOutputStream buff = null;
        ServletOutputStream outStr = null;
        try {
            outStr = response.getOutputStream();
            buff = new BufferedOutputStream(outStr);
            buff.write(delNull(fileContent).getBytes("GBK"));
            buff.flush();
            buff.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                buff.close();
                outStr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 如果字符串对象为 null，则返回空字符串，否则返回去掉字符串前后空格的字符串
     *
     * @param str
     * @return
     */
    public static String delNull(String str) {
        String returnStr = "";
        if (StringUtils.isNotBlank(str)) {
            returnStr = str.trim();
        }
        return returnStr;
    }

    /**
     * 处理银联批导文件数据以,分割
     *
     * @return
     */
    public static String generateUnionpayFiles(List<UnionpayMerchant> ulist) {
        String result = "";
        if (null != ulist && ulist.size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (UnionpayMerchant merchant : ulist) {
                sb.append(StringUtils.isBlank(merchant.getOper_in()) ? "" : merchant.getOper_in()).append(",")
                        .append(merchant.getMchnt_cd().length() > 3 ? merchant.getMchnt_cd().substring(3, merchant.getMchnt_cd().length()) : merchant.getMchnt_cd()).append(",")
                        .append(StringUtils.isBlank(merchant.getMchnt_svc_tp()) ? "" : merchant.getMchnt_svc_tp()).append(",")
                        .append(StringUtils.isBlank(merchant.getCup_branch_ins_id_cd()) ? "" : merchant.getCup_branch_ins_id_cd()).append(",")
                        .append(StringUtils.isBlank(merchant.getMchnt_cn_nm()) ? "" : merchant.getMchnt_cn_nm()).append(",")
                        .append(StringUtils.isBlank(merchant.getMchnt_cn_abbr()) ? "" : merchant.getMchnt_cn_abbr()).append(",")
                        .append(StringUtils.isBlank(merchant.getAcq_ins_id_cd()) ? "" : merchant.getAcq_ins_id_cd()).append(",")
                        .append(StringUtils.isBlank(merchant.getAcpt_ins_id_cd()) ? "" : merchant.getAcpt_ins_id_cd()).append(",")
                        .append(StringUtils.isBlank(merchant.getPlat_ins_id_cd()) ? "" : merchant.getPlat_ins_id_cd()).append(",")
                        .append(StringUtils.isBlank(merchant.getAcq_access_ins_id_cd()) ? "" : merchant.getAcq_access_ins_id_cd()).append(",")
                        .append(StringUtils.isBlank(merchant.getCntry_cd()) ? "" : merchant.getCntry_cd()).append(",")
                        .append(StringUtils.isBlank(merchant.getGb_region_cd()) ? "" : merchant.getGb_region_cd()).append(",")
                        .append(StringUtils.isBlank(merchant.getAcq_region_cd()) ? "" : merchant.getAcq_region_cd()).append(",")
                        .append(StringUtils.isBlank(merchant.getMchnt_tp()) ? "" : merchant.getMchnt_tp()).append(",")
                        .append(StringUtils.isBlank(merchant.getRegion_cd()) ? "" : merchant.getRegion_cd()).append(",")
                        .append(StringUtils.isBlank(merchant.getReal_mchnt_tp()) ? "" : merchant.getReal_mchnt_tp()).append(",")
                        .append(StringUtils.isBlank(merchant.getMchnt_grp()) ? "" : merchant.getMchnt_grp()).append(",")
                        .append(StringUtils.isBlank(merchant.getMchnt_st()) ? "" : merchant.getMchnt_st()).append(",")
                        .append(StringUtils.isBlank(merchant.getIndustry_tp()) ? "" : merchant.getIndustry_tp()).append(",")
                        .append(StringUtils.isBlank(merchant.getCard_accpt_nm_addr()) ? "" : merchant.getCard_accpt_nm_addr()).append(",")
                        .append(StringUtils.isBlank(merchant.getMchnt_en_nm()) ? "" : merchant.getMchnt_en_nm()).append(",")
                        .append(StringUtils.isBlank(merchant.getMchnt_en_abbr()) ? "" : merchant.getMchnt_en_abbr()).append(",")
                        .append(StringUtils.isBlank(merchant.getResv_fld2()) ? "" : merchant.getResv_fld2()).append(",")
                        .append(StringUtils.isBlank(merchant.getConn_md()) ? "" : merchant.getConn_md()).append(",")
                        .append(StringUtils.isBlank(merchant.getMis_mchnt_in()) ? "" : merchant.getMis_mchnt_in()).append(",")
                        .append(StringUtils.isBlank(merchant.getMchnt_acpt_curr_bmp()) ? "" : merchant.getMchnt_acpt_curr_bmp()).append(",")
                        .append(StringUtils.isBlank(merchant.getMchnt_dft_curr_cd()) ? "" : merchant.getMchnt_dft_curr_cd()).append(",")
                        .append(StringUtils.isBlank(merchant.getLic_no()) ? "" : merchant.getLic_no()).append(",")
                        .append(StringUtils.isBlank(merchant.getLic_expire_dt()) ? "" : merchant.getLic_expire_dt()).append(",")
                        .append(StringUtils.isBlank(merchant.getIss_organ()) ? "" : merchant.getIss_organ()).append(",")
                        .append(StringUtils.isBlank(merchant.getReg_addr()) ? "" : merchant.getReg_addr()).append(",")
                        .append(StringUtils.isBlank(merchant.getReve_reg_cd()) ? "" : merchant.getReve_reg_cd()).append(",")
                        .append(StringUtils.isBlank(merchant.getEtps_attr()) ? "" : merchant.getEtps_attr()).append(",")
                        .append(StringUtils.isBlank(merchant.getCorp_id()) ? "" : merchant.getCorp_id()).append(",")
                        .append(StringUtils.isBlank(merchant.getArtif_certif_tp()) ? "" : merchant.getArtif_certif_tp()).append(",")
                        .append(StringUtils.isBlank(merchant.getArtif_certif_id()) ? "" : merchant.getArtif_certif_id()).append(",")
                        .append(StringUtils.isBlank(merchant.getArtif_nm()) ? "" : merchant.getArtif_nm()).append(",")
                        .append(StringUtils.isBlank(merchant.getContact_person_nm()) ? "" : merchant.getContact_person_nm()).append(",")
                        .append(StringUtils.isBlank(merchant.getComm_addr()) ? "" : merchant.getComm_addr()).append(",")
                        .append(StringUtils.isBlank(merchant.getZip_cd()) ? "" : merchant.getZip_cd()).append(",")
                        .append(StringUtils.isBlank(merchant.getPhone()) ? "" : merchant.getPhone()).append(",")
                        .append(StringUtils.isBlank(merchant.getMobile()) ? "" : "18938080507").append(",")   //陈宇腾的手机
                        .append(StringUtils.isBlank(merchant.getEmail_addr()) ? "" : merchant.getEmail_addr()).append(",")
                        .append(StringUtils.isBlank(merchant.getFax_no()) ? "" : merchant.getFax_no()).append(",")
                        .append(StringUtils.isBlank(merchant.getCfo_nm()) ? "" : merchant.getCfo_nm()).append(",")
                        .append(StringUtils.isBlank(merchant.getMaintn_company_tp()) ? "" : merchant.getMaintn_company_tp()).append(",")
                        .append(StringUtils.isBlank(merchant.getReg_cap()) ? "" : merchant.getReg_cap()).append(",")
                        .append(StringUtils.isBlank(merchant.getReg_captial_curr_cd()) ? "" : merchant.getReg_captial_curr_cd()).append(",")
                        .append(StringUtils.isBlank(merchant.getBuss_range()) ? "" : merchant.getBuss_range()).append(",")
                        .append(StringUtils.isBlank(merchant.getBuss_addr()) ? "" : merchant.getBuss_addr()).append(",")
                        .append(StringUtils.isBlank(merchant.getSign_in()) ? "" : merchant.getSign_in()).append(",")
                        .append(StringUtils.isBlank(merchant.getEnotes_in()) ? "" : merchant.getEnotes_in()).append(",")
                        .append(StringUtils.isBlank(merchant.getSettle_ins_tp()) ? "" : merchant.getSettle_ins_tp()).append(",")
                        .append(StringUtils.isBlank(merchant.getAcct_ins_id_cd()) ? "" : merchant.getAcct_ins_id_cd()).append(",")
                        .append(StringUtils.isBlank(merchant.getMchnt_settle_md()) ? "" : merchant.getMchnt_settle_md()).append(",")
                        .append(StringUtils.isBlank(merchant.getPack_mchnt_cd()) ? "" : merchant.getPack_mchnt_cd()).append(",")
                        .append(StringUtils.isBlank(merchant.getCycle_settle_tp()) ? "" : merchant.getCycle_settle_tp()).append(",")
                        .append(StringUtils.isBlank(merchant.getPrepay_return_tp()) ? "" : merchant.getPrepay_return_tp()).append(",")
                        .append(StringUtils.isBlank(merchant.getMchnt_settle_in()) ? "" : merchant.getMchnt_settle_in()).append(",")
                        .append(StringUtils.isBlank(merchant.getBank_nm1()) ? "" : merchant.getBank_nm1()).append(",")
                        .append(StringUtils.isBlank(merchant.getBank_settle_no1()) ? "" : merchant.getBank_settle_no1()).append(",")
                        .append(StringUtils.isBlank(merchant.getMchnt_settle_bank_cd()) ? "" : merchant.getMchnt_settle_bank_cd()).append(",")
                        .append(StringUtils.isBlank(merchant.getAcct_curr_cd1()) ? "" : merchant.getAcct_curr_cd1()).append(",")
                        .append(StringUtils.isBlank(merchant.getAcct_nm1()) ? "" : merchant.getAcct_nm1()).append(",")
                        .append(StringUtils.isBlank(merchant.getAcct_no1()) ? "" : merchant.getAcct_no1()).append(",")
                        .append(StringUtils.isBlank(merchant.getSpec_settle_in()) ? "" : merchant.getSpec_settle_in()).append(",")
                        .append(StringUtils.isBlank(merchant.getIss_ins_id_cd()) ? "" : merchant.getIss_ins_id_cd()).append(",")
                        .append(StringUtils.isBlank(merchant.getSpec_disc_tp()) ? "" : merchant.getSpec_disc_tp()).append(",")
                        .append(StringUtils.isBlank(merchant.getSpec_disc_lvl()) ? "" : merchant.getSpec_disc_lvl()).append(",")
                        .append(StringUtils.isBlank(merchant.getAllot_algo()) ? "" : merchant.getAllot_algo()).append(",")
                        .append(StringUtils.isBlank(merchant.getMchnt_disc_det_index()) ? "" : generateDisc(merchant.getMchnt_disc_det_index())).append(",")
                        .append(StringUtils.isBlank(merchant.getMcmgm_allot_item_index()) ? "" : generateDisc(merchant.getMcmgm_allot_item_index())).append(",")
                        .append(StringUtils.isBlank(merchant.getAllot_role()) ? "" : merchant.getAllot_role()).append(",")
                        .append(StringUtils.isBlank(merchant.getAllot_ins_id_cd1()) ? "" : merchant.getAllot_ins_id_cd1()).append(",")
                        .append(StringUtils.isBlank(merchant.getAllot_ins_id_cd2()) ? "" : merchant.getAllot_ins_id_cd2()).append(",")
                        .append(StringUtils.isBlank(merchant.getAllot_ins_id_cd3()) ? "" : merchant.getAllot_ins_id_cd3()).append(",")
                        .append(StringUtils.isBlank(merchant.getAllot_ins_id_cd4()) ? "" : merchant.getAllot_ins_id_cd4()).append(",")
                        .append(StringUtils.isBlank(merchant.getAllot_ins_id_cd5()) ? "" : merchant.getAllot_ins_id_cd5()).append(",")
                        .append(StringUtils.isBlank(merchant.getMchnt_class()) ? "" : merchant.getMchnt_class()).append(",")
                        .append(StringUtils.isBlank(merchant.getRpt_gen_in_bmp()) ? "" : merchant.getRpt_gen_in_bmp()).append(",")
                        .append(StringUtils.isBlank(merchant.getIns_resv7()) ? "" : merchant.getIns_resv7()).append(",")
                        .append(StringUtils.isBlank(merchant.getBank_settle_seq()) ? "" : merchant.getBank_settle_seq()).append(",")
                        .append(StringUtils.isBlank(merchant.getInday_settle_in()) ? "" : merchant.getInday_settle_in()).append(",")
                        .append(StringUtils.isBlank(merchant.getAddn_inf()) ? "" : merchant.getAddn_inf()).append(",")
                        .append(StringUtils.isBlank(merchant.getIferr_acq_pay_flag()) ? "" : merchant.getIferr_acq_pay_flag()).append(",")
                        .append(StringUtils.isBlank(merchant.getMchnt_prio_in()) ? "" : merchant.getMchnt_prio_in()).append(",")
                        .append(StringUtils.isBlank(merchant.getInday_cycle_msg_no()) ? "" : merchant.getInday_cycle_msg_no()).append(",")
                        .append(StringUtils.isBlank(merchant.getInday_cycle_buss_tp()) ? "" : merchant.getInday_cycle_buss_tp()).append(",")
                        .append(StringUtils.isBlank(merchant.getEndday_cycle_msg_no()) ? "" : merchant.getEndday_cycle_msg_no()).append(",")
                        .append(StringUtils.isBlank(merchant.getEndday_cycle_buss_tp()) ? "" : merchant.getEndday_cycle_buss_tp()).append(",")
                        .append(StringUtils.isBlank(merchant.getAny_cycle_dbit_msg_no()) ? "" : merchant.getAny_cycle_dbit_msg_no()).append(",")
                        .append(StringUtils.isBlank(merchant.getAny_cycle_dbit_buss_tp()) ? "" : merchant.getAny_cycle_dbit_buss_tp()).append(",")
                        .append(StringUtils.isBlank(merchant.getMchnt_attr_bmp()) ? "" : merchant.getMchnt_attr_bmp()).append(",")
                        .append(StringUtils.isBlank(merchant.getFee_settle_ins_tp()) ? "" : merchant.getFee_settle_ins_tp()).append(",")
                        .append(StringUtils.isBlank(merchant.getMchnt_fee_bank_nm()) ? "" : merchant.getMchnt_fee_bank_nm()).append(",")
                        .append(StringUtils.isBlank(merchant.getMchnt_fee_acct_bank_cd()) ? "" : merchant.getMchnt_fee_acct_bank_cd()).append(",")
                        .append(StringUtils.isBlank(merchant.getMchnt_fee_acct()) ? "" : merchant.getMchnt_fee_acct()).append(",")
                        .append(StringUtils.isBlank(merchant.getMchnt_fee_acct_nm()) ? "" : merchant.getMchnt_fee_acct_nm()).append(",")
                        .append(StringUtils.isBlank(merchant.getMchnt_fee_settle_bank_cd()) ? "" : merchant.getMchnt_fee_settle_bank_cd()).append(",")
                        .append(StringUtils.isBlank(merchant.getMchnt_fee_acct_ins_id_cd()) ? "" : merchant.getMchnt_fee_acct_ins_id_cd()).append(",")
                        .append(StringUtils.isBlank(merchant.getSp_ins_id_cd()) ? "" : merchant.getSp_ins_id_cd()).append(",")
                        .append(StringUtils.isBlank(merchant.getIndustry_ins_id_cd()) ? "" : merchant.getIndustry_ins_id_cd()).append(",")
                        .append(StringUtils.isBlank(merchant.getMchnt_buss_proc_tp()) ? "" : merchant.getMchnt_buss_proc_tp()).append(",")
                        .append(StringUtils.isBlank(merchant.getTrust_app_tp()) ? "" : merchant.getTrust_app_tp()).append(",")
                        .append(StringUtils.isBlank(merchant.getLate_fee_calc_in()) ? "" : merchant.getLate_fee_calc_in()).append(",")
                        .append(StringUtils.isBlank(merchant.getLate_fee_calc_md()) ? "" : merchant.getLate_fee_calc_md()).append(",")
                        .append(StringUtils.isBlank(merchant.getLate_fee_usr_tp()) ? "" : merchant.getLate_fee_usr_tp()).append(",")
                        .append(StringUtils.isBlank(merchant.getMax_bill_print_num()) ? "" : merchant.getMax_bill_print_num()).append(",")
                        .append(StringUtils.isBlank(merchant.getFix_at()) ? "" : merchant.getFix_at()).append(",")
                        .append(StringUtils.isBlank(merchant.getLate_fee_lower_limit()) ? "" : merchant.getLate_fee_lower_limit()).append(",")
                        .append(StringUtils.isBlank(merchant.getLate_fee_higher_limit()) ? "" : merchant.getLate_fee_higher_limit()).append(",")
                        .append(StringUtils.isBlank(merchant.getLate_fee_base_rt()) ? "" : merchant.getLate_fee_base_rt()).append(",")
                        .append(StringUtils.isBlank(merchant.getLate_fee_over_year_rt()) ? "" : merchant.getLate_fee_over_year_rt()).append(",")
                        .append(StringUtils.isBlank(merchant.getMchnt_settle_attr()) ? "" : merchant.getMchnt_settle_attr()).append(",")
                        .append(StringUtils.isBlank(merchant.getEntrust_relation_in()) ? "" : merchant.getEntrust_relation_in()).append(",")
                        .append(StringUtils.isBlank(merchant.getAdd_act_entrust_in()) ? "" : merchant.getAdd_act_entrust_in()).append(",")
                        .append(StringUtils.isBlank(merchant.getTax_tp()) ? "" : merchant.getTax_tp()).append(",")
                        .append(StringUtils.isBlank(merchant.getSettle_tre_bank_no()) ? "" : merchant.getSettle_tre_bank_no()).append(",")
                        .append(StringUtils.isBlank(merchant.getPwd_enc_tp()) ? "" : merchant.getPwd_enc_tp()).append(",")
                        .append(StringUtils.isBlank(merchant.getPwd_enc_index()) ? "" : merchant.getPwd_enc_index()).append(",")
                        .append(StringUtils.isBlank(merchant.getChnl_mchnt_cd()) ? "" : merchant.getChnl_mchnt_cd()).append(",")
                        .append(StringUtils.isBlank(merchant.getSpec_charge_in()) ? "" : merchant.getSpec_charge_in()).append(",")
                        .append(StringUtils.isBlank(merchant.getAllot_in()) ? "" : merchant.getAllot_in()).append(",")
                        .append(StringUtils.isBlank(merchant.getSvc_net_url()) ? "" : merchant.getSvc_net_url()).append(",")
                        .append(StringUtils.isBlank(merchant.getMchnt_web_site_nm()) ? "" : merchant.getMchnt_web_site_nm()).append(",")
                        .append(StringUtils.isBlank(merchant.getBuss_tp()) ? "" : merchant.getBuss_tp()).append(",")
                        .append(StringUtils.isBlank(merchant.getProd_func()) ? "" : merchant.getProd_func()).append(",")
                        .append(StringUtils.isBlank(merchant.getEbuss_tp()) ? "" : merchant.getEbuss_tp()).append(",")
                        .append(StringUtils.isBlank(merchant.getProd_func_desc()) ? "" : merchant.getProd_func_desc()).append(",")
                        .append(StringUtils.isBlank(merchant.getSingle_at_limit()) ? "" : merchant.getSingle_at_limit()).append(",")
                        .append(StringUtils.isBlank(merchant.getSingle_at_limit_desc()) ? "" : merchant.getSingle_at_limit_desc()).append(",")
                        .append(StringUtils.isBlank(merchant.getSingle_card_day_at_limit()) ? "" : merchant.getSingle_card_day_at_limit()).append(",")
                        .append(StringUtils.isBlank(merchant.getSingle_card_day_at_limit_desc()) ? "" : merchant.getSingle_card_day_at_limit_desc()).append(",")
                        .append(StringUtils.isBlank(merchant.getBuss_cont1()) ? "" : merchant.getBuss_cont1()).append(",")
                        .append(StringUtils.isBlank(merchant.getBuss_cont1_tel()) ? "" : merchant.getBuss_cont1_tel()).append(",")
                        .append(StringUtils.isBlank(merchant.getBuss_cont1_cell()) ? "" : merchant.getBuss_cont1_cell()).append(",")
                        .append(StringUtils.isBlank(merchant.getBuss_cont1_email()) ? "" : merchant.getBuss_cont1_email()).append(",")
                        .append(StringUtils.isBlank(merchant.getBuss_cont2()) ? "" : merchant.getBuss_cont2()).append(",")
                        .append(StringUtils.isBlank(merchant.getBuss_cont2_tel()) ? "" : merchant.getBuss_cont2_tel()).append(",")
                        .append(StringUtils.isBlank(merchant.getBuss_cont2_cell()) ? "" : merchant.getBuss_cont2_cell()).append(",")
                        .append(StringUtils.isBlank(merchant.getBuss_cont2_email()) ? "" : merchant.getBuss_cont2_email()).append(",")
                        .append(StringUtils.isBlank(merchant.getCup_branch_resv10()) ? "" : merchant.getCup_branch_resv10()).append(",")
                        .append(StringUtils.isBlank(merchant.getCup_branch_resv9()) ? "" : merchant.getCup_branch_resv9()).append(",")
                        .append(StringUtils.isBlank(merchant.getCycle_mchnt_in()) ? "" : merchant.getCycle_mchnt_in()).append(",")
                        .append(StringUtils.isBlank(merchant.getMchnt_trans_tp()) ? "" : merchant.getMchnt_trans_tp()).append(",")
                        .append(StringUtils.isBlank(merchant.getFrn_acq_ins_id_cd()) ? "" : merchant.getFrn_acq_ins_id_cd()).append(",")
                        .append(StringUtils.isBlank(merchant.getIns_resv8()) ? "" : merchant.getIns_resv8()).append(",")
                        .append(StringUtils.isBlank(merchant.getSvc_fee_in()) ? "" : merchant.getSvc_fee_in()).append(",")
                        .append(StringUtils.isBlank(merchant.getInstl_in()) ? "" : merchant.getInstl_in()).append(",")
                        .append(StringUtils.isBlank(merchant.getCall_center_phone()) ? "" : merchant.getCall_center_phone()).append(",")
                        .append(StringUtils.isBlank(merchant.getMchnt_lvl()) ? "" : merchant.getMchnt_lvl()).append(",")
                        .append(StringUtils.isBlank(merchant.getPos_num()) ? "" : merchant.getPos_num()).append(",")
                        .append(StringUtils.isBlank(merchant.getCasher_num()) ? "" : merchant.getCasher_num()).append(",")
                        .append(StringUtils.isBlank(merchant.getMchnt_open_dt()) ? "" : merchant.getMchnt_open_dt()).append(",")
                        .append(StringUtils.isBlank(merchant.getConstr_quit_dt()) ? "" : merchant.getConstr_quit_dt()).append(",")
                        .append(StringUtils.isBlank(merchant.getTrans_chnl()) ? "" : merchant.getTrans_chnl()).append(",")
                        .append(StringUtils.isBlank(merchant.getConstr_quit_rsn_cd()) ? "" : merchant.getConstr_quit_rsn_cd()).append(",")
                        .append(StringUtils.isBlank(merchant.getRemark()) ? "" : merchant.getRemark()).append(",")
                        .append(StringUtils.isBlank(merchant.getCup_branch_spec_fld()) ? "" : merchant.getCup_branch_spec_fld()).append(",")
                        .append(StringUtils.isBlank(merchant.getIns_resv1()) ? "" : merchant.getIns_resv1()).append(",")
                        .append(StringUtils.isBlank(merchant.getIns_resv2()) ? "" : merchant.getIns_resv2()).append(",")
                        .append(StringUtils.isBlank(merchant.getIns_resv3()) ? "" : merchant.getIns_resv3()).append(",")
                        .append(StringUtils.isBlank(merchant.getIns_resv4()) ? "" : merchant.getIns_resv4()).append(",")
                        .append(StringUtils.isBlank(merchant.getIns_resv5()) ? "" : merchant.getIns_resv5()).append(",")
                        .append(StringUtils.isBlank(merchant.getIns_resv6()) ? "" : merchant.getIns_resv6()).append(",")
                        .append(StringUtils.isBlank(merchant.getIns_resv9()) ? "" : merchant.getIns_resv9()).append(",")
                        .append(StringUtils.isBlank(merchant.getIns_resv10()) ? "" : merchant.getIns_resv10()).append(",")
                        .append(StringUtils.isBlank(merchant.getCup_branch_resv1()) ? "" : merchant.getCup_branch_resv1()).append(",")
                        .append(StringUtils.isBlank(merchant.getCup_branch_resv2()) ? "" : merchant.getCup_branch_resv2()).append(",")
                        .append(StringUtils.isBlank(merchant.getCup_branch_resv3()) ? "" : merchant.getCup_branch_resv3()).append(",")
                        .append(StringUtils.isBlank(merchant.getCup_branch_resv4()) ? "" : merchant.getCup_branch_resv4()).append(",")
                        .append(StringUtils.isBlank(merchant.getCup_branch_resv5()) ? "" : merchant.getCup_branch_resv5()).append(",")
                        .append(StringUtils.isBlank(merchant.getCup_branch_resv6()) ? "" : merchant.getCup_branch_resv6()).append(",")
                        .append(StringUtils.isBlank(merchant.getCup_branch_resv7()) ? "" : merchant.getCup_branch_resv7()).append(",")
                        .append(StringUtils.isBlank(merchant.getCup_branch_resv8()) ? "" : merchant.getCup_branch_resv8()).append(",")
                        .append(StringUtils.isBlank(merchant.getCup_hdqrs_resv1()) ? "" : merchant.getCup_hdqrs_resv1()).append(",")
                        .append(StringUtils.isBlank(merchant.getCup_hdqrs_resv2()) ? "" : merchant.getCup_hdqrs_resv2()).append(",")
                        .append(StringUtils.isBlank(merchant.getCup_hdqrs_resv3()) ? "" : merchant.getCup_hdqrs_resv3()).append(",")
                        .append(StringUtils.isBlank(merchant.getCup_hdqrs_resv4()) ? "" : merchant.getCup_hdqrs_resv4()).append(",")
                        .append(StringUtils.isBlank(merchant.getMchnt_neg_act_in()) ? "" : merchant.getMchnt_neg_act_in()).append(",")
                        .append(StringUtils.isBlank(merchant.getRisk_artif_in()) ? "" : merchant.getRisk_artif_in()).append(",")
                        .append(StringUtils.isBlank(merchant.getRisk_mchnt_in()) ? "" : merchant.getRisk_mchnt_in()).append(",")
                        .append(StringUtils.isBlank(merchant.getSus_mchnt_in()) ? "" : merchant.getSus_mchnt_in()).append(",")
                        .append(StringUtils.isBlank(merchant.getHdqrs_branch_in()) ? "" : merchant.getHdqrs_branch_in()).append(",")
                        .append(StringUtils.isBlank(merchant.getHdqrs_mchnt_cd()) ? "" : merchant.getHdqrs_mchnt_cd()).append(",")
                        .append(StringUtils.isBlank(merchant.getRecncl_tp()) ? "" : merchant.getRecncl_tp()).append(",")
                        .append(StringUtils.isBlank(merchant.getNet_mchnt_svc_tp()) ? "" : merchant.getNet_mchnt_svc_tp()).append(",")
                        .append(StringUtils.isBlank(merchant.getPrincipal_nm()) ? "" : merchant.getPrincipal_nm()).append(",")
                        .append(StringUtils.isBlank(merchant.getSubmchnt_in()) ? "" : merchant.getSubmchnt_in()).append(",")
                        .append(StringUtils.isBlank(merchant.getSvc_ins_nm()) ? "" : merchant.getSvc_ins_nm()).append(",")
                        .append(StringUtils.isBlank(merchant.getCooking()) ? "" : merchant.getCooking()).append(",")
                        .append(StringUtils.isBlank(merchant.getMchnt_icp()) ? "" : merchant.getMchnt_icp()).append(",")
                        .append(StringUtils.isBlank(merchant.getTraffic_line()) ? "" : merchant.getTraffic_line()).append(",")
                        .append(StringUtils.isBlank(merchant.getDirect_acq_settle_in()) ? "" : merchant.getDirect_acq_settle_in()).append(",")
                        .append(StringUtils.isBlank(merchant.getNm_addr()) ? "" : merchant.getNm_addr()).append(",")
                        .append(StringUtils.isBlank(merchant.getAllot_cd()) ? "" : merchant.getAllot_cd()).append(",")
                        .append(StringUtils.isBlank(merchant.getMcc_appl_rule()) ? "" : merchant.getMcc_appl_rule()).append(",")
                        .append(StringUtils.isBlank(merchant.getMaster_pwd()) ? "" : merchant.getMaster_pwd()).append(",")
                        .append(StringUtils.isBlank(merchant.getMcc_appl_rsn_cd()) ? "" : merchant.getMcc_appl_rsn_cd()).append(",")
                        .append(StringUtils.isBlank(merchant.getSpec_fee_std_desc()) ? "" : merchant.getSpec_fee_std_desc()).append(",")
                        .append(StringUtils.isBlank(merchant.getNm_addr1()) ? "" : merchant.getOper_in())
                        .append("\r\n");
            }
            result = sb.toString();
        }
        return result;
    }

    public static String generateDisc(String disc) {
        StringBuffer sb = new StringBuffer();
        if (StringUtils.isNotBlank(disc)) {
            String[] discs = disc.split("#");
            String[] temp = null;
            for (String s : discs) {
                temp = s.split("&");
                if (temp.length >= 4) {
                    sb.append(temp[1]).append("&").append(temp[3]).append("&#");
                }
            }
        } else {
            return "";
        }
        return sb.toString();
    }
}
