package br.com.hbsis.fornecedor;

/**
 * Classe para tráfego das informações do usuário
 */
public class FornecedorDTO {
    private Long id;
    private String nomeFantasia;
    private String cnpj;
    private String razaoSocial;
    private String endereco;
    private String telefone;
    private String email;

    public FornecedorDTO() {
    }


    public FornecedorDTO(Long id, String cnpj, String nomeFantasia, String Email, String Telefone, String Endereco, String razaoSocial) {
        this.id = id;
        this.cnpj = cnpj;
        this.nomeFantasia = nomeFantasia;
        this.email = Email;
        this.telefone = Telefone;
        this.endereco = Endereco;
        this.razaoSocial = razaoSocial;
    }

    public static FornecedorDTO of(Fornecedor fornecedor) {
        return new FornecedorDTO(
                fornecedor.getId(),
                fornecedor.getCnpj(),
                fornecedor.getNomeFantasia(),
                fornecedor.getEmail(),
                fornecedor.getTelefone(),
                fornecedor.getEndereco(),
                fornecedor.getRazaoSocial()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome_fantasia() {
        return nomeFantasia;
    }

    public void setNome_fantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazao_social() {
        return razaoSocial;
    }

    public void setRazao_social(String razaoSocial) {
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
