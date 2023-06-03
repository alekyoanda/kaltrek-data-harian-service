package com.example.dataharianuser.repository;

import com.example.dataharianuser.model.DataHarian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DataHarianRepository extends JpaRepository<DataHarian, Long> {
    @NonNull
    List<DataHarian> findAll();

    @Query(value = "SELECT * FROM data_harian GROUP BY userId", nativeQuery = true)
    List<DataHarian> findAllDistinctUserIdFromDataHarian();

    List<DataHarian> findAllByUserId(Integer userId);

    Optional<DataHarian> findDataHarianByIdAndUserId(@NonNull Long id, Integer userId);

    Optional<DataHarian> findFirstByUserIdOrderByDateDesc(Integer userId);

    boolean existsByUserIdAndDate(Integer userId, Date date);

}
