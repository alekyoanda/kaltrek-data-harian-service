package com.example.dataharianuser.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DataHarianDetails {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private DataHarian dataHarian;

    private Long makananId;
    @Value("${cp.custom.takaran:false}")
    private Boolean isCustomTakaran;
    private Double jumlahTakaran;

}