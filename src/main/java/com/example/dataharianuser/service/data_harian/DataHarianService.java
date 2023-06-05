package com.example.dataharianuser.service.data_harian;

import com.example.dataharianuser.model.dto.data_harian.DataHarianDetailsResponse;
import com.example.dataharianuser.model.dto.data_harian.DataHarianDetailsRequest;
import com.example.dataharianuser.model.dto.data_harian.DataHarianRequest;
import com.example.dataharianuser.model.dto.data_harian.DataHarianResponse;
import com.example.dataharianuser.model.DataHarian;
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
