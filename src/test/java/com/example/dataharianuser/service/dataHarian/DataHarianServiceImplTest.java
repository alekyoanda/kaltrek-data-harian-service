package com.example.dataharianuser.service.dataHarian;

import com.example.dataharianuser.dto.DataHarianDetailsRequest;
import com.example.dataharianuser.dto.DataHarianRequest;
import com.example.dataharianuser.exception.DataHarianDoesNotExistException;
import com.example.dataharianuser.exception.DataHarianWithSameDateAlreadyExistException;
import com.example.dataharianuser.model.DataHarian;
import com.example.dataharianuser.model.DataHarianDetails;
import com.example.dataharianuser.repository.DataHarianDetailsRepository;
import com.example.dataharianuser.repository.DataHarianRepository;
import com.example.dataharianuser.service.DataHarianDetailsService;
import com.example.dataharianuser.service.DataHarianDetailsServiceImpl;
import com.example.dataharianuser.service.DataHarianServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.example.dataharianuser.service.DataHarianServiceImpl.setTimeToMidnight;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataHarianServiceImplTest {

    @InjectMocks
    private DataHarianServiceImpl dataHarianService;

    @Mock
    private DataHarianDetailsServiceImpl dataHarianDetailsService;

    @Mock
    private DataHarianRepository dataHarianRepository;

    Integer userId;
    Long makananId;

    DataHarian dataHarian;
    DataHarian newDataHarian;

    DataHarianDetails dataHarianDetails;

    DataHarianRequest createRequest;
    DataHarianRequest createRequest2;
    DataHarianRequest updateRequest;

    DataHarianDetailsRequest createDataHarianDetailsRequest;
    DataHarianDetailsRequest updateDataHarianDetailsRequest;

    @BeforeEach
    void setUp() {
        userId = 1;
        makananId = 1L;
        // Set up mock objects or test data if needed
        createRequest = DataHarianRequest.builder()
                .targetKalori(1600.0)
                .build();

        createRequest2 = DataHarianRequest.builder()
                .targetKalori(1800.0)
                .build();

        updateRequest = DataHarianRequest.builder()
                .targetKalori(2500.0)
                .build();

        createDataHarianDetailsRequest = DataHarianDetailsRequest.builder()
                .makananId(makananId)
                .jumlahTakaran(150.0)
                .build();

        updateDataHarianDetailsRequest = DataHarianDetailsRequest.builder()
                .makananId(makananId)
                .jumlahTakaran(210.0)
                .build();

        dataHarian = DataHarian.builder()
                .id(1L)
                .targetKalori(1600.0)
                .date(setTimeToMidnight(new Date()))
                .dataHarianDetailsList(new ArrayList<>())
                .userId(userId)
                .build();

        newDataHarian = DataHarian.builder()
                .id(1L)
                .targetKalori(2500.0)
                .date(setTimeToMidnight(new Date()))
                .dataHarianDetailsList(new ArrayList<>())
                .userId(userId)
                .build();

        dataHarianDetails = DataHarianDetails.builder()
                .dataHarian(dataHarian)
                .makananId(makananId)
                .jumlahTakaran(150.0)
                .id(1L)
                .build();
    }

//    @Test
//    void whenFindAllDataHarianByUserIdShouldReturnListOfDataHarian() {
//        List<DataHarianResponse> allDataHarianInUser = List.of(dataHarian);
//
//        when(dataHarianRepository.findAll()).thenReturn(allDataHarianInUser);
//
//        List<DataHarian> result = service.findAllByUserId(userId);
//        verify(dataHarianRepository, atLeastOnce()).findAll();
//        Assertions.assertEquals(allDataHarianInUser, result);
//    }

    @Test
    void whenCreateDataHarianShouldReturnTheCreatedDataHarian() {
        when(dataHarianRepository.save(any(DataHarian.class))).thenAnswer(invocation -> {
            var dataHarian = invocation.getArgument(0, DataHarian.class);
            dataHarian.setId(1L);
            return dataHarian;
        });

        DataHarian result = dataHarianService.create(userId, createRequest);
        verify(dataHarianRepository, atLeastOnce()).save(any(DataHarian.class));
        Assertions.assertEquals(dataHarian, result);
    }

    @Test
    void whenCreateDataHarianWithExistingDateShouldThrowException() {
        // Mocking the dataHarianRepository.existsByUserIdAndDate() method to return true
        when(dataHarianRepository.existsByUserIdAndDate(anyInt(), any(Date.class))).thenReturn(true);
        // Assert that the RuntimeException is thrown with the expected message
        Assertions.assertThrows(DataHarianWithSameDateAlreadyExistException.class, () -> {
            dataHarianService.create(userId, createRequest);
        });

        // Verify that the dataHarianRepository.existsByUserIdAndDate() method is called with the expected arguments
        verify(dataHarianRepository, atLeastOnce()).existsByUserIdAndDate(userId, setTimeToMidnight(new Date()));
    }

    @Test
    void whenUpdateTargetKaloriShouldReturnUpdatedDataHarian() {
        // Mock the dataHarianRepository.findDataHarianByIdAndUserId() method to return the existingDataHarian
        when(dataHarianRepository.findDataHarianByIdAndUserId(eq(dataHarian.getId()), eq(userId)))
                .thenReturn(Optional.of(dataHarian));

        // Invoke the updateTargetKalori() method
        DataHarian updatedDataHarian = dataHarianService.updateTargetKalori(userId, dataHarian.getId(), updateRequest);

        // Verify that the dataHarianRepository.save() method is called with the expected argument
        verify(dataHarianRepository, atLeastOnce()).save(eq(newDataHarian));

        // Assert that the updatedDataHarian has the updated target kalori
        Assertions.assertEquals(newDataHarian, updatedDataHarian);
    }


    @Test
    void whenUpdateTargetKaloriWithNonExistingDataHarianShouldThrowException() {
        // Mock the dataHarianRepository.findDataHarianByIdAndUserId() method to return an empty Optional
        when(dataHarianRepository.findDataHarianByIdAndUserId(eq(dataHarian.getId()), eq(userId)))
                .thenReturn(Optional.empty());

        // Assert that a DataHarianDoesNotExistException is thrown with the expected message
        Assertions.assertThrows(DataHarianDoesNotExistException.class, () -> {
            dataHarianService.updateTargetKalori(userId, dataHarian.getId(), updateRequest);
        });

        // Verify that the dataHarianRepository.findDataHarianByIdAndUserId() method is called with the expected arguments
        verify(dataHarianRepository, atLeastOnce()).findDataHarianByIdAndUserId(eq(dataHarian.getId()), eq(userId));
    }

    @Test
    void whenUpdateTambahMakananShouldReturnUpdatedDataHarian() {
        // Mock the dataHarianRepository.findDataHarianByIdAndUserId() method to return the existing dataHarian
        when(dataHarianRepository.findDataHarianByIdAndUserId(eq(dataHarian.getId()), eq(userId)))
                .thenReturn(Optional.of(dataHarian));

        // Invoke the updateTambahMakanan() method
        DataHarian updatedDataHarian = dataHarianService.updateTambahMakanan(userId, dataHarian.getId(), createDataHarianDetailsRequest);

        // Verify that the dataHarianRepository.findDataHarianByIdAndUserId() method is called with the expected arguments
        verify(dataHarianRepository, atLeastOnce()).findDataHarianByIdAndUserId(eq(dataHarian.getId()), eq(userId));

        // Verify that the dataHarianDetailsService.create() method is called with the expected arguments
        verify(dataHarianDetailsService, atLeastOnce()).create(eq(dataHarian), eq(userId), eq(createDataHarianDetailsRequest));

        // Assert that the updatedDataHarian is the same as the original dataHarian
        Assertions.assertEquals(dataHarian, updatedDataHarian);
    }

    @Test
    void whenUpdateTambahMakananWithNonExistingDataHarianShouldThrowException() {
        // Mock the dataHarianRepository.findDataHarianByIdAndUserId() method to return an empty Optional
        when(dataHarianRepository.findDataHarianByIdAndUserId(eq(dataHarian.getId()), eq(userId)))
                .thenReturn(Optional.empty());

        // Assert that a DataHarianDoesNotExistException is thrown with the expected message
        Assertions.assertThrows(DataHarianDoesNotExistException.class, () -> {
            dataHarianService.updateTambahMakanan(userId, dataHarian.getId(), createDataHarianDetailsRequest);
        });

        // Verify that the dataHarianRepository.findDataHarianByIdAndUserId() method is called with the expected arguments
        verify(dataHarianRepository, atLeastOnce()).findDataHarianByIdAndUserId(eq(dataHarian.getId()), eq(userId));
    }

    @Test
    void whenUpdateUbahMakananShouldReturnUpdatedDataHarian() {
        // Mock the dataHarianRepository.findDataHarianByIdAndUserId() method to return the existing dataHarian
        when(dataHarianRepository.findDataHarianByIdAndUserId(eq(dataHarian.getId()), eq(userId)))
                .thenReturn(Optional.of(dataHarian));

        // Invoke the updateUbahMakanan() method
        DataHarian updatedDataHarian = dataHarianService.updateUbahMakanan(userId, dataHarian.getId(), dataHarianDetails.getId(), updateDataHarianDetailsRequest);

        // Verify that the dataHarianRepository.findDataHarianByIdAndUserId() method is called with the expected arguments
        verify(dataHarianRepository, atLeastOnce()).findDataHarianByIdAndUserId(eq(dataHarian.getId()), eq(userId));

        // Verify that the dataHarianDetailsService.update() method is called with the expected arguments
        verify(dataHarianDetailsService, atLeastOnce()).update(eq(dataHarianDetails.getId()), eq(dataHarian), eq(userId), eq(updateDataHarianDetailsRequest));

        // Assert that the updatedDataHarian is the same as the original dataHarian
        Assertions.assertEquals(dataHarian, updatedDataHarian);
    }

    @Test
    void whenUpdateUbahMakananWithNonExistingDataHarianShouldThrowException() {
        // Mock the dataHarianRepository.findDataHarianByIdAndUserId() method to return an empty Optional
        when(dataHarianRepository.findDataHarianByIdAndUserId(eq(dataHarian.getId()), eq(userId)))
                .thenReturn(Optional.empty());

        // Assert that a DataHarianDoesNotExistException is thrown with the expected message
        Assertions.assertThrows(DataHarianDoesNotExistException.class, () -> {
            dataHarianService.updateUbahMakanan(userId, dataHarian.getId(), dataHarianDetails.getId(), updateDataHarianDetailsRequest);
        });

        // Verify that the dataHarianRepository.findDataHarianByIdAndUserId() method is called with the expected arguments
        verify(dataHarianRepository, atLeastOnce()).findDataHarianByIdAndUserId(eq(dataHarian.getId()), eq(userId));
    }

    @Test
    void whenDeleteDataHarianDetailShouldReturnDataHarian() {
        // Mock the dataHarianRepository.findDataHarianByIdAndUserId() method to return the existing dataHarian
        when(dataHarianRepository.findDataHarianByIdAndUserId(eq(dataHarian.getId()), eq(userId)))
                .thenReturn(Optional.of(dataHarian));

        // Invoke the deleteDataHarianDetail() method
        DataHarian result = dataHarianService.deleteDataHarianDetail(userId, dataHarian.getId(), dataHarianDetails.getId());

        // Verify that the dataHarianDetailsService.delete() method is called with the expected arguments
        verify(dataHarianDetailsService, atLeastOnce()).delete(eq(dataHarianDetails.getId()), eq(dataHarian), eq(userId));

        // Assert that the result is the same as the original dataHarian
        Assertions.assertEquals(dataHarian, result);
    }

    @Test
    void whenDeleteDataHarianDetailWithNonExistingDataHarianShouldThrowException() {
        // Mock the dataHarianRepository.findDataHarianByIdAndUserId() method to return an empty Optional
        when(dataHarianRepository.findDataHarianByIdAndUserId(eq(dataHarian.getId()), eq(userId)))
                .thenReturn(Optional.empty());

        // Assert that a DataHarianDoesNotExistException is thrown with the expected message
        assertThrows(DataHarianDoesNotExistException.class, () -> {
            dataHarianService.deleteDataHarianDetail(userId, dataHarian.getId(), dataHarianDetails.getId());
        });

        // Verify that the dataHarianRepository.findDataHarianByIdAndUserId() method is called with the expected arguments
        verify(dataHarianRepository, atLeastOnce()).findDataHarianByIdAndUserId(eq(dataHarian.getId()), eq(userId));
    }

}