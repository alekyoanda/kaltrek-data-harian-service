package com.example.dataharianuser.service.makananDetails;

import com.example.dataharianuser.model.dto.makanan.MakananDetailsDto;
import com.example.dataharianuser.model.dto.makanan.TypeMakananResponse;
import com.example.dataharianuser.service.makanan.MakananDetailsServiceImpl;
import com.example.dataharianuser.service.utils.RestTemplateProxy;
import com.example.dataharianuser.service.utils.URLManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Type;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MakananDetailsServiceImplTest {

    @InjectMocks
    private MakananDetailsServiceImpl makananDetailsService;

    @Mock
    private RestTemplateProxy restTemplateProxy;

    @Mock
    private URLManager urlManager;

    private TypeMakananResponse typeMakananResponse1;
    private MakananDetailsDto makananDetailsDto1;

    private ResponseEntity<MakananDetailsDto> responseEntityMakananDetails;
    private ResponseEntity<TypeMakananResponse> responseEntityTypeMakanan;

    private Long idMakanan;
    private Long idBahanOrResepMakanan;
    private String bearerToken;

    private String expectedUrlGetMakananDetails;
    private String expectedUrlGetTypeMakanan;

    @BeforeEach
    void setUp() {
        idMakanan = 1L;
        idBahanOrResepMakanan = 1L;
        bearerToken = "testToken";

        expectedUrlGetMakananDetails = "http://localhost:8081/api/v1/bahanmakanan/id/" + idBahanOrResepMakanan;
        expectedUrlGetTypeMakanan = "http://localhost:8081/api/v1/makanan/get-tipe-makanan/" + idMakanan;

        typeMakananResponse1 = TypeMakananResponse.builder()
                .namaMakanan("Nasi Goreng")
                .isResepMakanan(false)
                .idBahanOrResepMakanan(idBahanOrResepMakanan)
                .build();

        makananDetailsDto1 = MakananDetailsDto.builder()
                .namaMakanan("Nasi Goreng")
                .gula(4.0)
                .kalori(100.0)
                .karbohidrat(20.0)
                .kolesterol(10.0)
                .lemak(5.0)
                .protein(10.0)
                .sodium(10.0)
                .takaran(100.0)
                .build();

        responseEntityTypeMakanan = ResponseEntity.ok(typeMakananResponse1);
        responseEntityMakananDetails = ResponseEntity.ok(makananDetailsDto1);
    }

    @Test
    void whenGetMakananDetailsShouldReturnMakananDetails() {
        when(urlManager.getBaseUrlMakanan()).thenReturn("http://localhost:8081");

        when(restTemplateProxy.get(expectedUrlGetMakananDetails, bearerToken, MakananDetailsDto.class))
                .thenReturn(responseEntityMakananDetails);
        when(restTemplateProxy.get(expectedUrlGetTypeMakanan, bearerToken, TypeMakananResponse.class))
                .thenReturn(responseEntityTypeMakanan);

        MakananDetailsDto result = makananDetailsService.getMakananDetails(idMakanan, bearerToken);

        verify(restTemplateProxy, atLeastOnce()).get(expectedUrlGetMakananDetails, bearerToken, MakananDetailsDto.class);
        verify(restTemplateProxy, atLeastOnce()).get(expectedUrlGetTypeMakanan, bearerToken, TypeMakananResponse.class);

        Assertions.assertEquals(makananDetailsDto1, result);
    }

    @Test
    void whenGetTypeMakananShouldReturnTypeMakanan() {
        when(urlManager.getBaseUrlMakanan()).thenReturn("http://localhost:8081");

        when(restTemplateProxy.get(expectedUrlGetTypeMakanan, bearerToken, TypeMakananResponse.class))
                .thenReturn(responseEntityTypeMakanan);

        TypeMakananResponse result = makananDetailsService.getTypeMakanan(idMakanan, bearerToken);

        verify(restTemplateProxy, atLeastOnce()).get(expectedUrlGetTypeMakanan, bearerToken, TypeMakananResponse.class);

        Assertions.assertEquals(typeMakananResponse1, result);
    }

}
