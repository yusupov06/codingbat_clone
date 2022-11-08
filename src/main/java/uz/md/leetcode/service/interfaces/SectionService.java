package uz.md.leetcode.service.interfaces;

import uz.md.leetcode.payload.section.SectionDTO;
import uz.md.leetcode.payload.section.SectionUDTO;
import uz.md.leetcode.payload.ViewDTO;
import uz.md.leetcode.payload.section.SectionCDTO;
import uz.md.leetcode.projection.SectionDTOProjection;
import uz.md.leetcode.response.ApiResponse;

import java.util.List;



public interface SectionService {

    ApiResponse<SectionDTO> add(SectionCDTO sectionDTO);

    ApiResponse<SectionDTO> getSection(Integer id);

    ApiResponse<Void> delete(Integer id);

    ApiResponse<SectionDTO> edit(SectionUDTO sectionUDTO);

    ApiResponse<List<SectionDTOProjection>> getSections(ViewDTO viewDTO, int page, int size);

    ApiResponse<List<SectionDTO>> getSectionsForUser();
}
