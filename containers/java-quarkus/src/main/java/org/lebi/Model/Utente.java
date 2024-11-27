package org.lebi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utente {
    private Long id;
	private String username;
	private String name;
	private String surname;
	private String email;
	private String password;
	private String description;
	private String image;
}
