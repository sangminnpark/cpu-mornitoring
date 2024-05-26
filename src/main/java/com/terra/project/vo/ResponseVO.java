package com.terra.project.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseVO {
	String result;
	String statusCode;
	String errorMsg;
	Object data;
}
