package cm.acentice.ideale.controllers;

import cm.acentice.ideale.dto.ContactDto;
import cm.acentice.ideale.entities.Contact;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;
import cm.acentice.ideale.services.ContactService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/v1")
@RestController
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public List<Contact> getAllContacts(){
        return contactService.getAllContacts();
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.POST)
    public Contact createContact(@RequestBody Contact contact){
        return contactService.create(contact);
    }

    @RequestMapping(value = "/contacts/{id}", method = RequestMethod.PUT)
    public Contact updateContact(@RequestBody ContactDto ContactDto, @PathVariable String id){
        return contactService.updateContact(ContactDto,id);
    }

    @RequestMapping(value = "/contacts/{ref}", method = RequestMethod.DELETE)
    public boolean deletContact(@PathVariable String ref) throws ResourceNotFoundException {
        return contactService.deletContact(ref);
    }
    @RequestMapping(value = "/contacts/{ref}", method = RequestMethod.GET)
    public Contact getByReference(@PathVariable String ref) throws ResourceNotFoundException {
        return contactService.getByReference(ref);
    }
}
