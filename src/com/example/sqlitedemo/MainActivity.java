package com.example.sqlitedemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnClickListener {
	private Button bt_create, bt_add,bt_update,bt_delete,bt_query;
	private myDateBaseHelper dbhelper;
	SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bt_create = (Button) findViewById(R.id.bt_create);
		bt_add = (Button) findViewById(R.id.bt_add);
		bt_update=(Button) findViewById(R.id.bt_update);
		bt_delete=(Button) findViewById(R.id.bt_delete);
		bt_query=(Button) findViewById(R.id.bt_query);
		bt_create.setOnClickListener(this);
		bt_add.setOnClickListener(this);
		bt_update.setOnClickListener(this);
		bt_delete.setOnClickListener(this);
		bt_query.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_create:
			// dbhelper=new myDateBaseHelper(this, "Book.db", null, 1);
			// 更新数据库时，新建的dbhelper版本号要不一样
			dbhelper = new myDateBaseHelper(this, "Book.db", null, 2);
			dbhelper.getWritableDatabase();
			break;
		case R.id.bt_add:
			dbhelper = new myDateBaseHelper(this, "Book.db", null, 2);
			db = dbhelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("author", "ben jack");
			values.put("price", 20.19);
			values.put("pages", 230);
			values.put("name", "the first day");

			db.insert("Book", null, values);

			values.clear();

			values.put("author", "zhaoxin");
			values.put("price", 23.28);
			values.put("pages", 240);
			values.put("name", "the second day");

			db.insert("Book", null, values);
			Toast.makeText(this, "add success", Toast.LENGTH_SHORT).show();
			break;
		case R.id.bt_update:
			dbhelper = new myDateBaseHelper(this, "Book.db", null, 2);
			db=dbhelper.getWritableDatabase();
			ContentValues values2=new ContentValues();
			values2.put("price", 100);
			
			db.update("Book", values2, "author=?", new String[]{"zhaoxin"});
			Toast.makeText(this, "update success", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.bt_delete:
			dbhelper = new myDateBaseHelper(this, "Book.db", null, 2);
			db=dbhelper.getWritableDatabase();
			db.delete("Book", "price<?", new String[]{"90"});
			Toast.makeText(this, "delete success", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.bt_query:
			dbhelper = new myDateBaseHelper(this, "Book.db", null, 2);
			db=dbhelper.getWritableDatabase();
			
			Cursor cursor= db.query("Book", null, null	, null, null, null, null);
			
			while(cursor.moveToNext()){
				String name=cursor.getString(cursor.getColumnIndex("name"));
				String author=cursor.getString(cursor.getColumnIndex("author"));
				double price=cursor.getDouble(cursor.getColumnIndex("price"));
				int pages=cursor.getInt(cursor.getColumnIndex("pages"));
				
				Log.e("author", author);
				Log.e("price", price+"");
				Log.e("name", name);
				Log.e("pages", pages+"");
			}
			cursor.close();
			break;
		default:
			break;
		}

	}

}
