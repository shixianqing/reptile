package com.example.reptile.mapper;

import com.example.reptile.model.HaiNanTreament;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface HaiNanTreamentMapper {
    int insert(List<HaiNanTreament> list);

}

