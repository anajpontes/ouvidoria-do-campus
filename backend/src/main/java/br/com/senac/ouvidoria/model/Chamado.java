package br.com.senac.ouvidoria.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "chamados")
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, columnDefinition = "TEXT")
    private String protocolo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String localizacao;

    @Column(name = "foto_path", columnDefinition = "TEXT")
    private String fotoPath;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusChamado status = StatusChamado.RECEBIDO;

    @Column(name = "tipo_sugerido", columnDefinition = "TEXT")
    private String tipoSugerido;

    @Column(name = "prioridade_sugerida", columnDefinition = "TEXT")
    private String prioridadeSugerida;

    @Column(name = "tipo_revisado", columnDefinition = "TEXT")
    private String tipoRevisado;

    @Column(name = "prioridade_revisada", columnDefinition = "TEXT")
    private String prioridadeRevisada;

    @Column(name = "resposta_equipe", columnDefinition = "TEXT")
    private String respostaEquipe;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public Chamado() {
    }

    @PrePersist
    protected void aoCriar() {
        Instant agora = Instant.now();
        if (status == null) {
            status = StatusChamado.RECEBIDO;
        }
        if (createdAt == null) {
            createdAt = agora;
        }
        updatedAt = agora;
    }

    @PreUpdate
    protected void aoAtualizar() {
        updatedAt = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getFotoPath() {
        return fotoPath;
    }

    public void setFotoPath(String fotoPath) {
        this.fotoPath = fotoPath;
    }

    public StatusChamado getStatus() {
        return status;
    }

    public void setStatus(StatusChamado status) {
        this.status = status;
    }

    public String getTipoSugerido() {
        return tipoSugerido;
    }

    public void setTipoSugerido(String tipoSugerido) {
        this.tipoSugerido = tipoSugerido;
    }

    public String getPrioridadeSugerida() {
        return prioridadeSugerida;
    }

    public void setPrioridadeSugerida(String prioridadeSugerida) {
        this.prioridadeSugerida = prioridadeSugerida;
    }

    public String getTipoRevisado() {
        return tipoRevisado;
    }

    public void setTipoRevisado(String tipoRevisado) {
        this.tipoRevisado = tipoRevisado;
    }

    public String getPrioridadeRevisada() {
        return prioridadeRevisada;
    }

    public void setPrioridadeRevisada(String prioridadeRevisada) {
        this.prioridadeRevisada = prioridadeRevisada;
    }

    public String getRespostaEquipe() {
        return respostaEquipe;
    }

    public void setRespostaEquipe(String respostaEquipe) {
        this.respostaEquipe = respostaEquipe;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
