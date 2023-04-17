package com.zerolouis.practice10;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.zerolouis.practice10.adapter.NewsAdapter;
import com.zerolouis.practice10.bean.News;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.android.volley.toolbox.Volley.newRequestQueue;

public class MainActivity extends AppCompatActivity {

    private ListView newsList;
    private SwipeRefreshLayout mSRL;
    private ArrayList<News> newss;
    private Context mContext;
    private String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        initView();
        getData();
        refresh();
    }

    // 初始化控件
    public void initView() {
        newsList = findViewById(R.id.news_list);
        mSRL = findViewById(R.id.mSRL);
        mSRL.setColorSchemeColors(Color.parseColor("#6100ed"), Color.parseColor("#6100ed"));
    }

    public void refresh() {
        // 刷新回调
        mSRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 判断是否在刷新
                Toast.makeText(MainActivity.this, mSRL.isRefreshing() ? "正在刷新" : "刷新完成", Toast.LENGTH_SHORT).show();
                Log.d(LOG_TAG, "开始获取数据");
                // 获取数据
                // 创建一个请求队列
                RequestQueue requestQueue = newRequestQueue(mContext);
                // 请求地址
                String url = "http://192.168.123.131:8080/news/2";
                // 使用JsonArray处理json数组
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(LOG_TAG,"获取成功");
                        // 解析JSON
                        JsonParser parser = new JsonParser();
                        JsonArray jsonArray = parser.parse(response.toString()).getAsJsonArray();
                        Gson gson = new Gson();
                        // 接收到的数组
                        ArrayList<News> newsDataList = new ArrayList<>();
                        for (JsonElement news : jsonArray){
                            News news1 = gson.fromJson(news,News.class);
                            newsDataList.add(news1);
                        }
                        newss = newsDataList;
                        updateData();
                        Toast.makeText(mContext, "更新完成", Toast.LENGTH_SHORT).show();
                        mSRL.setRefreshing(false);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(LOG_TAG,"获取失败");
                        Toast.makeText(MainActivity.this,"连接失败", Toast.LENGTH_SHORT).show();
                        mSRL.setRefreshing(false);
                    }
                });
                requestQueue.add(jsonArrayRequest);
            }
        });
    }

    public void updateData() {
        NewsAdapter newsAdapter = new NewsAdapter(MainActivity.this, newss);
        newsList.setAdapter(newsAdapter);
    }

    public void getData(){
        Log.d(LOG_TAG, "开始获取数据");
        // 获取数据
        // 创建一个请求队列
        RequestQueue requestQueue = newRequestQueue(mContext);
        // 请求地址
        String url = "http://192.168.123.131:8080/news/1";
        // 使用JsonArray处理json数组
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(LOG_TAG,"获取成功");
                // 解析JSON
                JsonParser parser = new JsonParser();
                JsonArray jsonArray = parser.parse(response.toString()).getAsJsonArray();
                Gson gson = new Gson();
                // 接收到的数组
                ArrayList<News> newsDataList = new ArrayList<>();
                for (JsonElement news : jsonArray){
                    News news1 = gson.fromJson(news,News.class);
                    newsDataList.add(news1);
                }
                newss = newsDataList;
                updateData();
                Toast.makeText(mContext, "更新完成", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(LOG_TAG,"获取失败");
                Toast.makeText(MainActivity.this,"连接失败", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}