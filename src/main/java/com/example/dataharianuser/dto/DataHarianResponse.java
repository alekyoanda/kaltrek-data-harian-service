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
    private Date date;
    private Double targetKalori;
    private Double totalKaloriKonsumsi;
    private List<DataHarianDetailsData> dataHarianDetailsDataList;

    public static DataHarianResponse fromDataHarian(DataHarian dataHarian, List<DataHarianDetails> dataHarianDetailsList, RestTemplate restTemplate, String bearerToken) {
        Double totalKaloriKonsumsi = 0.0;

        List<DataHarianDetailsData> dataHarianDetailsDataList = new ArrayList<>();
        for (DataHarianDetails dataHarianDetails: dataHarianDetailsList){
            DataHarianDetailsData dataHarianDetailsData = DataHarianDetailsData.fromDataHarianDetails(dataHarianDetails, restTemplate, bearerToken);
            totalKaloriKonsumsi += dataHarianDetailsData.getMakanan().getKalori();
            dataHarianDetailsDataList.add(dataHarianDetailsData);
        }

        return DataHarianResponse.builder()
                .date(dataHarian.getDate())
                .targetKalori(dataHarian.getTargetKalori())
                .totalKaloriKonsumsi(totalKaloriKonsumsi)
                .dataHarianDetailsDataList(dataHarianDetailsDataList)
                .build();
    }
}
