package uz.md.leetcode.service.interfaces;

import uz.md.leetcode.domain.EntityMarker;

import java.io.Serializable;
import java.util.List;

/**
 * Me: muhammadqodir
 * Project: CodingCompiler/IntelliJ IDEA
 * Date:Wed 14/09/22 11:03
 */
public interface GenericService<
        E extends EntityMarker,
        D ,
        CD ,
        UD ,
        ID extends Serializable
        > {

    E findEntityById(ID id);

    List<D> findAll();

    void deleteById(ID id);

    void update(UD ud);

    D add(CD cd);

}
