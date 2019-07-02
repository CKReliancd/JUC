package common;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

/**
 * 这个不带认证！！！！
 * @see MongoPoolManger
 * @desc mongo连接池
 * @author XPP
 * @desc 简单的单例模式 如果有一天不止一个ybsdb的话，http://www.cnblogs.com/dmir/p/4780544.html
 *       copy please
 * @see https://blog.csdn.net/u013320750/article/details/50504562
 * @see mongoclient的连接池说明，注意事项https://bigcat2013.iteye.com/blog/2109633
 * 		重要的一句话：A MongoDB client with internal connection pooling. 
 * 				 For most applications, you should have one MongoClient instance for the entire JVM.
 *
 * FROM 2019年5月5日09:10:52
 * 1、测试环境与灰度/生产的mongo是不同的，测试是单点的，灰度和生产是集群的，而mongoclient连接单点和集群是存在差异的
 *
 */
public class MongoPoolMangerWithOutAuth {

	private static Logger logger = LoggerFactory.getLogger(MongoPoolMangerWithOutAuth.class);
	// 配置文件
	// datasource正式使用的
//	private static String propertiesFileName = "com/ybs/web/commons/mongodatasourcetest";
	private static String propertiesFileName = "datasource";
	//mongourl，单点集群用同一个
	private static String mongo_url = PropertiesUtil.getString(propertiesFileName, "mongo_url_pool_prod_front");
	//要连接的db
	private static String mongo_db = PropertiesUtil.getString(propertiesFileName, "mongo_db_pool_prod_front");
	private static String isSingle = PropertiesUtil.getString(propertiesFileName, "mongo_isSingle_pool");

	// 链接池数量
	private static String connectionsPerHost = PropertiesUtil.getString(propertiesFileName, "connectionsPerHost_pool");
	// 最大等待时间
	private static String maxWaitTime = PropertiesUtil.getString(propertiesFileName, "maxWaitTime_pool");
	// scoket超时时间
	private static String socketTimeout = PropertiesUtil.getString(propertiesFileName, "socketTimeout_pool");
	// 设置连接池最长生命时间
	private static String maxConnectionLifeTime = PropertiesUtil.getString(propertiesFileName, "maxConnectionLifeTime_pool");
	// 连接超时时间
	private static String connectTimeout = PropertiesUtil.getString(propertiesFileName, "connectTimeout_pool");

	// 单例 [懒]
	private final static MongoPoolMangerWithOutAuth instance = new MongoPoolMangerWithOutAuth();

	private MongoClient mongoClient = null;

	public static MongoPoolMangerWithOutAuth getInstance() {
		return instance;
	}

	public MongoDatabase getMongoDatabase(String dbName) {
		return mongoClient.getDatabase(dbName);
	}

	//ybsdb
	public MongoDatabase getDefalutMongoDatabase() {
		return mongoClient.getDatabase(mongo_db);
	}

	/**
	 * @desc 构造函数，创建mongoClient、mongoDatabase
	 */
	private MongoPoolMangerWithOutAuth() {

		MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(Integer.parseInt(connectionsPerHost)).maxWaitTime(Integer.parseInt(maxWaitTime)).socketTimeout(Integer.parseInt(socketTimeout)).maxConnectionLifeTime(Integer.parseInt(maxConnectionLifeTime))
				.connectTimeout(Integer.parseInt(connectTimeout)).build();

		//根据配置获取当前配置为单点还是集群，单点集群是不一样的！！！
		if("Y".equals(isSingle)){
			logger.info("----单点模式----,访问的url={}", mongo_url);
			mongoClient = new MongoClient(new ServerAddress(mongo_url), options);
		}else{
			logger.info("----集群模式----,访问的url={}", mongo_url);
			//判断url的配置有没有问题
			String[] urls = mongo_url.split(",");
			if(urls == null || urls.length < 2){
				logger.error("请检查mongo集群的url配置是否正确");
			}
			List<ServerAddress> hosts = new ArrayList<ServerAddress>();
			for (String url : urls) {
				hosts.add(new ServerAddress(url));
			}
			mongoClient = new MongoClient(hosts, options);
		}
//		mongoDatabase = mongoClient.getDatabase(mongo_db);
	}

	public static void main(String[] args) {
		MongoDatabase mdb = MongoPoolMangerWithOutAuth.getInstance().getDefalutMongoDatabase();
		MongoCollection<Document> dbCollection = mdb.getCollection("acp_callback_flow20190128");
//		Document document2 = new Document();
//		document2.put("uuid", "2019088888");
//		document2.put("_id", "201905051124");
//		dbCollection.insertOne(document2);
		MongoCursor<Document> dbCursor = dbCollection.find(Filters.eq("encoding", "GBK")).iterator();
		while (dbCursor.hasNext()) {
			System.out.println(dbCursor.next());
		}
	}
}
