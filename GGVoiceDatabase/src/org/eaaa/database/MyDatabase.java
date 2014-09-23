package org.eaaa.database;

import static org.eaaa.database.DatabaseFields.*;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class MyDatabase extends SQLiteOpenHelper
{
	private static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "Voice";
	
	public MyDatabase(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(String.format("CREATE TABLE %s (%s BIGINT, %s TEXT );", TABLE_VOICE, FIELD_TIME, FIELD_CONTENT));
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_VOICE);
	}
}
