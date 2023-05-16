package com.example.dataharianuser.dto;

import com.example.dataharianuser.model.DataHarianDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataHarianDetailsData {
    private MakananDetailsDto makanan;
    private Double jumlahTakaran;

    public static DataHarianDetailsData fromDataHarianDetails(DataHarianDetails dataHarianDetails, RestTemplate restTemplate, String bearerToken) {
        MakananDetailsDto makanan = requestGetMakananDetails(dataHarianDetails.getMakananId(), restTemplate, bearerToken);

        setInformasiGiziSesuaiTakaran(dataHarianDetails, makanan);

        return DataHarianDetailsData.builder()
                .makanan(makanan)
                .jumlahTakaran(dataHarianDetails.getJumlahTakaran())
                .build();
    }

    private static void setInformasiGiziSesuaiTakaran(DataHarianDetails dataHarianDetails, MakananDetailsDto makanan) {
        Double koefisienTakaran = dataHarianDetails.getJumlahTakaran() / makanan.getTakaran();

        makanan.setGula(makanan.getGula() * koefisienTakaran);
        makanan.setKalori(makanan.getKalori() * koefisienTakaran);
        makanan.setKarbohidrat(makanan.getKarbohidrat() * koefisienTakaran);
        makanan.setKolesterol(makanan.getKolesterol() * koefisienTakaran);
        makanan.setLemak(makanan.getLemak() * koefisienTakaran);
        makanan.setProtein(makanan.getProtein() * koefisienTakaran);
        makanan.setSodium(makanan.getSodium() * koefisienTakaran);
    }

    public static MakananDetailsDto requestGetMakananDetails(Long makananId, RestTemplate restTemplate, String bearerToken){
        String url;
        TypeMakananResponse makananType = requestGetTypeMakanan(makananId, restTemplate, bearerToken);
        ResponseEntity<MakananDetailsDto> response;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", bearerToken);
        HttpEntity<String> entity = new HttpEntity<>("",headers);

        if (makananType.isResepMakanan()){
            url = "http://localhost:8081/api/v1/resep/get-nutrisi-resep/" + makananType.getIdBahanOrResepMakanan();
        }
        else{
            url = "http://localhost:8081/api/v1/bahanmakanan/id/" + makananType.getIdBahanOrResepMakanan();
        }

        response = restTemplate.exchange(url, HttpMethod.GET, entity, MakananDetailsDto.class);
        MakananDetailsDto makanan = response.getBody();

        if (makanan != null){
            makanan.setNamaMakanan(makananType.getNamaMakanan());
            makanan.setCustomTakaran("Default");
        }
        System.out.println(makanan);
        return makanan;
    }

    private static TypeMakananResponse requestGetTypeMakanan(Long makananId, RestTemplate restTemplate, String bearerToken){
        String url = "http://localhost:8081/api/v1/makanan/get-tipe-makanan/" + makananId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", bearerToken);
        HttpEntity<String> entity = new HttpEntity<>("",headers);
        ResponseEntity<TypeMakananResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, TypeMakananResponse.class);

        return response.getBody();
    }

}
