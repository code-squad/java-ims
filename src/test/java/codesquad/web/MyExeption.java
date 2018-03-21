package codesquad.web;

import java.util.function.Supplier;

public class MyExeption extends Exception implements Supplier<Long>{
	private String message;

	public MyExeption() {
	}

	public MyExeption(String message) {
		super();
		this.message = message;
	}

	@Override
	public Long get() {
		return 0L;
	}
	
	public String getMessage() {
		return message;
	}

}
