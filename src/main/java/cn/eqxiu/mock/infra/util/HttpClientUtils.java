package cn.eqxiu.mock.infra.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class HttpClientUtils {



    /**
     * post请求传输json参数
     *
     * @param url
     *            url地址
     * @param jsonParam
     *            参数
     * @return
     */
    public static JSONObject httpPost(String url, Map<String,String> header, JSONObject jsonParam) {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonResult = null;
        HttpPost httpPost = new HttpPost(url);
        if (null != header && header.size() > 0) {
            for (String key:header.keySet()) {
                httpPost.setHeader(key,header.get(key));
            }
        }
        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(2000).setConnectTimeout(2000).build();
        httpPost.setConfig(requestConfig);
        try {
            if (null != jsonParam) {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(),
                        "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPost);
            //请求发送成功，并得到响应
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                try {
                    //读取服务器返回过来的json字符串数据
                    String str = EntityUtils.toString(result.getEntity(), "utf-8");
                    //把json字符串转换成json对象
                    jsonResult = JSONObject.parseObject(str);
                } catch (Exception e) {
                }
            }
        } catch (IOException e) {
        } finally {
            httpPost.releaseConnection();
        }
        return jsonResult;
    }


    /**
     * post请求传输String参数
     * 例如：name=Jack&sex=1&type=2
     * Content-type:application/x-www-form-urlencoded
     * @param url
     *            url地址
     * @param params
     *            参数
     *
     * @return
     */
    public static JSONObject httpPost(String url, Map<String,String> header, Map<String,String> params) {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonResult = null;
        String param = "";
        if (null != params && params.size() > 0) {
            StringBuffer buffer = new StringBuffer("?");
            for (String key:params.keySet()) {
                buffer.append(key);
                buffer.append("=");
                buffer.append(params.get(key));
                buffer.append("&");
            }
            param = buffer.toString();
            param = param.substring(0,param.lastIndexOf("&"));
        }
        HttpPost httpPost = new HttpPost(url + param);
        if (null != header && header.size() > 0) {
            for (String key:header.keySet()) {
                httpPost.setHeader(key,header.get(key));
            }
        }
        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(200000).setConnectTimeout(200000).build();
        httpPost.setConfig(requestConfig);
        try {
            if (null != params) {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity("utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/x-www-form-urlencoded");
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPost);
            //请求发送成功，并得到响应
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                try {
                    //读取服务器返回过来的json字符串数据
                    String str = EntityUtils.toString(result.getEntity(), "utf-8");
                    //把json字符串转换成json对象
                    jsonResult = JSONObject.parseObject(str);
                } catch (Exception e) {
                }
            }
        } catch (IOException e) {
        } finally {
            httpPost.releaseConnection();
        }
        return jsonResult;
    }

    /**
     * 发送get请求
     *
     * @param url
     *            路径
     * @return
     */
    public static JSONObject httpGet(String url, Map<String,String> header, Map<String,String> params) {
        // get请求返回结果
        JSONObject jsonResult = null;
//        CloseableHttpClient client = HttpClients.createDefault();
        CookieStore store = new BasicCookieStore();
        CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(store).build();
        // 发送get请求
        String param = "";
        if (null != params && params.size() > 0) {
            StringBuffer buffer = new StringBuffer("?");
            for (String key:params.keySet()) {
                buffer.append(key);
                buffer.append("=");
                buffer.append(params.get(key));
                buffer.append("&");
            }
            param = buffer.toString();
            param = param.substring(0,param.lastIndexOf("&"));
        }
        HttpGet request = new HttpGet(url + param);
        if (null != header && header.size() > 0) {
            for (String key:header.keySet()) {
                request.setHeader(key,header.get(key));
            }
        }
        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(2000).setConnectTimeout(2000).build();
        request.setConfig(requestConfig);
        try {
            CloseableHttpResponse response = client.execute(request);
            //请求发送成功，并得到响应
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //读取服务器返回过来的json字符串数据
                HttpEntity entity = response.getEntity();
                String strResult = EntityUtils.toString(entity, "utf-8");
                //把json字符串转换成json对象
                try {
                    jsonResult = JSONObject.parseObject(strResult);
                } catch (Exception e) {
//                    这个是内嵌定制的从cookie获取JSESSIONID
                    List<Cookie> cookies = store.getCookies();
                    for (Cookie cookie: cookies) {
                        if (cookie.getName().equals("JSESSIONID")) {
                            jsonResult = new JSONObject();
                            jsonResult.put("success",true);
                            jsonResult.put("JSESSIONID",cookie.getValue());
                            jsonResult.put("result",strResult);
                        }
                    }
                }
            } else {
            }
        } catch (IOException e) {
        } finally {
            request.releaseConnection();
        }
        return jsonResult;
    }

    /**
     * 发送get请求
     *
     * @param url
     *            路径
     * @return
     */
    public static String sendGet(String url,Map<String,String> header,Map<String,String> params) {
        // get请求返回结果
        String strResult = "";
        CloseableHttpClient client = HttpClients.createDefault();
        // 发送get请求
        String param = "";
        if (null != params && params.size() > 0) {
            StringBuffer buffer = new StringBuffer("?");
            for (String key:params.keySet()) {
                buffer.append(key);
                buffer.append("=");
                buffer.append(params.get(key));
                buffer.append("&");
            }
            param = buffer.toString();
            param = param.substring(0,param.lastIndexOf("&"));
        }
        HttpGet request = new HttpGet(url + param);
        if (null != header && header.size() > 0) {
            for (String key:header.keySet()) {
                request.setHeader(key,header.get(key));
            }
        }
        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(2000).setConnectTimeout(2000).build();
        request.setConfig(requestConfig);
        try {
            CloseableHttpResponse response = client.execute(request);
            //请求发送成功，并得到响应
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //读取服务器返回过来的json字符串数据
                HttpEntity entity = response.getEntity();
                strResult = EntityUtils.toString(entity, "utf-8");
            } else {
            }
        } catch (IOException e) {
        } finally {
            request.releaseConnection();
        }
        return strResult;
    }
}
