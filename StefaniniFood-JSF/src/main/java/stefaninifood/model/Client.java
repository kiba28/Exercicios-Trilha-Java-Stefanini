package stefaninifood.model;

import java.io.Serializable;

import lombok.Getter;

@Getter
public class Client implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String password;

}
