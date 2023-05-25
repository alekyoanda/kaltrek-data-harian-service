package com.example.dataharianuser.dto;

import com.example.dataharianuser.model.DataHarian;
import com.example.dataharianuser.model.DataHarianDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataHarianResponse {
    private Long id;
    private Date date;
    private Double targetKalori;
    private Double totalKaloriKonsumsi;
    private NutrisiDto nutrisiTotal;
    private List<DataHarianDetailsResponse> dataHarianDetailsDataList;

    public static DataHarianResponse fromDataHarian(DataHarian dataHarian, List<DataHarianDetails> dataHarianDetailsList, RestTemplate restTemplate, String bearerToken, String baseUrl) {
        Double totalKaloriKonsumsi = 0.0;
        NutrisiDto nutrisiTotal = NutrisiDto.builder()
                .karbohidrat(0.0)
                .lemak(0.0)
                .sodium(0.0)
                .kolesterol(0.0)
                .protein(0.0)
                .kalori(0.0)
                .gula(0.0)
                .build();

        List<DataHarianDetailsResponse> dataHarianDetailsDataList = new ArrayList<>();
        for (DataHarianDetails dataHarianDetails: dataHarianDetailsList){
            DataHarianDetailsResponse dataHarianDetailsData = DataHarianDetailsResponse.fromDataHarianDetails(dataHarianDetails, restTemplate, bearerToken, baseUrl);
            totalKaloriKonsumsi += dataHarianDetailsData.getMakanan().getKalori();
            nutrisiTotal.setKarbohidrat(nutrisiTotal.getKarbohidrat() + dataHarianDetailsData.getMakanan().getKarbohidrat());
            nutrisiTotal.setLemak(nutrisiTotal.getLemak() + dataHarianDetailsData.getMakanan().getLemak());
            nutrisiTotal.setSodium(nutrisiTotal.getSodium() + dataHarianDetailsData.getMakanan().getSodium());
            nutrisiTotal.setKolesterol(nutrisiTotal.getKolesterol() + dataHarianDetailsData.getMakanan().getKolesterol());
            nutrisiTotal.setProtein(nutrisiTotal.getProtein() + dataHarianDetailsData.getMakanan().getProtein());
            nutrisiTotal.setGula(nutrisiTotal.getGula() + dataHarianDetailsData.getMakanan().getGula());
            nutrisiTotal.setKalori(nutrisiTotal.getKalori() + dataHarianDetailsData.getMakanan().getKalori());
            dataHarianDetailsDataList.add(dataHarianDetailsData);
        }

        return DataHarianResponse.builder()
                .id(dataHarian.getId())
                .date(dataHarian.getDate())
                .targetKalori(dataHarian.getTargetKalori())
                .totalKaloriKonsumsi(totalKaloriKonsumsi)
                .nutrisiTotal(nutrisiTotal)
                .dataHarianDetailsDataList(dataHarianDetailsDataList)
                .build();
    }
}
