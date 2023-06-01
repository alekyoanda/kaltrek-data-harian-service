package com.example.dataharianuser.model.dto.dataHarian;

import com.example.dataharianuser.model.dto.makanan.MakananDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataHarianDetailsResponse {
    private Long id;
    private MakananDetailsDto makanan;
    private Double jumlahTakaran;
}
