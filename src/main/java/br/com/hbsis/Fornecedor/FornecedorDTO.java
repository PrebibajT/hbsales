package br.com.hbsis.Fornecedor;

/**
 * Classe para tráfego das informações do usuário
 */
public class FornecedorDTO {
    private Long id;
    private String nome_fantasia;
    private String cnpj;
    private String razao_social;
    private String endereco;
    private String telefone;
    private String email;

    public FornecedorDTO() {
    }


    public FornecedorDTO(String cnpj, String nome_fantasia) {
        this.cnpj = cnpj;
        this.nome_fantasia = nome_fantasia;
    }

    public FornecedorDTO(Long id, String cnpj, String nome_fantasia, String Email, String Telefone, String Endereco, String razao_social) {
        this.id = id;
        this.cnpj = cnpj;
        this.nome_fantasia = nome_fantasia;
        this.email = Email;
        this.telefone = Telefone;
        this.endereco = Endereco;
        this.razao_social = razao_social;
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
        return nome_fantasia;
    }

    public void setNome_fantasia(String nome_fantasia) {
        this.nome_fantasia = nome_fantasia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazao_social() {
        return razao_social;
    }

    public void setRazao_social(String razao_social) {
        this.razao_social = razao_social;
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
                ", Razao_Social='" + razao_social + '\'' +
                ", Endereco='" + endereco + '\'' +
                ", Telefone='" + telefone + '\'' +
                ", E-mail='" + email + '\'' +
                '}';
    }
}
