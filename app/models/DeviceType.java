package models;

import org.json.JSONException;
import org.json.JSONObject;

public class DeviceType {
	private String deviceTypeKey;	
	private String deviceTypeName;
	private String manufacturer;
	private double version;
	
	
	public DeviceType(){
	}


	public DeviceType(String deviceTypeKey, String deviceTypeName,
			String manufacturer, double version) {
		super();
		this.deviceTypeKey = deviceTypeKey;
		this.deviceTypeName = deviceTypeName;
		this.manufacturer = manufacturer;
		this.version = version;
	}


	public String getDeviceTypeKey() {
		return deviceTypeKey;
	}


	public void setDeviceTypeKey(String deviceTypeKey) {
		this.deviceTypeKey = deviceTypeKey;
	}


	public String getDeviceTypeName() {
		return deviceTypeName;
	}


	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}


	public String getManufacturer() {
		return manufacturer;
	}


	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}


	public double getVersion() {
		return version;
	}

	public String getCSVHeader() {
		return "device_type_key,device_type_name,manufacturer,version\n";
	}
	
	public void setVersion(double version) {
		this.version = version;
	}
	public String toCSVString() {
		return deviceTypeKey + "," + deviceTypeName + "," + manufacturer + "," + version;
	}
	
	public String toJSONString() {
		String jsonString = new String();
		try {
			JSONObject obj=new JSONObject();
			obj.put("device_type_key",  deviceTypeKey);
			obj.put("device_type_name", deviceTypeName);
			obj.put("manufacturer", manufacturer);
			obj.put("version", version);
			jsonString = obj.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}