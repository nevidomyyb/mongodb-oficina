package model;

import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class Venda {
    private ObjectId id;
    private ObjectId clienteId;
    private List<ObjectId> produtoIds;
    private List<Integer> quantidades;
    private BigDecimal valorTotal;
    private String formaPagamento;
    private String status;
    private String observacoes;
    private LocalDateTime dataVenda;

    // Construtores
    public Venda() {
        this.dataVenda = LocalDateTime.now();
        this.status = "CONCLUIDA";
        this.produtoIds = new ArrayList<>();
        this.quantidades = new ArrayList<>();
        this.valorTotal = BigDecimal.ZERO;
    }

    public Venda(ObjectId clienteId, List<ObjectId> produtoIds, List<Integer> quantidades,
                 BigDecimal valorTotal, String formaPagamento, String observacoes) {
        this();
        this.clienteId = clienteId;
        this.produtoIds = produtoIds != null ? produtoIds : new ArrayList<>();
        this.quantidades = quantidades != null ? quantidades : new ArrayList<>();
        this.valorTotal = valorTotal != null ? valorTotal : BigDecimal.ZERO;
        this.formaPagamento = formaPagamento;
        this.observacoes = observacoes;
    }

    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }

    public ObjectId getClienteId() { return clienteId; }
    public void setClienteId(ObjectId clienteId) { this.clienteId = clienteId; }

    public List<ObjectId> getProdutoIds() { return produtoIds; }
    public void setProdutoIds(List<ObjectId> produtoIds) { this.produtoIds = produtoIds; }

    public List<Integer> getQuantidades() { return quantidades; }
    public void setQuantidades(List<Integer> quantidades) { this.quantidades = quantidades; }

    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }

    public String getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(String formaPagamento) { this.formaPagamento = formaPagamento; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public LocalDateTime getDataVenda() { return dataVenda; }
    public void setDataVenda(LocalDateTime dataVenda) { this.dataVenda = dataVenda; }

    @Override
    public String toString() {
        return "Venda{" +
                "id=" + id +
                ", clienteId=" + clienteId +
                ", produtoIds=" + produtoIds +
                ", quantidades=" + quantidades +
                ", valorTotal=" + valorTotal +
                ", formaPagamento='" + formaPagamento + '\'' +
                ", status='" + status + '\'' +
                ", observacoes='" + observacoes + '\'' +
                ", dataVenda=" + dataVenda +
                '}';
    }
}