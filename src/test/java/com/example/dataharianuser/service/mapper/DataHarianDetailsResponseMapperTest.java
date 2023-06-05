package com.example.dataharianuser.service.mapper;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.dataharianuser.model.dto.data_harian.DataHarianDetailsResponse;
import com.example.dataharianuser.model.dto.makanan.MakananDetailsDto;
import com.example.dataharianuser.model.DataHarianDetails;
import com.example.dataharianuser.service.makanan.MakananDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataHarianDetailsResponseMapperTest {

    @InjectMocks
    private DataHarianDetailsResponseMapper dataHarianDetailsResponseMapper;

    @Mock
    private MakananDetailsService makananDetailsService;

    private DataHarianDetails dataHarianDetails;
    private MakananDetailsDto makananDetailsDto;

    private String bearerToken;

    @BeforeEach
    void setUp() {
        dataHarianDetails = DataHarianDetails.builder()
                .id(1L)
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

        bearerToken = "token";
    }

    @Test
    void mapToDataHarianDetailsResponse_ShouldMapDataHarianDetailsToDataHarianDetailsResponse() {
        when(makananDetailsService.getMakananDetails(dataHarianDetails.getMakananId(), bearerToken))
                .thenReturn(makananDetailsDto);

        // Act
        DataHarianDetailsResponse result = dataHarianDetailsResponseMapper.mapToDataHarianDetailsResponse(dataHarianDetails, bearerToken);

        // Assert
        assertEquals(dataHarianDetails.getId(), result.getId());
        assertEquals(makananDetailsDto, result.getMakanan());
        assertEquals(dataHarianDetails.getJumlahTakaran(), result.getJumlahTakaran());

        verify(makananDetailsService, atLeastOnce()).getMakananDetails(dataHarianDetails.getMakananId(), bearerToken);
    }
}
