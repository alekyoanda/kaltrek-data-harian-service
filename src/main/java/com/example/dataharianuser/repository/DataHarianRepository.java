package com.example.dataharianuser.repository;

import com.example.dataharianuser.model.DataHarian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Optional;

@Repository
public interface DataHarianRepository extends JpaRepository<DataHarian, Long> {
    List<DataHarian> findAll();

    List<DataHarian> findAllByUserId(Integer userId);

    Optional<DataHarian> findById(@NonNull Long id);
}
