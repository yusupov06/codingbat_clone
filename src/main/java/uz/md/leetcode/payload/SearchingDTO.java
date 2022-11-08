package uz.md.leetcode.payload;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SearchingDTO {

    private List<String> columns = new ArrayList<>();

    private String value = "";
}
