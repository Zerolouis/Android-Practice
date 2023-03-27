package com.zerolouis.practice06.bean;

import com.zerolouis.practice06.R;

import java.util.ArrayList;
import java.util.List;

public class Store {
	private String name;
	private Integer number;
	private int image;

	private boolean isNew;

	private static int[] iconArray = {R.drawable.logo_kfc, R.drawable.logo_m, R.drawable.logo_bk,
			R.drawable.logo_ji};
	private static String[] nameArray = {"肯德基", "金拱门", "汉堡王", "鸡你太美炸鸡"};
	private static int[] numberArray = {10,15,8,21};
	private static boolean[] isNewArray = {true,true,false,true};

	public Store() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean aNew) {
		isNew = aNew;
	}

	public static int[] getIconArray() {
		return iconArray;
	}

	public static void setIconArray(int[] iconArray) {
		Store.iconArray = iconArray;
	}

	public static String[] getNameArray() {
		return nameArray;
	}

	public static void setNameArray(String[] nameArray) {
		Store.nameArray = nameArray;
	}

	public Store(String name, Integer number, int image, boolean isNew) {
		this.name = name;
		this.number = number;
		this.image = image;
		this.isNew = isNew;
	}

	public static ArrayList<Store> getDefaultList(){
		ArrayList<Store> list = new ArrayList<>();
		for(int i=0;i<nameArray.length;i++){
			list.add(new Store(nameArray[i],numberArray[i], iconArray[i],isNewArray[i] ));
		}
		return list;
	}
}
