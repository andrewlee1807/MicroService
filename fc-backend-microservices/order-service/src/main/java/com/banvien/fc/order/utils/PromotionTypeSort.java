package com.banvien.fc.order.utils;

import com.banvien.fc.order.dto.ReductionDTO;

import java.util.Comparator;

public class PromotionTypeSort implements Comparator<ReductionDTO>
{
    // Used for sorting in descending order of
    // roll number
    public int compare(ReductionDTO a, ReductionDTO b)
    {
        return b.getPromotionType() - a.getPromotionType();
    }
}