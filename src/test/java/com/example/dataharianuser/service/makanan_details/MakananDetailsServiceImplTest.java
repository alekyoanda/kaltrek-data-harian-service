package com.example.dataharianuser.service.makanan_details;

import com.example.dataharianuser.exception.BahanOrResepMakananDoesNotExistException;
import com.example.dataharianuser.exception.MakananDoesNotExistException;
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
import org.springframework.http.ResponseEntity;

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
    private TypeMakananResponse typeMakananResponse2;

    private MakananDetailsDto makananDetailsDto1;
    private MakananDetailsDto makananDetailsDto2;

    private ResponseEntity<MakananDetailsDto> responseEntityMakananDetails1;
    private ResponseEntity<TypeMakananResponse> responseEntityTypeMakanan1;
    private ResponseEntity<MakananDetailsDto> responseEntityMakananDetails2;
    private ResponseEntity<TypeMakananResponse> responseEntityTypeMakanan2;


    private Long idMakanan;
    private Long idBahanOrResepMakanan;
    private String bearerToken;

    private String expectedUrlGetMakananDetails1;
    private String expectedUrlGetMakananDetails2;
    private String expectedUrlGetTypeMakanan;

    @BeforeEach
    void setUp() {
        idMakanan = 1L;
        idBahanOrResepMakanan = 1L;
        bearerToken = "testToken";

        expectedUrlGetMakananDetails1 = "http://localhost:8081/api/v1/bahanmakanan/id/" + idBahanOrResepMakanan;
        expectedUrlGetMakananDetails2 = "http://localhost:8081/api/v1/resep/get-nutrisi-resep/" + idBahanOrResepMakanan;
        expectedUrlGetTypeMakanan = "http://localhost:8081/api/v1/makanan/get-tipe-makanan/" + idMakanan;

        typeMakananResponse1 = TypeMakananResponse.builder()
                .namaMakanan("Nasi Goreng")
                .isResepMakanan(false)
                .idBahanOrResepMakanan(idBahanOrResepMakanan)
                .build();

        typeMakananResponse2 = TypeMakananResponse.builder()
                .namaMakanan("Cumi Lada Hitam")
                .isResepMakanan(true)
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

        makananDetailsDto2 = MakananDetailsDto.builder()
                .namaMakanan("Cumi Lada Hitam")
                .gula(20.0)
                .kalori(20.0)
                .karbohidrat(30.0)
                .kolesterol(40.0)
                .lemak(12.0)
                .protein(32.0)
                .sodium(122.0)
                .takaran(200.0)
                .build();

        responseEntityTypeMakanan1 = ResponseEntity.ok(typeMakananResponse1);
        responseEntityMakananDetails1 = ResponseEntity.ok(makananDetailsDto1);

        responseEntityTypeMakanan2 = ResponseEntity.ok(typeMakananResponse2);
        responseEntityMakananDetails2 = ResponseEntity.ok(makananDetailsDto2);
    }

    @Test
    void whenGetMakananDetailsShouldReturnMakananDetails1() {
        when(urlManager.getBaseUrlMakanan()).thenReturn("http://localhost:8081");

        when(restTemplateProxy.get(expectedUrlGetMakananDetails1, bearerToken, MakananDetailsDto.class))
                .thenReturn(responseEntityMakananDetails1);
        when(restTemplateProxy.get(expectedUrlGetTypeMakanan, bearerToken, TypeMakananResponse.class))
                .thenReturn(responseEntityTypeMakanan1);

        MakananDetailsDto result = makananDetailsService.getMakananDetails(idMakanan, bearerToken);

        verify(restTemplateProxy, atLeastOnce()).get(expectedUrlGetMakananDetails1, bearerToken, MakananDetailsDto.class);
        verify(restTemplateProxy, atLeastOnce()).get(expectedUrlGetTypeMakanan, bearerToken, TypeMakananResponse.class);

        Assertions.assertEquals(makananDetailsDto1, result);
    }

    @Test
    void whenGetMakananDetailsShouldReturnMakananDetails2() {
        when(urlManager.getBaseUrlMakanan()).thenReturn("http://localhost:8081");

        when(restTemplateProxy.get(expectedUrlGetMakananDetails2, bearerToken, MakananDetailsDto.class))
                .thenReturn(responseEntityMakananDetails2);
        when(restTemplateProxy.get(expectedUrlGetTypeMakanan, bearerToken, TypeMakananResponse.class))
                .thenReturn(responseEntityTypeMakanan2);

        MakananDetailsDto result = makananDetailsService.getMakananDetails(idMakanan, bearerToken);

        verify(restTemplateProxy, atLeastOnce()).get(expectedUrlGetMakananDetails2, bearerToken, MakananDetailsDto.class);
        verify(restTemplateProxy, atLeastOnce()).get(expectedUrlGetTypeMakanan, bearerToken, TypeMakananResponse.class);

        Assertions.assertEquals(makananDetailsDto2, result);
    }


    @Test
    void whenGetMakananDetailsShouldThrowException() {
        when(urlManager.getBaseUrlMakanan()).thenReturn("http://localhost:8081");

        when(restTemplateProxy.get(expectedUrlGetTypeMakanan, bearerToken, TypeMakananResponse.class))
                .thenReturn(responseEntityTypeMakanan1);

        // Mocking restTemplateProxy.get to throw an exception
        when(restTemplateProxy.get(anyString(), anyString(), eq(MakananDetailsDto.class)))
                .thenThrow(new BahanOrResepMakananDoesNotExistException(idBahanOrResepMakanan));

        // Verify that BahanOrResepMakananDoesNotExistException is thrown
        Assertions.assertThrows(BahanOrResepMakananDoesNotExistException.class, () ->
                makananDetailsService.getMakananDetails(idMakanan, bearerToken));

        // Verify that restTemplateProxy.get is called once
        verify(restTemplateProxy, atLeastOnce())
                .get(anyString(), anyString(), eq(MakananDetailsDto.class));
    }

    @Test
    void whenGetTypeMakananShouldReturnTypeMakanan() {
        when(urlManager.getBaseUrlMakanan()).thenReturn("http://localhost:8081");

        when(restTemplateProxy.get(expectedUrlGetTypeMakanan, bearerToken, TypeMakananResponse.class))
                .thenReturn(responseEntityTypeMakanan1);

        TypeMakananResponse result = makananDetailsService.getTypeMakanan(idMakanan, bearerToken);

        verify(restTemplateProxy, atLeastOnce()).get(expectedUrlGetTypeMakanan, bearerToken, TypeMakananResponse.class);

        Assertions.assertEquals(typeMakananResponse1, result);
    }

    @Test
    void whenGetTypeMakananShouldThrowException() {
        when(urlManager.getBaseUrlMakanan()).thenReturn("http://localhost:8081");

        when(restTemplateProxy.get(anyString(), anyString(), eq(MakananDetailsDto.class)))
                .thenThrow(new MakananDoesNotExistException(idMakanan));

        // Verify that BahanOrResepMakananDoesNotExistException is thrown
        Assertions.assertThrows(MakananDoesNotExistException.class, () ->
                makananDetailsService.getTypeMakanan(idMakanan, bearerToken));

        // Verify that restTemplateProxy.get is called once
        verify(restTemplateProxy, atLeastOnce())
                .get(anyString(), anyString(), eq(TypeMakananResponse.class));
    }

}
