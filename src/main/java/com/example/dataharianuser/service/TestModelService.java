package com.example.dataharianuser.service;


import com.example.dataharianuser.dto.TestModelRequest;
import com.example.dataharianuser.model.TestModel;

import java.util.List;

public interface TestModelService {
    List<TestModel> findAll();
    TestModel findById(Long id);
    TestModel create(TestModelRequest request);
    TestModel update(Long id, TestModelRequest request);
}
