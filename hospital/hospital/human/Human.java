package hospital.human;

public abstract class Human {
	private String name;
	private String phone;
	
	 Human(String name,String phone) throws HumanException{
		this.name = this.validateName(name);
		this.phone = this.validatePhone(phone);
	}
	 
	 private String validatePhone(String phone) throws HumanException {
		 if (phone.startsWith("08") && phone.length() == 10) {
			return phone;
		}
		 
		throw new HumanException("Invalid phone credentials");
	}

	private String validateName(String name) throws HumanException {
		 if (name != null && name.length() >= 2) {
			return name;
		}
		 
		throw new HumanException("Invalid name credentials");
	}

	public String getName() {
		 return this.name;
	 }
	 
	 public String getPhone() {
		 return this.phone;
	 }
}
