package com.example.reptile.mapper;

import com.example.reptile.model.Hospital;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ChongQingTreamentMapper {
    int insert(List<Map> list);

}

