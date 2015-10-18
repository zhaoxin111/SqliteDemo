package com.example.sqlitedemo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class MyContentProvider extends ContentProvider {
	public static final String AUTHORITY = "com.example.sqlitedemo.provider";
	public static final int BOOK_DIR = 0;
	public static final int BOOK_ITEM = 1;
	public static final int CATEGORY_DIR = 2;
	public static final int CATEGORY_ITEM = 3;
	private myDateBaseHelper dbhelper;
	private SQLiteDatabase db;
	private static UriMatcher uriMatcher;

	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, "Book", BOOK_DIR);
		uriMatcher.addURI(AUTHORITY, "Book/#", BOOK_ITEM);
		uriMatcher.addURI(AUTHORITY, "Category", CATEGORY_DIR);
		uriMatcher.addURI(AUTHORITY, "Category/#", CATEGORY_ITEM);
	}

	@Override
	public boolean onCreate() {
		dbhelper = new myDateBaseHelper(getContext(), "Book.db", null, 2);
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Cursor cursor = null;
		db = dbhelper.getReadableDatabase();
		switch (uriMatcher.match(uri)) {
		case BOOK_DIR:
			cursor = db.query("Book", projection, selection, selectionArgs,
					null, null, null);
			break;
		case BOOK_ITEM:
			String bookId = uri.getPathSegments().get(1);
			cursor = db.query("Book", projection, "id=?",
					new String[] { bookId }, null, null, null);
			break;
		case CATEGORY_DIR:
			cursor = db.query("Category", projection, selection, selectionArgs,
					null, null, null);
			break;
		case CATEGORY_ITEM:
			String category_id = uri.getPathSegments().get(1);
			cursor = db.query("Category", projection, "id=?",
					new String[] { category_id }, null, null, null);
			break;
		default:
			break;
		}

		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case BOOK_DIR:
			return "vnd.android.cursor.dir/vnd.com.example.sqlitedemo.provider.Book";
		case BOOK_ITEM:
			
			return "vnd.android.cursor.item/vnd.com.example.sqlitedemo.provider.Book";
		case CATEGORY_DIR:

			return "vnd.android.cursor.dir/vnd.com.example.sqlitedemo.provider.Category";
		case CATEGORY_ITEM:

			return "vnd.android.cursor.item/vnd.com.example.sqlitedemo.provider.Category";
		default:
			break;
		}
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Uri urireturn = null;
		db = dbhelper.getWritableDatabase();
		switch (uriMatcher.match(uri)) {
		case BOOK_DIR:
			Log.i("INSERYT", "BOOK_DIR");
		case BOOK_ITEM:
			Log.i("INSERT", "BOOK_ITEM");
			long newBookId = db.insert("Book", null, values);
			urireturn = Uri.parse("content://" + AUTHORITY + "/Book/"
					+ newBookId);
			break;
		case CATEGORY_DIR:

		case CATEGORY_ITEM:
			long newCategoryId = db.insert("Category", null, values);
			urireturn = Uri.parse("content://" + AUTHORITY + "/Category/"
					+ newCategoryId);
			break;
		default:
			break;
		}

		return urireturn;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		db = dbhelper.getWritableDatabase();
		int deleteRows = 0;
		switch (uriMatcher.match(uri)) {
		case BOOK_DIR:
			deleteRows = db.delete("Book", selection, selectionArgs);
			break;
		case BOOK_ITEM:
			String bookid = uri.getPathSegments().get(1);
			Log.w("delete_pages", bookid);
			deleteRows = db.delete("Book", "id=?", new String[] { bookid });
			break;
		case CATEGORY_DIR:
			deleteRows = db.delete("Category", selection, selectionArgs);
			break;
		case CATEGORY_ITEM:
			String category_id = uri.getPathSegments().get(1);
			deleteRows = db.delete("Category", "id=?",
					new String[] { category_id });
			break;
		default:
			break;
		}

		return deleteRows;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int updateRows = 0;
		switch (uriMatcher.match(uri)) {
		case BOOK_DIR:

			break;
		case BOOK_ITEM:
			String bookId = uri.getPathSegments().get(1);
			updateRows = db.update("Book", values, "id=?",
					new String[] { bookId });
			break;
		case CATEGORY_DIR:

			break;
		case CATEGORY_ITEM:
			String categoryId = uri.getPathSegments().get(1);
			updateRows = db.update("Category", values, "id=?",
					new String[] { categoryId });
			break;
		default:
			break;
		}
		return updateRows;
	}

}
