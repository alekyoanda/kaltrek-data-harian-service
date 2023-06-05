package com.example.dataharianuser.service.data_harian;

import com.example.dataharianuser.model.dto.data_harian.DataHarianDetailsResponse;
import com.example.dataharianuser.model.dto.data_harian.DataHarianDetailsRequest;
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
