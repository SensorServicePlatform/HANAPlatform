package models.dao;

import java.util.List;

import models.Device;

import java.sql.ResultSet;
import java.sql.SQLException;


import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

public class DeviceDaoImplementation implements DeviceDao{
	private SimpleJdbcTemplate simpleJdbcTemplate;
	
	public void setSimpleJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
		this.simpleJdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Device> getAllDevices() {
		final String SQL = "SELECT \"DEVICEID\", \"DEVICETYPE\", \"DEVICEAGENT\", \"LOCATION\" FROM CMU.DEVICE";
		final String NEW_SQL = "SELECT \"URI\", \"DEVICE_TYPE\", \"DEVICE_AGENT\", \"LOCATION_DESCRIPTION\", \"USER_DEFINED_FIELDS\" FROM CMU.NEW_DEVICES";
		List<Device> devices = simpleJdbcTemplate.query(SQL, ParameterizedBeanPropertyRowMapper.newInstance(Device.class));
		devices.addAll(simpleJdbcTemplate.query(NEW_SQL, new ParameterizedBeanPropertyRowMapper<Device>(){

			@Override
			public Device mapRow(ResultSet rs, int rowNum) throws SQLException {
				Device device = new Device();
				device.setDeviceId(rs.getString("URI"));
				device.setDeviceType(rs.getString("DEVICE_TYPE"));
				device.setDeviceAgent(rs.getString("DEVICE_AGENT"));
				device.setLocation(rs.getString("LOCATION_DESCRIPTION"));
				device.setUserDefinedFields(rs.getString("USER_DEFINED_FIELDS"));
				return device;
			}

		}));
		return devices;
	}

	@Override
	public List<String> getSensorType(String deviceType) {
		// TODO Auto-generated method stub
		return null;
	}

}
