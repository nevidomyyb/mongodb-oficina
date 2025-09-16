package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import config.Conexao;
import model.Venda;
import static com.mongodb.client.model.Filters.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {
    private final MongoCollection<Document> collection;

    public VendaDAO() {
        this.collection = new Conexao().getBanco().getCollection("vendas");
    }

    // Converte Venda para Document
    private Document vendaToDocument(Venda venda) {
        Document doc = new Document();
        if (venda.getId() != null) {
            doc.append("_id", venda.getId());
        }

        doc.append("clienteId", venda.getClienteId())
                .append("produtoIds", venda.getProdutoIds())
                .append("quantidades", venda.getQuantidades())
                .append("valorTotal", venda.getValorTotal().doubleValue())
                .append("formaPagamento", venda.getFormaPagamento())
                .append("status", venda.getStatus())
                .append("observacoes", venda.getObservacoes())
                .append("dataVenda", venda.getDataVenda().toString());

        return doc;
    }

    // Converte Document para Venda
    @SuppressWarnings("unchecked")
    private Venda documentToVenda(Document doc) {
        Venda venda = new Venda();
        venda.setId(doc.getObjectId("_id"));
        venda.setClienteId(doc.getObjectId("clienteId"));
        venda.setFormaPagamento(doc.getString("formaPagamento"));
        venda.setStatus(doc.getString("status"));
        venda.setObservacoes(doc.getString("observacoes"));

        Double valorTotal = doc.getDouble("valorTotal");
        if (valorTotal != null) {
            venda.setValorTotal(BigDecimal.valueOf(valorTotal));
        }

        String dataStr = doc.getString("dataVenda");
        if (dataStr != null) {
            venda.setDataVenda(LocalDateTime.parse(dataStr));
        }

        // Converter listas de IDs e quantidades
        List<ObjectId> produtoIds = doc.getList("produtoIds", ObjectId.class);
        if (produtoIds != null) {
            venda.setProdutoIds(produtoIds);
        }

        List<Integer> quantidades = doc.getList("quantidades", Integer.class);
        if (quantidades != null) {
            venda.setQuantidades(quantidades);
        }

        return venda;
    }

    public void inserir(Venda venda) {
        Document doc = vendaToDocument(venda);
        collection.insertOne(doc);
        venda.setId(doc.getObjectId("_id"));
        System.out.println("Venda inserida - Total: R$ " + venda.getValorTotal());
    }

    public Venda buscarPorId(ObjectId id) {
        Document doc = collection.find(eq("_id", id)).first();
        return doc != null ? documentToVenda(doc) : null;
    }

    public List<Venda> buscarTodas() {
        List<Venda> vendas = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                vendas.add(documentToVenda(cursor.next()));
            }
        }
        return vendas;
    }

    public List<Venda> buscarPorCliente(ObjectId clienteId) {
        List<Venda> vendas = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find(eq("clienteId", clienteId)).iterator()) {
            while (cursor.hasNext()) {
                vendas.add(documentToVenda(cursor.next()));
            }
        }
        return vendas;
    }

    public void atualizar(Venda venda) {
        Document doc = vendaToDocument(venda);
        collection.replaceOne(eq("_id", venda.getId()), doc);
        System.out.println("Venda atualizada - Total: R$ " + venda.getValorTotal());
    }

    public void excluir(ObjectId id) {
        collection.deleteOne(eq("_id", id));
        System.out.println("Venda excluída com ID: " + id);
    }
}