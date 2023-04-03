package com.example.dataharianuser.dto;

import com.example.dataharianuser.model.MakananCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public abstract class MakananDTO {
    private Long makananId;
    @Enumerated(EnumType.STRING)
    private MakananCategory makananCategory;
    private String namaMakanan;
    private Double jumlahKalori;
    private String namaTakaran;
    private Double jumlahTakaranInUnit;
}
