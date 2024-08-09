package com.gn.spring.board.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gn.spring.board.model.service.BoardService;
import com.gn.spring.board.model.service.UploadFileService;
import com.gn.spring.board.model.vo.Board;

@Controller
public class BoardApiController {
	
	private static final Logger LOGGER 
		= LogManager.getLogger(BoardApiController.class);
	
	@Autowired
	UploadFileService uploadFileService;
	
	@Autowired
	BoardService boardService;   
	
	@ResponseBody
	@PostMapping("/board")
	public Map<String,String> createBoard(Board vo,
			@RequestParam("file") MultipartFile file){
		
		// 3. 결과를 json 형태로 화면에 전달
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "게시글 등록중 오류가 발생하였습니다.");
		
		// 2. 게시글 정보, 파일 정보 DB에 등록
		String savedFileName = uploadFileService.upload(file);
		if(!"".equals(savedFileName)) {
			vo.setOri_thumbnail(file.getOriginalFilename());
			vo.setNew_thumbnail(savedFileName);

			// BoardService 의존성 주입
			int insertList = boardService.insertBoardList(vo);
			// service > dao > mapper 게시글 insert
			
			if(insertList > 0 ) {
				
				resultMap.put("res_code", "200");
				resultMap.put("res_msg", "게시글이 성공적으로 등록되었습니다.");
				
				return resultMap;
			}
		}
		
		
		return resultMap;
	}
	
	@ResponseBody
	@PostMapping("/board/{board_no}")
	public Map<String,String> updateBoard(Board vo,
			@RequestParam("file") MultipartFile file){
		
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "게시글 수정중 오류가 발생하였습니다.");
		
		if(file != null && !"".equals(file.getOriginalFilename())){
			String savedFileName = uploadFileService.upload(file);
			if(savedFileName != null) {
				vo.setOri_thumbnail(file.getOriginalFilename());
				vo.setNew_thumbnail(savedFileName);
			}else {
				resultMap.put("res_msg","파일 업로드중 오류가 발생했습니다.");
			}
		}
			
			if(boardService.updateBoard(vo) > 0 ) {
				
				resultMap.put("res_code", "200");
				resultMap.put("res_msg", "게시글이 성공적으로 수정되었습니다.");
				
				return resultMap;
			
		}
		
		return resultMap;
	}
	
	@ResponseBody
	@DeleteMapping("/board/{board_no}")
    public Map<String, String> deleteBoard(@PathVariable("board_no") int board_no) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("res_code", "404");
        resultMap.put("res_msg", "게시글 삭제 중 오류 발생");
        if(boardService.deleteBoard(board_no) > 0) {
        	resultMap.put("res_code", "200");
        	resultMap.put("res_msg", "게시글 삭제 완료");
        }
        return resultMap;
	}
}