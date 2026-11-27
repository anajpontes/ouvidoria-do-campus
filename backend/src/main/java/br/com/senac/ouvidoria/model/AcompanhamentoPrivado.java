package br.com.senac.ouvidoria.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Table(name = "acompanhamento_privado")
public class AcompanhamentoPrivado {

    @Id
    @Column(name = "chamado_id")
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "chamado_id", nullable = false)
    private Chamado chamado;

    @NotBlank
    @Column(name = "email_cifrado", nullable = false, columnDefinition = "TEXT")
    private String emailCifrado;

    public AcompanhamentoPrivado() {
    }

    public UUID getId() {
        return id;
    }

    public Chamado getChamado() {
        return chamado;
    }

    public void setChamado(Chamado chamado) {
        this.chamado = chamado;
    }

    public String getEmailCifrado() {
        return emailCifrado;
    }

    public void setEmailCifrado(String emailCifrado) {
        this.emailCifrado = emailCifrado;
    }
}
