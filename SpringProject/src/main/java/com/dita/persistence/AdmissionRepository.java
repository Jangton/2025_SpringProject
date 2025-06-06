package com.dita.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dita.domain.Admission;

public interface AdmissionRepository extends JpaRepository<Admission, Integer> {

    // 입원 대기 환자 (병상 없음 + 퇴원 안 함)
    @Query("SELECT a FROM Admission a WHERE a.bed IS NULL AND a.dischargeAt IS NULL")
    List<Admission> findWaitingPatients();

    // 병실별 현재 입원 중인 환자 (퇴원하지 않은 경우만)
    @Query("SELECT a FROM Admission a WHERE a.bed.ward.name = :wardName AND a.dischargeAt IS NULL")
    List<Admission> findByWardName(@Param("wardName") String wardName);
    
    //상세
    @Query("SELECT a FROM Admission a WHERE a.patient.patientId = :patientId AND a.dischargeAt IS NULL")
    Optional<Admission> findByPatientId(@Param("patientId") int patientId);

}

