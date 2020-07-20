package org.tain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tain.service.BoardService;
import org.tain.utils.CurrentInfo;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = {"/board"})
@Slf4j
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@CrossOrigin(origins = {"http://localhost:9000"}, maxAge = 3600)
	@GetMapping(value = {"/list"})
	public String listAop(Pageable pageable, Model model) throws Exception {
		log.info("KANG-20200618 >>>>> {}", CurrentInfo.get());
		model.addAttribute("boardList", this.boardService.findBoardListAop(pageable));
		return "board/list";
	}
	
	@CrossOrigin(origins = {"http://localhost:9000"}, maxAge = 3600)
	@GetMapping(value = {""})
	public String boardAop(@RequestParam(value = "id", defaultValue = "0") Long id, Model model) throws Exception {
		log.info("KANG-20200618 >>>>> {}", CurrentInfo.get());
		model.addAttribute("board", this.boardService.findBoardById(id));
		return "board/form";
	}
	
	@GetMapping(value = {"/list/{userId}"})
	public String listByUserIdAop(Pageable pageable, @PathVariable(value = "userId") String userId, Model model) throws Exception {
		log.info("KANG-20200618 >>>>> {}", CurrentInfo.get());
		model.addAttribute("boardList", this.boardService.findBoardListByUserId(pageable, userId));
		return "board/list";
	}
}
