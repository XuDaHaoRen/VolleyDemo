package activity.xbl.com.volleydemo;


import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * string_request_btn：实现一个最简单的StringRequsest请求
 * json_request_btn：获取json数据  以字符串的形式打印出来
 * show_image_btn：使用netWorkImageView显示网络图片
 * image_loader_btn：加载图片，并用Lru算法进行缓存
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button string_request_btn;
    private Button json_request_btn;
    private Button show_image_btn;
    private Button my_request_btn;
    private Button image_loader_btn;
    private ImageView image_loader_iv;
    private String key;
    private RequestQueue requestQueue;
    private ImageView request_image_iv;
    //这是一个自定义控件，继承ImageView
    private NetworkImageView network_image_view;
    private String imgUrl;
    private String jsonUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findID();
    }


    private void findID() {
        string_request_btn= (Button) findViewById(R.id.string_request_btn);
        string_request_btn.setOnClickListener(this);
        json_request_btn= (Button) findViewById(R.id.json_request_btn);
        json_request_btn.setOnClickListener(this);
        show_image_btn= (Button) findViewById(R.id.show_image_btn);
        show_image_btn.setOnClickListener(this);
        request_image_iv = (ImageView) findViewById(R.id.request_image_iv);
        network_image_view= (NetworkImageView) findViewById(R.id.netwok_image_iv);
        my_request_btn= (Button) findViewById(R.id.my_request_btn);
        my_request_btn.setOnClickListener(this);
        image_loader_btn= (Button) findViewById(R.id.image_loader_btn);
        image_loader_btn.setOnClickListener(this);
        image_loader_iv= (ImageView) findViewById(R.id.image_loader_iv);


    }

    @Override
    public void onClick(View v) {
        //实例化一个请求队列，进行统一的请求管理
        requestQueue = Volley.newRequestQueue(this);
        jsonUrl="http://103.244.59.105:8014/paopaoserver/getcityname";
        imgUrl="https://ss0.bdstatic.com/94oJfD_bAAc" +
                "T8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec" +
                "=1491615703&di=e7330763b72102a13789bfbe5b3d9bc6&src=ht" +
                "tp://images.17173.com/2013/news/2013/01/07/san0107dm01s.jpg";
        switch (v.getId()) {
            //Volley最简单的使用
            case R.id.string_request_btn:
                simpleVolley();
                break;
            case R.id.json_request_btn:
               // getJson();
                getJsonObj();
                break;
            //显示图片的方法
            case R.id.show_image_btn:
                showImageByRequest();
                showImageByNotWork();
                break;
            case R.id.my_request_btn:
                myRequset();
                break;
            case R.id.image_loader_btn:
                imageLoader();
                break;
        }


    }
    //加载网络图片使用NetWorkImageView控件
    private void showImageByNotWork() {
        ImageLoader imageLoader=new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {

            }
        });
        //设置默认图片
        network_image_view.setDefaultImageResId(R.mipmap.ic_launcher);
        //设置网络加载失败的图片
        network_image_view.setErrorImageResId(R.mipmap.ic_launcher);
        network_image_view.setImageUrl(imgUrl,imageLoader);


    }
    //下载图片的方法,使用ImageRequest
    private void showImageByRequest() {
        //图片的url地址，正确访问时的回调，设置最大的宽，高，BitmapConfig图片压缩的质量，失败时的回调
        ImageRequest imageRequest=new ImageRequest(imgUrl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                request_image_iv.setImageBitmap(response);

            }
        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG","图片下载失败"+error.getMessage());
            }
        });
        requestQueue.add(imageRequest);

    }
    //获取网络中的JSON数据,里面包含怎样封装请求头信息
    private void getJson(){
        StringRequest stringRequest=new StringRequest(jsonUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG",response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
//                }){
//                    //请求头信息的封装,如果程序有请求头信息
//                    @Override
//                    public Map<String, String> getHeaders() throws AuthFailureError {
//                        HashMap<String,String> map=new HashMap<>();
//                        map.put("apikey",key)   ;
//                        return map;
//                    }
        });
        requestQueue.add(stringRequest);
    }
    //volley最普通简单的使用
    private void simpleVolley(){
        StringRequest stringRequest = new StringRequest("http：//www.baidu.com", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //成功之后返回的数据,是一串HTML数据
                Log.d("TAG", response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //享用失败时的回调
                Log.d("TAG", error.getMessage());

            }
        });
        requestQueue.add(stringRequest);
    }
    private void myRequset(){
        GsonRequest<Data> gsonRequest = new GsonRequest<Data>(
                "http://103.244.59.105:8014/paopaoserver/getcityname", Data.class,
                new Response.Listener<Data>() {
                    @Override
                    public void onResponse(Data data) {
                        Log.d("TAG", "city is " + data.getDatas().get(0).getCity_name());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        requestQueue.add(gsonRequest);
    }
    //使用JsonObject和JsonArrayRequest
    private void getJsonObj(){
        //如果有请求头信息需要使用代码块 添加请求头信息
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(jsonUrl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("TAG",response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG",error.getMessage());
            }
        });
        requestQueue.add(jsonObjectRequest);
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(jsonUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("TAG","jsonArrayRequest:"+response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG",error.getMessage());
            }
        });
        requestQueue.add(jsonArrayRequest);

    }
    private void imageLoader(){
        ImageLoader imageLoader=new ImageLoader(requestQueue,new BitmapCache());
        //对ImageLoader的监听
        ImageLoader.ImageListener listener=ImageLoader.getImageListener(image_loader_iv,
                R.mipmap.ic_launcher,R.mipmap.ic_launcher);
        imageLoader.get(imgUrl,listener);
    }
}


