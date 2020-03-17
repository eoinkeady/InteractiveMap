package com.image.map.service;

import com.image.map.domain.EarthPornEntity;
import com.image.map.repositories.EarthPornRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EarthPornService {

    @Autowired
    EarthPornRepository earthPornRepository;

    public EarthPornEntity getImages(final String time) {
        return earthPornRepository.findAllByTime(time);
    }

    public void saveData(final EarthPornEntity earthPornEntity) {
        earthPornRepository.save(earthPornEntity);
    }


}
