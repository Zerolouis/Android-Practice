package com.zerolouis.practice06.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.zerolouis.practice06.R;
import com.zerolouis.practice06.bean.Store;

import java.util.ArrayList;
import java.util.Locale;

public class StoreAdapter extends BaseAdapter implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

	private Context mContext;   // 声明一个上下文对象
	private ArrayList<Store> list;  // 商店列表

	public StoreAdapter(Context mContext, ArrayList<Store> list) {
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
		if(convertView == null){
			holder = new ViewHolder();
			// 从布局文件item_store_list生成一个新的视图
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_store_list,null);
			holder.apply_btn = convertView.findViewById(R.id.apply_btn);
			holder.store_icon = convertView.findViewById(R.id.store_icon);
			holder.store_name = convertView.findViewById(R.id.store_name);
			holder.store_new = convertView.findViewById(R.id.store_new);
			holder.store_number = convertView.findViewById(R.id.store_number);
			convertView.setTag(holder); // 将视图保存到转换视图中
		}else {
			// 否则从转换视图中取出
			holder = (ViewHolder) convertView.getTag();
		}

		Store store = list.get(position);
		holder.store_name.setText(store.getName());
		holder.store_new.setText("新增");
		holder.store_number.setText(String.format(Locale.CHINA,"限额%d名",store.getNumber()));
		holder.store_icon.setImageResource(store.getImage());
		holder.store_icon.requestFocus();
		return convertView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		return false;
	}

	public final class ViewHolder{
		private ImageView store_icon;   // 商店图标
		private TextView store_name;    // 商店名称
		private TextView store_new; // 是否是新品
		private TextView store_number;  // 商店的限额
		private Button apply_btn;   // 申请按钮
	}
}
