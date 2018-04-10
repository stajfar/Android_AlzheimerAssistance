package businesslogic_layer;

import android.content.Context;
import dataacess_layer.DataBase;

public class LocationObj {

	private String _id;
	private String Title;
	private String DateTime;
	private String Position;

	public LocationObj() {

	}

	public LocationObj(String _id, String title, String dateTime,
			String position) {

		this._id = _id;
		this.Title = title;
		this.DateTime = dateTime;
		this.Position = position;
	}

	public void InsertEntry_Location(Context context, LocationObj mylocation) {
		dataacess_layer.DataBase dbObj = new DataBase(context);
		String title = mylocation.getTitle();
		String position = mylocation.getPosition();
		String datetime = mylocation.getDateTime();

		dbObj.open();
		dbObj.InsertEntry_Location(title, position, datetime);
		dbObj.close();

	}

	public String findPositionByRowID(Context context, long rowId) {
		dataacess_layer.DataBase dbObj = new DataBase(context);

		dbObj.open();
		String result = dbObj.findPositionByRowID(rowId);
		dbObj.close();
		return result;
	}

	public String findLatestPosition(Context context) {
		// TODO Auto-generated method stub
		dataacess_layer.DataBase dbObj = new DataBase(context);

		dbObj.open();
		String result = dbObj.findLatestPosition();
		dbObj.close();
		return result;
	}

	// Defining the getters and setters

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDateTime() {
		return DateTime;
	}

	public void setDateTime(String dateTime) {
		DateTime = dateTime;
	}

	public String getPosition() {
		return Position;
	}

	public void setPosition(String position) {
		Position = position;
	}

}
