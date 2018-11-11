package io.github.adrianogba.carrent.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name="TB_CLIENTE")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@JsonIgnore
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    @NotNull
    private String nome;

    @NotNull
    private String password;

    //Para sinalizar se houve um erro ao serviço cliente. Variavel usada apenas na comunicação com outros serviços, portanto não persistida.
    @Transient
    private Boolean error;

    //Para descrever um possível erro. Variavel usada apenas na comunicação com outros serviços, portanto não persistida.
    @Transient
    private String description;



    public long getId_cliente() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
