package com.mice.backoffice.c2v_mice_backoffice_api.common;

/**
 * 공통 상수
 * @author	HyukJun Kwon
 * @email	hyukjk@com2us.com
 * @since	2022.11.01
 */
public interface Constants {
	boolean DEV_MODE = true;

	// 프로퍼티 암호화 정의
	interface Jasypt {
		String JASYPT_ALGORITHM = "PBEWithMD5AndDES";
		int JASYPT_KEY_OBTENTION_ITERATIONS = 1000;
		int JASYPT_POOL_SIZE = 1;
		String JASYPT_STRING_OUTPUT_TYPE = "base64";
	}
	// Swagger 정의
	interface Swagger {
		String BASE_PACKAGE = "com.com2verse.backoffice.controller.v1";
		String API_TITLE = "Com2verse Office BackOffice API";
		String API_DESCRIPTION = "컴투버스 가상오피스 Backoffice API 입니다.";
		String API_VERSION = "v1";
	}
	// API 통신시 사용
	interface Auth {
		String API_TOKEN_ISSUER = "c2v_office_backoffice_api";
		//1시간
		long API_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 365;
		//24시간
		long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24;
		String API_TOKEN_PREFIX = "Bearer ";
		String API_KEY_INSTANCE = "EC";
		String API_TOKEN_CLAIMS = "data";
		String API_TOKEN_CLAIMS_ISSUER = "iss";
		String API_TOKEN_DATA_SECRET_INSTANCE = "AES/CBC/PKCS5Padding";
		String API_TOKEN_DATA_SECRET_ALGORITHM = "AES";
		String API_TOKEN_PRIVATE_KEY = "MGACAQAwEAYHKoZIzj0CAQYFK4EEACMESTBHAgEBBEIAKJFg/vZiZ3BksEO1exLZfPXix2LFhn5JFZaMtZePGAiWRe9lwSy8L3tQ3WFplPrWvADFOl7R/bewpMNtyqSyPXY=";
		String API_TOKEN_PUBLIC_KEY = "MIGbMBAGByqGSM49AgEGBSuBBAAjA4GGAAQBEPd0EQiy2/9mdpfjExi7WAnGZd6Y3sQIGjIZawJw2CH6qJ605mcuxFCgvOHI3k64iN3gKSRm0Z01Mf2MQ70FEKIA8njXRASnT+wOG/QkIYsriR423FQcvus2vIvMbW4o1wG2SckE6U+ecwZX4vQ33169+w7lGrN601PSTPXGR5lsHvk=";
	}

	interface MapKey {
		String COMPANY_CODE = "companyCode";
	}

	interface Parameter {
		int PER_PAGE = 20;
	}

	enum ValidateType {
		SORT,
		DATE_FORMAT;
	}

	enum ResponseCode {
		SUCCESS(200),
		SUCCESS_NO_CONTENT(204),
		FAIL_INVALID_SORT(100),
		FAIL_INVALID_DATE_FORMAT(101),
		FAIL_NOT_FOUND_TARGET(102),
		FAIL_UPLOAD_FILE(103),
		FAIL_TOKEN_EXCEPTION(400),
		FAIL_INVALID_LOGIN_INFORMATION(401),
		FAIL_TOKEN_EXPIRED(402),
		FAIL_ACCESS_DENIED(403),
		FAIL_TOKEN_INVALID(404),
		FAIL_INVALID_TOKEN_SIGNATURE(405),
		FAIL_INTERNAL_SERVER_ERROR(500),
		FAIL_DATABASE_ERROR(501);

		ResponseCode(int value) {
			this.value = value;
		}
		private final int value;
		public int value() {
			return this.value;
		}
	}
}
