package com.blasanka.appointment_service.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.blasanka.appointment_service.database.DatabaseRef;
import com.blasanka.appointment_service.exceptions.DataNotFoundException;
import com.blasanka.appointment_service.models.Appointment;

public class AppointmentController {
	
	private DatabaseRef dbRef;
//	private Map<Long, Appointment> appointments = DatabaseRef.getAppointments();

	public AppointmentController() {
		dbRef = new DatabaseRef();
	}
	
	public List<Appointment> getAppointments(String type, long id) {
		Map<Long, Appointment> appointments = dbRef.getAppointments(type, id);
		return new ArrayList<Appointment>(appointments.values());
	}

	public List<Appointment> getSortedAppointments(final String sort) {
		Map<Long, Appointment> appointments = dbRef.getAppointments("", 0L);
		List<Appointment> appoints = new ArrayList<Appointment>(appointments.values());
		Collections.sort(appoints, new Comparator<Appointment>() {
		  public int compare(Appointment o1, Appointment o2) {
			  if (sort.equals("no")) return Long.compare(o1.getAppointId(), o2.getAppointId());
			  else if (sort.equals("user")) return Long.compare(o1.getUserId(), o2.getUserId());
			  else if (sort.equals("doctor")) return o1.getDoctor().compareTo(o2.getDoctor());
			  else if (sort.equals("hospital")) return o1.getHospital().compareTo(o2.getHospital());
		      return o1.getDate().compareTo(o2.getDate());
		  }
		});
		return appoints;
	}

	public List<Appointment> getAppointmentsPaginated(int start, int size) {
		Map<Long, Appointment> appointments = dbRef.getAppointments("", 0L);
		List<Appointment> appoints = new ArrayList<Appointment>(appointments.values());
		if (start + size > appoints.size()) return new ArrayList<Appointment>();
		return appoints.subList(start, start + size);
	}
	
	public Appointment getAppointment(long id) {
		Map<Long, Appointment> appointments = dbRef.getAppointments("", 0L);
		Appointment appointment = appointments.get(id);
		if (appointment == null)
			throw new DataNotFoundException("Appointment with id: " + id + " not found!");
		return appointment;
	}

	public int addAppointment(Appointment appointment) {
		return dbRef.addAppointment(appointment);
	}
	
	public int updateAppointment(Appointment appointment) {
		if (appointment.getAppointId() <= 0) return 0;
		return dbRef.updateAppointment(appointment);
	}
	
	public int removeAppointment(long id) {
		if (id <= 0) return 0;
		return dbRef.deleteAppointment(id);
	}
	
}
