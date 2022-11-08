package uz.md.leetcode.service.interfaces;

import uz.md.leetcode.payload.ViewDTO;
import uz.md.leetcode.payload.language.AddLanguageDTO;
import uz.md.leetcode.payload.language.LanguageDTO;
import uz.md.leetcode.projection.LanguageDTOProjection;
import uz.md.leetcode.response.ApiResponse;

import java.util.List;



public interface LanguageService {

    ApiResponse<LanguageDTO> add(AddLanguageDTO addLanguageDTO);

    ApiResponse<LanguageDTO> findById(Integer id);

    ApiResponse<Boolean> deleteById(Integer id);

    ApiResponse<LanguageDTO> edit(AddLanguageDTO languageDTO, Integer id);

    ApiResponse<List<LanguageDTO>> getLanguagesForUser();

    ApiResponse<List<LanguageDTOProjection>> getLanguagesBySuperMethod(ViewDTO viewDTO, int page, int size);
}
