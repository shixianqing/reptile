package com.example.reptile.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ZhongYiBaoDianDiseaseMapper {
    int insert(List<String> list);

}

