package br.com.academia.domain.aluno;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "alunos")
public class Aluno implements Serializable {

	// enums
	public enum Sexo {
		Masculino, Feminino;
	}
	
	public enum Situacao {
		Ativo, Inativo, Pendente;
	}
	
	@Id
	@Column(name = "matricula", nullable = false, length = 8)
	private String matricula;
	
	@Column(name = "nome", nullable = false, length = 64)
	private String nome;
	
	@Enumerated
	@Column(name = "sexo", nullable = false, length = 1)
	private Sexo sexo;
	
	@Column(name = "rg", nullable = false, length = 10)
	private Integer rg;
	
	@Column(name = "nascimento", nullable = true)
	private Date nascimento;
	
	@Enumerated
	@Column(name = "situacao", nullable = false, length = 1)
	private Situacao situacao;
	
	@Column(name = "email", nullable = true, length = 64)
	private String email;
	
	/*
	 * Endereço e telefone não vão ter tabelas no banco de dados.
	 * No entanto, elas terão classes separadas, que serão atreladas à classe Aluno.
	 * Para isso, usa-se Embedded para dizer para a JPA que elas compartilharão o ID do aluno.
	 */
	@Embedded
	private Endereco endereco = new Endereco();
	
	@Embedded
	private Telefone telefone = new Telefone();
	
	// getters e setters
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Sexo getSexo() {
		return sexo;
	}
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}
	public Integer getRg() {
		return rg;
	}
	public void setRg(Integer rg) {
		this.rg = rg;
	}
	public Date getNascimento() {
		return nascimento;
	}
	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}
	public Situacao getSituacao() {
		return situacao;
	}
	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public Telefone getTelefone() {
		return telefone;
	}
	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}
	
	// toString
	@Override
	public String toString() {
		return "Aluno [matricula=" + matricula + ", nome=" + nome + ", sexo=" + sexo + ", rg=" + rg + ", nascimento="
				+ nascimento + ", situacao=" + situacao + ", endereco=" + endereco + ", telefone=" + telefone + "]";
	}
	
	// equals e hashCode
	// usado para distinguir objetos no banco de dados
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((matricula == null) ? 0 : matricula.hashCode());
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
		Aluno other = (Aluno) obj;
		if (matricula == null) {
			if (other.matricula != null)
				return false;
		} else if (!matricula.equals(other.matricula))
			return false;
		return true;
	}
}
