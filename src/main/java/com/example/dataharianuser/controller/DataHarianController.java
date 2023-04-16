package com.example.dataharianuser.controller;

import com.example.dataharianuser.dto.DataHarianRequest;
import com.example.dataharianuser.dto.DataHarianResponse;
import com.example.dataharianuser.model.DataHarian;
import com.example.dataharianuser.service.DataHarianService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/data-harian")
@RequiredArgsConstructor
public class DataHarianController {
    private final DataHarianService dataHarianService;

    @GetMapping("/all")
    public ResponseEntity<List<DataHarianResponse>> getAllDataHarian() {
        List<DataHarianResponse> response = dataHarianService.findAll();
        // TODO: Lengkapi kode berikut
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<DataHarianResponse>> getAllUserDataHarian(@PathVariable Integer userId) {
        List<DataHarianResponse> response = dataHarianService.findAllByUserId(userId);
        // TODO: Lengkapi kode berikut
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<DataHarian> createDataHarian(@PathVariable Integer userId, @RequestBody DataHarianRequest dataHarianRequest) {
        DataHarian response = dataHarianService.create(userId, dataHarianRequest);
        // TODO: Lengkapi kode berikut
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{userId}/{id}")
    public ResponseEntity<DataHarian> updateOrder(@PathVariable Integer userId, @PathVariable Long id, @RequestBody DataHarianRequest dataHarianRequest) {
        DataHarian response = dataHarianService.update(userId, id, dataHarianRequest);
        // TODO: Lengkapi kode berikut
        return ResponseEntity.ok(response);
    }

}
