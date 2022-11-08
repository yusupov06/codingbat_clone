package uz.md.leetcode.controller.implementations;

import lombok.NonNull;
import org.springframework.web.bind.annotation.RestController;
import uz.md.leetcode.controller.interfaces.RoleController;
import uz.md.leetcode.payload.auth.RoleCDTO;
import uz.md.leetcode.payload.auth.RoleDTO;
import uz.md.leetcode.response.ApiResponse;
import uz.md.leetcode.service.implementations.RoleServiceImpl;
import uz.md.leetcode.service.interfaces.RoleService;

import java.util.List;



@RestController
public class RoleControllerImpl implements RoleController {

    private final RoleService roleService;

    public RoleControllerImpl(RoleServiceImpl roleService) {
        this.roleService = roleService;
    }

    @Override
    public ApiResponse<RoleDTO> get(@NonNull Integer id) {
        return roleService.getById(id);
    }

    @Override
    public ApiResponse<List<RoleDTO>> getAll() {
        return roleService.getAll();
    }

    @Override
    public ApiResponse<RoleDTO> add(RoleCDTO roleDTO) {
        return roleService.add(roleDTO);
    }

    @Override
    public ApiResponse<Boolean> delete(Integer id) {
        return roleService.delete(id);
    }
}
