package com.example.dataharianuser.service.dataHarian;

import com.example.dataharianuser.model.dto.dataHarian.DataHarianDetailsResponse;
import com.example.dataharianuser.model.dto.dataHarian.DataHarianDetailsRequest;
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
