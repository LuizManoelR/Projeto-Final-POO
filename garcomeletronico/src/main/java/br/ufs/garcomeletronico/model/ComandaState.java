// Interface do Estado
package br.ufs.garcomeletronico.model;

import java.util.List;

public interface ComandaState {
    void adicionarItem(Comanda comanda, List<Item> itens);
    void removerItem(Comanda comanda, List<Item> itens);
    void fecharComanda(Comanda comanda);
    String getStatus();
}
