package com.example.reptile.mapper;

import com.example.reptile.model.Medicine;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MedicineMapper {
    int insert(List<Medicine> medicines);
}

