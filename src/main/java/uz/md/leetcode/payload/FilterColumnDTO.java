package uz.md.leetcode.payload;

import lombok.Getter;
import uz.md.leetcode.payload.enums.ConditionTypeEnum;

@Getter
public class FilterColumnDTO {

    private String name;

    private ConditionTypeEnum conditionType;

    private String value;

    private String from;

    private String till;
}
