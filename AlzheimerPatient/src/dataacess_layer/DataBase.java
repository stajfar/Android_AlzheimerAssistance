package dataacess_layer;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 

public class DataBase {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_TITLE = "title";
	public static final String KEY_DATETIME = "date_time";
	public static final String KEY_POSITION = "position";
	

	public static final String DATABASE_NAME = "MyAlzheimerDB";
	public static final String DATABASE_TABLE = "TBL_LOCATIONS";
	public static final int DATABASE_VERSION = 1;
	
	
	private DbHelper ourDbhelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;

	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub			
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			
			db.execSQL(
					"CREATE TABLE "+DATABASE_TABLE+" ("+
					  KEY_ROWID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
					  KEY_TITLE+" TEXT NOT NULL, "+
					  KEY_DATETIME+" TEXT NOT NULL, "+
					  KEY_POSITION+" TEXT NOT NULL);"
					
					);			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS"+ DATABASE_TABLE);
			onCreate(db);
			
		}
	}
	
	public DataBase(Context context)
	{
		ourContext=context;
	}
	
	
	
	public DataBase open()
	{
		ourDbhelper=new DbHelper(ourContext);
		ourDatabase=ourDbhelper.getWritableDatabase();
		return this;
	}
	
	public void close()
	{
		ourDbhelper.close();
	}
	
	public void InsertEntry_Location(String Title,String Position,String DateTime) {
		// TODO Auto-generated method stub
		ContentValues cv=new ContentValues();
		cv.put(KEY_TITLE, Title);
		cv.put(KEY_POSITION, Position);
		cv.put(KEY_DATETIME, DateTime);
		ourDatabase.insert(DATABASE_TABLE, null, cv);			
	}

	public String findPositionByRowID(long rowId) {
		// TODO Auto-generated method stub
		String[] columns={KEY_ROWID,KEY_TITLE,KEY_POSITION,KEY_DATETIME};		
		Cursor c= ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID+"="+ rowId , null, null, null, null);
		c.moveToFirst();
		int iposition=c.getColumnIndex(KEY_POSITION);
		String result=c.getString(iposition);		
		
		return result;
	}



	public String findLatestPosition() {
		// TODO Auto-generated method stub
		String[] columns={KEY_ROWID,KEY_TITLE,KEY_POSITION,KEY_DATETIME};		
		Cursor c= ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID+"="+ "(SELECT MAX("+KEY_ROWID+") FROM "+DATABASE_TABLE+")" , null, null, null, null);
		c.moveToFirst();
		int iposition=c.getColumnIndex(KEY_POSITION);
		String result=c.getString(iposition);		
		
		return result;
	}


/*
	public String findPersonHotnessByRowID(long rowId) {
		// TODO Auto-generated method stub
				String[] columns={KEY_ROWID,KEY_NAME,KEY_HOTNESS};		
				Cursor c= ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID+"="+ rowId , null, null, null, null);
				c.moveToFirst();
				int ihotness=c.getColumnIndex(KEY_HOTNESS);
				String result=c.getString(ihotness);				
				
				return result;
	}



	public void updatehotnessRow(long rowID, String name2, String hotness2) {
		// TODO Auto-generated method stub
		ContentValues cv=new ContentValues();
		cv.put(KEY_NAME, name2);
		cv.put(KEY_HOTNESS, hotness2);		
		ourDatabase.update(DATABASE_TABLE,cv ,KEY_ROWID+"="+ rowID,null);
		
	}



	public void DeletepersonFromTable(long rowID2) {
		// TODO Auto-generated method stub
		ourDatabase.delete(DATABASE_TABLE, KEY_ROWID+"="+rowID2, null);
		
	}    */
	
}
