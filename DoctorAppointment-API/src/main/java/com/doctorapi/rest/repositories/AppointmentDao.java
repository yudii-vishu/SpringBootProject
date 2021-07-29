package com.doctorapi.rest.repositories;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.doctorapi.rest.models.Appointment;

@Repository
public interface AppointmentDao extends JpaRepository<Appointment, Long> {
	
	
	public List<Appointment> findByOrderByIdAsc();

	public Appointment getAppointmentByPatientId(Long patientId);

	public List<Appointment> findAppointmentByDoctorId(Long doctorId);
	
	public List<Appointment> findByAppointmnetDate(LocalDate localdate);

	public Appointment findByReason(String reason);
	

	

}
