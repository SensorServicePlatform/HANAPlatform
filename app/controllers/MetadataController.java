package controllers;

import java.util.*;

import models.DBHandler;
import models.DeviceType;
import models.Event;
import models.Sensor;
import models.MessageBusHandler;
import models.SensorType;
import models.dao.DeviceTypeDao;
import models.dao.SensorDao;
import models.dao.SensorTypeDao;
//import models.cmu.sv.sensor.SensorReading;
import helper.Utils;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.sql.Timestamp;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import play.mvc.Controller;
import play.mvc.Result;
import play.libs.Json;

public class MetadataController extends Controller {
	private static DBHandler dbHandler = null;
	private static ApplicationContext context;
	private static SensorDao sensorDao;
	private static SensorTypeDao sensorTypeDao;
	private static DeviceTypeDao deviceTypeDao;

	private static void checkDao() {
		if (context == null) {
			context = new ClassPathXmlApplicationContext(
					"application-context.xml");
		}
		if (sensorDao == null) {
			sensorDao = (SensorDao) context.getBean("sensorDaoImplementation");
		}
		if (sensorTypeDao == null) {
			sensorTypeDao = (SensorTypeDao) context
					.getBean("sensorTypeDaoImplementation");
		}
		if (deviceTypeDao == null) {
			deviceTypeDao = (DeviceTypeDao) context
					.getBean("deviceTypeDaoImplementation");
		}
	}

	private static boolean testDBHandler() {
		if (dbHandler == null) {
			dbHandler = new DBHandler("conf/database.properties");
		}
		return true;
	}

	public static Result addDeviceType() {
		JsonNode json = request().body().asJson();
		if (json == null) {
			return badRequest("Expecting Json data");
		}
		if (!testDBHandler()) {
			return internalServerError("database conf file not found");
		}

		// Parse JSON FIle
		String id = json.findPath("id").getTextValue(); // add id

		String deviceTypeName = json.findPath("device_type").getTextValue();
		String manufacturer = json.findPath("manufacturer").getTextValue();
		String version = json.findPath("version").getTextValue();
		String userDefinedFields = json.findPath("user_defined_fields")
				.getTextValue();
		ArrayList<String> error = new ArrayList<String>();

		boolean result = dbHandler.addDeviceType(id, deviceTypeName,
				manufacturer, version, userDefinedFields);

		if (!result) {
			error.add(deviceTypeName);
		}

		if (error.size() == 0) {
			System.out.println("device type saved");
			ObjectNode msg = Json.newObject();
			msg.put("message", "device type saved");
			return ok(msg);
		} else {
			System.out.println("some device types not saved: "
					+ error.toString());

			ObjectNode msg = Json.newObject();
			msg.put("error", "device types not saved: " + error.toString());
			return ok(msg);
		}
	}

	public static Result addDevice() {
		JsonNode json = request().body().asJson();
		if (json == null) {
			return badRequest("Expecting Json data");
		}
		if (!testDBHandler()) {
			return internalServerError("database conf file not found");
		}

		// Parse JSON FIle
		String id = json.findPath("id").getTextValue(); // add id
		
		String uri = json.findPath("uri").getTextValue(); // which is missing 
		String deviceType = json.findPath("device_type").getTextValue();
		String deviceAgent = json.findPath("device_agent").getTextValue();
		String networkAddress = json.findPath("network_address").getTextValue();
		String locationDescription = json.findPath("location_description")
				.getTextValue();
		String latitude = json.findPath("latitude").getTextValue();
		String longitude = json.findPath("longitude").getTextValue();
		String altitude = json.findPath("altitude").getTextValue();
		String positionFormatSystem = json.findPath("position_format_system")
				.getTextValue();
		String userDefinedFields = json.findPath("user_defined_fields")
				.getTextValue();
		ArrayList<String> error = new ArrayList<String>();

		boolean result = dbHandler.addDeviceNew(id, uri, deviceType, deviceAgent,
				networkAddress, locationDescription, latitude, longitude,
				altitude, positionFormatSystem, userDefinedFields);

		if (!result) {
			error.add(deviceType);
		}

		if (error.size() == 0) {
			System.out.println("device saved");

			ObjectNode msg = Json.newObject();
			msg.put("message", "device saved");
			return ok(msg);
		} else {
			System.out.println("some devices not saved: " + error.toString());

			ObjectNode msg = Json.newObject();
			msg.put("error", "device not saved: " + error.toString());
			return ok(msg);
		}
	}

