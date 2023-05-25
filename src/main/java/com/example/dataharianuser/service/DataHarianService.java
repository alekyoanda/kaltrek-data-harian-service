package com.example.dataharianuser.service;

import com.example.dataharianuser.dto.DataHarianDetailsResponse;
import com.example.dataharianuser.dto.DataHarianDetailsRequest;
import com.example.dataharianuser.dto.DataHarianRequest;
import com.example.dataharianuser.dto.DataHarianResponse;
import com.example.dataharianuser.model.DataHarian;
import com.example.dataharianuser.model.DataHarianDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface DataHarianService {
    List<DataHarianResponse> findAllByUserId(Integer userId, String bearerToken);
    DataHarianResponse findDataHarianByDateAndUserId(Date date, Integer userId, String bearerToken);
    DataHarian create(Integer userId, DataHarianRequest dataHarianRequest);
    DataHarianDetailsResponse getDataHarianDetails(Integer userId, Long dataHarianId, Long dataHarianDetailsId, String bearerToken);
    DataHarian updateTargetKalori(Integer userId, Long id, DataHarianRequest dataHarianRequest);
    DataHarian updateTambahMakanan(Integer userId, Long id, DataHarianDetailsRequest dataHarianDetailsRequest);
    DataHarian updateUbahMakanan(Integer userId, Long dataHarianId, Long dataHarianDetailsId, DataHarianDetailsRequest dataHarianDetailsRequest);
    DataHarian deleteDataHarianDetail(Integer userId, Long dataHarianId, Long dataHarianDetailId);

}
