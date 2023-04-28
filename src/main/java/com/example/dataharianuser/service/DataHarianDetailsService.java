package com.example.dataharianuser.service;

import com.example.dataharianuser.dto.DataHarianDetailsRequest;
import com.example.dataharianuser.model.DataHarian;
import com.example.dataharianuser.model.DataHarianDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DataHarianDetailsService {
    DataHarianDetails create(DataHarian dataHarian, Integer userId, DataHarianDetailsRequest dataHarianDetailsRequest);
    DataHarianDetails update(Long id, DataHarian dataHarian, Integer userId, DataHarianDetailsRequest dataHarianDetailsRequest);
}
