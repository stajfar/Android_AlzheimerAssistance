package businesslogic_layer;


import android.content.Context;
import android.content.Intent;

public class MenuObj {
	

	private String MenuItemText;
	private String MenuItemValue;
	
	public MenuObj(){
	
	}
	

	public MenuObj(String menuItemText, String menuItemValue) {
		
		this.MenuItemText = menuItemText;
		this.MenuItemValue = menuItemValue;
	}


	public static Intent MenuItemClick(Context context,String selectedItem) {
		// TODO Auto-generated method stub
		Class<?> ourClass;
		try {
			ourClass = Class.forName("com.alzheimer.alzheimerassistant."+ selectedItem);
			Intent ourIntent= new Intent(context, ourClass);
			return ourIntent;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return null;
	}
	
	
	public String getMenuItemText() {
		return MenuItemText;
	}

	public void setMenuItemText(String menuItemText) {
		MenuItemText = menuItemText;
	}

	public String getMenuItemValue() {
		return MenuItemValue;
	}

	public void setMenuItemValue(String menuItemValue) {
		MenuItemValue = menuItemValue;
	}

}
