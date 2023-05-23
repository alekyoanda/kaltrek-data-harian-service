package com.example.dataharianuser.service;

import com.example.dataharianuser.dto.DataHarianRequest;
import com.example.dataharianuser.model.DataHarian;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduledService {
    @Autowired
    private DataHarianService dataHarianService;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${endpoint.url}")
    private String url;

//    @Scheduled(cron = "0 0 0 * * *") // Executes at midnight
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000) // Execute every 24 hours
    public void createDataHarianEveryDay() {

        // Get the user IDs for which you want to create daily data
        List<Integer> userIds = getUserIdsToCreateData();

        // Iterate over the user IDs and create daily data for each user
        for (Integer userId : userIds) {
            System.out.println(userId);
            DataHarianRequest dataHarianRequest = buildDataHarianRequest(); // Create the request object as per your requirements
            DataHarian dataHarian = dataHarianService.create(userId, dataHarianRequest); // Create the daily data
            // Do something with the created data, if needed
            System.out.println("success");
        }
    }

    private List<Integer> getUserIdsToCreateData() {
        // Implement the logic to retrieve the user IDs for which you want to create daily data
        // This could be fetching from a database or any other source
        // Return the list of user IDs

        return restTemplate.getForObject(url, List.class);
    }

    private DataHarianRequest buildDataHarianRequest() {
        // Implement the logic to build the DataHarianRequest object with the necessary data
        // Return the DataHarianRequest object
        DataHarianRequest dataHarianRequest = DataHarianRequest.builder()
                .targetKalori(2000.0)
                .build();
        return dataHarianRequest;
    }
}
