package com.example.dataharianuser.service;

import com.example.dataharianuser.dto.DataHarianDetailsData;
import com.example.dataharianuser.dto.DataHarianRequest;
import com.example.dataharianuser.dto.DataHarianResponse;
import com.example.dataharianuser.exception.DataHarianDoesNotExistException;
import com.example.dataharianuser.exception.MakananDoesNotExistException;
import com.example.dataharianuser.model.DataHarian;
import com.example.dataharianuser.model.DataHarianDetails;
import com.example.dataharianuser.repository.DataHarianDetailsRepository;
import com.example.dataharianuser.repository.DataHarianRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DataHarianServiceImpl implements DataHarianService{
    private final DataHarianRepository dataHarianRepository;
    private final DataHarianDetailsRepository dataHarianDetailsRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<DataHarianResponse> findAll() {
        return dataHarianRepository.findAll()
                .stream()
                .map(dataHarian -> DataHarianResponse.fromDataHarian(dataHarian, dataHarianDetailsRepository.findAllByDataHarianId(dataHarian.getId()), restTemplate))
                .toList();
    }

    @Override
    public List<DataHarianResponse> findAllByUserId(Integer userId) {
        return dataHarianRepository.findAllByUserId(userId)
                .stream()
                .map(dataHarianUser -> DataHarianResponse.fromDataHarian(dataHarianUser, dataHarianDetailsRepository.findAllByDataHarianId(dataHarianUser.getId()), restTemplate))
                .toList();
    }

    @Override
    public DataHarian create(Integer userId, DataHarianRequest dataHarianRequest) {
        var dataHarian = DataHarian.builder()
                .date(new Date())
                .targetKalori(dataHarianRequest.getTargetKalori())
                .totalKaloriKonsumsi(dataHarianRequest.getTotalKaloriKonsumsi())
                .userId(userId)
                .build();
        dataHarianRepository.save(dataHarian);

        return dataHarian;
    }

    @Override
    public DataHarian update(Integer userId, Long id, DataHarianRequest dataHarianRequest) {
        Optional<DataHarian> dataHarianOld = dataHarianRepository.findById(id);

        if (dataHarianOld.isEmpty()){
            throw new DataHarianDoesNotExistException(id);
        }
        var dataHarian = DataHarian.builder()
                .id(id)
                .date(dataHarianOld.get().getDate())
                .targetKalori(dataHarianRequest.getTargetKalori())
                .totalKaloriKonsumsi(dataHarianRequest.getTotalKaloriKonsumsi())
                .userId(userId)
                .build();
        dataHarianRepository.save(dataHarian);

//        var listOfDataHarianInDB = dataHarianDetailsRepository.findAllByDataHarianId(id);
//        dataHarianRequest.getDataHarianDetailsDataList().forEach(details -> {
//            // Update Order includes the updates of OrderDetails.
//            // There are 3 scenarios of OrderDetails update:
//            // 1. OrderDetails exists both in Database and Put Request
//            // 2. OrderDetails exists only in Put Request
//            // 3. OrderDetails exists only in Database
//
//            var dataHarianDetails = dataHarianDetailsRepository.findByDataHarianIdAndMakananId(dataHarian.getId(), details.getMakanan().getMakananId());
//            if (dataHarianDetails.isEmpty()) {
//                dataHarianDetailsRepository.save(
//                        DataHarianDetails.builder()
//                                .dataHarian(dataHarian)
//                                .makananId(details.getMakanan().getMakananId())
//                                .makananCategory(details.getMakanan().getMakananCategory())
//                                .isCustomTakaran(details.)
//                                .build()
//                );
//            } else {
//                listOfDataHarianInDB.remove(dataHarianDetails.get());
//                dataHarianDetailsRepository.save(
//                        OrderDetails.builder()
//                                .id(dataHarianDetails.get().getId())
//                                .order(dataHarian)
//                                .quantity(details.getQuantity())
//                                .totalPrice(details.getTotalPrice())
//                                .medicine(medicine.get())
//                                .build()
//                );
//            }
//        });
//        dataHarianDetailsRepository.deleteAll(listOfDataHarianInDB);
        return dataHarian;
    }
}
