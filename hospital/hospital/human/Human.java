package hospital.human;

public abstract class Human {
	private String name;
	private String phone;
	
	 Human(String name,String phone){
		this.name = name;
		this.phone = phone;
	}
	 
	 public String getName() {
		 return this.name;
	 }
	 
	 public String getPhone() {
		 return this.phone;
	 }
}
