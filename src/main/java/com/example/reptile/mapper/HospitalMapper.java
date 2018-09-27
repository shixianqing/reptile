package com.example.reptile.mapper;

import com.example.reptile.model.Hospital;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface HospitalMapper {
    int insert(List<Map> hospitals);
    int add(Hospital hospitals);
}

