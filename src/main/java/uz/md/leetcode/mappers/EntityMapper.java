package uz.md.leetcode.mappers;

import org.mapstruct.MappingTarget;

import java.util.List;


public interface EntityMapper<E, D, CD, UD> {

    E fromDTO(D d);
    E fromCDTO(CD cd);
    E fromUDTO(UD ud, @MappingTarget E e);
    D toDTO(E e);
    List<D> toDTO(List<E> list);
    List<E> fromDTO(List<D> list);

}
