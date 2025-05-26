package com.spring.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Staff {
	private int id;
    private String name;
    private String email;
    private String position;
    private int departmentId;
    private String contactNumber;
    private String workDescription;
}
