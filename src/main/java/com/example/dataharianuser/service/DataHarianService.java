package com.example.dataharianuser.service;

import com.example.dataharianuser.dto.DataHarianRequest;
import com.example.dataharianuser.dto.DataHarianResponse;
import com.example.dataharianuser.model.DataHarian;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DataHarianService {
    List<DataHarianResponse> findAll();
    List<DataHarianResponse> findAllByUserId(Integer userId);
    DataHarian create(Integer userId, DataHarianRequest dataHarianRequest);
    DataHarian update(Integer userId, Long id, DataHarianRequest orderRequest);
}
