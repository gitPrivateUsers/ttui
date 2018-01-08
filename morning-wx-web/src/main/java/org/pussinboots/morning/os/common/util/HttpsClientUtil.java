package org.pussinboots.morning.os.common.util;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
/**
 * 项目名称：xm-web-admin-cms
 * 类名称：HttpsClientUtil 
 * 类描述：调用远程接口工具类
 * 创建人：张国浩 
 * 创建时间：日期：2018年1月4日 时间：下午12:55:35   
 */
public class HttpsClientUtil {

	//日志
    public static Logger log = Logger.getLogger(HttpsClientUtil.class);
    //编码方式
    private static String UTF8 = "UTF-8";
    //数据格式
    private final String APPLICATION_JSON = "application/json";
    //数据类型标识
     public final String CONTENT_TYPE = "Content-Type";
    //https请求客户端    
    private CloseableHttpClient  httpclient = null;  
    
    private static HttpsClientUtil httpsClientUtil;
    
    private HttpsClientUtil(){
        try{
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{
                //证书信任管理器（用于https请求）
                new X509TrustManager(){
                    @Override
                    public void checkClientTrusted(X509Certificate[] arg0,
                            String arg1) throws CertificateException {
                    }
                    @Override
                    public void checkServerTrusted(X509Certificate[] arg0,
                            String arg1) throws CertificateException {
                    }
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                }
            }, new SecureRandom());
            //获取注册建造者
            RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.create();
            //注册http和https请求
            Registry<ConnectionSocketFactory> socketFactoryRegistry  = registryBuilder.register("http", PlainConnectionSocketFactory.INSTANCE)
                                                                     .register("https", new SSLConnectionSocketFactory(sslContext))
                                                                     .build();    
            //获取HttpClient池管理者
            PoolingHttpClientConnectionManager connManager  = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            //初始化httpClient
            httpclient = HttpClients.custom().setConnectionManager(connManager).build();
        }catch(KeyManagementException e){
            e.printStackTrace();    
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    };
    
    /**
     * 创建httpsClientUtil对象
     * @return
     */
    public static HttpsClientUtil getInstance(){
        if(httpsClientUtil==null){
            httpsClientUtil = new HttpsClientUtil();
        }
        return httpsClientUtil;
    }
    
    /**
     * 描述:  发送post or get请求并获取结果
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public JSONObject  sendRequest(String requestUrl, String requestMethod, String outputStr){
        String responseObj = null;
        CloseableHttpResponse execute = null;
        try{
            if("POST".equals(requestMethod)){
                HttpPost httpPost = new HttpPost(requestUrl);
                httpPost.addHeader(CONTENT_TYPE, APPLICATION_JSON);
                // 将JSON字符串进行UTF-8编码,以便传输中文
                StringEntity requestEntity = new StringEntity(outputStr,HttpsClientUtil.UTF8);
                httpPost.setEntity(requestEntity);
                execute = httpclient.execute(httpPost);
            }else{
                HttpGet httpGet = new HttpGet(requestUrl);
                httpGet.addHeader(CONTENT_TYPE, APPLICATION_JSON);
                execute = httpclient.execute(httpGet);
            }
            HttpEntity responseEntity = execute.getEntity();
            if(responseEntity!=null){
                responseObj = EntityUtils.toString(responseEntity,HttpsClientUtil.UTF8);
            }
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                //关闭响应流
                if(execute!=null) execute.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.info("远程接口响应:"+responseObj);
        return JSONObject.fromObject(responseObj);
    }
    
    /**
     * 描述:  发送post请求并获取结果
     * @param requestUrl 请求地址
     * @param requestUrlParam 请求地址拼接参数
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public JSONObject sendPostRequest(String requestUrl,Map<String,String> requestUrlParam, String outputStr){
        return sendRequest(requestUrlParam(requestUrl, requestUrlParam), "POST", outputStr);
    }
    
    /**
     * 描述:  发送get请求并获取结果
     * @param requestUrl 请求地址
     * @param requestUrlParam 请求地址拼接参数
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public JSONObject sendGetRequest(String requestUrl,Map<String,String> requestUrlParam){
        return sendRequest(requestUrlParam(requestUrl, requestUrlParam), "GET", null);
    }
    
    /**
     * 描述：拼接URL后接参数
     * @param requestUrl 请求地址
     * @param requestUrlParam 请求地址拼接参数
     * @return 带参数请求地址
     */
    public String requestUrlParam(String requestUrl,Map<String,String> requestUrlParam){
        if(requestUrlParam==null){
            return requestUrl;
        }
        String requestParam = "";
        for (Entry<String, String> entry : requestUrlParam.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            requestParam += "&"+key+"="+value;
        }
        if(requestParam.length()>0){
            if(requestUrl.indexOf("?")==-1){
                requestUrl = requestUrl+"?"+requestParam.substring(1);
            }else{
                requestUrl = requestUrl+requestParam;
            }
        }
        return requestUrl;
    }

}



