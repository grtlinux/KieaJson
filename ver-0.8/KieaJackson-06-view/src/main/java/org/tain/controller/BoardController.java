package org.tain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tain.service.BoardServiceImpl;
import org.tain.utils.CurrentInfo;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = {"/board"})
@Slf4j
public class BoardController {

	@Autowired
	private BoardServiceImpl boardServiceImpl;
	
	@GetMapping({"", "/"})
	public String board(@RequestParam(value="id", defaultValue="0") Long id, Model model) {
		log.info("KANG-20200607 >>>>> " + CurrentInfo.get());
		model.addAttribute("board", this.boardServiceImpl.findBoardById(id));
		return "board/form";
	}
	
	@GetMapping({"/list"})
	//public String list(@PageableDefault(direction=Direction.DESC, sort="id", size=20, page=0) Pageable pageable, @SocialUser User user, Model model) {
	//public String list(@PageableDefault Pageable pageable, @SocialUser User user, Model model) {  // Page request [number: 0, size 10, sort: UNSORTED]
	public String list(Pageable pageable, Model model) {
		log.info("KANG-20200607 >>>>> " + CurrentInfo.get());
		model.addAttribute("boardList", this.boardServiceImpl.findBoardList(pageable));
		return "board/list";
	}
}