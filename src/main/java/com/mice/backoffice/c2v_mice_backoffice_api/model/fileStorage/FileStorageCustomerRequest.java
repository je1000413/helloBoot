package com.mice.backoffice.c2v_mice_backoffice_api.model.fileStorage;


import com.mice.backoffice.c2v_mice_backoffice_api.common.support.AdminSupports;
import lombok.Builder;
import lombok.Data;

/**
 * 로그인 Request Object
 * @author	JiEun Chun
 * @email	je1000@com2us.com
 * @since	2023.02.16
 */
@Data
public class FileStorageCustomerRequest {
	private Integer managementId;
	private String customerName;
	private String rootPath;
	private Integer expireDay;
	private Long maxFileSize;
	private Integer maxDirectoryDepth;
	private String permitFileExtension;


	@Builder
	public FileStorageCustomerRequest(int managementId,
									  String customerName,
									  String rootPath) {
		this.managementId = managementId;
		this.customerName = customerName;
		this.rootPath = rootPath;
		this.expireDay = 3650;
		this.maxFileSize = 100000000000L;
		this.maxDirectoryDepth = 5;
		this.permitFileExtension = "hwp,doc,docx,ppt,pptx,xls,xlsx,txt,csv,jpg,jpeg,gif,png,bmp,pdf";
	}
}

