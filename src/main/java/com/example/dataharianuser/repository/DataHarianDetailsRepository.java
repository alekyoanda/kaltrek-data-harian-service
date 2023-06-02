package com.example.dataharianuser.repository;

import com.example.dataharianuser.model.DataHarianDetails;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DataHarianDetailsRepository extends JpaRepository<DataHarianDetails, Long> {
    @NonNull List<DataHarianDetails> findAll();
    List<DataHarianDetails> findAllByDataHarianId(Long dataHarianId);
    Optional<DataHarianDetails> findDataHarianDetailsByIdAndDataHarianId(Long id, Long dataHarianId);
}
