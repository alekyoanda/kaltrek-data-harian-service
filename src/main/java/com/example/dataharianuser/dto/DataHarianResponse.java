package com.example.dataharianuser.dto;

import com.example.dataharianuser.model.DataHarian;
import com.example.dataharianuser.model.DataHarianDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataHarianResponse {
    private Integer userId;
    private Date date;
    private Double targetKalori;
    private Double totalKaloriKonsumsi;
    private List<DataHarianDetailsData> dataHarianDetailsDataList;

    public static DataHarianResponse fromDataHarian(DataHarian dataHarian, List<DataHarianDetails> dataHarianDetailsList, RestTemplate restTemplate) {
        return DataHarianResponse.builder()
                .userId(dataHarian.getUserId())
                .date(dataHarian.getDate())
                .targetKalori(dataHarian.getTargetKalori())
                .totalKaloriKonsumsi(dataHarian.getTotalKaloriKonsumsi())
                .dataHarianDetailsDataList(dataHarianDetailsList
                        .stream()
                        .map((dataHarianDetails -> DataHarianDetailsData.fromDataHarianDetails(dataHarianDetails, restTemplate)))
                        .toList())
                .build();
    }
}
