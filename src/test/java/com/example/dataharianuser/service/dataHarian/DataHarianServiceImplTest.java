package com.example.dataharianuser.service.dataHarian;

import com.example.dataharianuser.model.dto.dataHarian.DataHarianDetailsRequest;
import com.example.dataharianuser.model.dto.dataHarian.DataHarianDetailsResponse;
import com.example.dataharianuser.model.dto.dataHarian.DataHarianRequest;
import com.example.dataharianuser.exception.DataHarianDoesNotExistException;
import com.example.dataharianuser.exception.DataHarianWithSameDateAlreadyExistException;
import com.example.dataharianuser.model.DataHarian;
import com.example.dataharianuser.model.DataHarianDetails;
import com.example.dataharianuser.model.dto.dataHarian.DataHarianResponse;
import com.example.dataharianuser.repository.DataHarianDetailsRepository;
import com.example.dataharianuser.repository.DataHarianRepository;
import com.example.dataharianuser.service.mapper.DataHarianResponseMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static com.example.dataharianuser.service.dataHarian.DataHarianServiceImpl.setTimeToMidnight;
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

    @Mock
    private DataHarianDetailsRepository dataHarianDetailsRepository;

    @Mock
    private DataHarianResponseMapper dataHarianResponseMapper;

    Integer userId;
    Long makananId;

    DataHarian dataHarian;
    DataHarian dataHarian2;
    DataHarian newDataHarian;

    DataHarianResponse dataHarianResponse;
    DataHarianResponse dataHarianResponse2;
    DataHarianResponse newDataHarianResponse;

    DataHarianDetails dataHarianDetails;

    DataHarianRequest createRequest;
    DataHarianRequest createRequest2;
    DataHarianRequest updateRequest;

    DataHarianDetailsRequest createDataHarianDetailsRequest;
    DataHarianDetailsRequest updateDataHarianDetailsRequest;

    List<DataHarian> dataHarianList;

    String bearerToken;
    Date date;

    @BeforeEach
    void setUp() {
        userId = 1;
        makananId = 1L;
        bearerToken = "Bearer token";
        date = new Date();
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
                .date(setTimeToMidnight(date))
                .dataHarianDetailsList(new ArrayList<>())
                .userId(userId)
                .build();

        dataHarian2 = DataHarian.builder()
                .id(2L)
                .targetKalori(1800.0)
                .date(setTimeToMidnight(new Date()))
                .dataHarianDetailsList(new ArrayList<>())
                .userId(userId)
                .build();

        newDataHarian = DataHarian.builder()
                .id(1L)
                .targetKalori(2500.0)
                .date(setTimeToMidnight(date))
                .dataHarianDetailsList(new ArrayList<>())
                .userId(userId)
                .build();

        dataHarianResponse = DataHarianResponse.builder()
                .id(1L)
                .targetKalori(1600.0)
                .date(setTimeToMidnight(date))
                .dataHarianDetailsDataList(new ArrayList<>())
                .build();

        dataHarianResponse2 = DataHarianResponse.builder()
                .id(2L)
                .targetKalori(1800.0)
                .date(setTimeToMidnight(date))
                .dataHarianDetailsDataList(new ArrayList<>())
                .build();

        newDataHarianResponse = DataHarianResponse.builder()
                .id(2L)
                .targetKalori(2500.0)
                .date(setTimeToMidnight(date))
                .dataHarianDetailsDataList(new ArrayList<>())
                .build();

        dataHarianDetails = DataHarianDetails.builder()
                .dataHarian(dataHarian)
                .makananId(makananId)
                .jumlahTakaran(150.0)
                .id(1L)
                .build();

        dataHarianList = new ArrayList<>();
        dataHarianList.add(dataHarian);
        dataHarianList.add(dataHarian2);
    }

    @Test
    void whenFindAllDataHarianByUserIdShouldReturnListOfDataHarian() {

        List<DataHarianDetails> details1 = new ArrayList<>();
        // Populate details1 with test data
        List<DataHarianDetails> details2 = new ArrayList<>();
        // Populate details2 with test data

        // Mock repository calls
        when(dataHarianRepository.findAllByUserId(userId)).thenReturn(dataHarianList);
        when(dataHarianDetailsRepository.findAllByDataHarianId(1L)).thenReturn(details1);
        when(dataHarianDetailsRepository.findAllByDataHarianId(2L)).thenReturn(details2);
        when(dataHarianResponseMapper.mapToDataHarianResponse(dataHarian, details1, bearerToken)).thenReturn(dataHarianResponse);
        when(dataHarianResponseMapper.mapToDataHarianResponse(dataHarian2, details2, bearerToken)).thenReturn(dataHarianResponse2);

        // Call the method under test
        List<DataHarianResponse> result = dataHarianService.findAllByUserId(userId, bearerToken);

        // Verify the interactions and assertions
        verify(dataHarianRepository).findAllByUserId(userId);
        verify(dataHarianDetailsRepository).findAllByDataHarianId(1L);
        verify(dataHarianDetailsRepository).findAllByDataHarianId(2L);
        verify(dataHarianResponseMapper).mapToDataHarianResponse(dataHarian, details1, bearerToken);
        verify(dataHarianResponseMapper).mapToDataHarianResponse(dataHarian2, details2, bearerToken);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(dataHarianResponse, result.get(0));
        Assertions.assertEquals(dataHarianResponse2, result.get(1));
    }

    @Test
    void whenFindDataHarianByDateAndUserIdShouldReturnCorrectDataHarianResponse() {
        when(dataHarianRepository.findAllByUserId(userId)).thenReturn(List.of(dataHarian));
        when(dataHarianResponseMapper.mapToDataHarianResponse(eq(dataHarian), anyList(), eq(bearerToken)))
                .thenReturn(dataHarianResponse);

        // Act
        DataHarianResponse result = dataHarianService.findDataHarianByDateAndUserId(date, userId, bearerToken);

        verify(dataHarianRepository, atLeastOnce()).findAllByUserId(userId);
        verify(dataHarianResponseMapper, atLeastOnce()).mapToDataHarianResponse(eq(dataHarian), anyList(), eq(bearerToken));
        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(dataHarianResponse, result);
        // Add more assertions as per your requirements
    }

    @Test
    void whenFindDataHarianByDateAndUserIdShouldThrownDataHarianDoesNotExistException() {
        when(dataHarianRepository.findAllByUserId(userId)).thenReturn(Collections.emptyList());

        // Act and Assert
        Assertions.assertThrows(DataHarianDoesNotExistException.class, () -> {
            dataHarianService.findDataHarianByDateAndUserId(new Date(), userId, bearerToken);
        });

        // Verify that the dataHarianRepository.findAllByUserId() method is called once with the expected arguments
        verify(dataHarianRepository,atLeastOnce()).findAllByUserId(userId);
    }

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

    @Test
    void whenGetDataHarianDetailsShouldReturnDataHarianDetails() {
        when(dataHarianRepository.findDataHarianByIdAndUserId(1L, userId)).thenReturn(Optional.of(dataHarian));
        when(dataHarianDetailsService.read(1L, dataHarian, userId, bearerToken))
                .thenReturn(new DataHarianDetailsResponse());

        // Act
        DataHarianDetailsResponse result = dataHarianService.getDataHarianDetails(userId, 1L, 1L, bearerToken);

        // Assert
        Assertions.assertNotNull(result);

        // Verify that the dataHarianRepository.findDataHarianByIdAndUserId() method is called once with the expected arguments
        verify(dataHarianRepository, times(1)).findDataHarianByIdAndUserId(1L, userId);

        // Verify that the dataHarianDetailsService.read() method is called once with the expected arguments
        verify(dataHarianDetailsService, times(1)).read(1L, dataHarian, userId, bearerToken);
    }

    @Test
    void whenGetDataHarianDetailsShouldThrowException() {
        when(dataHarianRepository.findDataHarianByIdAndUserId(1L, userId)).thenReturn(Optional.empty());

        // Assert and Verify
        Assertions.assertThrows(DataHarianDoesNotExistException.class, () -> {
            dataHarianService.getDataHarianDetails(userId, 1L, 1L, bearerToken);
        });

        // Verify that the dataHarianRepository.findDataHarianByIdAndUserId() method is called once with the expected arguments
        verify(dataHarianRepository,atLeastOnce()).findDataHarianByIdAndUserId(1L, userId);

        // Verify that the dataHarianDetailsService.read() method is not called
        verifyNoInteractions(dataHarianDetailsService);
    }

}