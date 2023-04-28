package com.example.dataharianuser.service;

import com.example.dataharianuser.dto.DataHarianDetailsData;
import com.example.dataharianuser.dto.DataHarianDetailsRequest;
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

    private final DataHarianDetailsService dataHarianDetailsService;
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
    public DataHarianResponse findDataHarianByDateAndUserId(Date date, Integer userId) {
        Optional<DataHarian> dataHarianOptional = dataHarianRepository.findDataHarianByDateAndUserId(date, userId);

        if (dataHarianOptional.isEmpty()){
            throw new DataHarianDoesNotExistException(date, userId);
        }
        return DataHarianResponse.fromDataHarian(dataHarianOptional.get(),
                dataHarianDetailsRepository.findAllByDataHarianId(dataHarianOptional.get().getId()),
                restTemplate);
    }

    @Override
    public DataHarian create(Integer userId, DataHarianRequest dataHarianRequest) {
        Date date = new Date();
        System.err.println(date.toString());
        var dataHarian = DataHarian.builder()
                .date(date)
                .targetKalori(dataHarianRequest.getTargetKalori())
                .totalKaloriKonsumsi(dataHarianRequest.getTotalKaloriKonsumsi())
                .userId(userId)
                .build();
        dataHarianRepository.save(dataHarian);

        return dataHarian;
    }

    @Override
    public DataHarian updateTargetKalori(Integer userId, Long id, DataHarianRequest dataHarianRequest) {
        var dataHarianOptional = dataHarianRepository.findDataHarianByIdAndUserId(id, userId);

        if (dataHarianOptional.isEmpty()){
            throw new DataHarianDoesNotExistException(id);
        }
        DataHarian dataHarian = dataHarianOptional.get();

        DataHarian updateData = DataHarian.builder()
                .id(id)
                .date(dataHarian.getDate())
                .targetKalori(dataHarianRequest.getTargetKalori())
                .totalKaloriKonsumsi(dataHarianRequest.getTotalKaloriKonsumsi())
                .dataHarianDetailsList(dataHarian.getDataHarianDetailsList())
                .userId(userId)
                .build();

        dataHarianRepository.delete(dataHarian);
        dataHarianRepository.save(updateData);

        return updateData;
    }

    @Override
    public DataHarianDetails updateTambahMakanan(Integer userId, Long id, DataHarianDetailsRequest dataHarianDetailsRequest) {
        var dataHarianOptional = dataHarianRepository.findDataHarianByIdAndUserId(id, userId);
        if (dataHarianOptional.isEmpty()){
            throw new DataHarianDoesNotExistException(id);
        }
        DataHarian dataHarian = dataHarianOptional.get();

        return dataHarianDetailsService.create(dataHarian, userId, dataHarianDetailsRequest);
    }

    @Override
    public DataHarianDetails updateUbahMakanan(Integer userId, Long id, DataHarianDetailsRequest dataHarianDetailsRequest) {
        var dataHarianOptional = dataHarianRepository.findDataHarianByIdAndUserId(id, userId);
        if (dataHarianOptional.isEmpty()){
            throw new DataHarianDoesNotExistException(id);
        }
        DataHarian dataHarian = dataHarianOptional.get();

        return dataHarianDetailsService.update(id, dataHarian, userId, dataHarianDetailsRequest);
    }

//    public DataHarianDetails update(Integer userId, Long id, DataHarianRequest dataHarianRequest) {
//        Optional<DataHarian> dataHarianOld = dataHarianRepository.findById(id);
//
//        if (dataHarianOld.isEmpty()){
//            throw new DataHarianDoesNotExistException(id);
//        }
//        var dataHarian = DataHarian.builder()
//                .id(id)
//                .date(dataHarianOld.get().getDate())
//                .targetKalori(dataHarianRequest.getTargetKalori())
//                .totalKaloriKonsumsi(dataHarianRequest.getTotalKaloriKonsumsi())
//                .userId(userId)
//                .build();
//        dataHarianRepository.save(dataHarian);
//
//        // Updating target kalori
//        var listOfDataHarianinDB = dataHarianDetailsRepository.findAllByDataHarianIdAndUserId(id, userId);
//
////        var listOfDataHarianInDB = dataHarianDetailsRepository.findAllByDataHarianId(id);
////        dataHarianRequest.getDataHarianDetailsDataList().forEach(details -> {
////            // Update Order includes the updates of OrderDetails.
////            // There are 3 scenarios of OrderDetails update:
////            // 1. OrderDetails exists both in Database and Put Request
////            // 2. OrderDetails exists only in Put Request
////            // 3. OrderDetails exists only in Database
////
////            var dataHarianDetails = dataHarianDetailsRepository.findByDataHarianIdAndMakananId(dataHarian.getId(), details.getMakanan().getMakananId());
////            if (dataHarianDetails.isEmpty()) {
////                dataHarianDetailsRepository.save(
////                        DataHarianDetails.builder()
////                                .dataHarian(dataHarian)
////                                .makananId(details.getMakanan().getMakananId())
////                                .makananCategory(details.getMakanan().getMakananCategory())
////                                .isCustomTakaran(details.)
////                                .build()
////                );
////            } else {
////                listOfDataHarianInDB.remove(dataHarianDetails.get());
////                dataHarianDetailsRepository.save(
////                        OrderDetails.builder()
////                                .id(dataHarianDetails.get().getId())
////                                .order(dataHarian)
////                                .quantity(details.getQuantity())
////                                .totalPrice(details.getTotalPrice())
////                                .medicine(medicine.get())
////                                .build()
////                );
////            }
////        });
////        dataHarianDetailsRepository.deleteAll(listOfDataHarianInDB);
//        return dataHarian;
//    }
}