	public static Result addSensorType() {
		JsonNode json = request().body().asJson();
		if (json == null) {
			return badRequest("Expecting Json data");
		}
		if (!testDBHandler()) {
			return internalServerError("database conf file not found");
		}

		// Parse JSON FIle
		String id = json.findPath("id").getTextValue(); // add id

		String sensorType = json.findPath("sensor_type").getTextValue();

		// below are the code tweeked later
		String manufacturer = json.findPath("manufacturer").getTextValue();
		double version = json.findPath("version").getDoubleValue();
		double maxValue = json.findPath("maxValue").getDoubleValue();
		double minValue = json.findPath("minValue").getDoubleValue();
		String unit = json.findPath("unit").getTextValue();
		String interpreter = json.findPath("interpreter").getTextValue();

		String userDefinedFields = json.findPath("user_defined_fields")
				.getTextValue();
		ArrayList<String> error = new ArrayList<String>();

		boolean result = dbHandler.addSensorType(id, sensorType, manufacturer,
				version, maxValue, minValue, unit, interpreter,
				userDefinedFields);

		if (!result) {
			error.add(sensorType);
		}

		if (error.size() == 0) {
			System.out.println("sensor type saved");

			ObjectNode msg = Json.newObject();
			msg.put("message", "sensor type saved");
			return ok(msg);
		} else {
			System.out.println("some sensor types not saved: "
					+ error.toString());

			ObjectNode msg = Json.newObject();
			msg.put("error", "sensor type not saved " + error.toString());
			return ok(msg);
		}
	}

	public static Result addSensor() {
		JsonNode json = request().body().asJson();
		if (json == null) {
			return badRequest("Expecting Json data");
		}
		if (!testDBHandler()) {
			return internalServerError("database conf file not found");
		}

		// Parse JSON FIle
		String id = json.findPath("id").getTextValue(); // add id

		String printName = json.findPath("print_name").getTextValue();
		String sensorType = json.findPath("sensor_type").getTextValue();
		String deviceId = json.findPath("device_id").getTextValue();
		String userDefinedFields = json.findPath("user_defined_fields")
				.getTextValue();
		ArrayList<String> error = new ArrayList<String>();

		boolean result = dbHandler.addSensor(id, printName, sensorType,
				deviceId, userDefinedFields);

		if (!result) {
			error.add(sensorType);
		}

		if (error.size() == 0) {
			System.out.println("sensor saved");

			ObjectNode msg = Json.newObject();
			msg.put("message", "sensor saved");
			return ok(msg);
		} else {
			System.out.println("some sensors not saved: " + error.toString());

			ObjectNode msg = Json.newObject();
			msg.put("error", "sensor not saved " + error.toString());
			return ok(msg);
		}
	}

	public static Result deleteSensorType(String guid) {
		if (!testDBHandler()) {
			return internalServerError("database conf file not found");
		}

		boolean result = dbHandler.deleteSensorType(guid);

		ArrayList<String> error = new ArrayList<String>();
		if (!result) {
			error.add(guid);
		}

		if (error.size() == 0) {
			System.out.println("sensor type deleted");

			ObjectNode msg = Json.newObject();
			msg.put("message", "sensor type deleted");
			return ok(msg);
		} else {
			System.out.println("sensor type not deleted: " + error.toString());

			ObjectNode msg = Json.newObject();
			msg.put("error", "sensor type not deleted " + error.toString());
			return ok(msg);
		}
	}

