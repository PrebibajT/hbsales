package br.com.hbsis.linha;

public class LinhaDTO {

    private Long id;
    private String codigoLinha;
    private Long idCategoria;
    private String nome;

    public LinhaDTO(){

    }

    public LinhaDTO(Long id, String codigoLinha, Long idCategoria, String nome){
    this.id = id;
    this.codigoLinha = codigoLinha;
    this.idCategoria = idCategoria;
    this.nome = nome;
  }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoLinha() {
        return codigoLinha;
    }

    public void setCodigoLinha(String codigoLinha) {
        this.codigoLinha = codigoLinha;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public static LinhaDTO of (Linha linha){
        return new LinhaDTO(
                linha.getId(),
                linha.getCodigoLinha(),
                linha.getCategoriaLinha().getId(),
                linha.getNome()
        );
    }
    @Override
    public String toString() {
        return "id{" + id +
                ", codigo_linha='" + codigoLinha + '\'' +
                ", categoria_linha='" + idCategoria + '\'' +
                ", nome='" + nome + '\'' +
                '}';
    }
}
