package com.example.dataharianuser.service.dataHarian;


import com.example.dataharianuser.model.DataHarian;
import com.example.dataharianuser.model.dto.dataHarian.DataHarianRequest;
import com.example.dataharianuser.repository.DataHarianRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduledServiceTest {

    @InjectMocks
    private ScheduledService scheduledService;

    @Mock
    private DataHarianRepository dataHarianRepository;

    @Mock
    private DataHarianService dataHarianService;

    List<DataHarian> dataHarianList;

    DataHarian dataHarian1;
    DataHarian dataHarian2;
    DataHarian dataHarian3;

    DataHarianRequest dataHarianRequest1;
    DataHarianRequest dataHarianRequest2;

    @BeforeEach
    void setUp() {
        dataHarian1 = DataHarian.builder().userId(1).targetKalori(2000.0).build();
        dataHarian2 = DataHarian.builder().userId(2).targetKalori(2500.0).build();
        dataHarian3 = DataHarian.builder().userId(3).targetKalori(1800.0).build();

        dataHarianRequest1 = DataHarianRequest.builder().targetKalori(2000.0).build();
        dataHarianRequest2 = DataHarianRequest.builder().targetKalori(2500.0).build();

        dataHarianList = List.of(dataHarian1, dataHarian2, dataHarian3);
    }

    @Test
    void whenCreateDataHarianEveryDayShouldCreateDataHarianForAllUserIds() {
        when(dataHarianRepository.findAll()).thenReturn(dataHarianList);

        when(dataHarianRepository.findFirstByUserIdOrderByDateDesc(1)).thenReturn(Optional.empty());
        when(dataHarianRepository.findFirstByUserIdOrderByDateDesc(2)).thenReturn(Optional.of(DataHarian.builder().targetKalori(2500.0).build()));
        when(dataHarianRepository.findFirstByUserIdOrderByDateDesc(3)).thenReturn(Optional.empty());
        when(dataHarianService.create(1, dataHarianRequest1)).thenReturn(DataHarian.builder().build());
        when(dataHarianService.create(2, dataHarianRequest2)).thenReturn(DataHarian.builder().build());
        when(dataHarianService.create(3, dataHarianRequest1)).thenReturn(DataHarian.builder().build());

        // Act
        scheduledService.createDataHarianEveryDay();

        // Assert
        verify(dataHarianService, atLeastOnce()).create(1, dataHarianRequest1);
        verify(dataHarianService, atLeastOnce()).create(2, dataHarianRequest2);
        verify(dataHarianService, atLeastOnce()).create(3, dataHarianRequest1);

        verify(dataHarianRepository, atLeastOnce()).findAll();
        verify(dataHarianRepository, atLeastOnce()).findFirstByUserIdOrderByDateDesc(1);
        verify(dataHarianRepository, atLeastOnce()).findFirstByUserIdOrderByDateDesc(2);
        verify(dataHarianRepository, atLeastOnce()).findFirstByUserIdOrderByDateDesc(3);
    }
}
