package pl.ziemowit.contact.model;

import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
public class ContactCreateDTO {

  @Email
  String email;
  @NotBlank
  String firstName;
  @NotBlank
  String lastName;
  @NotNull
  ContactArea area;

}
