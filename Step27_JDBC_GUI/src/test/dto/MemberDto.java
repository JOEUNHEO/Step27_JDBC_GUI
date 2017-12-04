package test.dto;

public class MemberDto {
	private int num;
	private String name;
	private String addr;
	//default 생성자를 만들 때, 오른쪽 마우스 버튼 클릭 -> source -> generate Constructor from superclass 클릭!
	public MemberDto() {
		
	}
	
	//멤버필드를 인수로 받는 생성자를 만들 때, 오른쪽 마우스 버튼 클릭 -> source -> generate Constructor from using Fields 클릭!
	public MemberDto(int num, String name, String addr) {
		super();
		this.num = num;
		this.name = name;
		this.addr = addr;
	}
	
	//오른쪽 마우스 버튼 클릭 -> source -> generate Getters and Setters 클릭! 
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}
}
