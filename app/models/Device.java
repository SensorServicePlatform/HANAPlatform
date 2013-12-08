package models;

import org.json.JSONException;
import org.json.JSONObject;

public class Device {
	private static DBHandler dbHandler = null;
	private String deviceId;
	private String uri;	
	private String deviceType;
	private String deviceAgent;
	private String deviceLocation;
	private String userDefinedFields;
	
	public Device(){
		dbHandler = new DBHandler("conf/database.properties");	
	}
	
	public Device(String uri, String deviceType, String deviceAgent, String deviceLocation){
		this.uri = uri;
		this.deviceType = deviceType;
		this.deviceAgent = deviceAgent;
		this.deviceLocation = deviceLocation;
	}
	
	public String getUri(){
		return uri;
	}
	
	public void setUri(String uri){
		this.uri = uri;
	}
	
	public String getDeviceType(){
		return deviceType;
	}
	
	public void setDeviceType(String type){
		this.deviceType = type;
	}
	
	public String getDeviceAgent(){
		return deviceAgent;
	}
	
	public void setDeviceAgent(String agent){
		this.deviceAgent = agent;
	}
		
	public String getLocation(){
		return deviceLocation;
	}
	
	public void setLocation(String location){
		this.deviceLocation = location;
	}
		
	public String getCSVHeader() {
		return "device_id,uri,device_type,device_agent,device_location,user_defined_fields\n";
	}
	
	public String toCSVString() {
		return deviceId+","+uri + "," + deviceType + "," + deviceAgent + "," + deviceLocation+","+userDefinedFields;
	}
	
	public String toJSONString() {
		String jsonString = new String();
		try {
			JSONObject obj=new JSONObject();
			obj.put("device_id", deviceId);
			obj.put("uri",  uri);
			obj.put("device_type", deviceType);
			obj.put("device_agent", deviceAgent);
			obj.put("device_location", deviceLocation);
			obj.put("user_defined_fields", userDefinedFields);
			jsonString = obj.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
	
	public String executeSQL(String sql, int number_of_result_columns){
		if(dbHandler == null){
			dbHandler = new DBHandler("conf/database.properties");
		}
		return dbHandler.runQuery(sql, number_of_result_columns);
	}

	public boolean save(){
		if(dbHandler == null){
			dbHandler = new DBHandler("conf/database.properties");
		}
		return dbHandler.addDevice(uri, deviceType, deviceAgent, deviceLocation);

	}

	public String getUserDefinedFields() {
		return userDefinedFields;
	}

	public void setUserDefinedFields(String userDefinedFields) {
		this.userDefinedFields = userDefinedFields;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
}
