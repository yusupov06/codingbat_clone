package uz.md.leetcode.compile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uz.md.leetcode.domain.Case;

@Getter
@AllArgsConstructor
public class CompileResult {

    private Case aCase;

    private String userAnswer;

    private boolean isSuccess;

}
