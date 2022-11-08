package uz.md.leetcode.service.interfaces;

import uz.md.leetcode.payload.auth.RoleCDTO;
import uz.md.leetcode.payload.auth.RoleDTO;
import uz.md.leetcode.response.ApiResponse;

import java.util.List;


public interface RoleService {

    //    @PreAuthorize("hasAuthority('ADD_ROLE')")
    ApiResponse<RoleDTO> add(RoleCDTO roleDTO);

    //    @PreAuthorize("hasAuthority('DELETE_ROLE')")
    ApiResponse<Boolean> delete(Integer id);

    ApiResponse<RoleDTO> getById(Integer id);

    ApiResponse<List<RoleDTO>> getAll();
}
