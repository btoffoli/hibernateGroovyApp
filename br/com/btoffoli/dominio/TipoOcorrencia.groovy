package br.com.btoffoli.dominio

import javax.persistence.*
import org.hibernate.cfg.*

@Entity class TipoOcorrencia {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    Long version = 0

    Boolean habilitado = true
    Date dataHoraCriacao = new Date()
    Date dataHoraAtualizacao = dataHoraCriacao

    String nome
    String sigla

    Boolean georeferenciavel = false
    Boolean transferivel = false

    String toString() { "<${this.class} - ${this.id} - ${this.nome}>" }
}