package com.image.map.repositories;

import com.image.map.domain.EarthPornEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EarthPornRepository extends JpaRepository<EarthPornEntity, String> {

    EarthPornEntity findAllByTime(String time);
}
