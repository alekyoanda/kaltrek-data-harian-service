package com.example.dataharianuser.model.dto.data_harian;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataHarianRequest {
    private Double targetKalori;
    private Double totalKaloriKonsumsi;
    private List<DataHarianDetailsResponse> dataHarianDetailsDataList;
}
