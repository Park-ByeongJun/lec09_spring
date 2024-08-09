package com.gn.spring.board.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gn.spring.board.model.service.BoardService;
import com.gn.spring.board.model.vo.Board;
@Controller
public class BoardViewController {
	
	@Autowired
	BoardService boardService;
	
	private static final Logger LOGGER = LogManager.getLogger(BoardViewController.class);
	
	
	// 화면단에 데이터 보내는 방법
	// 1. ModelAndView 
//	@GetMapping("/board")
//	public ModelAndView selectBoardList() {
//		List<Board> resultList = boardService.selectBoardList();
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("resultList",resultList);
//		mav.setViewName("/board/list");
//		return mav;
//	}
	
	
	@GetMapping("/board")
	public String selectBoardList(Model model, Board option) {
		
		option.setTotalData(boardService.selectBoardCount(option));
		
		List<Board> resultList = boardService.selectBoardList(option);
		
		// 2. Model (매개변수 설정!!)
		model.addAttribute("resultList", resultList);
		model.addAttribute("paging",option);
		
		// /WEB-INF/views/board/list.jsp
		return "/board/list";
	}
	
	@GetMapping("/board/create")
	public String createBoardPage() {
		// /WEB-INF/views/board/create.jsp
		return "/board/create";
	}
	
	@GetMapping("/board/{board_no}")
	public String selectBoardOne(@PathVariable("board_no") int board_no, Model model) {

	    Board board = boardService.detailBoard(board_no);
	    model.addAttribute("board", board);
	    
	    // WEB-INF/views/board/detail.jsp로 이동
	    return "/board/detail";
	}
	
	@GetMapping("/board/update/{board_no}")
	public String updateBoard(@PathVariable("board_no") int board_no , Model model) {
		Board board = boardService.detailBoard(board_no);
		model.addAttribute("board",board);
		return "/board/update";
	}
	
}