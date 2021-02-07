package br.com.chalenge.stoom.stoom;

import br.com.chalenge.stoom.stoom.controller.EnderecoController;
import br.com.chalenge.stoom.stoom.model.Endereco;
import br.com.chalenge.stoom.stoom.repository.EnderecoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.ArgumentMatchers.any;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StoomApplicationTests {

	private static final ObjectMapper om = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private EnderecoRepository enderecoRepository;

	private int idEndereco = 18;

	@Test
	public void find_ID_endereco_OK() throws Exception{
		this.mockMvc.perform(get("/api/getEndereco/"+idEndereco))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.streetName", is("Teste")))
				.andExpect(jsonPath("$.neighbourhood", is("B")));
	}

	@Test
	public void save_address_OK() throws Exception {

		Endereco _endereco = new Endereco();
		_endereco.setStreetName("Teste");
		_endereco.setNumber("1");
		_endereco.setComplement("C");
		_endereco.setNeighbourhood("B");
		_endereco.setCity("S");
		_endereco.setState("P");
		_endereco.setCountry("BR");
		_endereco.setZipcode("658");

		mockMvc.perform(post("/api/create")
				.content(om.writeValueAsString(_endereco))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}

	@Test
	public void update_address_OK() throws Exception {

		Endereco _endereco = new Endereco();
		_endereco.setStreetName("Teste2");
		_endereco.setNumber("1");
		_endereco.setComplement("C");
		_endereco.setNeighbourhood("B");
		_endereco.setCity("S");
		_endereco.setState("P");
		_endereco.setCountry("BR");
		_endereco.setZipcode("658");

		mockMvc.perform(put("/api/update/"+idEndereco)
				.content(om.writeValueAsString(_endereco))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void delete_address_OK()  throws Exception {
		mockMvc.perform(delete("/api/delete/"+idEndereco))
				.andExpect(status().isNoContent());
	}


}
