package com.image.map.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class PostsWrapper {

    private List<PostEntity> postEntityList;
}
