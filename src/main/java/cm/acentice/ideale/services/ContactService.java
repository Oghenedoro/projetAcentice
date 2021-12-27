package cm.acentice.ideale.services;

import cm.acentice.ideale.dto.ContactDto;
import cm.acentice.ideale.entities.Contact;
import cm.acentice.ideale.entities.FournisseurMP;
import cm.acentice.ideale.entities.FournisseurPD;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;
import cm.acentice.ideale.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public Contact create(Contact contact){
        return contactRepository.save(contact);
    }

    public List<Contact> getAllContacts(){
        return contactRepository.findAll();
    }

    public Contact getByReference(String ref) throws ResourceNotFoundException {
        Optional<Contact> contact = contactRepository.findById(ref);
        if(!contact.isPresent()){
            throw new ResourceNotFoundException("Contact not found !");
        }
        return contact.get();
    }

    public Contact updateContact(ContactDto contactDto, String id){
      Contact contact = contactRepository.findById(id).get();
        contact.setId(contact.getId());
        contact.setFournisseurMP(contactDto.getFournisseurMP());
        contact.setFournisseurPD(contactDto.getFournisseurPD());
        contact.setAdresse(contactDto.getAdresse());
        contact.setCodePostale(contactDto.getCodePostale());
        contact.setEmail(contactDto.getEmail());
        contact.setFonction(contactDto.getFonction());
        contact.setNom(contactDto.getNom());
        contact.setPrenom(contactDto.getPrenom());
        contact.setPays(contactDto.getPays());
        contact.setQuartier(contactDto.getQuartier());
        contact.setTelephone1(contactDto.getTelephone1());
        contact.setTelephone2(contactDto.getTelephone2());
        contact.setVille(contactDto.getVille());
       return contactRepository.save(contact);
    }

    public boolean deletContact(String ref) throws ResourceNotFoundException {
        Optional<Contact> contact = contactRepository.findById(ref);
        if(!contact.isPresent()){
            throw new ResourceNotFoundException("Contact not found !");
        }
        contactRepository.delete(contact.get());
        return true;
    }

}
