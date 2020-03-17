package com.image.map.domain;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "EARTH_PORN")
public class EarthPornEntity {

    @Id
    @Column(name = "TIME")
    private String time;

    @Column(name = "JSON")
    private String json;






}
