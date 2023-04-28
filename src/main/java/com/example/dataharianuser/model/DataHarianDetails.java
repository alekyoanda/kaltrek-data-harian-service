package com.example.dataharianuser.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    private DataHarian dataHarian;

    private Long makananId;
    @Value("${cp.custom.takaran:false}")
    private Boolean isCustomTakaran;
    private Double jumlahTakaran;

}