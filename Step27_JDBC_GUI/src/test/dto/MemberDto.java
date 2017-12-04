package test.dto;

public class MemberDto {
	private int num;
	private String name;
	private String addr;
	//default �����ڸ� ���� ��, ������ ���콺 ��ư Ŭ�� -> source -> generate Constructor from superclass Ŭ��!
	public MemberDto() {
		
	}
	
	//����ʵ带 �μ��� �޴� �����ڸ� ���� ��, ������ ���콺 ��ư Ŭ�� -> source -> generate Constructor from using Fields Ŭ��!
	public MemberDto(int num, String name, String addr) {
		super();
		this.num = num;
		this.name = name;
		this.addr = addr;
	}
	
	//������ ���콺 ��ư Ŭ�� -> source -> generate Getters and Setters Ŭ��! 
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
