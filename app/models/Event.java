package models;

import org.json.JSONException;
import org.json.JSONObject;

public class Event {
	public Event(String userId, String eventTypeId, String dateString,
			String startTime, String endTime, String eventRecord) {
		super();
		this.userId = userId;
		this.eventTypeId = eventTypeId;
		this.dateString = dateString;
		this.startTime = startTime;
		this.endTime = endTime;
		this.eventRecord = eventRecord;
	}
	private String userId;
	private String eventTypeId;
	private String dateString;
	private String startTime;
	private String endTime;
	private String eventRecord;
	
	public String getEventTypeId() {
		return eventTypeId;
	}
	public void setEventTypeId(String eventTypeId) {
		this.eventTypeId = eventTypeId;
	}
	public String getDateString() {
		return dateString;
	}
	public void setDateString(String dateString) {
		this.dateString = dateString;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getEventRecord() {
		return eventRecord;
	}
	public void setEventRecord(String eventRecord) {
		this.eventRecord = eventRecord;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String toJSONString() {
		String jsonString = new String();
		try {
			JSONObject obj=new JSONObject();
			obj.put("userId", userId);
			obj.put("eventTypeId", eventTypeId);
			obj.put("date",  dateString);
			obj.put("startTime", startTime);
			obj.put("endTime", endTime);
			obj.put("eventRecord", eventRecord);
			jsonString = obj.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
