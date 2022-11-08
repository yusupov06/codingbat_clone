package uz.md.leetcode.controller.interfaces;

import io.swagger.annotations.ApiOperation;

import lombok.NonNull;
import org.springframework.web.bind.annotation.*;
import uz.md.leetcode.config.security.RestConstants;
import uz.md.leetcode.payload.ViewDTO;
import uz.md.leetcode.payload.section.SectionCDTO;
import uz.md.leetcode.payload.section.SectionDTO;
import uz.md.leetcode.payload.section.SectionUDTO;
import uz.md.leetcode.projection.SectionDTOProjection;
import uz.md.leetcode.response.ApiResponse;

import javax.validation.Valid;
import java.util.List;


@RequestMapping(value = SectionController.BASE_PATH)
public interface SectionController {

    String BASE_PATH = "/api/v1/section";

        @ApiOperation(value = " getting all sections ")
    @PostMapping(path = "/list")
    ApiResponse<List<SectionDTOProjection>> getSections(@RequestBody(required = false) ViewDTO viewDTO,
                                                      @RequestParam(defaultValue = RestConstants.DEFAULT_PAGE_NUMBER) int page,
                                                      @RequestParam(defaultValue = RestConstants.DEFAULT_PAGE_SIZE) int size);

        @ApiOperation(value = " adding a section ")
    @PostMapping( "/add")
    ApiResponse<SectionDTO> add(@Valid @RequestBody SectionCDTO sectionCDTO);

        @ApiOperation(value = " Updating a section ")
    @PostMapping( "/update")
    ApiResponse<SectionDTO> update(@Valid @RequestBody SectionUDTO sectionUDTO);

        @ApiOperation(value = " get one by id ")
    @GetMapping( "/get/{id}")
    ApiResponse<SectionDTO> get(@Valid @NonNull @PathVariable Integer id);

        @ApiOperation(value = " Delete by id")
    @GetMapping( "/delete/{id}")
    ApiResponse<Void> delete(@Valid @NonNull @PathVariable Integer id);

    @GetMapping( "/me/all")
    ApiResponse<List<SectionDTO>> getSectionsForUser();

}
