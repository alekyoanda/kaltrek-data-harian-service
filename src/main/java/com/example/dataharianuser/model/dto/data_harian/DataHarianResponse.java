package com.example.dataharianuser.model.dto.data_harian;

import com.example.dataharianuser.model.dto.makanan.NutrisiDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
}
