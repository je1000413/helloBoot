package com.mice.backoffice.c2v_mice_backoffice_api.model.fileStorage;


import lombok.Builder;
import lombok.Data;

/**
 * 로그인 Request Object
 * @author	JiEun Chun
 * @email	je1000@com2us.com
 * @since	2023.02.16
 */
@Data
public class FileStorageUploadRequest {
	private String directoryPath;
	private String fileName;
	private long size;
	private String md5;
	private Integer expireDays;
	private Integer accessType;


	@Builder
	public FileStorageUploadRequest(String directoryPath,
									String fileName,
									long size,
									String md5,
									Integer expireDays,
									Integer accessType) {
		this.directoryPath = directoryPath;
		this.fileName = fileName;
		this.size = size;
		this.md5 = md5;
		this.expireDays = expireDays;
		this.accessType = accessType;
	}
}

