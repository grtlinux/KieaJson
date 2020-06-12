package org.tain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.tain.domain.Test07;

@RepositoryRestResource
public interface Test07Repository extends JpaRepository<Test07, Long>{

}
