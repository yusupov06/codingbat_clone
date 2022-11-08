package uz.md.leetcode.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Upload {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long size;

    @Column(nullable = false)
    private String contentType;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "upload")
    private UploadContent uploadContent;

    public Upload(String name, Long size, String contentType, UploadContent uploadContent) {
        this.name = name;
        this.size = size;
        this.contentType = contentType;
        this.uploadContent = uploadContent;
    }
}
