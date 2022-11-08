package uz.md.leetcode.controller.implementations;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import uz.md.leetcode.controller.interfaces.LanguageController;
import uz.md.leetcode.payload.ViewDTO;
import uz.md.leetcode.payload.language.AddLanguageDTO;
import uz.md.leetcode.payload.language.LanguageDTO;
import uz.md.leetcode.projection.LanguageDTOProjection;
import uz.md.leetcode.response.ApiResponse;
import uz.md.leetcode.service.interfaces.LanguageService;

import java.util.List;


@RestController
public class LanguageControllerImpl implements LanguageController {

    private final LanguageService service;

    public LanguageControllerImpl(LanguageService service) {
        this.service = service;
    }

    @Override
    public ApiResponse<LanguageDTO> add(AddLanguageDTO addLanguageDTO) {
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return service.add(addLanguageDTO);
    }

    @Override
    public ApiResponse<List<LanguageDTOProjection>> getLanguages(ViewDTO viewDTO, int page, int size) {
        return service.getLanguagesBySuperMethod(viewDTO, page, size);
    }

    @Override
    public ApiResponse<LanguageDTO> getLanguage(Integer id) {
        return service.findById(id);
    }

    @Override
    public ApiResponse<?> delete(Integer id) {
        return service.deleteById(id);
    }

    @Override
    public ApiResponse<LanguageDTO> edit(AddLanguageDTO addLanguageDTO, Integer id) {
        return service.edit(addLanguageDTO, id);
    }

    @Override
    public ApiResponse<List<LanguageDTO>> getLanguagesForUser() {
        return service.getLanguagesForUser();
    }
}
