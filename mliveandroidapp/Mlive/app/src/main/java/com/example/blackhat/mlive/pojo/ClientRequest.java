
package com.example.blackhat.mlive.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class ClientRequest  {
	
	private Integer userId;
	private Integer profileId;
	
	private Boolean isFirstTime;
	private Integer rawSizeperPage;
	private Integer start;
	private Integer end;
	private Integer pageNo;
	private Object data;
	
	private Boolean wantToStop;
	private Boolean wantToStart;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getProfileId() {
		return profileId;
	}

	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}

	public Boolean getIsFirstTime() {
		return isFirstTime;
	}

	public void setIsFirstTime(Boolean isFirstTime) {
		this.isFirstTime = isFirstTime;
	}

	public Boolean getWantToStop() {
		return wantToStop;
	}

	public void setWantToStop(Boolean wantToStop) {
		this.wantToStop = wantToStop;
	}

	public Integer getRawSizeperPage() {
		return rawSizeperPage;
	}

	public void setRawSizeperPage(Integer rawSizeperPage) {
		this.rawSizeperPage = rawSizeperPage;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Boolean getWantToStart() {
		return wantToStart;
	}

	public void setWantToStart(Boolean wantToStart) {
		this.wantToStart = wantToStart;
	}
	
	
	
}
