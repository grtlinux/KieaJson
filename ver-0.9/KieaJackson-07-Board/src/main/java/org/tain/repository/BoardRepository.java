package org.tain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.tain.domain.Board;

@RepositoryRestResource
public interface BoardRepository extends JpaRepository<Board, Long>{

	Page<Board> findBoardListByUserId(Pageable pageable, String userId);
}
