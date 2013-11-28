package models;

import org.json.JSONException;
import org.json.JSONObject;

public class Sensor {

	private static DBHandler dbHandler = null;
	private String sensorId;
	private String sensorName;
	private String sensorType;
	private String deviceId;
	
	public Sensor(){
		dbHandler = new DBHandler("conf/database.properties");	
	}
	
	
	public Sensor(String sensorId, String sensorName, String sensorType,
			String deviceId) {
		super();
		this.sensorId = sensorId;
		this.sensorName = sensorName;
		this.sensorType = sensorType;
		this.deviceId = deviceId;
	}


	public String getSensorId() {
		return sensorId;
	}
	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}
	public String getSensorName() {
		return sensorName;
	}
	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}
	public String getSensorType() {
		return sensorType;
	}
	public void setSensorType(String sensorType) {
		this.sensorType = sensorType;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public String getCSVHeader() {
		return "sensorId,sensorName,sensorType,deviceId\n";
	}
	
	public String toCSVString() {
		return sensorId + "," + sensorName + "," + sensorType + "," + deviceId;
	}
	
	public String toJSONString() {
		String jsonString = new String();
		try {
			JSONObject obj=new JSONObject();
			obj.put("sensorId",  sensorId);
			obj.put("sensorName", sensorName);
			obj.put("sensorType", sensorType);
			obj.put("deviceId", deviceId);
			jsonString = obj.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
	