package homework5;

public class Course {

	private String code;
	private String name;
	private int credit;
	
	public Course(String code, String name, int credit) {
		super();
		this.code = code;
		this.name = name;
		this.credit = credit;
	}
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}
	
	
	
}
