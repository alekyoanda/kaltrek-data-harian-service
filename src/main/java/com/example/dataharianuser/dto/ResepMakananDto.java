package com.example.dataharianuser.dto;

import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResepMakananDto extends MakananDTO{
    private String caraMemasak;
    private String deskripsi;
    private Boolean isDibagikan;
    private Integer waktuMemasak;

    private List<BahanMakananDto> bahanMakananList;
}
