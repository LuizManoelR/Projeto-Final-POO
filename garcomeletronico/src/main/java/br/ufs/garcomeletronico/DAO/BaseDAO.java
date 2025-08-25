package br.ufs.garcomeletronico.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.ufs.garcomeletronico.model.ComandaState;
import br.ufs.garcomeletronico.model.Identificavel;
import br.ufs.garcomeletronico.utils.ComandaStateAdapter;
import br.ufs.garcomeletronico.utils.LocalDateTimeAdapter;

public abstract class BaseDAO<T extends Identificavel> {//utiliza generics para permitir a reutilização em qualquer classe
    private final File arquivo;                         // e uma interface que possui um metodo de getid 
    private final Gson gson;
    private final Type tipoLista;
    private List<T> lista;

    public BaseDAO(String caminhoArquivo, Class<T> tipoClasse) {
        this.arquivo = new File(caminhoArquivo);//path do arquivo
        this.tipoLista = TypeToken.getParameterized(List.class, tipoClasse).getType();//armazena a classe que sera armazenada

         this.gson = new GsonBuilder()//usa Gsonbuilder para permitir compatibilidade com tipos não nativos
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())//utilza um adapatador para permitir a compatibilidade com datas
            .registerTypeAdapter(ComandaState.class, new ComandaStateAdapter())//utilza um adapatador para permitir a compatibilidade com a Comanda
            .setPrettyPrinting()//salva de forma mais legível
            .create();

        if (!arquivo.exists()) {
            try {
                arquivo.getParentFile().mkdirs();//cria toda a cadeia de pastas do path caso não exista
                arquivo.createNewFile();//cria o arquivo caso não exista
                salvar(new ArrayList<>());//salva um array vazio para evitar problemas com null
            } catch (IOException e) {
                throw new RuntimeException("Erro ao criar arquivo JSON", e);
            }
        }
        lista = listar();
    }

    public synchronized List<T> listar() {//lê o arquivo e retorna sua lista de objetos
        try (Reader reader = new FileReader(arquivo)) {
            List<T> lista = gson.fromJson(reader, tipoLista);
            return lista != null ? lista : new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo JSON", e);
        }
    }

    public synchronized void salvar(List<T> lista) {//sobreescreve o arquivo apartir de uma lista
        try (Writer writer = new FileWriter(arquivo)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar arquivo JSON", e);
        }
    }

    public synchronized void adicionar(T obj) {//adciona um objeto na lista e depois salva
        if(buscarPorCodigo(obj.getId()) == null){

            lista.add(obj);
            salvar(lista);

        }
    }

    public synchronized void remover(String id){//remove apartir de um id e salva

        lista.remove(buscarPorCodigo(id));
        salvar(lista);
    }
    public synchronized void remover(List<T> objs){//remove apartir de uma lista de objetos e salva

        for(T obj: objs){
            
            String id = obj.getId();

            lista.remove(buscarPorCodigo(id));

        }

        salvar(lista);
    }

    public synchronized T buscarPorCodigo(String codigo) {//busca o primeiro da lista que tiver o codigo passado caso 
        return lista.stream()                              //caso não encontre ele retorna null
                .filter(obj -> obj.getId().equalsIgnoreCase(codigo))
                .findFirst()
                .orElse(null);
    }

    public synchronized int buscarIndex(String codigo){
        
        for(int i = 0; i < lista.size(); i++){
            
            if(lista.get(i).equals(buscarPorCodigo(codigo))){
                return i;
            }
            
        }return -1;

    }
}
