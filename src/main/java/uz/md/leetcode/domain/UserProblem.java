package uz.md.leetcode.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import uz.md.leetcode.domain.abs.AbsLongEntity;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Table(uniqueConstraints = @UniqueConstraint(
        columnNames = {"user_id", "problem_id"}))
public class UserProblem extends AbsLongEntity {

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    private Problem problem;

    @Column(columnDefinition = "text")
    private String solution;

    @Column(columnDefinition = " bool default false")
    private boolean deleted;

    @Column(nullable = false)
    private Boolean solved;

}