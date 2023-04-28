package com.example.dataharianuser.dto;

import com.example.dataharianuser.model.MakananCategory;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class MakananDTO {
    @JsonProperty("id_makanan")
    private Long makananId;

    @JsonProperty("nama_makanan")
    private String namaMakanan;
}
