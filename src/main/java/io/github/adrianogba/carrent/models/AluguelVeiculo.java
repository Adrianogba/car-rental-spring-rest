package io.github.adrianogba.carrent.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name="TB_ALUGUEL")
public class AluguelVeiculo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@JsonIgnore
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false, unique = false)
    @JsonIgnore
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_veiculo", nullable = false, unique = false)
    @JsonIgnore
    private Veiculo veiculo;


    @NotNull
    @JsonFormat(timezone = "America/Recife")
    private Date data;

    @NotNull
    private String status;

    //Para sinalizar se houve um erro ao serviço cliente. Variavel usada apenas na comunicação com outros serviços, portanto não persistida.
    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean error;

    //Para descrever um possível erro. Variavel usada apenas na comunicação com outros serviços, portanto não persistida.
    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String description;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }
}
