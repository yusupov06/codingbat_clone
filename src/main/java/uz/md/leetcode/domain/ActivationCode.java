package uz.md.leetcode.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Future;
import java.time.LocalDateTime;



@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Valid for this time
     */
    @Future(message = "valid till must be future time")
    private LocalDateTime validTill;

    /**
     * Activation code
     */
    @Column(unique = true, nullable = false)
    private String activationCode;

    private boolean deleted;

    @Column(unique = true, nullable = false)
    private Integer userId;

}