package common;

import com.thoughtworks.xstream.XStream;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GuangDaUtils {

    private static final Logger logger = LoggerFactory.getLogger(GuangDaUtils.class);

    private static final int CACHE_SIZE = 1024;

    public static byte[] decode(String base64) throws Exception {
        return new Base64().decode(base64);
    }

    public static String encode(byte[] bytes) throws Exception {
        return new Base64().encodeToString(bytes);
    }

    public static String encodeFile(File file) throws Exception {
        byte[] bytes = fileToByte(file);
        return encode(bytes);
    }


    public static byte[] fileToByte(File file) throws Exception {
        byte[] data = new byte[0];
        if (!file.exists()) {
            return data;
        }

        FileInputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = new FileInputStream(file);
            out = new ByteArrayOutputStream(2048);
            byte[] cache = new byte[CACHE_SIZE];
            int nRead = 0;
            while ((nRead = in.read(cache)) != -1) {
                out.write(cache, 0, nRead);
                out.flush();
            }

            return out.toByteArray();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }

            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
    }

    private static final String CHARSET = "GBK";
    private static final int TIMEOUT = 5 * 1000 * 60;
    private static final int CONNECTION_TIMEOUT = 5 * 1000 * 60;
    private static final int SO_TIMEOUT = 5 * 1000 * 60;
    private static HttpClient httpClient;

    private static HttpClient getHttpClient() {
        if (null == httpClient) {
            synchronized (GuangDaUtils.class) {
                if (null == httpClient) {
                    HttpParams httpParams = new BasicHttpParams();
                    httpParams.setParameter("http.method.retry-handler", new DefaultHttpRequestRetryHandler());
                    httpParams.setBooleanParameter("http.connection.stalecheck", false);
                    //参数设置
                    HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
                    HttpProtocolParams.setContentCharset(httpParams, CHARSET);
                    HttpProtocolParams.setUseExpectContinue(httpParams, true);
                    //超时设置
                    ConnManagerParams.setTimeout(httpParams, TIMEOUT);
                    //连接超时
                    HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMEOUT);
                    //请求超时
                    HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);

                    //设置支持HTTP和HTTPS
                    SchemeRegistry schemeReg = new SchemeRegistry();
                    schemeReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
                    schemeReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
                    //使用线程安全的连接创建HttpClient
                    ClientConnectionManager conManager = new ThreadSafeClientConnManager(httpParams, schemeReg);
                    httpClient = new DefaultHttpClient(conManager, httpParams);
                }
            }
        }
        return httpClient;
    }

    public static String post(String url, Map<String, String> map) {
        HttpResponse response = null;
        try {
            if (StringUtils.isEmpty(url) || null == map || map.isEmpty()) {
                return null;
            }
            //创建POST请求
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-type", (new StringBuilder()).append("application/x-www-form-urlencoded; charset=").append(CHARSET).toString());
            post.setHeader("Accept", new StringBuffer("text/xml;charset=").append(CHARSET).toString());
            post.setHeader("Cache-Control", "no-cache");

            List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
            //请求参数
            for (String key : map.keySet()) {
                params.add(new BasicNameValuePair(key, map.get(key)));
            }
            post.setEntity(new UrlEncodedFormEntity(params, CHARSET));

            //发送请求
            HttpClient client = getHttpClient();
            response = client.execute(post);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                EntityUtils.consume(response.getEntity());
                return null;
            }

            return EntityUtils.toString(response.getEntity(), CHARSET);
        } catch (Exception e) {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException ex) {
                    logger.error("post consume error", ex);
                }
            }
            logger.error("post error", e);
        }
        return null;
    }

    private static final String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    private static volatile Map<Class, XStream> xStreamMap = new ConcurrentHashMap<Class, XStream>();
    private static final XStream writer = new XStream();

    static {
        XStream.setupDefaultSecurity(writer);
    }

    /**
     * 反序列化XML
     *
     * @param xml
     * @param clazz
     * @return
     */
    public static <T> T fromXML(String xml, Class<T> clazz) {
        XStream xStream = xStreamMap.get(clazz);
        if (xStream == null) {
            synchronized (GuangDaUtils.class) {
                xStream = new XStream();
                XStream.setupDefaultSecurity(xStream);
                xStream.allowTypeHierarchy(clazz);
                xStream.processAnnotations(clazz);
                xStreamMap.put(clazz, xStream);
            }
        }
        return (T) xStream.fromXML(xml);
    }

    /**
     * 序列化XML
     *
     * @param obj
     * @return
     */
    public static String toXML(Object obj) {
        writer.processAnnotations(obj.getClass());
        return XML_DECLARATION + writer.toXML(obj);
    }

}
