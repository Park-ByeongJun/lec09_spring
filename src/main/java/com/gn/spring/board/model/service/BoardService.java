package com.gn.spring.board.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gn.spring.board.model.dao.BoardDao;
import com.gn.spring.board.model.vo.Board;

@Service
public class BoardService {
	
	@Autowired
	BoardDao boardDao;
	
	public int selectBoardCount(Board option) {
		int result = 0;
		try {
			result = boardDao.selectBoardCount(option);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Board detailBoard(int board_no) {
	    Board result = null;
	    try {
	        result = boardDao.detailBoard(board_no);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}
	
	public List<Board> selectBoardList(Board option){
		List<Board> resultList = new ArrayList<Board>();
		try {
			resultList = boardDao.selectBoardList(option);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}
	
	public int insertBoardList(Board vo){
		int result = 0;
		try {
			result = boardDao.insertBoardList(vo);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int updateBoard(Board vo) {
		int result = 0;
		try {
			result = boardDao.updateBoard(vo);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int deleteBoard(int board_no ) {
		int result = 0 ; 
		try {
			result = boardDao.deleteBoard(board_no);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}