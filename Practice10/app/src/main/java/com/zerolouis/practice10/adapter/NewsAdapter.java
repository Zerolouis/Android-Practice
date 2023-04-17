package com.zerolouis.practice10.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.zerolouis.practice10.R;
import com.zerolouis.practice10.bean.News;

import java.util.ArrayList;

public class NewsAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {

    private Context mContext;
    private ArrayList<News> list;

    public NewsAdapter(Context mContext, ArrayList<News> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            // 生成新的布局
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_news_list,null);
            holder.news_icon = convertView.findViewById(R.id.news_icon);
            holder.news_name = convertView.findViewById(R.id.news_name);
            holder.news_desc = convertView.findViewById(R.id.news_desc);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        News news = list.get(position);
        holder.news_name.setText(news.getName());
        holder.news_icon.setImageResource(news.getIcon());
        holder.news_desc.setText(news.getDesc());
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public final class ViewHolder{
        private ImageView news_icon;
        private TextView news_name;
        private TextView news_desc;
    }
}
