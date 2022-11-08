package uz.md.leetcode.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import uz.md.leetcode.domain.abs.AbsTitleIntegerEntity;
import uz.md.leetcode.utils.BaseUtils;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"title"}))
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public final class Language extends AbsTitleIntegerEntity {
    @Column(unique = true, nullable = false)
    private String url;

    @JsonIgnore
    @OneToMany(mappedBy = "language")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private List<Section> sections;

    public Language(String title) {
        setTitle(title);
    }

    public Language(String title, String url) {
        setTitle(title);
        this.url = url;
    }

    public Language(String title, String url, Integer id) {
        this(title,url);
        setId(id);
    }

    private void setUrl() {
        this.url = BaseUtils.makeUrl(super.getTitle());
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
        setUrl();
    }
}
