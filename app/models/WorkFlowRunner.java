package models;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class WorkFlowRunner {
		private String virtualDeviceID;
		private String sensorlDeviceID;
		private String topic;
		private MessageBusHandler handler;
		
		public WorkFlowRunner(String virtualDeviceID, String sensorDeviceID, String topic){
			this.virtualDeviceID = virtualDeviceID;
			this.topic = topic;
			this.sensorlDeviceID = sensorDeviceID;
			this.handler = new MessageBusHandler();
		}
		
		public boolean notifyVirtualDevice(int value){
			return handler.publishToListener(this.topic, this.virtualDeviceID, value);
		}
		
		public int computeThreshold(){
			DBHandler dbHandler = new DBHandler("conf/database.properties");
			//dbHandler.makeConnection();
			int numAlert = 0;
			try {
				CallableStatement statement = dbHandler.getConnection().prepareCall("CALL CMU.check_body_temperature_threshold(?)");
				statement.setInt(1, 3);
				statement.executeUpdate();
				ResultSet bodyTemp = statement.getResultSet();
				
				if(!bodyTemp.next()){
					return 0;
				}
				numAlert = bodyTemp.getInt(1);
				System.out.println(numAlert);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return numAlert;
			//ResultSet heartRate = reading.executeSQL("SELECT * FROM CMU.CMU_SENSOR WHERE DEVICEID='" + this.sensorlDeviceID + "' AND SENSORTYPE='heart_rate'");
			//ResultSet bloodPressure = reading.executeSQL("SELECT * FROM CMU.CMU_SENSOR WHERE DEVICEID='" + this.sensorlDeviceID + "' AND SENSORTYPE='blood_pressure'");
			//ResultSet avgTemp = reading.executeSQL("SELECT * FROM CMU.CMU_SENSOR WHERE DEVICEID='" + this.sensorlDeviceID + "' AND SENSORTYPE='avg_temp'");
			
			
			
		}
}
