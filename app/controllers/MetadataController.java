package controllers;
import java.util.*;

import models.DBHandler;
import models.MessageBusHandler;
//import models.cmu.sv.sensor.SensorReading;
import helper.Utils;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.sql.Timestamp;

import org.codehaus.jackson.JsonNode;

import play.mvc.Controller;
import play.mvc.Result;

public class MetadataController extends Controller {
	private static DBHandler dbHandler = null;
	private static boolean testDBHandler(){
		if(dbHandler == null){
			dbHandler = new DBHandler("conf/database.properties");
		}
		return true;
	}

	public static Result addDeviceType() {
		JsonNode json = request().body().asJson();
		if(json == null) {
			return badRequest("Expecting Json data");
		} 
		if(!testDBHandler()){
			return internalServerError("database conf file not found");
		}

		// Parse JSON FIle 
		String deviceTypeName= json.findPath("device_type").getTextValue();
		String manufacturer = json.findPath("manufacturer").getTextValue();
		String version = json.findPath("version").getTextValue();
		String userDefinedFields = json.findPath("user_defined_fields").getTextValue();
		ArrayList<String> error = new ArrayList<String>();

		boolean result = dbHandler.addDeviceType(deviceTypeName, manufacturer, version, userDefinedFields);

		if(!result){
			error.add(deviceTypeName);
		}

		if(error.size() == 0){
			System.out.println("device type saved");
			return ok("device type saved");
		}
		else{
			System.out.println("some device types not saved: " + error.toString());
			return ok("some device types not saved: " + error.toString());
		}
	}

	public static Result addDevice() {
		JsonNode json = request().body().asJson();
		if(json == null) {
			return badRequest("Expecting Json data");
		} 
		if(!testDBHandler()){
			return internalServerError("database conf file not found");
		}

		// Parse JSON FIle 
		String deviceType= json.findPath("device_type").getTextValue();
		String deviceAgent= json.findPath("device_agent").getTextValue();
		String networkAddress = json.findPath("network_address").getTextValue();
		String locationDescription = json.findPath("location_description").getTextValue();
		String latitude = json.findPath("latitude").getTextValue();
		String longitude = json.findPath("longitude").getTextValue();
		String altitude = json.findPath("altitude").getTextValue();
		String positionFormatSystem = json.findPath("position_format_system").getTextValue();
		String userDefinedFields = json.findPath("user_defined_fields").getTextValue();
		ArrayList<String> error = new ArrayList<String>();

		boolean result = dbHandler.addDeviceNew(deviceType, deviceAgent, networkAddress, locationDescription, latitude, longitude, altitude, positionFormatSystem, userDefinedFields);

		if(!result){
			error.add(deviceType);
		}

		if(error.size() == 0){
			System.out.println("device saved");
			return ok("device saved");
		}
		else{
			System.out.println("some devices not saved: " + error.toString());
			return ok("some devices not saved: " + error.toString());
		}
	}

	public static Result addSensorType() {
		JsonNode json = request().body().asJson();
		if(json == null) {
			return badRequest("Expecting Json data");
		} 
		if(!testDBHandler()){
			return internalServerError("database conf file not found");
		}

		// Parse JSON FIle 
		String sensorType = json.findPath("sensor_type").getTextValue();
		String userDefinedFields = json.findPath("user_defined_fields").getTextValue();
		ArrayList<String> error = new ArrayList<String>();

		boolean result = dbHandler.addSensorType(sensorType, userDefinedFields);

		if(!result){
			error.add(sensorType);
		}

		if(error.size() == 0){
			System.out.println("sensor type saved");
			return ok("sensor type saved");
		}
		else{
			System.out.println("some sensor types not saved: " + error.toString());
			return ok("some sensor types not saved: " + error.toString());
		}
	}

