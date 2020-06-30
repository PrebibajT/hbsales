package br.com.hbsis.categoria;

public class CategoriasDTO {
    private Long id;
    private String codigoCategoria;
    private Long idFornecedor;
    private String nomeCategoria;

    public CategoriasDTO(){

    }

    public CategoriasDTO(Long id, String codigoCategoria, String nomeCategoria, Long idFornecedor){
        this.id = id;
        this.codigoCategoria = codigoCategoria;
        this.nomeCategoria = nomeCategoria;
        this.idFornecedor = idFornecedor;
    }

    public String getCodigoCategoria() {
        return codigoCategoria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCodigoCategoria(String codigoCategoria) {
        this.codigoCategoria = codigoCategoria;
    }

    public Long getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Long idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public static CategoriasDTO of (Categorias categorias) {
        return new CategoriasDTO(
                categorias.getId(),
                categorias.getCodigoCategoria(),
                categorias.getNomeCategoria(),
                categorias.getFornecedorCategoria().getId()
        );
    }

    @Override
    public String toString() {
        return "id{" + id+ '\'' +
                ", nome_Categoria='" + nomeCategoria +'\'' +
                ", fornecedor_Categoria='" + idFornecedor + '\'' +
                ", codigoCategoria='"+ codigoCategoria + '\'' +
                '}';
        
    }
}
