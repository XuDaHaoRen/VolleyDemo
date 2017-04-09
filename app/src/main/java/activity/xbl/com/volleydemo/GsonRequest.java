package activity.xbl.com.volleydemo;

import android.net.nsd.NsdManager;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Created by April on 2017/4/8.
 * 自定义的用来获取JSON数据并进行解析成数据的类
 * 不论是JsonRequest还是StringRequest都是继承于Request
 * 通过StringRequest的类自定义两个构造方法
 * 实现抽象方法
 * parseNetworkResponse：NetworkResponse response 的参数得到网络上的JSON数据
 * 使用Response.success 和  gson 解析数据并返回
 * deliverResponse 重写是根据StringRequest的样子进行重写
 *
 */

public class GsonRequest<T> extends Request<T> {
    //回调成功的监听
    private Response.Listener<T> listener;
    //Gson对象
    private Gson gson;
    //需要将数据解析成的泛型
    private Class<T> tClass;
    //这两个构造函数相比于Request有所改动，是根据StringRequest而更改的
    public GsonRequest(String url, Class<T> tClass,Response.Listener listener,
                       Response.ErrorListener errorlistener) {
        //调用这个构造函数时其实是调用下面的构造函数
        this(Method.GET, url, tClass, listener, errorlistener);
    }
    public GsonRequest(int method, String url, Class<T> tClass,Response.Listener listener,
                       Response.ErrorListener errorlistener) {
        super(method, url, errorlistener);
        this.gson=new Gson();
        this.listener = listener;
        this.tClass=tClass;
    }
    //进行数据的解析
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        //response 不仅仅存放得到的json数据，还有头信息，时间等信息
        //json数据在 response.data里面
        try {
            //StringRequest传了两个参数
            String jsonStr=new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(gson.fromJson(jsonStr,tClass),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }
    //给Listener一个回调，是否得到了数据
    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);

    }
}
