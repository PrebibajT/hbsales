package br.com.hbsis.fornecedor;

import javax.persistence.*;


@Entity
@Table(name = "seg_fornecedores")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_fantasia", nullable = false, length = 100)
    private String nomeFantasia;
    @Column(name = "cnpj", nullable = false, unique = true, length = 14)
    private String cnpj;
    @Column(name = "razao_social", length = 100)
    private String razaoSocial;
    @Column(name = "endereco", length = 100)
    private String endereco;
    @Column(name = "telefone", length = 12)
    private String telefone;
    @Column(name = "email", unique = true, length = 50)
    private String email;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;

    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Nome_Fantasia{" +
                "id=" + id +
                ", CNPJ='" + cnpj + '\'' +
                ", Razao_Social='" + razaoSocial + '\'' +
                ", Endereco='" + endereco + '\'' +
                ", Telefone='" + telefone + '\'' +
                ", E-mail='" + email + '\'' +
                '}';
    }
}