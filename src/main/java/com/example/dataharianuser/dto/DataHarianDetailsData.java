package com.example.dataharianuser.dto;

import com.example.dataharianuser.model.DataHarianDetails;
import com.example.dataharianuser.model.MakananCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataHarianDetailsData {
    private MakananDTO makanan;
    private Double jumlahTakaran;

    public static DataHarianDetailsData fromDataHarianDetails(DataHarianDetails dataHarianDetails, RestTemplate restTemplate) {
        MakananDTO makananDTO = requestGetMakanan(dataHarianDetails.getMakananId(), dataHarianDetails.getMakananCategory(), restTemplate);

        return DataHarianDetailsData.builder()
                .makanan(makananDTO)
                .jumlahTakaran(dataHarianDetails.getJumlahTakaran())
                .build();
    }

    public static MakananDTO requestGetMakanan(Long makananId, MakananCategory makananCategory, RestTemplate restTemplate){
        String url = "http://localhost:8080/makanan/" + makananId;

        MakananDTO makananDTO;
        if (makananCategory.equals(MakananCategory.BAHAN_MAKANAN)){
            makananDTO = restTemplate.getForObject(url, BahanMakananDto.class);
        }
        else{
            makananDTO = restTemplate.getForObject(url, ResepMakananDto.class);
        }

        return makananDTO;
    }
}
