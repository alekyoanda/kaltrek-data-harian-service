package com.example.dataharianuser.controller;

import com.example.dataharianuser.dto.TestModelRequest;
import com.example.dataharianuser.model.TestModel;
import com.example.dataharianuser.service.TestModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/data-harian-user")
@RequiredArgsConstructor
public class TestModelController {
    @Autowired
    private TestModelService testModelService;

    @GetMapping("/all")
    public ResponseEntity<List<TestModel>> getAllTestModel() {
        List<TestModel> response = testModelService.findAll();
        // TODO: Lengkapi kode berikut
        return ResponseEntity.ok(response);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<TestModel> getTestModelById(@PathVariable Long id) {
        TestModel response = testModelService.findById(id);
        // TODO: Lengkapi kode berikut
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<TestModel> addTestModel(@RequestBody TestModelRequest request) {
        TestModel response = testModelService.create(request);
        // TODO: Lengkapi kode berikut
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TestModel> putTestModel(@PathVariable Long id, @RequestBody TestModelRequest request) {
        TestModel response = testModelService.update(id, request);
        // TODO: Lengkapi kode berikut
        return ResponseEntity.ok(response);
    }
}