package com.example.dataharianuser.repository;

import com.example.dataharianuser.model.DataHarian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DataHarianRepository extends JpaRepository<DataHarian, Long> {
    List<DataHarian> findAll();

    List<DataHarian> findAllByUserId(Integer userId);

    Optional<DataHarian> findDataHarianById(@NonNull Long id);

    Optional<DataHarian> findDataHarianByIdAndUserId(@NonNull Long id, Integer userId);

    Optional<DataHarian> findDataHarianByDateAndUserId(Date date, Integer userId);

    Optional<DataHarian> findFirstByUserIdOrderByDateDesc(Integer userId);

    boolean existsByUserIdAndDate(Integer userId, Date date);

}
