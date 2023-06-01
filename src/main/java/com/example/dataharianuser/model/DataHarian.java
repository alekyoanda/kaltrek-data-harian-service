package com.example.dataharianuser.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
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

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "dataHarian", orphanRemoval = true)
    @JsonManagedReference
    private List<DataHarianDetails> dataHarianDetailsList;

    public void addDataHarianDetails(DataHarianDetails dataHarianDetails){
        this.dataHarianDetailsList.add(dataHarianDetails);
    }

    public void removeDataHarianDetails(DataHarianDetails dataHarianDetails){
        this.dataHarianDetailsList.remove(dataHarianDetails);
    }

    @Override
    public String toString() {
        return "DataHarian{" +
                "id=" + id +
                ", date=" + date +
                ", targetKalori=" + targetKalori +
                ", userId=" + userId +
                '}';
    }



}
