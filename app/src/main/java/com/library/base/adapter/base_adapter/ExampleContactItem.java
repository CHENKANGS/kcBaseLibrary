package com.library.base.adapter.base_adapter;



public class ExampleContactItem implements ContactItemInterface {

	private String nickName;
	private String fullName;
	private boolean isChoice;
	public boolean isChoice() {
		return isChoice;
	}

	public void setChoice(boolean choice) {
		isChoice = choice;
	}


	
	public ExampleContactItem(String nickName, String fullName, boolean isChoice) {
		super();
		this.nickName = nickName;
		this.setFullName(fullName);
		this.isChoice = isChoice;
	}

	// index the list by nickname
	@Override
	public String getItemForIndex() {
		return nickName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

}
