package test.com.busi.kidd.rpc;

import java.io.Serializable;

public class HelloBean implements Serializable {

	private static final long serialVersionUID = -5129938600098972724L;
	private String name;
	private String password;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
