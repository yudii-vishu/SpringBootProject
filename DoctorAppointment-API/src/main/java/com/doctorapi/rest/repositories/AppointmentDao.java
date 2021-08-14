package com.doctorapi.rest.repositories;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.doctorapi.rest.models.Appointment;

@Repository
public interface AppointmentDao extends JpaRepository<Appointment, Long> {
	
	
	public List<Appointment> findByOrderByIdAsc();

	public Appointment getAppointmentByPatientId(Long patientId);

	public List<Appointment> findAppointmentByDoctorId(Long doctorId);
	
	public List<Appointment> findByAppointmnetDate(LocalDate localdate);

	public Appointment findByReason(String reason);

	public List<Appointment> getAppointmentByDoctorId(Long doctorId);

	@Query(value="SELECT * from appointment WHERE doctor_id=?1 and DATE(appointment_date)=CURDATE()", nativeQuery=true)
	public List<Appointment> findAllAppointmentByDoctorId(@Param("doctor") Long doctorId);

	
	

}