	public static Result deleteSensor(String guid) {
		if (!testDBHandler()) {
			return internalServerError("database conf file not found");
		}

		boolean result = dbHandler.deleteSensor(guid);

		ArrayList<String> error = new ArrayList<String>();
		if (!result) {
			error.add(guid);
		}

		if (error.size() == 0) {
			System.out.println("sensor deleted");
			ObjectNode msg = Json.newObject();
			msg.put("message", "sensor deleted");
			return ok(msg);
		} else {
			System.out.println("sensor not deleted: " + error.toString());
			ObjectNode msg = Json.newObject();
			msg.put("error", "sensor not deleted " + error.toString());
			return ok(msg);
		}
	}

	public static Result deleteDeviceType(String deviceTypeKey) {
		if (!testDBHandler()) {
			return internalServerError("database conf file not found");
		}

		boolean result = dbHandler.deleteDeviceType(deviceTypeKey);

		ArrayList<String> error = new ArrayList<String>();
		if (!result) {
			error.add(deviceTypeKey);
		}

		if (error.size() == 0) {
			System.out.println("device type deleted");
			ObjectNode msg = Json.newObject();
			msg.put("message", "device type deleted");
			return ok(msg);
		} else {
			System.out.println("device type not deleted: " + error.toString());
			ObjectNode msg = Json.newObject();
			msg.put("error", "device type not deleted " + error.toString());
			return ok(msg);
		}
	}

	public static Result deleteDevice(String guid) {
		if (!testDBHandler()) {
			return internalServerError("database conf file not found");
		}

		boolean result = dbHandler.deleteDevice(guid);

		ArrayList<String> error = new ArrayList<String>();
		if (!result) {
			error.add(guid);
		}

		if (error.size() == 0) {
			System.out.println("device deleted");
			ObjectNode msg = Json.newObject();
			msg.put("message", "device deleted");
			return ok(msg);
		} else {
			System.out.println("device not deleted: " + error.toString());
			ObjectNode msg = Json.newObject();
			msg.put("error", "device not deleted " + error.toString());
			return ok(msg);
		}
	}

