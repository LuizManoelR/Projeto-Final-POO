// Interface do Estado
package br.ufs.garcomeletronico.model;


public interface ComandaState {
    void adicionarItem(Comanda comanda, Item item);
    boolean removerItem(Comanda comanda, Item item);
    void fecharComanda(Comanda comanda);
    String getStatus();
}
