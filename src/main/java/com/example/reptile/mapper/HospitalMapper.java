package com.example.reptile.mapper;

import com.example.reptile.model.Hospital;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HospitalMapper {
    int insert(List<Hospital> hospitals);
}

