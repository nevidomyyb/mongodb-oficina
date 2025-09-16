package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import config.Conexao;
import model.Cliente;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private final MongoCollection<Document> collection;

    public ClienteDAO() {
        this.collection = new Conexao().getBanco().getCollection("clientes");
    }

    private Document clienteToDocument(Cliente cliente) {
        Document doc = new Document();
        if (cliente.getId() != null) {
            doc.append("_id", cliente.getId());
        }
        doc.append("nome", cliente.getNome())
                .append("cpf", cliente.getCpf())
                .append("telefone", cliente.getTelefone())
                .append("email", cliente.getEmail())
                .append("dataCadastro", cliente.getDataCadastro().toString());

        return doc;
    }

    private Cliente documentToCliente(Document doc) {
        Cliente cliente = new Cliente();
        cliente.setId(doc.getObjectId("_id"));
        cliente.setNome(doc.getString("nome"));
        cliente.setCpf(doc.getString("cpf"));
        cliente.setTelefone(doc.getString("telefone"));
        cliente.setEmail(doc.getString("email"));

        String dataStr = doc.getString("dataCadastro");
        if (dataStr != null) {
            cliente.setDataCadastro(LocalDateTime.parse(dataStr));
        }

        return cliente;
    }

    public void inserir(Cliente cliente) {
        Document doc = clienteToDocument(cliente);
        collection.insertOne(doc);
        cliente.setId(doc.getObjectId("_id"));
        System.out.println("Cliente inserido: " + cliente.getNome());
    }

    public Cliente buscarPorId(ObjectId id) {
        Document doc = collection.find(eq("_id", id)).first();
        return doc != null ? documentToCliente(doc) : null;
    }

    public List<Cliente> buscarTodos() {
        List<Cliente> clientes = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                clientes.add(documentToCliente(cursor.next()));
            }
        }
        return clientes;
    }

    public Cliente buscarPorCpf(String cpf) {
        Document doc = collection.find(eq("cpf", cpf)).first();
        return doc != null ? documentToCliente(doc) : null;
    }

    public void atualizar(Cliente cliente) {
        Document doc = clienteToDocument(cliente);
        collection.replaceOne(eq("_id", cliente.getId()), doc);
        System.out.println("Cliente atualizado: " + cliente.getNome());
    }

    public void excluir(ObjectId id) {
        collection.deleteOne(eq("_id", id));
        System.out.println("Cliente excluído com ID: " + id);
    }
}