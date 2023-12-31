package com.example.dataharianuser.service.data_harian;

import com.example.dataharianuser.model.dto.data_harian.DataHarianRequest;
import com.example.dataharianuser.model.DataHarian;
import com.example.dataharianuser.repository.DataHarianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduledService {
    private final DataHarianRepository dataHarianRepository;
    private final DataHarianService dataHarianService;

    @Scheduled(cron = "1 0 0 * * *") // Executes at midnight
    public void createDataHarianEveryDay() {
        List<Integer> userIds = getUserIdsToCreateData();
        for (Integer userId : userIds) {
            DataHarianRequest dataHarianRequest = buildDataHarianRequest(userId);
            dataHarianService.create(userId, dataHarianRequest);
        }
    }

    private List<Integer> getUserIdsToCreateData() {
        List<Integer> userIds = new ArrayList<>();

        List<DataHarian> allDistinctDataHarian = dataHarianRepository.findAll();
        for (DataHarian dataHarian : allDistinctDataHarian) {
            userIds.add(dataHarian.getUserId());
        }
        return userIds;
    }

    private DataHarianRequest buildDataHarianRequest(Integer userId) {
        Optional<DataHarian> dataHarian = dataHarianRepository.findFirstByUserIdOrderByDateDesc(userId);

        if (dataHarian.isPresent()) {
            return DataHarianRequest.builder()
                    .targetKalori(dataHarian.get().getTargetKalori())
                    .build();
        }
        return DataHarianRequest.builder()
                .targetKalori(2000.0)
                .build();
    }
}
