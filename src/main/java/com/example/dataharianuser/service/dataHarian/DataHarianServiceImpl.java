package com.example.dataharianuser.service.dataHarian;

import com.example.dataharianuser.model.DataHarianDetails;
import com.example.dataharianuser.model.dto.dataHarian.DataHarianDetailsResponse;
import com.example.dataharianuser.model.dto.dataHarian.DataHarianDetailsRequest;
import com.example.dataharianuser.model.dto.dataHarian.DataHarianRequest;
import com.example.dataharianuser.model.dto.dataHarian.DataHarianResponse;
import com.example.dataharianuser.exception.DataHarianDoesNotExistException;
import com.example.dataharianuser.exception.DataHarianWithSameDateAlreadyExistException;
import com.example.dataharianuser.model.DataHarian;
import com.example.dataharianuser.repository.DataHarianDetailsRepository;
import com.example.dataharianuser.repository.DataHarianRepository;
import com.example.dataharianuser.service.mapper.DataHarianResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataHarianServiceImpl implements DataHarianService{
    private final DataHarianRepository dataHarianRepository;
    private final DataHarianDetailsRepository dataHarianDetailsRepository;

    private final DataHarianDetailsService dataHarianDetailsService;

    private final DataHarianResponseMapper dataHarianResponseMapper;

    @Override
    public List<DataHarianResponse> findAllByUserId(Integer userId, String bearerToken) {
        List<DataHarianResponse> result = new ArrayList<>();
        List<DataHarian> dataHarianList = dataHarianRepository.findAllByUserId(userId);

        for (DataHarian dataHarianUser : dataHarianList) {
            List<DataHarianDetails> details = dataHarianDetailsRepository.findAllByDataHarianId(dataHarianUser.getId());
            DataHarianResponse response = dataHarianResponseMapper.mapToDataHarianResponse(dataHarianUser, details, bearerToken);
            result.add(response);
        }

        return result;
    }

    @Override
    public DataHarianResponse findDataHarianByDateAndUserId(Date date, Integer userId, String bearerToken) {
        DataHarian dataHarian = null;

        for (DataHarian dataHarianObj: dataHarianRepository.findAllByUserId(userId)){
            int compare = setTimeToMidnight(dataHarianObj.getDate()).compareTo(setTimeToMidnight(date));
            if (compare == 0){
                dataHarian = dataHarianObj;
            }
        }

        if (dataHarian == null){
            throw new DataHarianDoesNotExistException(date, userId);
        }

        return dataHarianResponseMapper.mapToDataHarianResponse(dataHarian,
                dataHarianDetailsRepository.findAllByDataHarianId(dataHarian.getId()),
                bearerToken);
    }

    @Override
    public DataHarian create(Integer userId, DataHarianRequest dataHarianRequest) {
        Date date = new Date();

        if (dataHarianRepository.existsByUserIdAndDate(userId, setTimeToMidnight(date))) {
            throw new DataHarianWithSameDateAlreadyExistException(userId, setTimeToMidnight(date));
        }

        var dataHarian = DataHarian.builder()
                .date(setTimeToMidnight(date))
                .targetKalori(dataHarianRequest.getTargetKalori())
                .userId(userId)
                .dataHarianDetailsList(new ArrayList<>())
                .build();
        dataHarianRepository.save(dataHarian);

        return dataHarian;
    }

    @Override
    public DataHarianDetailsResponse getDataHarianDetails(Integer userId, Long dataHarianId, Long dataHarianDetailsId, String bearerToken) {
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
                .id(dataHarian.getId())
                .date(dataHarian.getDate())
                .targetKalori(dataHarianRequest.getTargetKalori())
                .dataHarianDetailsList(dataHarian.getDataHarianDetailsList())
                .userId(userId)
                .build();

        dataHarianRepository.save(updateData);

        return updateData;
    }

    @Override
    public DataHarian updateTambahMakanan(Integer userId, Long id, DataHarianDetailsRequest dataHarianDetailsRequest) {
        var dataHarianOptional = dataHarianRepository.findDataHarianByIdAndUserId(id, userId);
        if (dataHarianOptional.isEmpty()){
            throw new DataHarianDoesNotExistException(id);
        }
        DataHarian dataHarian = dataHarianOptional.get();

        dataHarianDetailsService.create(dataHarian, userId, dataHarianDetailsRequest);

        return dataHarian;
    }

    @Override
    public DataHarian updateUbahMakanan(Integer userId, Long dataHarianId, Long dataHarianDetailsId, DataHarianDetailsRequest dataHarianDetailsRequest) {
        var dataHarianOptional = dataHarianRepository.findDataHarianByIdAndUserId(dataHarianId, userId);
        if (dataHarianOptional.isEmpty()){
            throw new DataHarianDoesNotExistException(dataHarianId);
        }
        DataHarian dataHarian = dataHarianOptional.get();

        dataHarianDetailsService.update(dataHarianDetailsId, dataHarian, userId, dataHarianDetailsRequest);

        return dataHarian;
    }

    @Override
    public DataHarian deleteDataHarianDetail(Integer userId, Long dataHarianId, Long dataHarianDetailId) {
        var dataHarianOptional = dataHarianRepository.findDataHarianByIdAndUserId(dataHarianId, userId);
        if (dataHarianOptional.isEmpty()){
            throw new DataHarianDoesNotExistException(dataHarianId);
        }
        DataHarian dataHarian = dataHarianOptional.get();
        dataHarianDetailsService.delete(dataHarianDetailId, dataHarian, userId);

        return dataHarian;
    }

    public static Date setTimeToMidnight(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime( date );
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 1);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }
}
