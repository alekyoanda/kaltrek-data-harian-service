package com.example.dataharianuser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TypeMakananResponse {
    private String namaMakanan;
    private boolean isResepMakanan;
    private Long idBahanOrResepMakanan;
}
