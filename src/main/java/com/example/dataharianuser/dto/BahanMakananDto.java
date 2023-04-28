package com.example.dataharianuser.dto;

import com.example.dataharianuser.model.DataHarianDetails;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BahanMakananDto extends MakananDTO{
    @JsonProperty("id_bahan")
    private Long idBahan;

    private MakananDTO makanan;

    private Double kalori;
    private Double gula;
    private Double lemak;
    private Double karbohidrat;
    private Double kolesterol;
    private Double sodium;
    private Double protein;
    private Double takaran;

    private Integer userId;
}
