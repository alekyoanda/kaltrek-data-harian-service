package com.example.dataharianuser.service;

import com.example.dataharianuser.dto.DataHarianDetailsData;
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
    DataHarianDetailsData getDataHarianDetailsData(Integer userId, Long dataHarianId, Long dataHarianDetailsId, String bearerToken);
    DataHarian updateTargetKalori(Integer userId, Long id, DataHarianRequest dataHarianRequest);
    DataHarianDetails updateTambahMakanan(Integer userId, Long id, DataHarianDetailsRequest dataHarianDetailsRequest);
    DataHarianDetails updateUbahMakanan(Integer userId, Long dataHarianId, Long dataHarianDetailsId, DataHarianDetailsRequest dataHarianDetailsRequest);
    DataHarianDetails deleteDataHarianDetail(Integer userId, Long dataHarianId, Long dataHarianDetailId);
}
