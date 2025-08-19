package br.ufs.garcomeletronico.DAO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import br.ufs.garcomeletronico.model.Identificavel;
import br.ufs.garcomeletronico.utils.LocalDateTimeAdapter;

public abstract class BaseDAO<T extends Identificavel> {
    private final File arquivo;
    private final Gson gson;
    private final Type tipoLista;
    private List<T> lista;

    public BaseDAO(String caminhoArquivo, Class<T> tipoClasse) {
        this.arquivo = new File(caminhoArquivo);
        this.tipoLista = TypeToken.getParameterized(List.class, tipoClasse).getType();

         this.gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .setPrettyPrinting()
            .create();

        if (!arquivo.exists()) {
            try {
                arquivo.getParentFile().mkdirs();
                arquivo.createNewFile();
                salvar(new ArrayList<>());
            } catch (IOException e) {
                throw new RuntimeException("Erro ao criar arquivo JSON", e);
            }
        }
        lista = listar();
    }

    public List<T> listar() {
        try (Reader reader = new FileReader(arquivo)) {
            List<T> lista = gson.fromJson(reader, tipoLista);
            return lista != null ? lista : new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo JSON", e);
        }
    }

    public void salvar(List<T> lista) {
        try (Writer writer = new FileWriter(arquivo)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar arquivo JSON", e);
        }
    }

    public void adicionar(T obj) {
        if(buscarPorCodigo(obj.getId()) == null){

            lista.add(obj);
            salvar(lista);

        }
    }

    public void remover(String id){

        lista.remove(buscarPorCodigo(id));
        salvar(lista);
    }

    public T buscarPorCodigo(String codigo) {
        return lista.stream()
                .filter(obj -> obj.getId().equalsIgnoreCase(codigo))
                .findFirst()
                .orElse(null);
    }
}
