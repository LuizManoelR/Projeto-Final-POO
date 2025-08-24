// Interface do Estado
package br.ufs.garcomeletronico.model;

import java.util.List;

public interface ComandaState {
    void adicionarItem(Comanda comanda, List<Item> itens);
    void removerItem(Comanda comanda, Produto produto);
    void fecharComanda(Comanda comanda);
    void abrirComanda(Comanda comanda);
    void resetComanda(Comanda comanda);
    String getStatus();
}
