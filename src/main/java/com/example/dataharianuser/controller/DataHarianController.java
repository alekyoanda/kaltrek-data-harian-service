package com.example.dataharianuser.controller;

import com.example.dataharianuser.dto.DataHarianDetailsResponse;
import com.example.dataharianuser.dto.DataHarianDetailsRequest;
import com.example.dataharianuser.dto.DataHarianRequest;
import com.example.dataharianuser.dto.DataHarianResponse;
import com.example.dataharianuser.exception.UnauthenticatedException;
import com.example.dataharianuser.model.DataHarian;
import com.example.dataharianuser.model.DataHarianDetails;
import com.example.dataharianuser.service.DataHarianService;
import com.example.dataharianuser.service.utils.Authenticator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/data-harian")
@RequiredArgsConstructor
public class DataHarianController {
    @Autowired
    private final DataHarianService dataHarianService;

    @GetMapping("/all")
    public ResponseEntity<List<DataHarianResponse>> getAllUserDataHarian(HttpServletRequest request) {
        List<DataHarianResponse> response = dataHarianService.findAllByUserId(getUserId(request), getBearerToken(request));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-by-date")
    public ResponseEntity<DataHarianResponse> getDataHarianUserByDate(HttpServletRequest request,
                                                                      @RequestParam(value="date") @DateTimeFormat(pattern="ddMMyyyy") Date date) {
        DataHarianResponse response = dataHarianService.findDataHarianByDateAndUserId(date, getUserId(request), getBearerToken(request));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<DataHarian> createDataHarian(HttpServletRequest request,
                                                       @RequestBody DataHarianRequest dataHarianRequest) {
        DataHarian response = dataHarianService.create(getUserId(request), dataHarianRequest);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/get-data-harian-details/{data_harian_id}/{data_harian_detail_id}")
    public ResponseEntity<DataHarianDetailsResponse> getDataHarianDetailsById(HttpServletRequest request,
                                                                              @PathVariable Long data_harian_id,
                                                                              @PathVariable Long data_harian_detail_id) {
        DataHarianDetailsResponse response = dataHarianService.getDataHarianDetails(getUserId(request), data_harian_id, data_harian_detail_id, getBearerToken(request));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-target-kalori/{id}")
    public ResponseEntity<DataHarian> updateDataHarianTargetKalori(HttpServletRequest request,
                                                                   @PathVariable Long id,
                                                                   @RequestBody DataHarianRequest dataHarianRequest) {
        DataHarian response = dataHarianService.updateTargetKalori(getUserId(request), id, dataHarianRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-tambah-makanan/{id}")
    public ResponseEntity<DataHarianDetails> updateTambahMakanan(HttpServletRequest request,
                                                                 @PathVariable Long id,
                                                                 @RequestBody DataHarianDetailsRequest dataHarianDetailsRequest) {
        DataHarianDetails response = dataHarianService.updateTambahMakanan(getUserId(request), id, dataHarianDetailsRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-ubah-makanan/{data_harian_id}/{data_harian_detail_id}")
    public ResponseEntity<DataHarianDetails> updateUbahMakanan(HttpServletRequest request,
                                                               @PathVariable Long data_harian_id,
                                                               @PathVariable Long data_harian_detail_id,
                                                               @RequestBody DataHarianDetailsRequest dataHarianDetailsRequest) {
        DataHarianDetails response = dataHarianService.updateUbahMakanan(getUserId(request), data_harian_id, data_harian_detail_id, dataHarianDetailsRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{data_harian_id}/{data_harian_detail_id}")
    public ResponseEntity<DataHarianDetails> deleteDataHarianDetail(HttpServletRequest request,
                                                                    @PathVariable Long data_harian_id,
                                                                    @PathVariable Long data_harian_detail_id) {
        DataHarianDetails response = dataHarianService.deleteDataHarianDetail(getUserId(request), data_harian_id, data_harian_detail_id);
        return ResponseEntity.ok(response);
    }

    private Integer getUserId(HttpServletRequest request){
        return (Integer) request.getAttribute("userId");
    }

    private String getBearerToken(HttpServletRequest request){
        return request.getHeader("Authorization");
    }
}
