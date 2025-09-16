package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import config.Conexao;
import model.Produto;
import org.bson.Document;
import org.bson.types.ObjectId;
import static com.mongodb.client.model.Filters.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    private final MongoCollection<Document> collection;

    public ProdutoDAO() {
        this.collection = new Conexao().getBanco().getCollection("produtos");
    }

    // Converte Produto para Document
    private Document produtoToDocument(Produto produto) {
        Document doc = new Document();
        if (produto.getId() != null) {
            doc.append("_id", produto.getId());
        }
        doc.append("codigo", produto.getCodigo())
                .append("nome", produto.getNome())
                .append("descricao", produto.getDescricao())
                .append("categoria", produto.getCategoria())
                .append("preco", produto.getPreco().doubleValue())
                .append("quantidadeEstoque", produto.getQuantidadeEstoque())
                .append("fornecedor", produto.getFornecedor())
                .append("dataCadastro", produto.getDataCadastro().toString());

        return doc;
    }

    // Converte Document para Produto
    private Produto documentToProduto(Document doc) {
        Produto produto = new Produto();
        produto.setId(doc.getObjectId("_id"));
        produto.setCodigo(doc.getString("codigo"));
        produto.setNome(doc.getString("nome"));
        produto.setDescricao(doc.getString("descricao"));
        produto.setCategoria(doc.getString("categoria"));

        Double preco = doc.getDouble("preco");
        if (preco != null) {
            produto.setPreco(BigDecimal.valueOf(preco));
        }

        produto.setQuantidadeEstoque(doc.getInteger("quantidadeEstoque"));
        produto.setFornecedor(doc.getString("fornecedor"));

        String dataStr = doc.getString("dataCadastro");
        if (dataStr != null) {
            produto.setDataCadastro(LocalDateTime.parse(dataStr));
        }

        return produto;
    }

    public void inserir(Produto produto) {
        Document doc = produtoToDocument(produto);
        collection.insertOne(doc);
        produto.setId(doc.getObjectId("_id"));
        System.out.println("Produto inserido: " + produto.getNome());
    }

    public Produto buscarPorId(ObjectId id) {
        Document doc = collection.find(eq("_id", id)).first();
        return doc != null ? documentToProduto(doc) : null;
    }

    public List<Produto> buscarTodos() {
        List<Produto> produtos = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                produtos.add(documentToProduto(cursor.next()));
            }
        }
        return produtos;
    }

    public Produto buscarPorCodigo(String codigo) {
        Document doc = collection.find(eq("codigo", codigo)).first();
        return doc != null ? documentToProduto(doc) : null;
    }

    public List<Produto> buscarPorCategoria(String categoria) {
        List<Produto> produtos = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find(eq("categoria", categoria)).iterator()) {
            while (cursor.hasNext()) {
                produtos.add(documentToProduto(cursor.next()));
            }
        }
        return produtos;
    }

    public void atualizar(Produto produto) {
        Document doc = produtoToDocument(produto);
        collection.replaceOne(eq("_id", produto.getId()), doc);
        System.out.println("Produto atualizado: " + produto.getNome());
    }

    public void excluir(ObjectId id) {
        collection.deleteOne(eq("_id", id));
        System.out.println("Produto excluído com ID: " + id);
    }
}