package pl.ziemowit.contact.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.ziemowit.contact.model.ContactCreateDTO;
import pl.ziemowit.contact.model.ContactResponseDTO;
import pl.ziemowit.contact.model.ContactUpdateDTO;
import pl.ziemowit.contact.service.ContactService;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/contacts")
public class ContactController {

  private final ContactService contactService;

  @PostMapping
  public ContactResponseDTO create(@RequestBody @Valid ContactCreateDTO request) {
    return contactService.create(request);
  }

  @PutMapping
  public ContactResponseDTO update(@RequestBody @Valid ContactUpdateDTO request) {
    return contactService.update(request);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable("id") String id) {
    contactService.delete(id);
  }

  @GetMapping("/{id}")
  public ContactResponseDTO findOne(@PathVariable("id") String id) {
    return contactService.findOne(id)
                         .orElse(null);
  }

  @GetMapping
  public Page<ContactResponseDTO> findMany(@RequestParam("id") @PositiveOrZero int page) {
    return contactService.findMany(PageRequest.of(page, 20, Sort.Direction.ASC, "id"));
  }

}
