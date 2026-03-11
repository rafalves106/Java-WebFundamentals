/**
 * @author falvesmac
 */

package br.com.falves.Projeto.repository;

import br.com.falves.Projeto.model.TipoTransacao;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class TransacaoRepository implements AutoCloseable {
    private final MongoClient client;
    private final MongoCollection<Document> collection;

    public TransacaoRepository(){
        this.client = MongoClients.create("mongodb://localhost:27017");

        MongoDatabase database = client.getDatabase("mongo-webfundamentals");

        this.collection = database.getCollection("transacoes");

        System.out.println("Conexão aberta e Collection Configurada!");
    }

    public TransacaoRepository(String collection){
        this.client = MongoClients.create("mongodb://localhost:27017");

        MongoDatabase database = client.getDatabase("mongo-webfundamentals");

        this.collection = database.getCollection(collection);

        System.out.println("Conexão aberta e Collection de testes Configurada!");
    }

    public void criarTransacao(Document doc){
        collection.insertOne(doc);
    }

    public List<Document> listarTransacoes(){
        List<Document> transacoes = new ArrayList<>();

        collection.find().forEach(transacoes::add);

        return transacoes;
    }

    public Document buscarTransacaoPorId(Long id){
        return collection.find(eq("id", id)).first();
    }

    public List<Document> buscarTransacaoPorTipo(TipoTransacao tipo){
        List<Document> transacoes = new ArrayList<>();

        return collection.find(eq("tipo", tipo.toString())).into(transacoes);
    }

    public void deletarTransacao(Document doc){
        collection.deleteOne(doc);
    }

    public void alterarTransacao(Document doc1, Document doc2){
        collection.replaceOne(doc1, doc2);
    }

    @Override
    public void close() throws Exception {
        if (this.client != null){
            this.client.close();
            System.out.println("Conexão fechada com sucesso.");
        }
    }
}