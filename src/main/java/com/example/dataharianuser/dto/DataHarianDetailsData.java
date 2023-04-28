package com.example.dataharianuser.dto;

import com.example.dataharianuser.model.DataHarianDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.client.RestTemplate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataHarianDetailsData {
    private BahanMakananDto makanan;
    private Double jumlahTakaran;

    public static DataHarianDetailsData fromDataHarianDetails(DataHarianDetails dataHarianDetails, RestTemplate restTemplate) {
        BahanMakananDto bahanMakanan = requestGetBahanMakanan(dataHarianDetails.getMakananId(), restTemplate);

        setInformasiGiziSesuaiTakaran(dataHarianDetails, bahanMakanan);

        return DataHarianDetailsData.builder()
                .makanan(bahanMakanan)
                .jumlahTakaran(dataHarianDetails.getJumlahTakaran())
                .build();
    }

    private static void setInformasiGiziSesuaiTakaran(DataHarianDetails dataHarianDetails, BahanMakananDto bahanMakanan) {
        Double koefisienTakaran = dataHarianDetails.getJumlahTakaran() / bahanMakanan.getTakaran();

        bahanMakanan.setGula(bahanMakanan.getGula() * koefisienTakaran);
        bahanMakanan.setKalori(bahanMakanan.getKalori() * koefisienTakaran);
        bahanMakanan.setKarbohidrat(bahanMakanan.getKarbohidrat() * koefisienTakaran);
        bahanMakanan.setKolesterol(bahanMakanan.getKolesterol() * koefisienTakaran);
        bahanMakanan.setLemak(bahanMakanan.getLemak() * koefisienTakaran);
        bahanMakanan.setProtein(bahanMakanan.getProtein() * koefisienTakaran);
        bahanMakanan.setSodium(bahanMakanan.getSodium() * koefisienTakaran);
    }

    public static BahanMakananDto requestGetBahanMakanan(Long bahanMakananId, RestTemplate restTemplate){
        String url = "http://localhost:8080/api/v1/bahanmakanan/id/" + bahanMakananId;

        return restTemplate.getForObject(url, BahanMakananDto.class);
    }

}
