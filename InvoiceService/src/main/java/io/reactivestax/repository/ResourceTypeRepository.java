package io.reactivestax.repository;

//import com.reactivestax.domain.ResourceTypes;
import io.reactivestax.domain.ResourceTypes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceTypeRepository extends CrudRepository<ResourceTypes,Integer> {


}
