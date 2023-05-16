package com.example.dataharianuser.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    private Integer userId;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JsonBackReference
    private List<DataHarianDetails> dataHarianDetailsList;

    public void addDataHarianDetails(DataHarianDetails dataHarianDetails){
        this.dataHarianDetailsList.add(dataHarianDetails);
    }

    public void removeDataHarianDetails(DataHarianDetails dataHarianDetails){
        this.dataHarianDetailsList.remove(dataHarianDetails);
    }


}
