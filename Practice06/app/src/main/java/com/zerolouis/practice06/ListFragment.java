package com.zerolouis.practice06;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zerolouis.practice06.adapter.StoreAdapter;
import com.zerolouis.practice06.bean.Store;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

	private Context mContext;
	private View mView;
	private ListView store_list;

	public ListFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment ListFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static ListFragment newInstance(String param1, String param2) {
		ListFragment fragment = new ListFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		mContext = getActivity(); // 获取活动页面的上下文
		mView = inflater.inflate(R.layout.fragment_list,container,false);
		store_list = mView.findViewById(R.id.store_list);
		ArrayList<Store> stores = Store.getDefaultList();
		// 构造适配器
		StoreAdapter storeAdapter = new StoreAdapter(mContext, stores);
		// 添加适配器
		store_list.setAdapter(storeAdapter);
		// Inflate the layout for this fragment
		return mView;
	}
}