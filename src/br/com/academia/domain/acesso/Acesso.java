package br.com.academia.domain.acesso;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import br.com.academia.domain.aluno.Aluno;

@Entity
@Table(name = "acessos")
public class Acesso implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "aluno_id", nullable = false)
	private Aluno aluno;
	
	@Column(name = "entrada", nullable = false)
	private LocalDateTime entrada;
	
	@Column(name = "saida", nullable = true)
	private LocalDateTime saida;
	
	// se a sa�da estiver vazia, o aluno ainda est� na academia
	public boolean isEntradaSaidaPreenchidas() {
		if(entrada != null && saida != null) {
			return true;
		}
		
		return false;
	}
	
	public TipoAcesso registrarAcesso() {
		LocalDateTime agora = LocalDateTime.now();
		TipoAcesso tipoAcesso;
		
		if(entrada == null) {
			entrada = agora;
			tipoAcesso = TipoAcesso.Entrada;
		} else if(saida == null) {
			saida = agora;
			tipoAcesso = TipoAcesso.Saida;
		} else {
			tipoAcesso = null;
		}
		
		return tipoAcesso;
	}
	
	public String calcularDuracao() {
		if (entrada == null || saida == null) {
			return null;
		}
		
		Duration d = Duration.between(entrada, saida);
		return String.format("%02d:%02d", d.toHoursPart(), d.toMinutesPart());
	}
	
	// getters e setters
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Aluno getAluno() {
		return aluno;
	}
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	public LocalDateTime getEntrada() {
		return entrada;
	}
	public void setEntrada(LocalDateTime entrada) {
		this.entrada = entrada;
	}
	public LocalDateTime getSaida() {
		return saida;
	}
	public void setSaida(LocalDateTime saida) {
		this.saida = saida;
	}
	
	// toString
	@Override
	public String toString() {
		return "Acesso [id=" + id + ", aluno=" + aluno + ", entrada=" + entrada + ", saida=" + saida + "]";
	}
	
	// equals e hashCode
	// usado para distinguir objetos no banco de dados
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Acesso other = (Acesso) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
