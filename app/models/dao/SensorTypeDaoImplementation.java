package models.dao;

import java.util.List;

import models.SensorType;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

public class SensorTypeDaoImplementation implements SensorTypeDao{
	private SimpleJdbcTemplate simpleJdbcTemplate;
	
	public void setSimpleJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
		this.simpleJdbcTemplate = jdbcTemplate;
	}
	@Override
	public List<SensorType> getAllSensorTypes() {
		final String NEW_SQL = "SELECT * FROM CMU.NEW_SENSOR_TYPES";
		List<SensorType> sensorTypes= simpleJdbcTemplate.query(NEW_SQL, new ParameterizedBeanPropertyRowMapper<SensorType>(){

			@Override
			public SensorType mapRow(ResultSet rs, int rowNum) throws SQLException {
				SensorType sensorType = new SensorType();
				sensorType.setSensorTypeId(rs.getString("GUID"));
				sensorType.setSensorTypeName(rs.getString("SENSOR_CATEGORY"));
				sensorType.setManufacturer(rs.getString("MANUFACTURER"));
				sensorType.setVersion(rs.getDouble("VERSION"));
				sensorType.setMax_value(rs.getDouble("MAX_VALUE"));
				sensorType.setMin_value(rs.getDouble("MIN_VALUE"));
				sensorType.setUnit(rs.getString("Unit"));
				sensorType.setInterpreter(rs.getString("INTERPRETER"));
				sensorType.setDeviceType(rs.getString("DEVICE_TYPE"));
				
				return sensorType;
			}

		});
		return sensorTypes;
	}
}