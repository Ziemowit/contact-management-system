package pl.ziemowit.contact.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.ziemowit.contact.converter.ContactConverter;
import pl.ziemowit.contact.model.Contact;
import pl.ziemowit.contact.model.ContactCreateDTO;
import pl.ziemowit.contact.model.ContactResponseDTO;
import pl.ziemowit.contact.model.ContactUpdateDTO;
import pl.ziemowit.contact.repository.ContactRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactService {

  private final ContactRepository contactRepo;
  private final ContactConverter contactConverter;

  public ContactResponseDTO create(ContactCreateDTO request) {
    var contact = contactRepo.save(new Contact(request.getEmail(),
                                               request.getFirstName(),
                                               request.getLastName(),
                                               request.getArea()));

    return contactConverter.convert(contact);
  }

  public ContactResponseDTO update(ContactUpdateDTO request) {
    var contactFound = contactRepo.findById(request.getId())
                                  .orElseThrow();

    contactFound.setEmail(request.getEmail());
    contactFound.setFirstName(request.getFirstName());
    contactFound.setLastName(request.getLastName());
    contactFound.setArea(request.getArea());

    var contactUpdated = contactRepo.save(contactFound);

    return contactConverter.convert(contactUpdated);
  }

  public void delete(String id) {
    contactRepo.deleteById(id);
  }

  public Optional<ContactResponseDTO> findOne(String id) {
    return contactRepo.findById(id)
                      .map(contactConverter::convert);
  }

  public Page<ContactResponseDTO> findMany(PageRequest pageRequest) {
    return contactRepo.findAll(pageRequest)
                      .map(contactConverter::convert);
  }

}
