package com.gn.spring.board.model.service;

import java.io.File;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService {
	public String upload(MultipartFile file) {
		
		String newFileName = "";
		
		try {
			
				// 1. 파일을 저장
				String fileOriName = file.getOriginalFilename();
				// 파일 자름(.을 기준으로 파일명과 확장자 자르기)
				String fileExtension = fileOriName.substring(fileOriName.lastIndexOf("."),fileOriName.length());
				// 파일 명칭 바꾸기 > UUID(버전4)
				UUID uuid = UUID.randomUUID();
				// 8자리마다 포함되는 하이픈 제거
				String uniqueName = uuid.toString().replaceAll("-", "");
				
				newFileName = uniqueName + fileExtension;
				
				// 파일 저장 경로 설정
				String uploadDir = "C:\\board\\upload";
				
				// 파일 껍데기 생성
				File saveFile = new File(uploadDir+"\\"+uniqueName+fileExtension);
				
				// 껍데기에 파일 정보 복제
				file.transferTo(saveFile);
				
				if(!saveFile.exists()) {
					saveFile.mkdirs();
				}
				
		}catch(Exception e) {
			e.printStackTrace();
		}
				
		return newFileName;
		
	}
}
