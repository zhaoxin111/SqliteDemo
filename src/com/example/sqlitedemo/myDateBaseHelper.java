package com.example.sqlitedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class myDateBaseHelper extends SQLiteOpenHelper {
	private Context context;
	public myDateBaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		this.context=context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String create_table_book="create table Book(" +
				"id integer primary key autoincrement," +
				"author text," +
				"price real," +
				"pages integer," +
				"name text"+
				")";
		String create_table_category="create table Category(" +
				"id integer primary key autoincrement," +
				"name text," +
				"number integer" +
				")";
		db.execSQL(create_table_book);
		db.execSQL(create_table_category);
//		Toast.makeText(context, "create table succed", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table  if exists Book");
		db.execSQL("drop table  if exists Category");
		onCreate(db);
	}

}
