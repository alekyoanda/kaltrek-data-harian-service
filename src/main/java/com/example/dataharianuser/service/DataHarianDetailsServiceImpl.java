package com.example.dataharianuser.service;

import com.example.dataharianuser.dto.DataHarianDetailsResponse;
import com.example.dataharianuser.dto.DataHarianDetailsRequest;
import com.example.dataharianuser.exception.DataHarianDetailsDoesNotExistException;
import com.example.dataharianuser.model.DataHarian;
import com.example.dataharianuser.model.DataHarianDetails;
import com.example.dataharianuser.repository.DataHarianDetailsRepository;
import com.example.dataharianuser.repository.DataHarianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DataHarianDetailsServiceImpl implements DataHarianDetailsService{
    private final DataHarianDetailsRepository dataHarianDetailsRepository;
    private final DataHarianRepository dataHarianRepository;
    private final RestTemplate restTemplate;

    @Override
    public DataHarianDetails create(DataHarian dataHarian, Integer userId, DataHarianDetailsRequest dataHarianDetailsRequest) {
        DataHarianDetails newDataHarianDetails = DataHarianDetails.builder()
                .dataHarian(dataHarian)
                .makananId(dataHarianDetailsRequest.getMakananId())
                .jumlahTakaran(dataHarianDetailsRequest.getJumlahTakaran())
                .isCustomTakaran(dataHarianDetailsRequest.getIsCustomTakaran())
                .build();

        dataHarianDetailsRepository.save(newDataHarianDetails);

        dataHarian.addDataHarianDetails(newDataHarianDetails);
        dataHarianRepository.save(dataHarian);

        return newDataHarianDetails;
    }

    @Override
    public DataHarianDetailsResponse read(Long id, DataHarian dataHarian, Integer userId, String bearerToken) {
        var dataHarianDetailsOptional = dataHarianDetailsRepository.
                findDataHarianDetailsByIdAndDataHarianId(id, dataHarian.getId());

        if (dataHarianDetailsOptional.isEmpty()){
            throw new DataHarianDetailsDoesNotExistException(id, dataHarian.getId());
        }

        return DataHarianDetailsResponse.fromDataHarianDetails(dataHarianDetailsOptional.get(), restTemplate, bearerToken);
    }

    @Override
    public DataHarianDetails update(Long id, DataHarian dataHarian, Integer userId, DataHarianDetailsRequest dataHarianDetailsRequest) {
        var dataHarianDetailsOptional = dataHarianDetailsRepository.
                findDataHarianDetailsByIdAndDataHarianId(id, dataHarian.getId());

        if (dataHarianDetailsOptional.isEmpty()){
            throw new DataHarianDetailsDoesNotExistException(id, dataHarian.getId());
        }

        DataHarianDetails updateData = DataHarianDetails.builder()
                .id(id)
                .dataHarian(dataHarian)
                .makananId(dataHarianDetailsOptional.get().getMakananId())
                .isCustomTakaran(dataHarianDetailsRequest.getIsCustomTakaran())
                .jumlahTakaran(dataHarianDetailsRequest.getJumlahTakaran())
                .build();

        dataHarian.removeDataHarianDetails(dataHarianDetailsOptional.get());
        dataHarian.addDataHarianDetails(updateData);

        dataHarianRepository.save(dataHarian);
        dataHarianDetailsRepository.save(updateData);

        return updateData;
    }

    @Override
    public DataHarianDetails delete(Long id, DataHarian dataHarian, Integer userId) {
        Optional<DataHarianDetails> dataHarianDetailsOptional = dataHarianDetailsRepository.
                findDataHarianDetailsByIdAndDataHarianId(id, dataHarian.getId());

        if (dataHarianDetailsOptional.isEmpty()){
            throw new DataHarianDetailsDoesNotExistException(id, dataHarian.getId());
        }
        DataHarianDetails dataHarianDetails = dataHarianDetailsOptional.get();
        dataHarian.removeDataHarianDetails(dataHarianDetails);
        dataHarianRepository.save(dataHarian);
        dataHarianDetailsRepository.delete(dataHarianDetails);

        return dataHarianDetails;
    }
}
