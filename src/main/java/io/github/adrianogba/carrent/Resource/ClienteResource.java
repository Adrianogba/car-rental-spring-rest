package io.github.adrianogba.carrent.Resource;

import io.github.adrianogba.carrent.models.Cliente;
import io.github.adrianogba.carrent.repository.ClienteRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@Api(value = "API Rest Clientes")
@CrossOrigin(origins = "*")
public class ClienteResource {

    @Autowired
    ClienteRepository clienteRepository;

    @GetMapping("/clientes")
    @ApiOperation(value = "Retorna todos os clientes no sistema")
    public List<Cliente> allClientes(){
        return clienteRepository.findAll();
    }


    @GetMapping("/cliente/{id}")
    @ApiOperation(value = "Retorna um cliente")
    public Cliente getCliente(@PathVariable(value = "id") long id){
        return clienteRepository.findById(id);
    }


    @PostMapping("/cliente")
    @ApiOperation(value = "Salva um cliente")
    public Cliente saveCliente(@RequestBody Cliente cliente, String password_confirm){
        cliente.setError(false);
        cliente.setDescription(null);
        if (!cliente.getPassword().equals(password_confirm)){
            Cliente c = new Cliente();
            c.setError(true);
            c.setDescription("Senhas não conferem.");
            return c;
        } else {
            return clienteRepository.save(cliente);
        }


    }

    @DeleteMapping("/cliente")
    @ApiOperation(value = "Remove um cliente")
    public void deleteCliente(@RequestBody Cliente cliente){
        clienteRepository.delete(cliente);
    }

    @PutMapping("/cliente")
    @ApiOperation(value = "Atualiza um cliente")
    public Cliente updateCliente(@RequestBody Cliente cliente){
        return clienteRepository.save(cliente);
    }
    

}
