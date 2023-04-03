package com.example.dataharianuser.dto;

import com.example.dataharianuser.model.DataHarianDetails;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BahanMakananDto extends MakananDTO{
    private Double sodium;
    private Double lemak;
    private Double gula;
    private Double karbohidrat;
    private Double kolesterol;
    private Double protein;
}
