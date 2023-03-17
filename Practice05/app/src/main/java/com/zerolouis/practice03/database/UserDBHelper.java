package com.zerolouis.practice03.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.zerolouis.practice03.bean.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class UserDBHelper extends SQLiteOpenHelper {

	private static final String TAG = UserDBHelper.class.getSimpleName();
	private static final String DB_NAME = "user.db"; // 数据库的名称
	private static final int DB_VERSION = 1; // 数据库的版本号
	private static UserDBHelper mHelper = null; // 数据库帮助器的实例
	private SQLiteDatabase mDB = null; // 数据库的实例
	public static final String TABLE_NAME = "user_info"; // 表的名称

	private UserDBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	private UserDBHelper(Context context, int version) {
		super(context, DB_NAME, null, version);
	}
	// 利用单例模式获取数据库帮助器的唯一实例
	public static UserDBHelper getInstance(Context context, int version) {
		if (version > 0 && mHelper == null) {
			mHelper = new UserDBHelper(context, version);
		} else if (mHelper == null) {
			mHelper = new UserDBHelper(context);
		}
		return mHelper;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "onCreate");
		String drop_sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
		Log.d(TAG, "drop_sql:" + drop_sql);
		db.execSQL(drop_sql);
		String create_sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
				+ "_id INTEGER PRIMARY KEY  AUTOINCREMENT NOT NULL,"
				+ "name VARCHAR NOT NULL," + "age INTEGER NOT NULL,"
				+ "phone VARCHAR,"
				//演示数据库升级时要先把下面这行注释
				+ "password VARCHAR NOT NULL"
				+ ");";
		Log.d(TAG, "create_sql:" + create_sql);
		db.execSQL(create_sql); // 执行完整的SQL语句
	}
	// 打开数据库的读连接
	public SQLiteDatabase openReadLink() {
		if (mDB == null || !mDB.isOpen()) {
			mDB = mHelper.getReadableDatabase();
		}
		return mDB;
	}

	// 打开数据库的写连接
	public SQLiteDatabase openWriteLink() {
		if (mDB == null || !mDB.isOpen()) {
			mDB = mHelper.getWritableDatabase();
		}
		return mDB;
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	// 根据指定条件删除表记录
	public int delete(String condition) {
		// 执行删除记录动作，该语句返回删除记录的数目
		return mDB.delete(TABLE_NAME, condition, null);
	}

	// 删除该表的所有记录
	public int deleteAll() {
		// 执行删除记录动作，该语句返回删除记录的数目
		return mDB.delete(TABLE_NAME, "1=1", null);
	}

	// 往该表添加一条记录
	public long insert(UserInfo info) {
		long result = -1;
		// 不存在唯一性重复的记录，则插入新记录
		ContentValues cv = new ContentValues();
		cv.put("name", info.getUsername());
		cv.put("age", info.getAge());
		cv.put("phone", info.getPhone());
		cv.put("password", info.getPassword());
		// 执行插入记录动作，该语句返回插入记录的行号
		result = mDB.insert(TABLE_NAME, "", cv);
		if (result == -1) { // 添加成功则返回行号，添加失败则返回-1
			return result;
		}
		return result;
	}

	public int update(UserInfo info, String condition) {
		ContentValues cv = new ContentValues();
		cv.put("name", info.getUsername());
		cv.put("age", info.getAge());
		cv.put("phone", info.getPhone());
		cv.put("password", info.getPassword());
		// 执行更新记录动作，该语句返回更新的记录数量
		return mDB.update(TABLE_NAME, cv, condition, null);
	}

	public int update(UserInfo info) {
		// 执行更新记录动作，该语句返回更新的记录数量
		return update(info, "_id=" + info.getId());
	}

	public List<UserInfo> query(String condition) {
		String sql = String.format("select _id,name,password,age,phone from %s where %s;", TABLE_NAME, condition);
		Log.d(TAG, "query sql: " + sql);
		List<UserInfo> infoList = new ArrayList<UserInfo>();
		// 执行记录查询动作，该语句返回结果集的游标
		Cursor cursor = mDB.rawQuery(sql, null);
		// 循环取出游标指向的每条记录
		while (cursor.moveToNext()) {
			UserInfo info = new UserInfo();
			info.setId(cursor.getInt(0));
			info.setUsername(cursor.getString(1));
			info.setPassword(cursor.getString(2));
			info.setAge(cursor.getInt(3));
			info.setPhone(cursor.getString(4));
			infoList.add(info);
		}
		cursor.close(); // 查询完毕，关闭数据库游标
		return infoList;
	}

	// 根据手机号码查询指定记录
	public UserInfo queryByPhone(String phone) {
		UserInfo info = null;
		List<UserInfo> infoList = query(String.format("phone='%s'", phone));
		if (infoList.size() > 0) { // 存在该号码的登录信息
			info = infoList.get(0);
		}
		return info;
	}

	public UserInfo queryByUsername(String username){
		UserInfo info =null;
		List<UserInfo> userList = query(String.format("name='%s'", username));
		if (userList.size()>0){
			info = userList.get(0);
		}
		return info;
	}


}
