package com.example.dataharianuser.service;

import com.example.dataharianuser.dto.DataHarianDetailsRequest;
import com.example.dataharianuser.dto.DataHarianRequest;
import com.example.dataharianuser.dto.DataHarianResponse;
import com.example.dataharianuser.model.DataHarian;
import com.example.dataharianuser.model.DataHarianDetails;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@Service
public interface DataHarianService {
    List<DataHarianResponse> findAll();
    List<DataHarianResponse> findAllByUserId(Integer userId);
    DataHarianResponse findDataHarianByDateAndUserId(Date date, Integer userId);
    DataHarian create(Integer userId, DataHarianRequest dataHarianRequest);
    DataHarian updateTargetKalori(Integer userId, Long id, DataHarianRequest dataHarianRequest);
    DataHarianDetails updateTambahMakanan(Integer userId, Long id, DataHarianDetailsRequest dataHarianDetailsRequest);
    DataHarianDetails updateUbahMakanan(Integer userId, Long id, DataHarianDetailsRequest dataHarianDetailsRequest);
}
