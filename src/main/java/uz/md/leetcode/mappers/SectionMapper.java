package uz.md.leetcode.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.md.leetcode.domain.Language;
import uz.md.leetcode.payload.section.SectionDTO;
import uz.md.leetcode.payload.section.SectionUDTO;
import uz.md.leetcode.domain.Section;
import uz.md.leetcode.payload.section.SectionCDTO;
import uz.md.leetcode.repository.LanguageRepository;


@Mapper(componentModel = "spring", uses = {LanguageRepository.class})
public interface SectionMapper extends EntityMapper<
        Section,
        SectionDTO,
        SectionCDTO,
        SectionUDTO
        > {

    @Override
    @Mapping(target = "language", ignore = true)
    Section fromCDTO(SectionCDTO sectionCDTO);


}
