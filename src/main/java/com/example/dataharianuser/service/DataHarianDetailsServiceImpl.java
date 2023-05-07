package com.example.dataharianuser.service;

import com.example.dataharianuser.dto.DataHarianDetailsRequest;
import com.example.dataharianuser.exception.DataHarianDetailsDoesNotExistException;
import com.example.dataharianuser.model.DataHarian;
import com.example.dataharianuser.model.DataHarianDetails;
import com.example.dataharianuser.repository.DataHarianDetailsRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DataHarianDetailsServiceImpl implements DataHarianDetailsService{
    private final DataHarianDetailsRepository dataHarianDetailsRepository;

    @Override
    public DataHarianDetails create(DataHarian dataHarian, Integer userId, DataHarianDetailsRequest dataHarianDetailsRequest) {
        DataHarianDetails newDataHarianDetails = DataHarianDetails.builder()
                .dataHarian(dataHarian)
                .makananId(dataHarianDetailsRequest.getMakananId())
                .jumlahTakaran(dataHarianDetailsRequest.getJumlahTakaran())
                .isCustomTakaran(dataHarianDetailsRequest.getIsCustomTakaran())
                .build();

        dataHarianDetailsRepository.save(newDataHarianDetails);

        return newDataHarianDetails;
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

        dataHarianDetailsRepository.delete(dataHarianDetailsOptional.get());
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
//        dataHarianDetailsRepository.delete(dataHarianDetailsOptional.get());
        return dataHarianDetailsOptional.get();
    }
}
