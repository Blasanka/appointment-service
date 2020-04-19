package com.blasanka.appointment_service.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import com.blasanka.appointment_service.models.Appointment;
import com.blasanka.appointment_service.models.Doctor;
import com.blasanka.appointment_service.models.Hospital;
import com.blasanka.appointment_service.models.Patient;
import com.blasanka.appointment_service.models.User;

public class DatabaseRef {
	
	private Connection connection = null;
	
	public DatabaseRef() {
		String url = "jdbc:mysql://localhost:3306/helth_care?useUnicode=true"
				+ "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String username = "root";
		String password = "";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map<Long, Appointment> getAppointments(String type, long id) {
		
		Map<Long, Appointment> appointments = new HashMap<>();
		String sql = "";
		
		PreparedStatement statement;
		try {
			
			switch (type) {
				case "patient":
					sql = "SELECT * FROM appointments a WHERE a.patient_id = " + id
							+ " LEFT JOIN doctors d ON d.doctor_id = a.doctor_id"
							+ " LEFT JOIN patients p ON p.patient_id = a.patient_id"
							+ " LEFT JOIN hospital h ON h.hospital_id = a.hospital_id"
							+ " LEFT JOIN users u ON u.user_id = a.user_id";
					statement = connection.prepareStatement(sql);
					break;
				case "doctor":
					sql = "SELECT * FROM appointments a WHERE a.doctor_id = " + id
							+ " LEFT JOIN doctors d ON d.doctor_id = a.doctor_id"
							+ " LEFT JOIN patients p ON p.patient_id = a.patient_id"
							+ " LEFT JOIN hospital h ON h.hospital_id = a.hospital_id"
							+ " LEFT JOIN users u ON u.user_id = a.user_id";
					statement = connection.prepareStatement(sql);
					break;
				case "hospital":
					sql = "SELECT * FROM appointments a WHERE a.hospital_id = " + id
							+ " LEFT JOIN doctors d ON d.doctor_id = a.doctor_id"
							+ " LEFT JOIN patients p ON p.patient_id = a.patient_id"
							+ " LEFT JOIN hospital h ON h.hospital_id = a.hospital_id"
							+ " LEFT JOIN users u ON u.user_id = a.user_id";
					statement = connection.prepareStatement(sql);
					break;
				default:
					sql = "SELECT * FROM appointments a "
							+ " LEFT JOIN doctors d ON d.doctor_id = a.doctor_id"
							+ " LEFT JOIN patients p ON p.patient_id = a.patient_id"
							+ " LEFT JOIN hospital h ON h.hospital_id = a.hospital_id"
							+ " LEFT JOIN users u ON u.user_id = a.user_id";
					statement = connection.prepareStatement(sql);
					break;
			}
			
			ResultSet rs = statement.executeQuery(sql);
//			ResultSetMetaData rsmd = rs.getMetaData();
//			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
//				for (int i = 1; i <= columnsNumber; i++) {
//			        if (i > 1) System.out.print(",  ");
//			        String columnValue = rs.getString(i);
//			        System.out.print(columnValue + " " + rsmd.getColumnName(i));
//			    }
				Appointment appointment = new Appointment(
					rs.getLong(1),
					rs.getDate(6),
					rs.getDate(7),
					rs.getDate(8),
					new Doctor(rs.getLong(9), rs.getString(10), rs.getInt(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getLong(15)),
					new Patient(rs.getLong(16), rs.getString(17), rs.getInt(18), rs.getString(19), rs.getString(20), rs.getString(21), rs.getLong(22), rs.getLong(23)),
					new Hospital(rs.getLong(24), rs.getString(25), rs.getString(26), rs.getString(27), rs.getString(28)),
					new User(rs.getLong(29), rs.getString(30), rs.getString(31), rs.getString(32), rs.getLong(33), rs.getDate(34))
				);
				appointments.put(appointment.getAppointId(), appointment);
			}
		    rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return appointments;
	}

	public Appointment getAppointment(long id) {
		
		Appointment appointment = new Appointment();
		String sql = "SELECT * FROM appointments WHERE appoint_id='"+ id +"'";
		
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			if (rs.next()) {
				appointment = new Appointment(
					rs.getLong(1),
					rs.getDate(6),
					rs.getDate(7),
					rs.getDate(8),
					new Doctor(rs.getLong(9), rs.getString(10), rs.getInt(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getLong(15)),
					new Patient(rs.getLong(16), rs.getString(17), rs.getInt(18), rs.getString(19), rs.getString(20), rs.getString(21), rs.getLong(22), rs.getLong(23)),
					new Hospital(rs.getLong(24), rs.getString(25), rs.getString(26), rs.getString(27), rs.getString(28)),
					new User(rs.getLong(29), rs.getString(30), rs.getString(31), rs.getString(32), rs.getLong(33), rs.getDate(34))
				);
			}
		    rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return appointment;
	}
	

	public int addAppointment(Appointment appointment) {
		String sql = "INSERT INTO appointments(doctor_id, patient_id, hospital_id, user_id, date, time, created_at) "
				+ "VALUES(?,?,?,?,?,?,?)";
		
		PreparedStatement statement;
		int affectedRows = 0;
		try {
			statement = connection.prepareStatement(sql);
			statement.setLong(1, appointment.getDoctorId());
			statement.setLong(2, appointment.getPatientId());
			statement.setLong(3, appointment.getHospitalId());
			statement.setLong(4, appointment.getUserId());
			statement.setDate(5, appointment.getDate());
			statement.setDate(6, appointment.getDate());
			statement.setDate(7, new Date(System.currentTimeMillis()));
			affectedRows = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return affectedRows;
	}

	public int updateAppointment(Appointment appointment) {
		String sql = "UPDATE appointments SET "
				+ "doctor_id = ?,"
				+ "hospital_id = ?,"
				+ "date = ?,"
				+ "time = ? "
				+ "WHERE appoint_id = ?";
		
		PreparedStatement statement;
		int affectedRows = 0;
		try {
			statement = connection.prepareStatement(sql);
			statement.setLong(1, appointment.getDoctorId());
			statement.setLong(2, appointment.getHospitalId());
			statement.setDate(3, appointment.getDate());
			statement.setDate(4, appointment.getDate());
			statement.setLong(5, appointment.getAppointId());
			affectedRows = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return affectedRows;
	}

	public int deleteAppointment(long id) {
		String sql = "DELETE FROM appointments WHERE appoint_id = ?";
		
		PreparedStatement statement;
		int affectedRows = 0;
		try {
			statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			affectedRows = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return affectedRows;
	}
}
