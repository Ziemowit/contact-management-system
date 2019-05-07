package pl.ziemowit.contact.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.ziemowit.contact.model.Contact;
import pl.ziemowit.contact.model.ContactResponseDTO;

@Component
public class ContactConverter implements Converter<Contact, ContactResponseDTO> {

  @Override
  public ContactResponseDTO convert(Contact source) {
    return new ContactResponseDTO(source.getId(),
                                  source.getEmail(),
                                  source.getFirstName(),
                                  source.getLastName(),
                                  source.getArea());
  }

}
