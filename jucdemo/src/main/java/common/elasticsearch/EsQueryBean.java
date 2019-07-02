package common.elasticsearch;

import com.ybs.web.commons.elasticsearch.inter.Query;

/**
 * ElasticSearch 查询语句的实体，用于表达查询条件的关系
 * 
 * @author huangf
 *
 */
public final class EsQueryBean implements Query {
	private String relation;
	private String key;
	private Object value;

	private EsQueryBean(){
		super();
	}
	
	/**
	 * 获取普通匹配 相当于SQL中“=”号
	 * 
	 * @param key
	 * @param value
	 */
	public static EsQueryBean term(String key, Object value){
		EsQueryBean bean = new EsQueryBean();
		bean.relation = "term";
		bean.key = key;
		bean.value = value;
		return bean;
	}
	
	/**
	 * 模糊匹配，相当于SQL中like
	 * 
	 * @param key
	 * @param value 格式使用“*”匹配，如：*中国*，匹配key中带"中国"的数据
	 */
	public static EsQueryBean like(String key, String value){
		EsQueryBean bean = new EsQueryBean();
		bean.relation = "wildcard";
		bean.key = key;
		bean.value = value;
		return bean;
	}
	/**
	 * 比较条件匹配，如 age >= 10 
	 * 注：不存在的关系使用null或空串代替 如(compare(null,100))
	 * 
	 * @param gt 大于，当关系为大于(>)时必填
	 * @param lt 小于，当关系为小于(<)时必填
	 * @param gte 大于等于 ，当关系为大于等于(>=)时必填
	 * @param lte 小于等于 ，当关系为小于等于(<=)时必填
	 * @param key 
	 * @param value 
	 */
	public static EsQueryBean compare(String key, String gt, String lt, String gte, String lte){
		StringBuilder sb = new StringBuilder();
		EsQueryBean bean = new EsQueryBean();
		sb.append("{");
		if(isNotBlank(gt)){
			sb.append("\"gt\":\"" + gt + "\",");
		}
		if(isNotBlank(gte)){
			sb.append("\"gte\":\"" + gte + "\",");
		}
		if(isNotBlank(lt)){
			sb.append("\"lt\":\"" + lt + "\",");
		}
		if(isNotBlank(lte)){
			sb.append("\"lte\":\"" + lte + "\",");
		}
		if(sb.length() > 1){
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("}");
		bean.relation = "range";
		bean.key = key;
		bean.value = sb.toString();
		return bean;
	}
	
	private static boolean isNotBlank(String str){
		if(str == null || "".equals(str)){
			return false;
		}
		return true;
	}
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("{\"");
		sb.append(relation);
		sb.append("\":{\"");
		sb.append(key);
		sb.append("\":");
		if("range".equals(relation)){
			sb.append(value);
		}else{
			sb.append("\"" + value + "\"");
		}
		sb.append("}}");
		return sb.toString();
	}
	
}
