package com.example.dataharianuser.service.mapper;

import com.example.dataharianuser.model.DataHarian;
import com.example.dataharianuser.model.DataHarianDetails;
import com.example.dataharianuser.model.dto.data_harian.DataHarianDetailsResponse;
import com.example.dataharianuser.model.dto.data_harian.DataHarianResponse;
import com.example.dataharianuser.model.dto.makanan.MakananDetailsDto;
import com.example.dataharianuser.model.dto.makanan.NutrisiDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataHarianResponseMapperTest {
    @InjectMocks
    private DataHarianResponseMapper dataHarianResponseMapper;
    @Mock
    private DataHarianDetailsResponseMapper dataHarianDetailsResponseMapper;

    private DataHarianResponse dataHarianResponse;
    private DataHarian dataHarian;

    private List<DataHarianDetails> dataHarianDetailsList;

    private List<DataHarianDetailsResponse> dataHarianDetailsResponseList;

    private DataHarianDetails dataHarianDetails1;
    private DataHarianDetailsResponse dataHarianDetailsResponse1;

    private MakananDetailsDto makananDetailsDto;
    private NutrisiDto nutrisiDto;

    private String token;
    private Date date;

    @BeforeEach
    void setUp() {
        token = "token";
        date = new Date();

        dataHarianDetails1 = DataHarianDetails.builder()
                .dataHarian(dataHarian)
                .makananId(1L)
                .jumlahTakaran(100.0)
                .build();

        makananDetailsDto = MakananDetailsDto.builder()
                .namaMakanan("Nasi Goreng")
                .gula(10.0)
                .kalori(100.0)
                .karbohidrat(10.0)
                .kolesterol(10.0)
                .lemak(10.0)
                .protein(10.0)
                .sodium(10.0)
                .takaran(100.0)
                .build();

        nutrisiDto = NutrisiDto.builder()
                .karbohidrat(10.0)
                .kalori(100.0)
                .gula(10.0)
                .lemak(10.0)
                .kolesterol(10.0)
                .sodium(10.0)
                .protein(10.0)
                .build();

        dataHarianDetailsResponse1 = DataHarianDetailsResponse.builder()
                .makanan(makananDetailsDto)
                .jumlahTakaran(200.0)
                .build();

        dataHarianDetailsList = List.of(dataHarianDetails1);
        dataHarianDetailsResponseList = List.of(dataHarianDetailsResponse1);

        dataHarian = DataHarian.builder()
                .userId(1)
                .date(date)
                .targetKalori(2000.0)
                .dataHarianDetailsList(dataHarianDetailsList)
                .build();

        dataHarianResponse = DataHarianResponse.builder()
                .date(date)
                .targetKalori(2000.0)
                .totalKaloriKonsumsi(dataHarianDetailsResponse1.getMakanan().getKalori())
                .nutrisiTotal(nutrisiDto)
                .dataHarianDetailsDataList(dataHarianDetailsResponseList)
                .build();


    }

    @Test
    void whenMapFromDataHarianButListIsEmptyShouldReturnDataHarianResponse() {
        // Mock the behavior of dataHarianDetailsResponseMapper.mapToDataHarianDetailsResponse
        when(dataHarianDetailsResponseMapper.mapToDataHarianDetailsResponse(dataHarianDetails1, token))
                .thenReturn(dataHarianDetailsResponse1);

        // Call the method to be tested
        DataHarianResponse result = dataHarianResponseMapper.mapToDataHarianResponse(dataHarian, dataHarianDetailsList, token);

        // Verify the result using assertions
        Assertions.assertEquals(dataHarianResponse.getDate(), result.getDate());
        Assertions.assertEquals(dataHarianResponse.getTargetKalori(), result.getTargetKalori());
        Assertions.assertEquals(dataHarianResponse.getTotalKaloriKonsumsi(), result.getTotalKaloriKonsumsi());
        Assertions.assertEquals(dataHarianResponse.getNutrisiTotal(), result.getNutrisiTotal());
        Assertions.assertEquals(dataHarianResponse.getDataHarianDetailsDataList(), result.getDataHarianDetailsDataList());

        // Verify that the mock method was called with the expected arguments
        verify(dataHarianDetailsResponseMapper, atLeastOnce()).mapToDataHarianDetailsResponse(dataHarianDetails1, token);
    }
}
