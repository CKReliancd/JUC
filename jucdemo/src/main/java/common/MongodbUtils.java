/**
 * 
 */
package common;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * @Package: com.ybs.web.commons
 * @author wukun
 * @date: 2019年1月14日 下午4:51:16  
 */
@Deprecated
public class MongodbUtils {
	private static MongoDatabase database = null;
	private static String mongo_url = PropertiesUtil.getString("datasource", "mongo_url_write");
	private static int mongo_port = PropertiesUtil.getInt("datasource", "mongo_port_write");
	private static String mongo_db = PropertiesUtil.getString("datasource", "mongo_db_write");
	private static String mongoNeedPassword = PropertiesUtil.getString("datasource", "mongo_need_password");
	private static String mongoName = PropertiesUtil.getString("datasource", "mongo_Name");
	private static String mongoPassword = PropertiesUtil.getString("datasource", "mongo_passWord");
	
	/**
	 * 获取mongo的database
	 * 
	 * @return
	 */
//	@SuppressWarnings("resource")
//	public static MongoDatabase getMongoDatabase() {
//		try {
//			MongoClient mongoClient = null;
//			if (mongoNeedPassword.equals("1")) {
//				ServerAddress serverurl = new ServerAddress(mongo_url, mongo_port);
//				List<ServerAddress> lists = new ArrayList<ServerAddress>();
//				lists.add(serverurl);
//				MongoCredential credential = MongoCredential.createCredential(mongoName, "admin",
//						mongoPassword.toCharArray()); // admin 认证使用的db 不是需要查询的db
//														// mongo认证的用户（如：mongoName）必须加到admin库中
//				List<MongoCredential> listm = new ArrayList<MongoCredential>();
//				listm.add(credential);
//				mongoClient = new MongoClient(lists, listm); // new
//																// MongoClient(url,
//			} else {
//				mongoClient = new MongoClient(mongo_url, mongo_port);
//			}
//			database = mongoClient.getDatabase(mongo_db);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return database;
//	}

	/**
	 * 获取mongo的collection
	 * 
	 * @param tableName
	 * @return
	 */
//	public static MongoCollection<Document> getMongoCollection(String tableName) {
//		MongoCollection<Document> collection = null;
//		try {
//			MongoDatabase mongoDatabase = getMongoDatabase();
//			collection = mongoDatabase.getCollection(tableName);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return collection;
//	}
	
	
	
	/**
	 * 获取mongo表里面的数据总数
	 * 
	 * @param basicDBObject
	 * @return
	 */
//	public long getMongoCollectionTotalCounts(String mongodbCollection) {
//		MongoCollection<Document> mongoCollection= getMongoCollection(mongodbCollection);
//		int count = 0;
//		try {
//			count = (int) mongoCollection.count();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return count;
//	}
}
