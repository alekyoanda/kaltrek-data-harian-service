package com.example.dataharianuser.repository;

import com.example.dataharianuser.model.DataHarian;
import com.example.dataharianuser.model.DataHarianDetails;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DataHarianDetailsRepository extends JpaRepository<DataHarianDetails, Long> {
    List<DataHarianDetails> findAll();
    List<DataHarianDetails> findAllByDataHarianId(Long dataHarianId);
    Optional<DataHarianDetails> findDataHarianDetailsByIdAndDataHarianId(Long id, Long dataHarianId);
}
