package uz.md.leetcode.service;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.md.leetcode.mappers.MapperMarker;

/**
 * Me: muhammadqodir
 * Project: CodingCompiler/IntelliJ IDEA
 * Date:Wed 14/09/22 11:17
 */
public abstract class AbstractService<
        R extends JpaRepository,
        M extends MapperMarker
        > {

    protected final R repository;
    protected final M mapper;

    public AbstractService(R repository, M mapper){
        this.repository = repository;
        this.mapper = mapper;
    }

}
