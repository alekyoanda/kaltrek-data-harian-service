package com.example.dataharianuser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MakananDetailsDto {
    private String namaMakanan;
    private String customTakaran;

    private Double kalori;
    private Double gula;
    private Double lemak;
    private Double karbohidrat;
    private Double kolesterol;
    private Double sodium;
    private Double protein;
    private Double takaran;
}
