package com.example.dataharianuser.model.dto.makanan;

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
