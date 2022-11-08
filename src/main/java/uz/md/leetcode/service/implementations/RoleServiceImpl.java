package uz.md.leetcode.service.implementations;

import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.md.leetcode.domain.Role;
import uz.md.leetcode.payload.auth.RoleCDTO;
import uz.md.leetcode.payload.auth.RoleDTO;
import uz.md.leetcode.exceptions.RestException;
import uz.md.leetcode.mappers.RoleMapper;
import uz.md.leetcode.repository.RoleRepository;
import uz.md.leetcode.response.ApiResponse;
import uz.md.leetcode.service.interfaces.RoleService;

import java.security.MessageDigest;
import java.util.List;

/**
 * Me: muhammadqodir
 * Project: Leetcode/IntelliJ IDEA
 * Date:Wed 28/09/22 16:43
 */

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public ApiResponse<RoleDTO> add(RoleCDTO roleDTO) {
        if (roleRepository.exists(Example.of(new Role(roleDTO.getName()))))
            throw RestException.restThrow("Such role already exists", HttpStatus.CONFLICT);
        Role role = roleMapper.fromCDTO(roleDTO);
        roleRepository.save(role);
        return ApiResponse.successResponse(roleMapper.toDTO(role));
    }

    @Override
    public ApiResponse<Boolean> delete(Integer id) {
        roleRepository.deleteById(id);
        return ApiResponse.successResponse();
    }

    @Override
    public ApiResponse<RoleDTO> getById(Integer id) {
        Role role = roleRepository
                .findById(id)
                .orElseThrow(() -> RestException.restThrow("ROLE NOT FOUND", HttpStatus.NOT_FOUND));

        return ApiResponse.successResponse(roleMapper.toDTO(role));

    }

    @Override
    public ApiResponse<List<RoleDTO>> getAll() {
        return ApiResponse.successResponse(
                roleRepository
                        .findAll()
                        .stream()
                        .map(roleMapper::toDTO)
                        .toList());
    }

}
