package models;

import org.json.JSONException;
import org.json.JSONObject;

public class SensorType {

	private String sensorTypeId;
	private String sensorTypeName;
	private String manufacturer;
	private double version;
	private int max_value;
	private int min_value;
	private String unit;
	private String interpreter;
	private String deviceType;
	public SensorType(){
		
	}
	public String getSensorTypeId() {
		return sensorTypeId;
	}

	public void setSensorTypeId(String sensorTypeId) {
		this.sensorTypeId = sensorTypeId;
	}

	public String getSensorTypeName() {
		return sensorTypeName;
	}

	public void setSensorTypeName(String sensorTypeName) {
		this.sensorTypeName = sensorTypeName;
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

	public void setVersion(double version) {
		this.version = version;
	}

	public int getMax_value() {
		return max_value;
	}

	public void setMax_value(int max_value) {
		this.max_value = max_value;
	}

	public int getMin_value() {
		return min_value;
	}

	public void setMin_value(int min_value) {
		this.min_value = min_value;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getInterpreter() {
		return interpreter;
	}

	public void setInterpreter(String interpreter) {
		this.interpreter = interpreter;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getCSVHeader() {
		return "sensor_type_id,sensor_type_name,manufacturer,version,"
				+ "max_value,min_value,unit,interpreter,device_type\n";
	}

	public String toCSVString() {
		return sensorTypeId + "," + sensorTypeName + "," + manufacturer + ","
				+ max_value + "," + min_value + "," + unit + "," + interpreter + ","
				+ deviceType;
	}

	public String toJSONString() {
		String jsonString = new String();
		try {
			JSONObject obj = new JSONObject();
			obj.put("sensor_type_id", sensorTypeId);
			obj.put("sensor_type_name", sensorTypeName);
			obj.put("manufacturer", manufacturer);
			obj.put("max_value", max_value);
			obj.put("min_value", min_value);
			obj.put("unit", unit);
			obj.put("interpreter", interpreter);
			obj.put("device_type", deviceType);
			
			
			jsonString = obj.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}