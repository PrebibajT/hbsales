package br.com.hbsis.Fornecedor;

/**
 * Classe para tráfego das informações do usuário
 */
public class FornecedorDTO {
    private Long id;
    private String NomeFantasia;
    private String CNPJ;
    private String Razao_Social;
    private String Endereco;
    private String Telefone;
    private String Email;

    public FornecedorDTO() {
    }


    public FornecedorDTO(String CNPJ, String NomeFantasia) {
        this.CNPJ = CNPJ;
        this.NomeFantasia = NomeFantasia;
    }

    public FornecedorDTO(Long id, String CNPJ, String NomeFantasia, String Email, String Telefone, String Endereco, String Razao_Social) {
        this.id = id;
        this.CNPJ = CNPJ;
        this.NomeFantasia = NomeFantasia;
        this.Email = Email;
        this.Telefone = Email;
        this.Endereco = Email;
        this.Razao_Social = Razao_Social;
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

    public String getRazao_Social() {
        return Razao_Social;
    }

    public void setRazao_Social(String razao_Social) {
        Razao_Social = razao_Social;
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

    public static FornecedorDTO of(Fornecedor fornecedor) {
        return new FornecedorDTO(
                fornecedor.getId(),
                fornecedor.getCNPJ(),
                fornecedor.getRazaoSocial(),
                fornecedor.getEndereco(),
                fornecedor.getTelefone(),
                fornecedor.getEmail(),
                fornecedor.getNomeFantasia()
        );
    }

    public Long getId() {
        return id;
    }



    @Override
    public String toString() {
        return "Nome_Fantasia{" +
                "id=" + id +
                ", CNPJ='" + CNPJ + '\'' +
                ", Razao_Social='" + Razao_Social + '\'' +
                ", Endereco='" + Endereco + '\'' +
                ", Telefone='" + Telefone + '\'' +
                ", E-mail='" + Email + '\'' +
                '}';
    }
}
