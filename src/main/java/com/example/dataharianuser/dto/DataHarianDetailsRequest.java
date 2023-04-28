package com.example.dataharianuser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataHarianDetailsRequest {
    private Long makananId;
    private Boolean isCustomTakaran;
    private Double jumlahTakaran;
}
