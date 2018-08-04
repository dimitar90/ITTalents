package hospital.hospital;

public class Cardboard {
	private int age;
	private String name;
	private String phone;
	private String docName;
	
	Cardboard(int age,String name,String phone){
		this.age = age;
		this.name = name;
		this.phone = phone;
	}

	public static Cardboard createCardboard(int age, String name, String phone) {
		return new Cardboard(age, name, phone);
	}
	
	public void setDoctorName(String docName) {
		if (docName != null) {
			this.docName = docName;
		}
	}
}
