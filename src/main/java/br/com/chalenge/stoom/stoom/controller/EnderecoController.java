package br.com.chalenge.stoom.stoom.controller;

import br.com.chalenge.stoom.stoom.model.Endereco;
import br.com.chalenge.stoom.stoom.repository.EnderecoRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EnderecoController {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private Environment env;

    @GetMapping("/getAll")
    public ResponseEntity<List<Endereco>> getTodosEnderecos() {

        List<Endereco> lista = new ArrayList<>();

        try {

            enderecoRepository.findAll().forEach(lista::add);

            if (lista.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(lista, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getEndereco/{id}")
    public ResponseEntity<Endereco> getEndereco(@PathVariable("id") int id) {
        Optional<Endereco> enderecoOptional = enderecoRepository.findById(id);

        if (enderecoOptional.isPresent()) {
            return new ResponseEntity<>(enderecoOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Endereco> criar(@RequestBody Endereco endereco) {

        GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyCj0cY2yEvVfYhAaTz3-P2MW-YRKmhz5Uw");
        GeocodingResult[] request = new GeocodingResult[0];

        try {

            request = GeocodingApi.newRequest(context).address(endereco.getStreetName()).await();
            LatLng location = request[0].geometry.location;

            Endereco _endereco = new Endereco();
            _endereco.setStreetName(endereco.getStreetName());
            _endereco.setNumber(endereco.getNumber());
            _endereco.setComplement(endereco.getComplement());
            _endereco.setNeighbourhood(endereco.getNeighbourhood());
            _endereco.setCity(endereco.getCity());
            _endereco.setState(endereco.getState());
            _endereco.setCountry(endereco.getCountry());
            _endereco.setZipcode(endereco.getZipcode());

            if (endereco.getLatitude() == 0) {
                _endereco.setLatitude(location.lat);
            } else {
                _endereco.setLatitude(endereco.getLatitude());
            }

            if (endereco.getLongitude() == 0) {
                _endereco.setLongitude(location.lng);
            } else {
                _endereco.setLongitude(endereco.getLongitude());
            }

            enderecoRepository.save(_endereco);

            return new ResponseEntity<>(_endereco, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Endereco> atualizar(@PathVariable("id") int id, @RequestBody Endereco endereco) {
        Optional<Endereco> enderecoOptional = enderecoRepository.findById(id);

        if (enderecoOptional.isPresent()) {
            Endereco _endereco = enderecoOptional.get();
            _endereco.setStreetName(endereco.getStreetName());
            _endereco.setNumber(endereco.getNumber());
            _endereco.setComplement(endereco.getComplement());
            _endereco.setNeighbourhood(endereco.getNeighbourhood());
            _endereco.setCity(endereco.getCity());
            _endereco.setState(endereco.getState());
            _endereco.setCountry(endereco.getCountry());
            _endereco.setZipcode(endereco.getZipcode());
            _endereco.setLatitude(endereco.getLatitude());
            _endereco.setLongitude(endereco.getLongitude());

            return new ResponseEntity<>(enderecoRepository.save(_endereco), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> apagar(@PathVariable("id") int id) {
        try {
            enderecoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
