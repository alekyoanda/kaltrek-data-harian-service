package com.example.dataharianuser.service.makanan;

import com.example.dataharianuser.model.dto.makanan.MakananDetailsDto;
import com.example.dataharianuser.model.dto.makanan.TypeMakananResponse;
import org.springframework.stereotype.Service;

@Service
public interface MakananDetailsService {
    MakananDetailsDto getMakananDetails(Long makananId, String bearerToken);
    TypeMakananResponse getTypeMakanan(Long makananId, String bearerToken);
}
