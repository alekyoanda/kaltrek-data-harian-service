package com.example.dataharianuser.service.data_harian;

import com.example.dataharianuser.model.dto.data_harian.DataHarianDetailsResponse;
import com.example.dataharianuser.model.dto.data_harian.DataHarianDetailsRequest;
import com.example.dataharianuser.exception.DataHarianDetailsDoesNotExistException;
import com.example.dataharianuser.model.DataHarian;
import com.example.dataharianuser.model.DataHarianDetails;
import com.example.dataharianuser.repository.DataHarianDetailsRepository;
import com.example.dataharianuser.repository.DataHarianRepository;
import com.example.dataharianuser.service.mapper.DataHarianDetailsResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DataHarianDetailsServiceImpl implements DataHarianDetailsService{
    private final DataHarianDetailsRepository dataHarianDetailsRepository;
    private final DataHarianRepository dataHarianRepository;

    private final DataHarianDetailsResponseMapper dataHarianDetailsResponseMapper;

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

        return dataHarianDetailsResponseMapper.mapToDataHarianDetailsResponse(dataHarianDetailsOptional.get(), bearerToken);
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
