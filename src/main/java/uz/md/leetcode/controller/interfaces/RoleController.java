package uz.md.leetcode.controller.interfaces;

//import io.swagger.annotations.ApiOperation;

import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import org.springframework.web.bind.annotation.*;
import uz.md.leetcode.payload.auth.RoleCDTO;
import uz.md.leetcode.payload.auth.RoleDTO;
import uz.md.leetcode.response.ApiResponse;

import javax.validation.Valid;
import java.util.List;



@RequestMapping(value = RoleController.ROLE_BASE_PATH)
public interface RoleController {

    String ROLE_BASE_PATH = "/api/role";

    @ApiOperation(value = " get by id ")
    @GetMapping( "/get/{id}")
    ApiResponse<RoleDTO> get(@Valid @NonNull  @PathVariable Integer id);

    @ApiOperation(value = " get all roles")
    @GetMapping( "/get/all")
    ApiResponse<List<RoleDTO>> getAll();

    @ApiOperation(value = " Adding a role")
    @PostMapping( "/add")
    ApiResponse<RoleDTO> add(@Valid @RequestBody RoleCDTO roleDTO);

    @ApiOperation(value = " Delete a role ")
    @DeleteMapping("/{id}")
    ApiResponse<Boolean> delete(@PathVariable Integer id);

}
