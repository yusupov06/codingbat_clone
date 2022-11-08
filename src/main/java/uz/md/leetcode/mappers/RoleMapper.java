package uz.md.leetcode.mappers;

import org.mapstruct.Mapper;
import uz.md.leetcode.domain.Role;
import uz.md.leetcode.payload.auth.RoleCDTO;
import uz.md.leetcode.payload.auth.RoleUDTO;
import uz.md.leetcode.payload.auth.RoleDTO;



@Mapper(componentModel = "spring")
public interface RoleMapper extends EntityMapper<
        Role,
        RoleDTO,
        RoleCDTO,
        RoleUDTO
        > {
}
