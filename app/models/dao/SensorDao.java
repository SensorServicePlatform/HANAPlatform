package models.dao;

import java.util.List;

import models.Sensor;

public interface SensorDao {
	
	public List<Sensor> getAllSensors();
	
	public List<Sensor> getSensorByDeviceId(String deviceId);
}
