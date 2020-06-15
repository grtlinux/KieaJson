package org.tain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.tain.domain.FieldInfo;

@RepositoryRestResource
public interface FieldInfoRepository extends JpaRepository<FieldInfo, Long>{

}
