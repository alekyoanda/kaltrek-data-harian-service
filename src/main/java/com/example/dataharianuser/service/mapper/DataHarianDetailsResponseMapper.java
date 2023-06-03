package com.example.dataharianuser.service.mapper;

import com.example.dataharianuser.model.dto.dataHarian.DataHarianDetailsResponse;
import com.example.dataharianuser.model.dto.dataHarian.DataHarianResponse;
import com.example.dataharianuser.model.dto.makanan.MakananDetailsDto;
import com.example.dataharianuser.model.DataHarianDetails;
import com.example.dataharianuser.service.makanan.MakananDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataHarianDetailsResponseMapper {
    @Autowired
    private MakananDetailsService makananDetailsService;

    public DataHarianDetailsResponse mapToDataHarianDetailsResponse(DataHarianDetails dataHarianDetails, String bearerToken){
        MakananDetailsDto makanan = makananDetailsService.getMakananDetails(dataHarianDetails.getMakananId(), bearerToken);

        setInformasiGiziSesuaiTakaran(dataHarianDetails, makanan);

        return DataHarianDetailsResponse.builder()
                .id(dataHarianDetails.getId())
                .makanan(makanan)
                .jumlahTakaran(dataHarianDetails.getJumlahTakaran())
                .build();
    }

    private void setInformasiGiziSesuaiTakaran(DataHarianDetails dataHarianDetails, MakananDetailsDto makanan) {
        Double koefisienTakaran = dataHarianDetails.getJumlahTakaran() / makanan.getTakaran();

        makanan.setGula(makanan.getGula() * koefisienTakaran);
        makanan.setKalori(makanan.getKalori() * koefisienTakaran);
        makanan.setKarbohidrat(makanan.getKarbohidrat() * koefisienTakaran);
        makanan.setKolesterol(makanan.getKolesterol() * koefisienTakaran);
        makanan.setLemak(makanan.getLemak() * koefisienTakaran);
        makanan.setProtein(makanan.getProtein() * koefisienTakaran);
        makanan.setSodium(makanan.getSodium() * koefisienTakaran);
    }
}
