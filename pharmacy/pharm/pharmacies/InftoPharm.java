package pharm.pharmacies;

abstract class InftoPharm {
	private static final int MIN_LENGTH_FOR_NAME = 2;
	private static final String MSG_INVALID_NAME = "Invalid name phor pharmacey";
	private String name;

	InftoPharm(String name) throws PharmacyException {
		this.setName(name);
	}

	protected String getName() {
		return name;
	}

	private void setName(String name) throws PharmacyException {
		if (name != null && name.length() > MIN_LENGTH_FOR_NAME) {
			this.name = name;
		} else {
			throw new PharmacyException(MSG_INVALID_NAME);
		}
	}
}
