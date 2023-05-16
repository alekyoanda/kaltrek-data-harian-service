package com.example.dataharianuser.service;

import com.example.dataharianuser.dto.DataHarianDetailsData;
import com.example.dataharianuser.dto.DataHarianDetailsRequest;
import com.example.dataharianuser.dto.DataHarianRequest;
import com.example.dataharianuser.dto.DataHarianResponse;
import com.example.dataharianuser.exception.DataHarianDoesNotExistException;
import com.example.dataharianuser.model.DataHarian;
import com.example.dataharianuser.model.DataHarianDetails;
import com.example.dataharianuser.repository.DataHarianDetailsRepository;
import com.example.dataharianuser.repository.DataHarianRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.internal.util.ZonedDateTimeComparator;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DataHarianServiceImpl implements DataHarianService{
    private final DataHarianRepository dataHarianRepository;
    private final DataHarianDetailsRepository dataHarianDetailsRepository;

    private final DataHarianDetailsService dataHarianDetailsService;
    private final RestTemplate restTemplate;

    @Override
    public List<DataHarianResponse> findAllByUserId(Integer userId, String bearerToken) {
        return dataHarianRepository.findAllByUserId(userId)
                .stream()
                .map(dataHarianUser -> DataHarianResponse.fromDataHarian(dataHarianUser, dataHarianDetailsRepository.findAllByDataHarianId(dataHarianUser.getId()), restTemplate, bearerToken))
                .toList();
    }

    @Override
    public DataHarianResponse findDataHarianByDateAndUserId(Date date, Integer userId, String bearerToken) {
        DataHarian dataHarian = null;

        for (DataHarian dataHarianObj: dataHarianRepository.findAllByUserId(userId)){
            int compare = setTimeToMidnight(dataHarianObj.getDate()).compareTo(date);
            System.err.println(compare);
            if (compare == 0){
                dataHarian = dataHarianObj;
            }
        }

        if (dataHarian == null){
            throw new DataHarianDoesNotExistException(date, userId);
        }
        return DataHarianResponse.fromDataHarian(dataHarian,
                dataHarianDetailsRepository.findAllByDataHarianId(dataHarian.getId()),
                restTemplate, bearerToken);
    }

    @Override
    public DataHarian create(Integer userId, DataHarianRequest dataHarianRequest) {
        Date date = new Date();
        var dataHarian = DataHarian.builder()
                .date(date)
                .targetKalori(dataHarianRequest.getTargetKalori())
                .userId(userId)
                .build();
        dataHarianRepository.save(dataHarian);

        return dataHarian;
    }

    @Override
    public DataHarianDetailsData getDataHarianDetailsData(Integer userId, Long dataHarianId, Long dataHarianDetailsId, String bearerToken) {
        var dataHarianOptional = dataHarianRepository.findDataHarianByIdAndUserId(dataHarianId, userId);
        if (dataHarianOptional.isEmpty()){
            throw new DataHarianDoesNotExistException(dataHarianId);
        }
        DataHarian dataHarian = dataHarianOptional.get();

        return dataHarianDetailsService.read(dataHarianDetailsId, dataHarian, userId, bearerToken);
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
    public DataHarianDetails updateUbahMakanan(Integer userId, Long dataHarianId, Long dataHarianDetailsId, DataHarianDetailsRequest dataHarianDetailsRequest) {
        var dataHarianOptional = dataHarianRepository.findDataHarianByIdAndUserId(dataHarianId, userId);
        if (dataHarianOptional.isEmpty()){
            throw new DataHarianDoesNotExistException(dataHarianId);
        }
        DataHarian dataHarian = dataHarianOptional.get();

        return dataHarianDetailsService.update(dataHarianDetailsId, dataHarian, userId, dataHarianDetailsRequest);
    }

    @Override
    public DataHarianDetails deleteDataHarianDetail(Integer userId, Long dataHarianId, Long dataHarianDetailId) {
        var dataHarianOptional = dataHarianRepository.findDataHarianByIdAndUserId(dataHarianId, userId);
        if (dataHarianOptional.isEmpty()){
            throw new DataHarianDoesNotExistException(dataHarianId);
        }
        DataHarian dataHarian = dataHarianOptional.get();
        DataHarianDetails newDataHarianDetails = dataHarianDetailsService.delete(dataHarianDetailId, dataHarian, userId);



        return newDataHarianDetails;
    }

    private static Date setTimeToMidnight(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime( date );
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }
}
