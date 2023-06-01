package com.example.dataharianuser.service.mapper;

import com.example.dataharianuser.model.dto.dataHarian.DataHarianDetailsResponse;
import com.example.dataharianuser.model.dto.dataHarian.DataHarianResponse;
import com.example.dataharianuser.model.dto.makanan.NutrisiDto;
import com.example.dataharianuser.model.DataHarian;
import com.example.dataharianuser.model.DataHarianDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHarianResponseMapper {
    @Autowired
    private DataHarianDetailsResponseMapper dataHarianDetailsResponseMapper;

    public DataHarianResponse mapToDataHarianResponse(DataHarian dataHarian, List<DataHarianDetails> dataHarianDetailsList, String bearerToken) {
        Double totalKaloriKonsumsi = 0.0;
        NutrisiDto nutrisiTotal = NutrisiDto.builder()
                .karbohidrat(0.0)
                .lemak(0.0)
                .sodium(0.0)
                .kolesterol(0.0)
                .protein(0.0)
                .kalori(0.0)
                .gula(0.0)
                .build();

        List<DataHarianDetailsResponse> dataHarianDetailsDataList = new ArrayList<>();
        for (DataHarianDetails dataHarianDetails : dataHarianDetailsList) {
            DataHarianDetailsResponse dataHarianDetailsData = dataHarianDetailsResponseMapper.mapToDataHarianDetailsResponse(dataHarianDetails, bearerToken);

            totalKaloriKonsumsi += dataHarianDetailsData.getMakanan().getKalori();
            nutrisiTotal.setKarbohidrat(nutrisiTotal.getKarbohidrat() + dataHarianDetailsData.getMakanan().getKarbohidrat());
            nutrisiTotal.setLemak(nutrisiTotal.getLemak() + dataHarianDetailsData.getMakanan().getLemak());
            nutrisiTotal.setSodium(nutrisiTotal.getSodium() + dataHarianDetailsData.getMakanan().getSodium());
            nutrisiTotal.setKolesterol(nutrisiTotal.getKolesterol() + dataHarianDetailsData.getMakanan().getKolesterol());
            nutrisiTotal.setProtein(nutrisiTotal.getProtein() + dataHarianDetailsData.getMakanan().getProtein());
            nutrisiTotal.setGula(nutrisiTotal.getGula() + dataHarianDetailsData.getMakanan().getGula());
            nutrisiTotal.setKalori(nutrisiTotal.getKalori() + dataHarianDetailsData.getMakanan().getKalori());

            dataHarianDetailsDataList.add(dataHarianDetailsData);
        }

        return DataHarianResponse.builder()
                .id(dataHarian.getId())
                .date(dataHarian.getDate())
                .targetKalori(dataHarian.getTargetKalori())
                .totalKaloriKonsumsi(totalKaloriKonsumsi)
                .nutrisiTotal(nutrisiTotal)
                .dataHarianDetailsDataList(dataHarianDetailsDataList)
                .build();
    }
}
