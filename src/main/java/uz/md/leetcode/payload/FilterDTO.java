package uz.md.leetcode.payload;

import lombok.Getter;
import uz.md.leetcode.payload.enums.OperatorTypeEnum;

import java.util.ArrayList;
import java.util.List;

@Getter
public class FilterDTO {

    private OperatorTypeEnum operatorType;//AND, OR

    private List<FilterColumnDTO> columns = new ArrayList<>();
}
