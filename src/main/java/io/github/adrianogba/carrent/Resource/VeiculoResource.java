package io.github.adrianogba.carrent.Resource;

import io.github.adrianogba.carrent.models.Veiculo;
import io.github.adrianogba.carrent.repository.VeiculoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api")
@Api(value = "API Rest Veiculos")
@CrossOrigin(origins = "*")
public class VeiculoResource {

    @Autowired
    VeiculoRepository veiculoRepository;

    @GetMapping("/veiculos")
    @ApiOperation(value = "Retorna todos os veículos do sistema")
    public List<Veiculo> allVeiculos(){
        return veiculoRepository.findAll();
    }


    @GetMapping("/veiculo/{id}")
    @ApiOperation(value = "Retorna um veiculo")
    public Veiculo getVeiculo(@PathVariable(value = "id") long id){
        return veiculoRepository.findById(id);
    }


    @PostMapping("/veiculo")
    @ApiOperation(value = "Salva um veiculo")
    public Veiculo saveVeiculo(@RequestBody Veiculo veiculo){
        veiculo.setError(false);
        veiculo.setDescription(null);

        //Apenas aceita placas no formato ABC1234
        if(!veiculo.getPlaca().toUpperCase().matches("[A-Z]{3}\\d{4}")){
           Veiculo v = new Veiculo();
           v.setError(true);
           v.setDescription("Placa invalida");
           return  v;

        }
        return veiculoRepository.save(veiculo);
    }

    @DeleteMapping("/veiculo")
    @ApiOperation(value = "Remove um veiculo")
    public void deleteVeiculo(@RequestBody Veiculo veiculo){
        veiculoRepository.delete(veiculo);
    }

    @PutMapping("/veiculo")
    @ApiOperation(value = "Atualiza um veiculo")
    public Veiculo updateVeiculo(@RequestBody Veiculo veiculo){
        return veiculoRepository.save(veiculo);
    }


}
