package uz.md.leetcode.controller.interfaces;

import org.springframework.web.bind.annotation.*;
import uz.md.leetcode.config.security.RestConstants;
import uz.md.leetcode.payload.ViewDTO;
import uz.md.leetcode.payload.language.AddLanguageDTO;
import uz.md.leetcode.payload.language.LanguageDTO;
import uz.md.leetcode.projection.LanguageDTOProjection;
import uz.md.leetcode.response.ApiResponse;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;




@RequestMapping(LanguageController.BASE_PATH)
public interface LanguageController {

    String BASE_PATH = "/api/language";
    String ADD_PATH = "/add";
    String LIST_FOR_USERS_PATH = "/list-for-users";
    String LANGUAGE_GET_PATH = "/{id}";
    String LANGUAGE_DELETE_PATH = "delete/{id}";
    String LANGUAGE_UPDATE_PATH = "update/{id}";

    @PostMapping(path = ADD_PATH)
    ApiResponse<LanguageDTO> add(@Valid @RequestBody AddLanguageDTO addLanguageDTO);


    @PostMapping("/list")
    ApiResponse<List<LanguageDTOProjection>> getLanguages(@RequestBody(required = false) ViewDTO viewDTO,
                                                        @RequestParam(defaultValue = RestConstants.DEFAULT_PAGE_NUMBER) int page,
                                                        @RequestParam(defaultValue = RestConstants.DEFAULT_PAGE_SIZE) int size);

    @GetMapping(LANGUAGE_GET_PATH)
    ApiResponse<LanguageDTO> getLanguage(@PathVariable
                                       @NotNull(message = "Id must be not null") Integer id);

    @DeleteMapping(LANGUAGE_DELETE_PATH)
    ApiResponse<?> delete(@PathVariable @NotNull Integer id);


    @PutMapping(LANGUAGE_UPDATE_PATH)
    ApiResponse<LanguageDTO> edit(@Valid @RequestBody AddLanguageDTO addLanguageDTO,
                                @PathVariable Integer id);


    @GetMapping(LIST_FOR_USERS_PATH)
    ApiResponse<List<LanguageDTO>> getLanguagesForUser();

}