	// query for readings
	public static Result sql_query() {
		String resultStr = "";

		if (!testDBHandler()) {
			return internalServerError("database conf file not found");
		}
		try {
			response().setHeader("Access-Control-Allow-Origin", "*");
			JsonNode sql_json = request().body().asJson();
			if (sql_json == null) {
				return badRequest("Expect sql in valid json");
			}
			String sql = sql_json.findPath("sql").getTextValue();
			int number_of_result_columns = sql_json.findPath(
					"number_of_result_columns").getIntValue();
			resultStr = dbHandler.runQuery(sql, number_of_result_columns);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ok(resultStr);
	}

	// search reading at a specific timestamp
	public static Result searchAtTimestamp(String deviceId, Long timeStamp,
			String sensorType, String format) {
		if (!testDBHandler()) {
			return internalServerError("database conf file not found");
		}
		response().setHeader("Access-Control-Allow-Origin", "*");
		models.SensorReading reading = dbHandler.searchReading(deviceId,
				timeStamp, sensorType);
		if (reading == null) {
			ObjectNode notFoundMsg = Json.newObject();
			notFoundMsg.put("message", "no reading found");
			return notFound(notFoundMsg);
		}
		String ret = format.equals("json") ? reading.toJSONString() : reading
				.toCSVString();
		return ok(ret);
	}

	// Get all the sensors
	public static Result get_sensors(String format) {
		if (!testDBHandler()) {
			return internalServerError("database conf file not found");
		}
		response().setHeader("Access-Control-Allow-Origin", "*");
		checkDao();
		List<Sensor> sensors = sensorDao.getAllSensors();
		if (sensors == null || sensors.isEmpty()) {
			ObjectNode notFoundMsg = Json.newObject();
			notFoundMsg.put("message", "no reading found");
			return notFound(notFoundMsg);
		}
		String ret = "";
		if (format.equals("json")) {

			for (Sensor sensor : sensors) {
				if (ret.isEmpty())
					ret += "[";
				else
					ret += ',';
				ret += sensor.toJSONString();
			}
			ret += "]";
		} else if (format.equals("csv")) {
			for (Sensor sensor : sensors) {
				if (ret.isEmpty())
					ret += sensor.getCSVHeader();
				else
					ret += '\n';
				ret += sensor.toCSVString();
			}
			ret += "]";
		}

		return ok(ret);
	}

	// Get all the sensor types
	public static Result get_sensor_types(String format) {
		if (!testDBHandler()) {
			return internalServerError("database conf file not found");
		}
		response().setHeader("Access-Control-Allow-Origin", "*");
		checkDao();
		List<SensorType> sensorTypes = sensorTypeDao.getAllSensorTypes();
		if (sensorTypes == null || sensorTypes.isEmpty()) {
			ObjectNode notFoundMsg = Json.newObject();
			notFoundMsg.put("message", "no reading found");
			return notFound(notFoundMsg);
		}
		String ret = "";
		if (format.equals("json")) {

			for (SensorType sensorType : sensorTypes) {
				if (ret.isEmpty())
					ret += "[";
				else
					ret += ',';
				ret += sensorType.toJSONString();
			}
			ret += "]";
		} else if (format.equals("csv")) {
			for (SensorType sensorType : sensorTypes) {
				if (ret.isEmpty())
					ret += sensorType.getCSVHeader();
				else
					ret += '\n';
				ret += sensorType.toCSVString();
			}
		}

		return ok(ret);
	}

	// Get sensor by device id
	public static Result getSensorByDeviceId(String deviceId, String format) {
		if (!testDBHandler()) {
			return internalServerError("database conf file not found");
		}
		response().setHeader("Access-Control-Allow-Origin", "*");
		checkDao();
		List<Sensor> sensors = sensorDao.getSensorByDeviceId(deviceId);
		if (sensors == null || sensors.isEmpty()) {
			ObjectNode notFoundMsg = Json.newObject();
			notFoundMsg.put("message", "no reading found");
			return notFound(notFoundMsg);
		}
		String ret = "";
		if (format.equals("json")) {

			for (Sensor sensor : sensors) {
				if (ret.isEmpty())
					ret += "[";
				else
					ret += ',';
				ret += sensor.toJSONString();
			}
			ret += "]";
		} else if (format.equals("csv")) {
			for (Sensor sensor : sensors) {
				if (ret.isEmpty())
					ret += sensor.getCSVHeader();
				else
					ret += '\n';
				ret += sensor.toCSVString();
			}
		}

		return ok(ret);
	}

	// Get all the device types
	public static Result get_device_types(String format) {
		if (!testDBHandler()) {
			return internalServerError("database conf file not found");
		}
		response().setHeader("Access-Control-Allow-Origin", "*");
		checkDao();
		List<DeviceType> deviceTypes = deviceTypeDao.getAllDeviceTypes();
		if (deviceTypes == null || deviceTypes.isEmpty()) {
			ObjectNode notFoundMsg = Json.newObject();
			notFoundMsg.put("message", "no reading found");
			return notFound(notFoundMsg);
		}
		String ret = "";
		if (format.equals("json")) {

			for (DeviceType deviceType : deviceTypes) {
				if (ret.isEmpty())
					ret += "[";
				else
					ret += ',';
				ret += deviceType.toJSONString();
			}
			ret += "]";
		} else if (format.equals("csv")) {
			for (DeviceType deviceType : deviceTypes) {
				if (ret.isEmpty())
					ret += deviceType.getCSVHeader();
				else
					ret += '\n';
				ret += deviceType.toCSVString();
			}
		}

		return ok(ret);
	}
}
