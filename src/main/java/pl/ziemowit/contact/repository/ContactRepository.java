package pl.ziemowit.contact.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.ziemowit.contact.model.Contact;

public interface ContactRepository extends MongoRepository<Contact, String> {
}
