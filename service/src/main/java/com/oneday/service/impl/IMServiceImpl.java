package com.oneday.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.oneday.common.util.CheckSumBuilder;
import com.oneday.constant.ConfigConstant;
import com.oneday.constant.ErrorCodeEnum;
import com.oneday.domain.po.User;
import com.oneday.exceptions.OndayException;
import com.oneday.service.IMService;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/7/11 16:13
 */
@Service("iMService")
public class IMServiceImpl implements IMService {

    @Value("${netease.im.url.create}")
    private String createUrl ;
    @Value("${netease.im.url.update}")
    private String updateUrl ;
    @Value("${netease.im.url.refreshToken}")
    private String refreshTokenUrl ;
    @Value("${netease.im.url.block}")
    private String blockUrl ;
    @Value("${netease.im.url.unblock}")
    private String unblockUrl ;

    @Value("${netease.app.key}")
    private String appKey;
    @Value("${netease.app.secret}")
    private String appSecret;


    @Override
    public JSONObject create(User user) {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        JSONObject res = null;
        try {
            String curTime = String.valueOf((new Date()).getTime() / 1000L);
            String nonce =  curTime+"12345";
            String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码
            HttpPost httpPost = getHttpPost(createUrl);
            // 设置请求的header
            httpPost.addHeader("AppKey", appKey);
            httpPost.addHeader("Nonce", nonce);
            httpPost.addHeader("CurTime", curTime);
            httpPost.addHeader("CheckSum", checkSum);
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            // 设置请求的参数
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("accid", user.getId()+""));
            nvps.add(new BasicNameValuePair("name", user.getName()));
            nvps.add(new BasicNameValuePair("icon", user.getHead()));
            nvps.add(new BasicNameValuePair("token", CheckSumBuilder.getMD5(user.getId()+curTime)));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
            // 执行请求
            HttpResponse response = closeableHttpClient.execute(httpPost);
            res = JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"));
            if (res == null || !"200".equals(res.getString("code")) ){
                throw new OndayException(ErrorCodeEnum.THIRD_SERVICE_FAIL.getCode(), "创建通信ID错误");
            }
            return res;
        } catch (IOException e) {
            e.printStackTrace();
            throw new OndayException(ErrorCodeEnum.THIRD_SERVICE_FAIL.getCode(), "调用创建网易云通信ID发生IO错误",e);
        } finally {
            try {
                //关闭流并释放资源
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    private HttpPost getHttpPost(String url) {
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(ConfigConstant.IM_CONNCET_TIMEOUT).setConnectionRequestTimeout(ConfigConstant.IM_CONNCET_REQUEST_TIMEOUT)
                .setSocketTimeout(ConfigConstant.IM_SOCKET_TIMEOUT).build();
        httpPost.setConfig(requestConfig);
        return httpPost;
    }

    @Override
    public JSONObject update(User user) {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();;
        JSONObject res = null;
        try {
            String curTime = String.valueOf((new Date()).getTime() / 1000L);
            String nonce =  curTime+"12345";
            String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码
            HttpPost httpPost = getHttpPost(updateUrl);
            // 设置请求的header
            httpPost.addHeader("AppKey", appKey);
            httpPost.addHeader("Nonce", nonce);
            httpPost.addHeader("CurTime", curTime);
            httpPost.addHeader("CheckSum", checkSum);
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            // 设置请求的参数
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("accid", user.getId()+""));
            nvps.add(new BasicNameValuePair("token", CheckSumBuilder.getMD5(user.getId()+curTime)));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

            // 执行请求
            HttpResponse response = closeableHttpClient.execute(httpPost);
            res = JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"));
            if (res == null || !"200".equals(res.getString("code")) ){
                throw new OndayException(ErrorCodeEnum.THIRD_SERVICE_FAIL.getCode(), "更新通信ID错误");
            }

            return res;
        } catch (IOException e) {
            e.printStackTrace();
            throw new OndayException(ErrorCodeEnum.THIRD_SERVICE_FAIL.getCode(), "更新网易云通信ID发生IO错误",e);
        } finally {
            try {
                //关闭流并释放资源
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public JSONObject refresh(User user) {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();;
        JSONObject res = null;
        try {
            String curTime = String.valueOf((new Date()).getTime() / 1000L);
            String nonce =  curTime+"12345";
            String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码
            HttpPost httpPost = getHttpPost(refreshTokenUrl);

            // 设置请求的header
            httpPost.addHeader("AppKey", appKey);
            httpPost.addHeader("Nonce", nonce);
            httpPost.addHeader("CurTime", curTime);
            httpPost.addHeader("CheckSum", checkSum);
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            // 设置请求的参数
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("accid", user.getId()+""));
            nvps.add(new BasicNameValuePair("token", CheckSumBuilder.getMD5(user.getId()+curTime)));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

            // 执行请求
            HttpResponse response = closeableHttpClient.execute(httpPost);
            res = JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"));
            if (res == null || !"200".equals(res.getString("code")) ){
                throw new OndayException(ErrorCodeEnum.THIRD_SERVICE_FAIL.getCode(), "刷新通信ID错误");
            }
            return res;
        } catch (IOException e) {
            e.printStackTrace();
            throw new OndayException(ErrorCodeEnum.THIRD_SERVICE_FAIL.getCode(), "调用刷新网易云通信ID发生IO错误",e);
        } finally {
            try {
                //关闭流并释放资源
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public JSONObject block(User user) {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();;
        JSONObject res = null;
        try {
            String curTime = String.valueOf((new Date()).getTime() / 1000L);
            String nonce =  curTime+"12345";
            String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码
            HttpPost httpPost = getHttpPost(blockUrl);

            // 设置请求的header
            httpPost.addHeader("AppKey", appKey);
            httpPost.addHeader("Nonce", nonce);
            httpPost.addHeader("CurTime", curTime);
            httpPost.addHeader("CheckSum", checkSum);
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            // 设置请求的参数
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("accid", user.getId()+""));
            nvps.add(new BasicNameValuePair("needkick", "true"));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

            // 执行请求
            HttpResponse response = closeableHttpClient.execute(httpPost);
            res = JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"));
            if (res == null || !"200".equals(res.getString("code")) ){
                throw new OndayException(ErrorCodeEnum.THIRD_SERVICE_FAIL.getCode(), "封禁通信错误");
            }

            return res;
        } catch (IOException e) {
            e.printStackTrace();
            throw new OndayException(ErrorCodeEnum.THIRD_SERVICE_FAIL.getCode(), "调用封禁网易云通信ID发生IO错误",e);
        } finally {
            try {
                //关闭流并释放资源
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public JSONObject unblock(User user) {
        return null;
    }

    public static void main(String[] args) throws Exception{
//        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpClient httpClient = HttpClients.createDefault();;
        String url = "https://api.netease.im/nimserver/user/create.action";
        HttpPost httpPost = new HttpPost(url);

        String appKey = "94kid09c9ig9k1loimjg012345123456";
        String appSecret = "123456789012";
        String nonce =  "12345";
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码

        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("accid", "helloworld"));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);

        // 打印执行结果
        System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));
    }


}
