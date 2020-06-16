package org.tain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.tain.domain.Board;
import org.tain.repository.BoardRepository;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BoardServiceImpl {

	@Autowired
	private BoardRepository boardRepository;
	
	public Board findBoardById(Long id) {
		return this.boardRepository.findById(id).orElse(new Board());
	}
	
	public Page<Board> findBoardList(Pageable pageable) {
		log.info("KANG-20200616 >>>>> {}", CurrentInfo.get());
		if (Flag.flag) {
			int pageNumber = pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1;
			int pageSize = pageable.getPageSize();
			Sort sort = pageable.getSort();
			//sort = Sort.by("title");
			//sort = Sort.by("id").descending();
			//sort = Sort.by("title").descending();
			//pageNumber = 0;
			pageSize = 15;
			sort = Sort.by("id").descending().and(Sort.by("title"));
			pageable = PageRequest.of(pageNumber, pageSize, sort);
			log.info("KANG-20200607 >>>>> " + CurrentInfo.get() + ":" + pageable);
		}
		return this.boardRepository.findAll(pageable);
	}
}
