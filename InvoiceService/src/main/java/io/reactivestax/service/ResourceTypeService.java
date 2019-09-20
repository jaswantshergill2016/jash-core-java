package io.reactivestax.service;

//import com.reactivestax.domain.ResourceTypes;
import io.reactivestax.domain.ResourceTypes;
import io.reactivestax.repository.ResourceTypeRepository;
import io.reactivestax.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResourceTypeService {
    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private ResourceTypeRepository resourceTypeRepository;


    public void createResourceType(ResourceTypes resourceTypes) {

        resourceTypeRepository.save(resourceTypes);
    }

    public ResourceTypes getResourceType(String resourceTypeId) {
        Optional<ResourceTypes> resourceTypes =  resourceTypeRepository.findById(Integer.parseInt(resourceTypeId));
        return resourceTypes.get();
    }

    public void updateResourceType(ResourceTypes resourceTypes, String resourceTypesId) {

        Optional<ResourceTypes> resourceTypesRetrieved =  resourceTypeRepository.findById(Integer.parseInt(resourceTypesId));

        if(resourceTypesRetrieved.isPresent()){
            resourceTypes.setResourceTypeId(resourceTypesRetrieved.get().getResourceTypeId());
            resourceTypesRetrieved.get().setResourceName(resourceTypes.getResourceName());
            resourceTypesRetrieved.get().setResourceNickName(resourceTypes.getResourceNickName());
            resourceTypeRepository.save(resourceTypesRetrieved.get());
        }
    }

    public void deleteResourceType(String resourceTypeId) {
        resourceTypeRepository.deleteById(Integer.parseInt(resourceTypeId));
    }
}


