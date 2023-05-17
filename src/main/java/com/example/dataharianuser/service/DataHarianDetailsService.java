package com.example.dataharianuser.service;

import com.example.dataharianuser.dto.DataHarianDetailsResponse;
import com.example.dataharianuser.dto.DataHarianDetailsRequest;
import com.example.dataharianuser.model.DataHarian;
import com.example.dataharianuser.model.DataHarianDetails;
import org.springframework.stereotype.Service;

@Service
public interface DataHarianDetailsService {
    DataHarianDetails create(DataHarian dataHarian, Integer userId, DataHarianDetailsRequest dataHarianDetailsRequest);
    DataHarianDetailsResponse read(Long id, DataHarian dataHarian, Integer userId, String bearerToken);
    DataHarianDetails update(Long id, DataHarian dataHarian, Integer userId, DataHarianDetailsRequest dataHarianDetailsRequest);
    DataHarianDetails delete(Long id, DataHarian dataHarian, Integer userId);
}
