package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ybs.web.commons.BaseHttpSSLSocketFactory.TrustAnyHostnameVerifier;
import com.ybs.web.dataserver.entity.nsec.TerminalSecret;

//import org.json.JSONObject;
import net.sf.json.JSONObject;
//import org.json.JSONObject;

@Service
public class HttpClient {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public Map<String, Object> httpGet(TerminalSecret secret) { //"merchantid=0004403241422223333&terminalid=44445555&tmkkek=87DB6F8F3E27B67F87DB6F8F3E27B67F
        CloseableHttpClient httpclient = HttpClients.createDefault();
        boolean flag = true;
        String url = null;
        StringBuilder sb = new StringBuilder();
        Map<String, Object> reslut = new HashMap<>();
        JSONObject resultJsonObject = null;

        try {
            try {
                Properties prop = new Properties();
                InputStream in = HttpClient.class.getClassLoader().getResource("datasource.properties").openStream();
                //getResourceAsStream( "/datasource.properties" );
                prop.load(in);
                url = prop.getProperty("http_nsec_url").toString().trim();
            } catch (IOException e) {
                e.printStackTrace();
            }

            sb.append("merchantid=").append(secret.getMerchantId().trim()).append("&terminalid=").append(secret.getTerminal().trim());
            sb.append("&tmkkek=").append(secret.getSecretKey().trim());
            sb.append("&actionscope=").append(secret.getSecretType());
            sb.append("&org=").append(secret.getOrg().trim());
            // 创建httpget.
            HttpGet httpget = new HttpGet(url + "?" + sb.toString());

            logger.info("uri  " + httpget.getURI());

            // logger.d("executing request " + httpget.getURI());
            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                //  System.out.println("----------------------response");
                // 打印响应状态
                logger.info("status  " + response.getStatusLine());
                int statusCode = response.getStatusLine().getStatusCode();
                logger.debug("statusCode  " + statusCode);
                if (statusCode == HttpStatus.SC_OK) {
                    // 打印响应内容
                    //System.out.println("Response content: " + EntityUtils.toString(entity));
                    if (entity != null) {
                        try {
                            InputStream instreams = entity.getContent();
                            String str = convertStreamToString(instreams);
                            httpget.abort();
                            //利用从HttpEntity中得到的String生成JsonObject
                            resultJsonObject = JSONObject.fromObject(str);
                            if (!resultJsonObject.get("retcode").equals("0000")) {
                                reslut.put("msg", resultJsonObject.get("retmsg"));
                                flag = false;
                                reslut.put("code", flag);
                            }
                            logger.info("Response content: " + str);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                logger.info("----------------------------------" + flag);
            } finally {
                response.close();
            }
            // return reslut;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            logger.info("---------------------------连接失败");
            reslut.put("msg", "加密机连接失败");
        } catch (ParseException e) {
            e.printStackTrace();
            flag = false;
            reslut.put("msg", "加密机连接失败");
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
            reslut.put("msg", "加密机连接失败");
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        reslut.put("code", flag);
        return reslut;
    }

    public static String convertStreamToString(InputStream is) {
        StringBuilder sb = new StringBuilder();
        byte[] bytes = new byte[4096];
        int size = 0;

        try {
            while ((size = is.read(bytes)) > 0) {
                String str = new String(bytes, 0, size, "GBK");
                sb.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 查询es数据
     *
     * @param sb
     * @param url
     * @return
     */
    public JSONObject httpGetFindEs(String url, StringBuilder sb, String userName, String password) {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        ;
        JSONObject json = null;
        HttpPost httpPost = null;
        try {
            // 创建httpget.
            httpPost = new HttpPost(url);
            logger.debug("uri  " + httpPost.getURI());
            logger.debug("sb  " + sb.toString());
            StringEntity entity = new StringEntity(sb.toString(), Charset.forName("UTF-8"));
            /*设置http请求头*/
            httpPost.setHeader("accept", "*/*");
            httpPost.addHeader("connection", "Keep-Alive");
            httpPost.setHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            httpPost.addHeader("Authorization", "Basic " + Base64.encodeBase64String(((userName + ":" + password).getBytes())));
            logger.info("entity  " + entity.toString());
            httpPost.setEntity(entity);
            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity resentity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();
            logger.debug("status  " + statusCode);
            if (statusCode == HttpStatus.SC_OK) {
                if (response != null) {
                    if (resentity != null) {
                        String result = EntityUtils.toString(resentity, "utf-8");
                        logger.info("result:" + result);
                        json = JSONObject.fromObject(result);
                    }
                }
            } else {
                logger.debug("es  response fail  " + statusCode);
            }


        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return json;
    }

    public JSONObject httpGetFindTianyancha(String url, String name) {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        ;
        JSONObject json = null;
        HttpGet get = null;
        try {
            // 创建httpget.
            get = new HttpGet(url + "?name=" + name);
            //get.setHeader("Authorization", TianyanchaHttpGet.token);
            logger.info("uri  " + get.getURI());
            logger.info("name  " + name);
            /*设置http请求头*/
            get.setHeader("accept", "*/*");
            get.addHeader("connection", "Keep-Alive");
            get.setHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            get.addHeader("Authorization", TianyanchaHttpGet.token);

            HttpResponse response = httpclient.execute(get);
            HttpEntity resentity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();
            logger.info("status  " + statusCode);
            if (statusCode == HttpStatus.SC_OK) {
                if (response != null) {
                    if (resentity != null) {
                        String result = EntityUtils.toString(resentity, "utf-8");
                        logger.info("result:" + result);
                        json = JSONObject.fromObject(result);
                    }
                }
            } else {
                logger.debug("es  response fail  " + statusCode);
            }


        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return json;
    }

    /**
     * 查询es数据
     * 处理大金额，防止金额转化过程丢失精度
     *
     * @param sb
     * @param url
     * @return
     */
    public com.alibaba.fastjson.JSONObject httpGetFindEsToSolveBigAmount(String url, StringBuilder sb, String userName, String password) {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        ;
        com.alibaba.fastjson.JSONObject parseObject = null;
        HttpPost httpPost = null;
        try {
            // 创建httpget.
            httpPost = new HttpPost(url);
            logger.debug("uri  " + httpPost.getURI());
            logger.debug("sb  " + sb.toString());
            StringEntity entity = new StringEntity(sb.toString(), Charset.forName("UTF-8"));
            /*设置http请求头*/
            httpPost.setHeader("accept", "*/*");
            httpPost.addHeader("connection", "Keep-Alive");
            httpPost.setHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            httpPost.addHeader("Authorization", "Basic " + Base64.encodeBase64String(((userName + ":" + password).getBytes())));
            logger.info("entity  " + entity.toString());
            httpPost.setEntity(entity);
            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity resentity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();
            logger.debug("status  " + statusCode);
            if (statusCode == HttpStatus.SC_OK) {
                if (response != null) {
                    if (resentity != null) {
                        String result = EntityUtils.toString(resentity, "utf-8");
                        logger.info("result:" + result);
                        parseObject = com.alibaba.fastjson.JSONObject.parseObject(result);
                        logger.info("parseObject:" + parseObject);
                    }
                }
            } else {
                logger.debug("es  response fail  " + statusCode);
            }


        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return parseObject;
    }


    /**
     * 查询es数据
     * 处理大金额，防止金额转化过程丢失精度
     *
     * @param sb
     * @param url
     * @return
     */

    public static com.alibaba.fastjson.JSONObject httpGetFindEsToSolveBigAmountByString(String url, String sb, String userName, String password) {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        ;
        com.alibaba.fastjson.JSONObject parseObject = null;
        HttpPost httpPost = null;
        try {
            // 创建httpget.
            httpPost = new HttpPost(url);
            StringEntity entity = new StringEntity(sb.toString(), Charset.forName("UTF-8"));
            /*设置http请求头*/
            httpPost.setHeader("accept", "*/*");
            httpPost.addHeader("connection", "Keep-Alive");
            httpPost.setHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            httpPost.addHeader("Authorization", "Basic " + Base64.encodeBase64String(((userName + ":" + password).getBytes())));
            httpPost.setEntity(entity);
            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity resentity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                if (response != null) {
                    if (resentity != null) {
                        String result = EntityUtils.toString(resentity, "utf-8");
                        parseObject = com.alibaba.fastjson.JSONObject.parseObject(result);
                    }
                }
            } else {
            }


        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return parseObject;
    }

    /**
     * 查询es数据
     *
     * @param sb
     * @param url
     * @return
     */
    public static JSONObject PostByHttpClient(String url, StringBuilder sb, Map<String, String> header) {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        ;
        JSONObject json = new JSONObject();
        HttpPost httpPost = null;
        Logger logger = LoggerFactory.getLogger("com.ybs.web.commons.HttpClient");
        try {
            // 创建httpget.
            httpPost = new HttpPost(url);
            logger.info("uri  " + httpPost.getURI());
            logger.info("sb  " + Aes128.getInstance().encrypt(sb.toString()));
            StringEntity entity = new StringEntity(sb.toString(), Charset.forName("gbk"));
            /*设置http请求头*/
            httpPost.setHeader("accept", "*/*");
            httpPost.addHeader("connection", "Keep-Alive");
            httpPost.setHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            Iterator<Map.Entry<String, String>> iter = header.entrySet().iterator();
            if (!header.isEmpty()) {  //判断是否需要设置请求头参数，如需要 则遍历map集合取出所有参数
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = iter.next();
                    httpPost.setHeader(entry.getKey(), entry.getValue());  //设置请求头信息
                }
            }
            logger.info("entity  " + Aes128.getInstance().encrypt(entity.toString()));
            httpPost.setEntity(entity);
            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity resentity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();
            logger.info("status  " + statusCode);
            if (statusCode == HttpStatus.SC_OK) {
                if (response != null) {
                    if (resentity != null) {
                        String result = EntityUtils.toString(resentity, "gbk");
                        json = JSONObject.fromObject(result);
                        logger.info("resentity  " + json);
                    }
                }
            } else {
                logger.info("es  response fail  " + statusCode);
            }


        } catch (Exception e) {
            e.printStackTrace();
            json.put("retcode", "9999");
            json.put("retmsg", "连接异常");
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return json;
    }


    /**
     * 发送httpget请求  使用httpclint工具包
     *
     * @param sb     请求的参数信息
     * @param url    请求的地址
     * @param header 请求头需要设置的自定义参数
     * @return
     */
    public static JSONObject httpSendGet(String url, StringBuilder sb, Map<String, String> header) {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        Logger logger = LoggerFactory.getLogger("com.ybs.web.commons.HttpClient");
        JSONObject json = null;
        HttpGet httpget = null;
        try {
            // 创建httpget.
            httpget = new HttpGet(url + "?" + sb.toString());
            logger.info("url  " + url);
            logger.info("sb  " + sb.toString());
            /*设置http请求头*/
            httpget.setHeader("accept", "*/*");
            httpget.addHeader("connection", "Keep-Alive");
            httpget.setHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            Iterator<Map.Entry<String, String>> iter = header.entrySet().iterator();
            if (!header.isEmpty()) {  //判断是否需要设置请求头参数，如需要 则遍历map集合取出所有参数
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = iter.next();
                    httpget.setHeader(entry.getKey(), entry.getValue());  //设置请求头信息
                }
            }
            CloseableHttpResponse response = httpclient.execute(httpget);
            HttpEntity resentity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();
            logger.info("status  " + statusCode);
            if (statusCode == HttpStatus.SC_OK) {
                if (response != null) {
                    if (resentity != null) {
                        String result = EntityUtils.toString(resentity, "gbk");
                        json = JSONObject.fromObject(result);
                        logger.info("httpSendGet  resentity  " + json);
                    }
                }
            } else {
                logger.info("es  response fail  " + statusCode);
            }


        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return json;
    }


/*	 public JSONObject getJSONObjectByGet(){
	    	JSONObject resultJsonObject=null;

			HttpClient httpClient=new DefaultHttpClient();
			StringBuilder urlStringBuilder=new StringBuilder(uriString);
			StringBuilder entityStringBuilder=new StringBuilder();
			//利用URL生成一个HttpGet请求
			HttpGet httpGet=new HttpGet(urlStringBuilder.toString());
			BufferedReader bufferedReader=null;
			HttpResponse httpResponse=null;
			try {
				//HttpClient发出一个HttpGet请求
				httpResponse=httpClient.execute(httpGet);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//得到httpResponse的状态响应码
			int statusCode=httpResponse.getStatusLine().getStatusCode();
			if (statusCode==HttpStatus.SC_OK) {
				//得到httpResponse的实体数据
				HttpEntity httpEntity=httpResponse.getEntity();
				if (httpEntity!=null) {
					try {
						bufferedReader=new BufferedReader
						(new InputStreamReader(httpEntity.getContent(), "UTF-8"), 8*1024);
					    String line=null;
						while ((line=bufferedReader.readLine())!=null) {
							entityStringBuilder.append(line+"/n");
						}
						//利用从HttpEntity中得到的String生成JsonObject
						resultJsonObject=new JSONObject(entityStringBuilder.toString());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
	    	return resultJsonObject;
	    }*/
    //"merchantid=0004403241422223333&terminalid=44445555&tmkkek=87DB6F8F3E27B67F87DB6F8F3E27B67F


    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        Logger httplogger = LoggerFactory.getLogger("com.ybs.web.commons.HttpClient");
        httplogger.info("url==========" + url + "==========");
        httplogger.info("param==========" + param + "==========");
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "GBK"));
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "GBK"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "{'retcode':'8999','retmsg':'Communication to reject or timeout'}";
            httplogger.info("result==========" + result + "==========");
            return result;
        } finally {//使用finally块来关闭输出流、输入流
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        httplogger.info("result==========" + result + "==========");
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPostWithHeader(String url, String param, String header) {
        Logger httplogger = LoggerFactory.getLogger("com.ybs.web.commons.HttpClient");
        httplogger.info("url==========" + url + "==========");
        httplogger.info("param==========" + param + "==========");
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-type", header);
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "GBK"));
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "GBK"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "{'retcode':'8999','retmsg':'Communication to reject or timeout'}";
            httplogger.info("result==========" + result + "==========");
            return result;
        } finally {//使用finally块来关闭输出流、输入流
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        httplogger.info("result==========" + result + "==========");
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url     发送请求的 URL
     * @param param   请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param charset 指定编码集。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPostCharSet(String url, String param, String charset) {
        Logger httplogger = LoggerFactory.getLogger("com.ybs.web.commons.HttpClient");
        httplogger.info("url==========" + url + "==========");
        httplogger.info("param==========" + param + "==========");
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), charset));
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), charset));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "{'retcode':'8999','retmsg':'Communication to reject or timeout'}";
            httplogger.info("result==========" + result + "==========");
            return result;
        } finally {//使用finally块来关闭输出流、输入流
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        httplogger.info("result==========" + result + "==========");
        return result;
    }


    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url     发送请求的 URL
     * @param param   请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param charset 指定编码集。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPostCharSet(String url, String param, String charset, String userName, String password) {
        Logger httplogger = LoggerFactory.getLogger("com.ybs.web.commons.HttpClient");
        httplogger.info("url==========" + url + "==========");
        httplogger.info("param==========" + param + "==========");
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Authorization", "Basic " + org.apache.commons.codec.binary.Base64.encodeBase64String(((userName + ":" + password).getBytes())));

            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), charset));
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), charset));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "{'retcode':'8999','retmsg':'Communication to reject or timeout'}";
            httplogger.info("result==========" + result + "==========");
            return result;
        } finally {//使用finally块来关闭输出流、输入流
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        httplogger.info("result==========" + result + "==========");
        return result;
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        Logger httplogger = LoggerFactory.getLogger("com.ybs.web.commons.HttpClient");
        httplogger.info("url==========" + url + "==========");
        httplogger.info("param==========" + param + "==========");
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Charsert", "GBK");
            // 建立实际的连接
            connection.connect();
	            /*// 获取所有响应头字段
	            Map<String, List<String>> map = connection.getHeaderFields();
	            // 遍历所有的响应头字段
	            for (String key : map.keySet()) {
	                System.out.println(key + "--->" + map.get(key));
	            }*/
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "GBK"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            httplogger.info("result==========" + result);
        } catch (Exception e) {
            e.printStackTrace();
            httplogger.error("发送GET请求出现异常！" + e.toString());
            result = "error";
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送https POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendHttpsPost(String url, String param) {
        Logger httplogger = LoggerFactory.getLogger("com.ybs.web.commons.HttpClient");
        httplogger.info("url==========" + url + "==========");
        httplogger.info("param==========" + param + "==========");
        StringBuffer buffer = new StringBuffer();
        ;
        try {
		       /* //创建SSLContext  
		        SSLContext sslContext=SSLContext.getInstance("SSL");  
		        TrustManager[] tm={new MyX509TrustManager()};  
		        //初始化  
		        sslContext.init(null, tm, new java.security.SecureRandom());;  
		        //获取SSLSocketFactory对象  
		        SSLSocketFactory ssf=sslContext.getSocketFactory();  
		        URL reqUrl=new URL(url);  
		        HttpsURLConnection conn=(HttpsURLConnection)reqUrl.openConnection();
		        HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {  
		            public boolean verify(String s, SSLSession sslsession) {  
		                return true;  
		            }  
		        };
		        conn.setDefaultHostnameVerifier(ignoreHostnameVerifier);
		        conn.setDoOutput(true);  
		        conn.setDoInput(true);  
		        conn.setUseCaches(false);  
		        conn.setRequestMethod("POST");  
		        //设置当前实例使用的SSLSoctetFactory  
		        conn.setDefaultSSLSocketFactory(ssf);  */
            HttpsURLConnection conn = (HttpsURLConnection) createConnection(url);
            conn.connect();
            //往服务器端写内容
            if (null != param) {
                OutputStream os = conn.getOutputStream();
                os.write(param.getBytes("GBK"));
                os.close();
            }

            //读取服务器端返回的内容
            InputStream is = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "GBK");
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            httplogger.info("发送https请求报错：" + e.getMessage());
            buffer.append("{'retcode':'8999','retmsg':'Communication to reject or timeout'}");
            e.printStackTrace();
        }
        return buffer.toString();
    }

    private static HttpURLConnection createConnection(String url) throws ProtocolException, MalformedURLException {
        URL reqUrl = new URL(url);

        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) reqUrl.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        httpURLConnection.setConnectTimeout(15 * 1000);// 连接超时时间
        httpURLConnection.setReadTimeout(20 * 1000);// 读取结果超时时间
        httpURLConnection.setDoInput(true); // 可读
        httpURLConnection.setDoOutput(true); // 可写
        httpURLConnection.setUseCaches(false);// 取消缓存
        httpURLConnection.setRequestMethod("POST");
        if ("https".equalsIgnoreCase(reqUrl.getProtocol())) {
            HttpsURLConnection husn = (HttpsURLConnection) httpURLConnection;
            //是否验证https证书，测试环境请设置false，生产环境建议优先尝试true，不行再false
            if (!false) {
                husn.setSSLSocketFactory(new BaseHttpSSLSocketFactory());
                husn.setHostnameVerifier(new TrustAnyHostnameVerifier());//解决由于服务器证书问题导致HTTPS无法访问的情况
            }
            return husn;
        }
        return httpURLConnection;
    }

    public static void main(String args[]) {
        HttpClient hp = new HttpClient();
        System.out.println(hp.httpGetFindTianyancha(TianyanchaHttpGet.url_362, "深圳市玫瑰潮搭贸易有限公司"));
    }

    /**
     * 获取主控流水
     *
     * @return mcssn
     */
    public static String getMcssn() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Random rand = new Random();
        int a = rand.nextInt(100);
        return sdf.format(new Date()) + ((a < 10) ? "0" + a : a);
    }
}
