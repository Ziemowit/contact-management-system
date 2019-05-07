package pl.ziemowit.contact.service

import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import pl.ziemowit.contact.converter.ContactConverter
import pl.ziemowit.contact.model.Contact
import pl.ziemowit.contact.model.ContactArea
import pl.ziemowit.contact.model.ContactCreateDTO
import pl.ziemowit.contact.model.ContactUpdateDTO
import pl.ziemowit.contact.repository.ContactRepository
import spock.lang.Specification

class ContactServiceTest extends Specification {

  def contactRepo = Mock(ContactRepository)
  def contactService = new ContactService(contactRepo, new ContactConverter())

  def '''Create'''() {
    given:
    def request = new ContactCreateDTO('foo@bar.com',
                                       'Foo',
                                       'Bar',
                                       ContactArea.FAMILY)

    when:
    def result = contactService.create(request)

    then:
    1 * contactRepo.save(_ as Contact) >> { Contact it -> it.id = 'contact-id'; it }
    0 * _

    with(result) {
      id == 'contact-id'
      email == request.email
      firstName == request.firstName
      lastName == request.lastName
      area == request.area
    }
  }

  def '''Update'''() {
    given:
    def existingContact = new Contact('foo@bar.com',
                                      'Foo',
                                      'Bar',
                                      ContactArea.FAMILY)
    existingContact.id = 'contact-id'

    def request = new ContactUpdateDTO('contact-id',
                                       'updated-foo@bar.com',
                                       'updated-Foo',
                                       'updated-Bar',
                                       ContactArea.FRIENDS)

    when:
    def result = contactService.update(request)

    then:
    1 * contactRepo.findById('contact-id') >> Optional.of(existingContact)
    1 * contactRepo.save(_ as Contact) >> { Contact it -> it }
    0 * _

    with(result) {
      id == 'contact-id'
      email == request.email
      firstName == request.firstName
      lastName == request.lastName
      area == request.area
    }
  }

  def '''Delete'''() {
    given:
    def id = 'contact-id'

    when:
    contactService.delete(id)

    then:
    1 * contactRepo.deleteById(id)
    0 * _
  }

  def '''FindOne'''() {
    given:
    def id = 'contact-id'
    def existingContact = new Contact('foo@bar.com',
                                      'Foo',
                                      'Bar',
                                      ContactArea.FAMILY)
    existingContact.id = id

    when:
    def result = contactService.findOne(id)

    then:
    1 * contactRepo.findById(id) >> Optional.of(existingContact)
    0 * _

    result.isPresent()
    with(result.get()) {
      id == 'contact-id'
      email == 'foo@bar.com'
      firstName == 'Foo'
      lastName == 'Bar'
      area == ContactArea.FAMILY
    }
  }

  def '''FindMany'''() {
    given:
    def existingContact1 = new Contact('foo1@bar.com',
                                       'Foo1',
                                       'Bar1',
                                       ContactArea.FRIENDS)
    def existingContact2 = new Contact('foo2@bar.com',
                                       'Foo2',
                                       'Bar2',
                                       ContactArea.COLLEAGUES)
    existingContact1.id = 'contact-id-1'
    existingContact2.id = 'contact-id-2'

    def pageRequest = PageRequest.of(0, 10)

    when:
    def result = contactService.findMany(pageRequest)

    then:
    1 * contactRepo.findAll(pageRequest) >> new PageImpl<>([existingContact1, existingContact2])
    0 * _

    result.content.size() == 2
    with(result[0]) {
      id == 'contact-id-1'
      email == 'foo1@bar.com'
      firstName == 'Foo1'
      lastName == 'Bar1'
      area == ContactArea.FRIENDS
    }
    with(result[1]) {
      id == 'contact-id-2'
      email == 'foo2@bar.com'
      firstName == 'Foo2'
      lastName == 'Bar2'
      area == ContactArea.COLLEAGUES
    }
  }

}
