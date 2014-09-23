package org.eaaa.database;

import static org.eaaa.database.DatabaseFields.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.Console;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class MyService
{
	private static volatile MyService service;
	private SQLiteDatabase db;
	private MyDatabase myDB;
	
	
	private MyService(Context context)
	{
		myDB = new MyDatabase(context);
	}
	
	public void open()
	{
		db = myDB.getWritableDatabase();
	}
	
	public void close()
	{
		db.close();
	}
	
	public static MyService getInstance(Context... context)
	{
		if (service == null)
		{
			synchronized (MyService.class)
			{
				if (service == null)
				{
					if (context.length == 1)
					{
						service = new MyService(context[0]);
					}
				}
			}
		}
		return service;
	}
	
	public void insertContent(String content)
	{
		ContentValues values = new ContentValues();
		values.put(FIELD_CONTENT, content);
		values.put(FIELD_TIME, new GregorianCalendar().getTimeInMillis());
		db.insert(TABLE_VOICE, null, values);
	}
	
	public String fetchLatestContent()
	{
		Cursor cursorContent = db.rawQuery(String.format("SELECT %s FROM %s ORDER BY %s DESC LIMIT 1", FIELD_CONTENT, TABLE_VOICE, FIELD_TIME),null);
		Log.d("test", "test");
		if(cursorContent.moveToFirst())
		{
			Log.d(FIELD_CONTENT, cursorContent.getString(cursorContent.getColumnIndex(FIELD_CONTENT)));
			return cursorContent.getString(cursorContent.getColumnIndex(FIELD_CONTENT));
		}
		return "";
	}
	
	
	
	
}
