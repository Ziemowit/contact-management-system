package pl.ziemowit.contact.model;

import lombok.Value;

@Value
public class ContactResponseDTO {

  String id;
  String email;
  String firstName;
  String lastName;
  ContactArea area;

}
