package controllers;

import java.util.ArrayList;
import java.util.List;

import models.DBHandler;
import models.Event;
import models.dao.DeviceDao;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import play.mvc.Controller;
import play.mvc.Result;
import play.libs.Json;

public class UserEventController extends Controller {
	private static DBHandler dbHandler = null;
	private static ApplicationContext context;
	private static DeviceDao deviceDao;

	private static void checkDao() {
		if (context == null) {
			context = new ClassPathXmlApplicationContext(
					"application-context.xml");
		}
		if (deviceDao == null) {
			deviceDao = (DeviceDao) context.getBean("deviceDaoImplementation");
		}
	}

	private static boolean testDBHandler() {
		if (dbHandler == null) {
			dbHandler = new DBHandler("conf/database.properties");
		}
		return true;
	}

	public static Result getUserEventHistory(String userId, String eventTypeId,
			String startDate, String endDate) {
		if (!testDBHandler()) {
			return internalServerError("database conf file not found");
		}
		response().setHeader("Access-Control-Allow-Origin", "*");
		// checkDao();
		List<Event> events = dbHandler.getUserEventHistory(userId, eventTypeId,
				startDate, endDate);
		if (events == null || events.isEmpty()) {
			ObjectNode notFoundMsg = Json.newObject();
			notFoundMsg.put("message","no reading found");
			return notFound(notFoundMsg);
		}
		String ret = new String();

		for (models.Event event : events) {
			if (ret.isEmpty())
				ret += "[";
			else
				ret += ',';
			ret += event.toJSONString();
		}
		ret += "]";

		return ok(ret);
	}

	public static Result recordEvent() {
		JsonNode json = request().body().asJson();
		if (json == null) {
			return badRequest("Expecting Json data");
		}
		if (!testDBHandler()) {
			return internalServerError("database conf file not found");
		}

		// Parse JSON FIle
		String userId = json.findPath("userId").getTextValue();
		String eventTypeId = json.findPath("eventTypeId").getTextValue();
		String dateString = json.findPath("date").getTextValue();
		String startTime = json.findPath("startTime").getTextValue();
		String endTime = json.findPath("endTime").getTextValue();
		String eventRecord = json.findPath("eventRecord").getTextValue();

		models.Event event = new Event(userId, eventTypeId, dateString,
				startTime, endTime, eventRecord);

		System.out.println(event.toJSONString());

		ArrayList<String> error = new ArrayList<String>();

		boolean result = dbHandler.recordEvent(event);

		if (!result) {
			error.add(event.toJSONString());
		}

		if (error.size() == 0) {
			System.out.println("sensor type saved");
			return ok("sensor type saved");
		} else {
			System.out.println("some sensor types not saved: "
					+ error.toString());
			return ok("some sensor types not saved: " + error.toString());
		}
	}

}
