package com.example.dataharianuser.dto;

import com.example.dataharianuser.model.DataHarianDetails;
import com.example.dataharianuser.service.utils.URLManager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataHarianDetailsResponse {
    private Long id;
    private MakananDetailsDto makanan;
    private Double jumlahTakaran;

    public static DataHarianDetailsResponse fromDataHarianDetails(DataHarianDetails dataHarianDetails, RestTemplate restTemplate, String bearerToken, String baseUrl) {
        MakananDetailsDto makanan = requestGetMakananDetails(dataHarianDetails.getMakananId(), restTemplate, bearerToken, baseUrl);

        setInformasiGiziSesuaiTakaran(dataHarianDetails, makanan);

        return DataHarianDetailsResponse.builder()
                .id(dataHarianDetails.getId())
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

    public static MakananDetailsDto requestGetMakananDetails(Long makananId, RestTemplate restTemplate, String bearerToken, String baseUrl){
        String url = baseUrl;
        System.out.println("BASE URL: " + url);
        TypeMakananResponse makananType = requestGetTypeMakanan(makananId, restTemplate, bearerToken, baseUrl);
        ResponseEntity<MakananDetailsDto> response;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", bearerToken);
        HttpEntity<String> entity = new HttpEntity<>("",headers);

        if (makananType.isResepMakanan()){
            url += "/api/v1/resep/get-nutrisi-resep/" + makananType.getIdBahanOrResepMakanan();
        }
        else{
            url += "/api/v1/bahanmakanan/id/" + makananType.getIdBahanOrResepMakanan();
        }

        response = restTemplate.exchange(url, HttpMethod.GET, entity, MakananDetailsDto.class);
        MakananDetailsDto makanan = response.getBody();

        if (makanan != null){
            makanan.setNamaMakanan(makananType.getNamaMakanan());
            makanan.setCustomTakaran("Default");
        }
        return makanan;
    }

    private static TypeMakananResponse requestGetTypeMakanan(Long makananId, RestTemplate restTemplate, String bearerToken, String baseUrl){
        String url = baseUrl + "/api/v1/makanan/get-tipe-makanan/" + makananId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", bearerToken);
        HttpEntity<String> entity = new HttpEntity<>("",headers);
        ResponseEntity<TypeMakananResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, TypeMakananResponse.class);

        return response.getBody();
    }

}
