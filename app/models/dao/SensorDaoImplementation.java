package models.dao;

import java.util.List;

import models.Sensor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

public class SensorDaoImplementation implements SensorDao{
	private SimpleJdbcTemplate simpleJdbcTemplate;
	
	public void setSimpleJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
		this.simpleJdbcTemplate = jdbcTemplate;
	}
	@Override
	public List<Sensor> getAllSensors() {
		final String NEW_SQL = "SELECT GUID, PRINT_NAME, SENSOR_TYPE, DEVICE FROM CMU.NEW_SENSORS";
		List<Sensor> sensors= simpleJdbcTemplate.query(NEW_SQL, new ParameterizedBeanPropertyRowMapper<Sensor>(){

			@Override
			public Sensor mapRow(ResultSet rs, int rowNum) throws SQLException {
				Sensor sensor = new Sensor();
				sensor.setSensorId(rs.getString("GUID"));
				sensor.setSensorName(rs.getString("PRINT_NAME"));
				sensor.setSensorType(rs.getString("SENSOR_TYPE"));
				sensor.setDeviceId(rs.getString("DEVICE"));
				return sensor;
			}

		});
		return sensors;
	}
	
	@Override
	public List<Sensor> getSensorByDeviceId(String deviceId) {
		final String NEW_SQL = "SELECT GUID, PRINT_NAME, SENSOR_TYPE, DEVICE FROM CMU.NEW_SENSORS where DEVICE = ?";
		List<Sensor> sensors= simpleJdbcTemplate.query(NEW_SQL, new ParameterizedBeanPropertyRowMapper<Sensor>(){

			@Override
			public Sensor mapRow(ResultSet rs, int rowNum) throws SQLException {
				Sensor sensor = new Sensor();
				sensor.setSensorId(rs.getString("GUID"));
				sensor.setSensorName(rs.getString("PRINT_NAME"));
				sensor.setSensorType(rs.getString("SENSOR_TYPE"));
				sensor.setDeviceId(rs.getString("DEVICE"));
				return sensor;
			}

		}, deviceId);
		return sensors;
	}
}