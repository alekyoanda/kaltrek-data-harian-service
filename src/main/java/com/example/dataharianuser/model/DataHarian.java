package com.example.dataharianuser.model;

import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DataHarian {
    @Id
    @GeneratedValue
    private Long id;
    private Date date;
    private Double targetKalori;
    private Double totalKaloriKonsumsi;
    private Integer userId;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    private List<DataHarianDetails> dataHarianDetailsList;


}
