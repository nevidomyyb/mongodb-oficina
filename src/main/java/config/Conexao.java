package config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class Conexao {

    private MongoClient cliente;
    private MongoDatabase database;

    public Conexao() {
        this.cliente = MongoClients.create("mongodb://root:Senhals@localhost:27017");
        this.database = this.cliente.getDatabase("oficina");
    }

    public MongoDatabase getBanco() {return database;}

    public static void main(String[] args) {
        Conexao c = new Conexao();
    }
}
