package cn.fitrecipe.android.Http;

/**
 * Created by 奕峰 on 2015/4/25.
 */
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class HttpUtils{

    public static String submitGetData(String url,Map<String, String> params, String encode){

        return null;
    }

    /*
     * Function  :   发送Post请求到服务器
     * Param     :   params请求体内容，encode编码格式
     * Author    :   Crowley
     */
    public static String submitPostData(String url,Map<String, String> params, String encode) {

        byte[] data = getRequestData(params, encode).toString().getBytes();//获得请求体
        URL realurl = null;
        try {
            realurl = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)realurl.openConnection();
            httpURLConnection.setConnectTimeout(3000);//设置连接超时时间
            httpURLConnection.setDoInput(true);//打开输入流，以便从服务器获取数据
            httpURLConnection.setDoOutput(true);//打开输出流，以便向服务器提交数据
            httpURLConnection.setRequestMethod("POST");//设置以Post方式提交数据
            httpURLConnection.setUseCaches(false);//使用Post方式不能使用缓存
            //设置请求体的类型是文本类型
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //设置请求体的长度
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
            //获得输出流，向服务器写入数据
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(data);

            int response = httpURLConnection.getResponseCode();//获得服务器的响应码
            if(response == HttpURLConnection.HTTP_OK) {
                InputStream inptStream = httpURLConnection.getInputStream();
                return dealResponseResult(inptStream); //处理服务器的响应结果
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String dealResponseResult(InputStream inputStream) {
        // TODO Auto-generated method stub
        String resultData = null;      //存储处理结果
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
            while((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        return resultData;
    }

    private static StringBuffer getRequestData(Map<String, String> params,
                                               String encode) {
        StringBuffer stringBuffer = new StringBuffer();        //存储封装好的请求体信息
        try {
            for(Map.Entry<String, String> entry : params.entrySet()) {
                stringBuffer.append(entry.getKey())
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), encode))
                        .append("&");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //删除最后的一个"&"
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer;
    }

}
