package com.example.dataharianuser.service;

import com.example.dataharianuser.dto.TestModelRequest;
import com.example.dataharianuser.model.TestModel;
import com.example.dataharianuser.repository.TestModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestModelServiceImpl implements TestModelService{
    @Autowired
    private TestModelRepository testModelRepository;

    @Override
    public List<TestModel> findAll() {
        return testModelRepository.findAll();
    }

    @Override
    public TestModel findById(Long id) {
        return testModelRepository.findById(id).orElseThrow();
    }

    @Override
    public TestModel create(TestModelRequest request) {
        TestModel testModel = TestModel.builder()
                .name(request.getName())
                .number(request.getNumber())
                .date(new Date())
                .build();
        return testModelRepository.save(testModel);
    }

    @Override
    public TestModel update(Long id, TestModelRequest request) {
        Optional<TestModel> testModelOptional = testModelRepository.findById(id);
        if (testModelOptional.isPresent()){
            TestModel testModel = TestModel.builder()
                    .id(id)
                    .name(request.getName())
                    .number(request.getNumber())
                    .date(new Date())
                    .build();
            return testModelRepository.save(testModel);
        }
        else{
            return null;
        }
    }
}
