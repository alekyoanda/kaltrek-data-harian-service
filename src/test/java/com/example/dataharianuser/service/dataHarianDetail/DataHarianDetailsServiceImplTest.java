package com.example.dataharianuser.service.dataHarianDetail;

import com.example.dataharianuser.model.dto.dataHarian.DataHarianDetailsRequest;
import com.example.dataharianuser.model.dto.dataHarian.DataHarianDetailsResponse;
import com.example.dataharianuser.model.dto.dataHarian.DataHarianRequest;
import com.example.dataharianuser.exception.DataHarianDetailsDoesNotExistException;
import com.example.dataharianuser.model.DataHarian;
import com.example.dataharianuser.model.DataHarianDetails;
import com.example.dataharianuser.model.dto.makanan.MakananDetailsDto;
import com.example.dataharianuser.repository.DataHarianDetailsRepository;
import com.example.dataharianuser.repository.DataHarianRepository;
import com.example.dataharianuser.service.dataHarian.DataHarianDetailsServiceImpl;
import com.example.dataharianuser.service.mapper.DataHarianDetailsResponseMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static com.example.dataharianuser.service.dataHarian.DataHarianServiceImpl.setTimeToMidnight;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataHarianDetailsServiceImplTest {
    @InjectMocks
    private DataHarianDetailsServiceImpl dataHarianDetailsService;

    @Mock
    private DataHarianRepository dataHarianRepository;

    @Mock
    private DataHarianDetailsRepository dataHarianDetailsRepository;
    @Mock
    private DataHarianDetailsResponseMapper dataHarianDetailsResponseMapper;

    Integer userId;
    Long makananId;

    DataHarian dataHarian;
    DataHarianRequest createRequest;

    DataHarianDetails dataHarianDetails;
    DataHarianDetails newDataHarianDetails;

    DataHarianDetailsResponse dataHarianDetailsResponse;

    DataHarianDetailsRequest createDataHarianDetailsRequest;
    DataHarianDetailsRequest updateDataHarianDetailsRequest;

    MakananDetailsDto makananDetailsDto;
    String bearerToken;

    @BeforeEach
    void setUp() {
        userId = 1;
        makananId = 1L;
        bearerToken = "Bearer token";

        makananDetailsDto = MakananDetailsDto.builder()
                .namaMakanan("Nasi Goreng")
                .kalori(200.0)
                .build();
        // Set up mock objects or test data if needed
        createRequest = DataHarianRequest.builder()
                .targetKalori(1600.0)
                .build();

        createDataHarianDetailsRequest = DataHarianDetailsRequest.builder()
                .makananId(makananId)
                .jumlahTakaran(150.0)
                .build();

        updateDataHarianDetailsRequest = DataHarianDetailsRequest.builder()
                .makananId(makananId)
                .jumlahTakaran(250.0)
                .build();

        dataHarian = DataHarian.builder()
                .id(1L)
                .targetKalori(1600.0)
                .date(setTimeToMidnight(new Date()))
                .dataHarianDetailsList(new ArrayList<>())
                .userId(userId)
                .build();

        dataHarianDetails = DataHarianDetails.builder()
                .id(1L)
                .makananId(makananId)
                .dataHarian(dataHarian)
                .jumlahTakaran(150.0)
                .build();

        dataHarianDetailsResponse = DataHarianDetailsResponse.builder()
                .id(1L)
                .jumlahTakaran(150.0)
                .makanan(makananDetailsDto)
                .build();

        newDataHarianDetails = DataHarianDetails.builder()
                .id(1L)
                .makananId(makananId)
                .dataHarian(dataHarian)
                .jumlahTakaran(250.0)
                .build();
    }

    @Test
    void whenReadExistingDataHarianDetailsShouldReturnDataHarianDetailsResponse() {
        // Arrange
        when(dataHarianDetailsRepository.findDataHarianDetailsByIdAndDataHarianId(1L, dataHarian.getId()))
                .thenReturn(Optional.of(dataHarianDetails));

        when(dataHarianDetailsResponseMapper.mapToDataHarianDetailsResponse(dataHarianDetails, bearerToken))
                .thenReturn(dataHarianDetailsResponse);

        DataHarianDetailsResponse actualResponse = dataHarianDetailsService.read(1L, dataHarian, userId, bearerToken);

        // Assert
        Assertions.assertEquals(dataHarianDetailsResponse, actualResponse);
        verify(dataHarianDetailsRepository, atLeastOnce()).findDataHarianDetailsByIdAndDataHarianId(1L, dataHarian.getId());
        verify(dataHarianDetailsResponseMapper, atLeastOnce())
                .mapToDataHarianDetailsResponse(dataHarianDetails, bearerToken);
    }

    @Test
    void whenReadNonExistingDataHarianDetailsShouldThrowException() {
        // Arrange
        when(dataHarianDetailsRepository.findDataHarianDetailsByIdAndDataHarianId(1L, dataHarian.getId()))
                .thenReturn(Optional.empty());

        // Act and Assert
        Assertions.assertThrows(DataHarianDetailsDoesNotExistException.class,
                () -> dataHarianDetailsService.read(1L, dataHarian, userId, bearerToken));

        verify(dataHarianDetailsRepository, times(1)).findDataHarianDetailsByIdAndDataHarianId(1L, dataHarian.getId());
        verifyNoInteractions(dataHarianDetailsResponseMapper);
    }

    @Test
    void whenCreateDataHarianDetailsShouldReturnTheCreatedDataHarianDetails() {
        when(dataHarianDetailsRepository.save(any(DataHarianDetails.class))).thenAnswer(invocation -> {
            var dataHarianDetails = invocation.getArgument(0, DataHarianDetails.class);
            dataHarianDetails.setId(1L);
            return dataHarianDetails;
        });

        DataHarianDetails result = dataHarianDetailsService.create(dataHarian, userId, createDataHarianDetailsRequest);
        verify(dataHarianDetailsRepository, atLeastOnce()).save(any(DataHarianDetails.class));
        Assertions.assertEquals(dataHarianDetails, result);
    }

    @Test
    void whenUpdateDataHarianDetailsShouldReturnTheUpdatedDataHarianDetails() {
        when(dataHarianDetailsRepository.findDataHarianDetailsByIdAndDataHarianId(anyLong(), anyLong()))
                .thenReturn(Optional.of(dataHarianDetails));
        when(dataHarianDetailsRepository.save(any(DataHarianDetails.class)))
                .thenReturn(dataHarianDetails);

        // Perform the update
        DataHarianDetails updatedData = dataHarianDetailsService.update(
                dataHarianDetails.getId(), dataHarian, userId, updateDataHarianDetailsRequest);

        // Verify that the repository methods were called with the correct arguments
        verify(dataHarianDetailsRepository, atLeastOnce()).findDataHarianDetailsByIdAndDataHarianId(dataHarianDetails.getId(), dataHarian.getId());
        verify(dataHarianDetailsRepository, atLeastOnce()).save(any(DataHarianDetails.class));

        // Verify the result
        Assertions.assertEquals(newDataHarianDetails, updatedData);
    }

    @Test
    void whenUpdateWithNonExistingDataHarianDetailsShouldThrowException() {
        // Mock the dataHarianRepository.findDataHarianByIdAndUserId() method to return an empty Optional
        when(dataHarianDetailsRepository.findDataHarianDetailsByIdAndDataHarianId(
                eq(dataHarianDetails.getId()), eq(dataHarianDetails.getId())))
                .thenReturn(Optional.empty());

        // Assert that a DataHarianDoesNotExistException is thrown with the expected message
        Assertions.assertThrows(DataHarianDetailsDoesNotExistException.class, () -> {
            dataHarianDetailsService.update(dataHarianDetails.getId(), dataHarian, userId, updateDataHarianDetailsRequest);
        });

        // Verify that the dataHarianRepository.findDataHarianByIdAndUserId() method is called with the expected arguments
        verify(dataHarianDetailsRepository, atLeastOnce()).findDataHarianDetailsByIdAndDataHarianId(
                eq(dataHarianDetails.getId()), eq(dataHarian.getId())
        );
    }

    @Test
    void whenDeleteDataHarianDetailsShouldReturnDeletedDataHarianDetails() {
        // Mock the dataHarianDetailsRepository.findDataHarianDetailsByIdAndDataHarianId() method to return the existingDataHarianDetails
        when(dataHarianDetailsRepository.findDataHarianDetailsByIdAndDataHarianId(eq(dataHarianDetails.getId()), eq(dataHarian.getId())))
                .thenReturn(Optional.of(dataHarianDetails));

        // Invoke the delete method
        DataHarianDetails deletedDataHarianDetails = dataHarianDetailsService.delete(dataHarianDetails.getId(), dataHarian, userId);

        // Verify that the dataHarianRepository.save() method is called with the expected argument
        verify(dataHarianRepository, atLeastOnce()).save(eq(dataHarian));

        // Verify that the dataHarianDetailsRepository.delete() method is called with the expected argument
        verify(dataHarianDetailsRepository, atLeastOnce()).delete(eq(dataHarianDetails));

        // Assert that the deletedDataHarianDetails matches the original dataHarianDetails
        Assertions.assertEquals(dataHarianDetails, deletedDataHarianDetails);
    }

    @Test
    void whenDeleteWithNonExistingDataHarianDetailsShouldThrowException() {
        // Mock the dataHarianRepository.findDataHarianByIdAndUserId() method to return an empty Optional
        when(dataHarianDetailsRepository.findDataHarianDetailsByIdAndDataHarianId(
                eq(dataHarianDetails.getId()), eq(dataHarianDetails.getId())))
                .thenReturn(Optional.empty());

        // Assert that a DataHarianDoesNotExistException is thrown with the expected message
        Assertions.assertThrows(DataHarianDetailsDoesNotExistException.class, () -> {
            dataHarianDetailsService.delete(dataHarianDetails.getId(), dataHarian, userId);
        });

        // Verify that the dataHarianRepository.findDataHarianByIdAndUserId() method is called with the expected arguments
        verify(dataHarianDetailsRepository, atLeastOnce()).findDataHarianDetailsByIdAndDataHarianId(
                eq(dataHarianDetails.getId()), eq(dataHarian.getId())
        );
    }

}
