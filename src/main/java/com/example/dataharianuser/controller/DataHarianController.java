package com.example.dataharianuser.controller;

import com.example.dataharianuser.model.dto.data_harian.DataHarianDetailsResponse;
import com.example.dataharianuser.model.dto.data_harian.DataHarianDetailsRequest;
import com.example.dataharianuser.model.dto.data_harian.DataHarianRequest;
import com.example.dataharianuser.model.dto.data_harian.DataHarianResponse;
import com.example.dataharianuser.model.DataHarian;
import com.example.dataharianuser.service.data_harian.DataHarianService;
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


    @GetMapping("/get-data-harian-details/{dataHarianId}/{dataHarianDetailsId}")
    public ResponseEntity<DataHarianDetailsResponse> getDataHarianDetailsById(HttpServletRequest request,
                                                                              @PathVariable Long dataHarianId,
                                                                              @PathVariable Long dataHarianDetailsId) {
        DataHarianDetailsResponse response = dataHarianService.getDataHarianDetails(getUserId(request), dataHarianId, dataHarianDetailsId, getBearerToken(request));
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
    public ResponseEntity<DataHarian> updateTambahMakanan(HttpServletRequest request,
                                                                 @PathVariable Long id,
                                                                 @RequestBody DataHarianDetailsRequest dataHarianDetailsRequest) {
        DataHarian response = dataHarianService.updateTambahMakanan(getUserId(request), id, dataHarianDetailsRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-ubah-makanan/{dataHarianId}/{dataHarianDetailsId}")
    public ResponseEntity<DataHarian> updateUbahMakanan(HttpServletRequest request,
                                                               @PathVariable Long dataHarianId,
                                                               @PathVariable Long dataHarianDetailsId,
                                                               @RequestBody DataHarianDetailsRequest dataHarianDetailsRequest) {
        DataHarian response = dataHarianService.updateUbahMakanan(getUserId(request), dataHarianId, dataHarianDetailsId, dataHarianDetailsRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{dataHarianId}/{dataHarianDetailsId}")
    public ResponseEntity<DataHarian> deleteDataHarianDetail(HttpServletRequest request,
                                                                    @PathVariable Long dataHarianId,
                                                                    @PathVariable Long dataHarianDetailsId) {
        DataHarian response = dataHarianService.deleteDataHarianDetail(getUserId(request), dataHarianId, dataHarianDetailsId);
        return ResponseEntity.ok(response);
    }

    private Integer getUserId(HttpServletRequest request){
        return (Integer) request.getAttribute("userId");
    }

    private String getBearerToken(HttpServletRequest request){
        return request.getHeader("Authorization");
    }
}
