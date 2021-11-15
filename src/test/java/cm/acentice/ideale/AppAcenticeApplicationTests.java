package cm.acentice.ideale;

import cm.acentice.ideale.dto.CommandeDto;
import cm.acentice.ideale.entities.Commande;
import cm.acentice.ideale.interfaces.DataPMapper;
import cm.acentice.ideale.tessData.MockData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class AppAcenticeApplicationTests {

    @InjectMocks
	private DataPMapper dataPMapper = Mappers.getMapper(DataPMapper.class);

    CommandeDto commandeDto = MockData.getCommandeDto();
	   @Test
	    void whenDtoToEntity_thenReturnEntity() {
		Commande commande = dataPMapper.mapFromCmdDtoToCommande(commandeDto);
	    Assertions.assertNotNull(commandeDto);
	    Assertions.assertNotNull(commande);
	   // Assertions.assertEquals(commandeDto.getIdCommande(),commande.getIdCommande());

	}
	@Test
	void when_mapFromCommandeListDto_ToCommandeList_thenReturn_CommandeList(){
       //Given
		List<Commande>commandeList = MockData.getListCommande();
		//When
		List<CommandeDto>commandeDtoListList = dataPMapper.mapFromCommandeListToCommandeDtoList(commandeList);
		//Then
		Assertions.assertEquals(2, commandeDtoListList.size());
		Assertions.assertEquals(commandeDtoListList.get(0).getIdCommande(),commandeList.get(0).getIdCommande());
	}

	@Test
	void when_mapFromCommandeList_ToCommandeListDto_thenReturn_CommandeListDto(){
		//Given
		List<CommandeDto>commandeListDto = MockData.getListCommandeDto();
		//When
		List<Commande>commandes = dataPMapper.mapFromCommandeListDtoToCommandeList(commandeListDto);
		//Then
		Assertions.assertEquals(2, commandeListDto.size());
		Assertions.assertEquals(commandeListDto.get(0).getIdCommande(),commandes.get(0).getIdCommande());
	}
}
