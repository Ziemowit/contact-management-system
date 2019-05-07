package pl.ziemowit.contact.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("contact")
public class Contact extends BaseDocument {

  private String email;
  private String firstName;
  private String lastName;
  private ContactArea area;

}
