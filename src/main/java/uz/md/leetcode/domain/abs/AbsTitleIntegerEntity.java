package uz.md.leetcode.domain.abs;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@MappedSuperclass
public abstract class AbsTitleIntegerEntity extends AbsIntegerEntity  {
    @Column(nullable = false)
    private String title;

}