	public static Result addSensor() {
		JsonNode json = request().body().asJson();
		if(json == null) {
			return badRequest("Expecting Json data");
		} 
		if(!testDBHandler()){
			return internalServerError("database conf file not found");
		}

		// Parse JSON FIle 
		String printName= json.findPath("print_name").getTextValue();
		String sensorType= json.findPath("sensor_type").getTextValue();
		String deviceId = json.findPath("device_id").getTextValue();
		String userDefinedFields = json.findPath("user_defined_fields").getTextValue();
		ArrayList<String> error = new ArrayList<String>();

		boolean result = dbHandler.addSensor(printName, sensorType, deviceId, userDefinedFields);

		if(!result){
			error.add(sensorType);
		}

		if(error.size() == 0){
			System.out.println("sensor saved");
			return ok("sensor saved");
		}
		else{
			System.out.println("some sensors not saved: " + error.toString());
			return ok("some sensors not saved: " + error.toString());
		}
	}
	
	public static Result deleteSensorType(String sensorType) {
		if(!testDBHandler()){
			return internalServerError("database conf file not found");
		}
		
		boolean result = dbHandler.deleteSensorType(sensorType);

		ArrayList<String> error = new ArrayList<String>();
		if(!result){
			error.add(sensorType);
		}

		if(error.size() == 0){
			System.out.println("sensor type deleted");
			return ok("sensor type deleted");
		}
		else{
			System.out.println("sensor type not deleted: " + error.toString());
			return ok("sensor type not deleted: " + error.toString());
		}
	}
	
	public static Result deleteSensor(String printName) {
		if(!testDBHandler()){
			return internalServerError("database conf file not found");
		}
		
		boolean result = dbHandler.deleteSensor(printName);

		ArrayList<String> error = new ArrayList<String>();
		if(!result){
			error.add(printName);
		}

		if(error.size() == 0){
			System.out.println("sensor deleted");
			return ok("sensor deleted");
		}
		else{
			System.out.println("sensor not deleted: " + error.toString());
			return ok("sensor not deleted: " + error.toString());
		}
	}

	public static Result deleteDeviceType(String deviceTypeName) {
		if(!testDBHandler()){
			return internalServerError("database conf file not found");
		}
		
		boolean result = dbHandler.deleteDeviceType(deviceTypeName);

		ArrayList<String> error = new ArrayList<String>();
		if(!result){
			error.add(deviceTypeName);
		}

		if(error.size() == 0){
			System.out.println("device type deleted");
			return ok("device type deleted");
		}
		else{
			System.out.println("device type not deleted: " + error.toString());
			return ok("device type not deleted: " + error.toString());
		}
	}

	public static Result deleteDevice(String networkAddress) {
		if(!testDBHandler()){
			return internalServerError("database conf file not found");
		}
		
		boolean result = dbHandler.deleteDevice(networkAddress);

		ArrayList<String> error = new ArrayList<String>();
		if(!result){
			error.add(networkAddress);
		}

		if(error.size() == 0){
			System.out.println("device deleted");
			return ok("device deleted");
		}
		else{
			System.out.println("device not deleted: " + error.toString());
			return ok("device not deleted: " + error.toString());
		}
	}
	// query for readings
	public static Result sql_query(){
		String resultStr = "";

		if(!testDBHandler()){
			return internalServerError("database conf file not found");
		}
		try {
			response().setHeader("Access-Control-Allow-Origin", "*");
			JsonNode sql_json = request().body().asJson();
			if (sql_json == null) {
				return badRequest("Expect sql in valid json");
			}
			String sql = sql_json.findPath("sql").getTextValue();
			int number_of_result_columns = sql_json.findPath("number_of_result_columns").getIntValue();			
			resultStr = dbHandler.runQuery(sql, number_of_result_columns);
		} catch(Exception e){
			e.printStackTrace();
		}
		return ok(resultStr);		
	}

	// search reading at a specific timestamp
	public static Result searchAtTimestamp(String deviceId, Long timeStamp, String sensorType, String format){
		if(!testDBHandler()){
			return internalServerError("database conf file not found");
		}
		response().setHeader("Access-Control-Allow-Origin", "*");
		models.SensorReading reading = dbHandler.searchReading(deviceId, timeStamp, sensorType);
		if(reading == null){
			return notFound("no reading found");
		}
		String ret = format.equals("json") ? reading.toJSONString() : reading.toCSVString(); 
		return ok(ret);
	}

	
}
