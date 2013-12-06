package models.dao;

import java.util.List;

import models.DeviceType;

import java.sql.ResultSet;
import java.sql.SQLException;


import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

public class DeviceTypeDaoImplementation implements DeviceTypeDao{
	private SimpleJdbcTemplate simpleJdbcTemplate;
	
	public void setSimpleJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
		this.simpleJdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<DeviceType> getAllDeviceTypes() {
		final String NEW_SQL = "SELECT \"DEVICE_TYPE_KEY\", \"DEVICE_TYPE_NAME\", \"MANUFACTURER\", \"VERSION\" FROM CMU.NEW_DEVICE_TYPES";
		List<DeviceType> deviceTypes = simpleJdbcTemplate.query(NEW_SQL, ParameterizedBeanPropertyRowMapper.newInstance(DeviceType.class));
		deviceTypes.addAll(simpleJdbcTemplate.query(NEW_SQL, new ParameterizedBeanPropertyRowMapper<DeviceType>(){

			@Override
			public DeviceType mapRow(ResultSet rs, int rowNum) throws SQLException {
				DeviceType device = new DeviceType();
				device.setDeviceTypeKey(rs.getString("DEVICE_TYPE_KEY"));
				device.setDeviceTypeName(rs.getString("DEVICE_TYPE_NAME"));
				device.setManufacturer(rs.getString("MANUFACTURER"));
				device.setVersion(rs.getDouble("VERSION"));
				return device;
			}

		}));
		return deviceTypes;
	}
}
