package br.com.hbsis.Fornecedor;

import javax.persistence.*;




@Entity
@Table(name = "seg_fornecedores")
class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Nome_Fantasia", unique = true, nullable = false, length = 255)
    private String NomeFantasia;
    @Column(name = "CNPJ", nullable = false, length = 16)
    private String CNPJ;
    @Column(name = "Razao_Social", unique = true, length = 155)
    private String RazaoSocial;
    @Column(name = "Endereco", unique = true,  length = 255)
    private String Endereco;
    @Column(name = "Telefone", unique = true, length = 16)
    private String Telefone;
    @Column(name = "E-mail", unique = true, length = 120)
    private String Email;


    public Long getId() {
        return id;
    }

    public String getNomeFantasia() {
        return NomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        NomeFantasia = nomeFantasia;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getRazaoSocial() {
        return RazaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        RazaoSocial = razaoSocial;
    }

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String endereco) {
        Endereco = endereco;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String telefone) {
        Telefone = telefone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @Override
    public String toString() {
        return "Nome_Fantasia{" +
                "id=" + id +
                ", CNPJ='" + CNPJ + '\'' +
                ", Razao_Social='" + RazaoSocial + '\'' +
                ", Endereco='" + Endereco + '\'' +
                ", Telefone='" + Telefone + '\'' +
                ", E-mail='" + Email + '\'' +
                '}';
    }
}