package uz.md.leetcode.controller.implementations;

import lombok.NonNull;
import org.springframework.web.bind.annotation.RestController;
import uz.md.leetcode.controller.interfaces.SectionController;
import uz.md.leetcode.payload.ViewDTO;
import uz.md.leetcode.payload.section.SectionCDTO;
import uz.md.leetcode.payload.section.SectionDTO;
import uz.md.leetcode.payload.section.SectionUDTO;
import uz.md.leetcode.projection.SectionDTOProjection;
import uz.md.leetcode.response.ApiResponse;
import uz.md.leetcode.service.interfaces.SectionService;

import java.util.List;


@RestController
public class SectionControllerImpl implements SectionController {

    private final SectionService service;

    public SectionControllerImpl(SectionService service) {
        this.service = service;
    }

    @Override
    public ApiResponse<List<SectionDTOProjection>> getSections(ViewDTO viewDTO, int page, int size) {
        return service.getSections(viewDTO, page, size);
    }

    @Override
    public ApiResponse<SectionDTO> add(@NonNull SectionCDTO sectionCDTO) {
        return service.add(sectionCDTO);
    }

    @Override
    public ApiResponse<SectionDTO> update(@NonNull SectionUDTO sectionUDTO) {
        return service.edit(sectionUDTO);
    }

    @Override
    public ApiResponse<SectionDTO> get(@NonNull Integer id) {
        return service.getSection(id);
    }

    @Override
    public ApiResponse<Void> delete(@NonNull Integer id) {
        return service.delete(id);
    }

    @Override
    public ApiResponse<List<SectionDTO>> getSectionsForUser() {
        return service.getSectionsForUser();
    }
}
