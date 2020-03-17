package com.image.map.engine;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.image.map.Utils.MapUtils;
import com.image.map.domain.EarthPornEntity;
import com.image.map.service.EarthPornService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class PostsEngine {

    @Value("${reddit.earth}")
    private String redditUrl;

    @Value("${run.engine}")
    private boolean runEngine;

    @Autowired
    private MapUtils mapUtils;

    @Autowired
    EarthPornService earthPornService;

    List<String> times = Arrays.asList("DAY", "WEEK", "MONTH", "YEAR", "ALL");

    @Scheduled(cron = "0 0/2 * * * ?")
    private void getJsonForPosts() {
        if (runEngine) {
            for (String time : times) {
                JSONObject postJson = null;
                try {
                    String url = redditUrl + time.toLowerCase();
                    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                    postJson = new JSONObject(ow.writeValueAsString(mapUtils.getTopRedditPosts(url)));
                    EarthPornEntity earthPornEntity = new EarthPornEntity();
                    earthPornEntity.setTime(time);
                    earthPornEntity.setJson(postJson.toString());
                    earthPornService.saveData(earthPornEntity);
                } catch (JsonProcessingException ex) {
                    System.out.println("Cannot convert to json");
                } catch (Exception e) {

                }
            }
        }

    }

}
