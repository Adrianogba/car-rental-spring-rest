package io.github.adrianogba.carrent.Resource;


import io.github.adrianogba.carrent.models.AluguelVeiculo;
import io.github.adrianogba.carrent.models.ReservaVeiculo;
import io.github.adrianogba.carrent.repository.AluguelVeiculoRepository;
import io.github.adrianogba.carrent.repository.ClienteRepository;
import io.github.adrianogba.carrent.repository.ReservaVeiculoRepository;
import io.github.adrianogba.carrent.repository.VeiculoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
@Api(value = "API Rest dos Aluguéis")
@CrossOrigin(origins = "*")
public class AluguelVeiculoResource {

    @Autowired
    AluguelVeiculoRepository aluguelRepository;

    @Autowired
    ReservaVeiculoRepository reservaRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    VeiculoRepository veiculoRepository;

    @GetMapping("/alugueis")
    @ApiOperation(value = "Retorna todos os aluguéis")
    public List<AluguelVeiculo> allAlugueis(){

        List<AluguelVeiculo> allAlugueis = aluguelRepository.findAll();
        allAlugueis.sort((o1, o2) -> o2.getData().compareTo(o1.getData()));
        return allAlugueis;
    }


    @GetMapping("/alugueis/{id_cliente}")
    @ApiOperation(value = "Retorna todos os aluguéis feitos por um cliente em específico")
    public List<AluguelVeiculo> allAlugueisSameCliente(@PathVariable(value = "id_cliente") long id_cliente){

        List<AluguelVeiculo> allAlugueis = aluguelRepository.findAllByCliente(clienteRepository.findById(id_cliente));
        allAlugueis.sort((o1, o2) -> o2.getData().compareTo(o1.getData()));
        return allAlugueis;
    }


    @GetMapping("/alugueis/{id_veiculo}")
    @ApiOperation(value = "Retorna todos os aluguéis de um veiculo em específico")
    public List<AluguelVeiculo> allAlugueisSameVeiculo(@PathVariable(value = "id_veiculo") long id_veiculo){

        List<AluguelVeiculo> allAlugueis = aluguelRepository.findAllByVeiculo(veiculoRepository.findById(id_veiculo));
        allAlugueis.sort((o1, o2) -> o2.getData().compareTo(o1.getData()));
        return allAlugueis;
    }


    @GetMapping("/aluguel/{id}")
    @ApiOperation(value = "Retorna os dados de um aluguel")
    public AluguelVeiculo getAluguel(@PathVariable(value = "id") long id){
        return aluguelRepository.findById(id);
    }


    @PostMapping("/aluguel")
    @ApiOperation(value = "Registra um aluguel")
    public AluguelVeiculo saveAluguel(@RequestBody AluguelVeiculo aluguel, Long id_veiculo, Long id_cliente){
        aluguel.setError(false);
        aluguel.setDescription(null);

        AluguelVeiculo av = new AluguelVeiculo();

        try{

            if(!veiculoRepository.findById(id_veiculo).isPresent() && !clienteRepository.findById(id_cliente).isPresent()){

                av.setError(true);
                av.setDescription("Veiculo ou Cliente não encontrados.");
                return av;


            } else if(aluguel.getData().after(getDateWithoutTimeOneYearAhead())){

                av.setError(true);
                av.setDescription("Só é possível alugar um veículo por no maximo 1 ano.");
                return av;

            } else if(aluguel.getData().before(getDateWithoutTime())){

                av.setError(true);
                av.setDescription("Não é possível alugar veículos no passado. Insira uma data válida.");
                return av;

            } else{



                List<ReservaVeiculo> reservas = reservaRepository.findAllByVeiculo(veiculoRepository.findById(id_veiculo).get());
                reservas.sort((o1, o2) -> o2.getData().compareTo(o1.getData()));

                if(!reservas.isEmpty()){
                    for (ReservaVeiculo rv : reservas){
                        if(rv.getData().before(getDateWithoutTime())){
                            av.setError(true);
                            av.setDescription("O veículo já foi reservado.");
                            return av;
                        }
                    }
                }

                aluguel.setCliente(clienteRepository.findById(id_cliente).get());
                aluguel.setVeiculo(veiculoRepository.findById(id_veiculo).get());

                List<AluguelVeiculo> alugueis = aluguelRepository.findAllByCliente(clienteRepository.findById(id_cliente).get());
                alugueis.sort((o1, o2) -> o2.getData().compareTo(o1.getData()));

                if(!alugueis.isEmpty()) {
                    for (AluguelVeiculo al : alugueis) {
                        if (al.getVeiculo().getId() == id_veiculo) {
                            if (al.getData().before(getDateWithoutTime())) {
                                return aluguelRepository.save(aluguel);
                            } else {
                                av.setError(true);
                                av.setDescription("Só é possível alugar um veículo por vez .");
                                return av;
                            }

                        }
                    }

                }
                return aluguelRepository.save(aluguel);
            }



        }catch (Exception e){
            e.printStackTrace();
            av.setError(true);
            av.setDescription("Cliente ou Veículo informados são inválidos");
            return av;
        }


    }

    //A ideia seria manter um histórico dos aluguéis. Mas para este exemplo vou manter a função de deletar abaixo
    @DeleteMapping("/aluguel")
    @ApiOperation(value = "Remove um aluguel")
    public void deleteAluguel(@RequestBody AluguelVeiculo aluguel){
        aluguelRepository.delete(aluguel);
    }

    @PutMapping("/aluguel")
    @ApiOperation(value = "Atualiza um aluguel")
    public AluguelVeiculo updateAluguel(@RequestBody AluguelVeiculo aluguel){
        return aluguelRepository.save(aluguel);
    }



    public Date getDateWithoutTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public Date getDateWithoutTimeOneYearAhead() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.YEAR, 1);

        return calendar.getTime();
    }


    public Date getYesterdayWithoutTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }


    public Date getTomorrowWithoutTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }



}
