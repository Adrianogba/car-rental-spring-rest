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

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
@Api(value = "API Rest das Reservas")
@CrossOrigin(origins = "*")

public class ReservaVeiculoResource {

    @Autowired
    ReservaVeiculoRepository reservaRepository;

    @Autowired
    AluguelVeiculoRepository aluguelRepository;


    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    VeiculoRepository veiculoRepository;

    @GetMapping("/reservas")
    @ApiOperation(value = "Retorna todas as reservas")
    public List<ReservaVeiculo> allReservas(){

        List<ReservaVeiculo> allReservas = reservaRepository.findAll();
        allReservas.sort((o1, o2) -> o2.getData().compareTo(o1.getData()));
        return allReservas;
    }


    @GetMapping("/reservas/{id_cliente}")
    @ApiOperation(value = "Retorna todas as reservas feitas por um cliente em específico")
    public List<ReservaVeiculo> allReservasSameCliente(@PathVariable(value = "id_cliente") long id_cliente){

        List<ReservaVeiculo> allReservas = reservaRepository.findAllByCliente(clienteRepository.findById(id_cliente));
        allReservas.sort((o1, o2) -> o2.getData().compareTo(o1.getData()));
        return allReservas;
    }


    @GetMapping("/reservas/{id_veiculo}")
    @ApiOperation(value = "Retorna todas as reservas de um veiculo em específico")
    public List<ReservaVeiculo> allReservasSameVeiculo(@PathVariable(value = "id_veiculo") long id_veiculo){

        List<ReservaVeiculo> allReservas = reservaRepository.findAllByVeiculo(veiculoRepository.findById(id_veiculo));
        allReservas.sort((o1, o2) -> o2.getData().compareTo(o1.getData()));
        return allReservas;
    }


    @GetMapping("/reservas/{id}")
    @ApiOperation(value = "Retorna os dados de uma reserva")
    public ReservaVeiculo getReserva(@PathVariable(value = "id") long id){
        return reservaRepository.findById(id);
    }


    @PostMapping("/reserva")
    @ApiOperation(value = "Registra uma reserva")
    public ReservaVeiculo saveReserva(@RequestBody ReservaVeiculo reserva, Long id_veiculo, Long id_cliente){
        reserva.setError(false);
        reserva.setDescription(null);

        ReservaVeiculo rv = new ReservaVeiculo();



        try{

            if(!veiculoRepository.findById(id_veiculo).isPresent() && !clienteRepository.findById(id_cliente).isPresent()){

                rv.setError(true);
                rv.setDescription("Veiculo ou Cliente não encontrados.");
                return rv;
            } else if(reserva.getData().after(getDateWithoutTimeOneYearAhead())){

                rv.setError(true);
                rv.setDescription("Só é possível reservar um veículo por no maximo 1 ano.");
                return rv;

            } else if(reserva.getData().before(getDateWithoutTime())){

                rv.setError(true);
                rv.setDescription("Não é possível reservar veículos no passado. Insira uma data válida.");
                return rv;

            } else{

                List<AluguelVeiculo> alugueis = aluguelRepository.findAllByVeiculo(veiculoRepository.findById(id_veiculo).get());
                alugueis.sort((o1, o2) -> o2.getData().compareTo(o1.getData()));

                if(!alugueis.isEmpty()){
                    for (AluguelVeiculo aluguel : alugueis){
                        if(rv.getData().before(getDateWithoutTime())){
                            rv.setError(true);
                            rv.setDescription("O veículo está alugado no momento.");
                            return rv;
                        }
                    }
                }

                reserva.setCliente(clienteRepository.findById(id_cliente).get());
                reserva.setVeiculo(veiculoRepository.findById(id_veiculo).get());

                List<ReservaVeiculo> reservas = reservaRepository.findAllByCliente(clienteRepository.findById(id_cliente).get());
                reservas.sort((o1, o2) -> o2.getData().compareTo(o1.getData()));

                if(!reservas.isEmpty()) {
                    for (ReservaVeiculo res : reservas) {
                        if (res.getVeiculo().getId() == id_veiculo) {
                            if (res.getData().before(getDateWithoutTime())) {
                                return reservaRepository.save(reserva);
                            } else {
                                rv.setError(true);
                                rv.setDescription("Só é possível reservar um veículo por vez .");
                                return rv;
                            }

                        }
                    }

                }
                return reservaRepository.save(reserva);
            }



        }catch (Exception e){
            e.printStackTrace();
            rv.setError(true);
            rv.setDescription("Cliente ou Veículo informados são inválidos");
            return rv;
        }


    }

    //A ideia seria manter um histórico das reservas. Mas para este exemplo vou manter a função de deletar abaixo
    @DeleteMapping("/reserva")
    @ApiOperation(value = "Remove uma reserva")
    public void deleteReserva(@RequestBody ReservaVeiculo reserva){
        reservaRepository.delete(reserva);
    }

    @PutMapping("/reserva")
    @ApiOperation(value = "Atualiza uma reserva")
    public ReservaVeiculo updateReserva(@RequestBody ReservaVeiculo reserva){
        return reservaRepository.save(reserva);
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
