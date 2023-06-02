package com.example.dataharianuser.service.makanan;

import com.example.dataharianuser.exception.BahanOrResepMakananDoesNotExistException;
import com.example.dataharianuser.exception.MakananDoesNotExistException;
import com.example.dataharianuser.model.dto.makanan.MakananDetailsDto;
import com.example.dataharianuser.model.dto.makanan.TypeMakananResponse;
import com.example.dataharianuser.service.utils.RestTemplateProxy;
import com.example.dataharianuser.service.utils.URLManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MakananDetailsServiceImpl implements MakananDetailsService {

    private final RestTemplateProxy restTemplateProxy;
    private final URLManager urlManager;

    @Override
    public MakananDetailsDto getMakananDetails(Long makananId, String bearerToken) {
        TypeMakananResponse makananType = getTypeMakanan(makananId, bearerToken);
        String url = buildUrl(makananType.isResepMakanan(), makananType.getIdBahanOrResepMakanan());

        try{
            ResponseEntity<MakananDetailsDto> response = restTemplateProxy.get(url, bearerToken, MakananDetailsDto.class);
            MakananDetailsDto makanan = response.getBody();
            assert makanan != null;
            makanan.setNamaMakanan(makananType.getNamaMakanan());
            makanan.setCustomTakaran("Default");
            return makanan;
        } catch (Exception e){
            throw new BahanOrResepMakananDoesNotExistException(makananType.getIdBahanOrResepMakanan());
        }


    }

    @Override
    public TypeMakananResponse getTypeMakanan(Long makananId, String bearerToken) {
        String url = urlManager.getBaseUrlMakanan() + "/api/v1/makanan/get-tipe-makanan/" + makananId;
        try{
            ResponseEntity<TypeMakananResponse> response = restTemplateProxy.get(url, bearerToken, TypeMakananResponse.class);

            assert response.getBody() != null;
            return response.getBody();
        }
        catch (Exception e){
            throw new MakananDoesNotExistException(makananId);
        }

    }

    private String buildUrl(boolean isResepMakanan, Long makananId) {
        if (isResepMakanan) {
            return urlManager.getBaseUrlMakanan() + "/api/v1/resep/get-nutrisi-resep/" + makananId;
        } else {
            return urlManager.getBaseUrlMakanan() + "/api/v1/bahanmakanan/id/" + makananId;
        }
    }
}
