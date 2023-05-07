package com.example.dataharianuser.controller;

import com.example.dataharianuser.dto.DataHarianDetailsRequest;
import com.example.dataharianuser.dto.DataHarianRequest;
import com.example.dataharianuser.dto.DataHarianResponse;
import com.example.dataharianuser.model.DataHarian;
import com.example.dataharianuser.model.DataHarianDetails;
import com.example.dataharianuser.service.DataHarianService;
import com.example.dataharianuser.utils.Authenticator;
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
    private final DataHarianService dataHarianService;
    private final Authenticator authenticator = Authenticator.getInstance();

    @GetMapping("/all")
    public ResponseEntity<List<DataHarianResponse>> getAllUserDataHarian(@RequestHeader("Authorization") String bearerToken) {
        Integer userId = authenticator.getUserId(bearerToken);
        List<DataHarianResponse> response = dataHarianService.findAllByUserId(userId, bearerToken);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-by-date")
    public ResponseEntity<DataHarianResponse> getDataHarianUserByDate(@RequestHeader("Authorization") String bearerToken,
                                                                      @RequestParam(value="date") @DateTimeFormat(pattern="ddMMyyyy") Date date) {
        Integer userId = authenticator.getUserId(bearerToken);
        DataHarianResponse response = dataHarianService.findDataHarianByDateAndUserId(date, userId, bearerToken);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<DataHarian> createDataHarian(@RequestHeader("Authorization") String bearerToken, @RequestBody DataHarianRequest dataHarianRequest) {
        Integer userId = authenticator.getUserId(bearerToken);
        DataHarian response = dataHarianService.create(userId, dataHarianRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-target-kalori/{id}")
    public ResponseEntity<DataHarian> updateDataHarianTargetKalori(@RequestHeader("Authorization") String bearerToken, @PathVariable Long id, @RequestBody DataHarianRequest dataHarianRequest) {
        Integer userId = authenticator.getUserId(bearerToken);
        DataHarian response = dataHarianService.updateTargetKalori(userId, id, dataHarianRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-tambah-makanan/{id}")
    public ResponseEntity<DataHarianDetails> updateTambahMakanan(@RequestHeader("Authorization") String bearerToken, @PathVariable Long id, @RequestBody DataHarianDetailsRequest dataHarianDetailsRequest) {
        Integer userId = authenticator.getUserId(bearerToken);
        DataHarianDetails response = dataHarianService.updateTambahMakanan(userId, id, dataHarianDetailsRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-ubah-makanan/{data_harian_id}/{data_harian_detail_id}")
    public ResponseEntity<DataHarianDetails> updateUbahMakanan(@RequestHeader("Authorization") String bearerToken,
                                                               @PathVariable Long data_harian_id, @PathVariable Long data_harian_detail_id,
                                                               @RequestBody DataHarianDetailsRequest dataHarianDetailsRequest) {
        Integer userId = authenticator.getUserId(bearerToken);
        DataHarianDetails response = dataHarianService.updateUbahMakanan(userId, data_harian_id, data_harian_detail_id, dataHarianDetailsRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{data_harian_id}/{data_harian_detail_id}")
    public ResponseEntity<DataHarianDetails> deleteDataHarianDetail(@RequestHeader("Authorization") String bearerToken, @PathVariable Long data_harian_id, @PathVariable Long data_harian_detail_id) {
        Integer userId = authenticator.getUserId(bearerToken);
        DataHarianDetails response = dataHarianService.deleteDataHarianDetail(userId, data_harian_id, data_harian_detail_id);
        return ResponseEntity.ok(response);
    }
}
