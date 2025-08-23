package br.ufs.garcomeletronico.listener;

import br.ufs.garcomeletronico.event.CarrinhoEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

// Reage aos eventos publicados pelo Spring (@EventListener).
@Component
public class CarrinhoEventListener {

    @EventListener
    public void handleCarrinhoEvent(CarrinhoEvent event) {
        System.out.println("Evento de carrinho: " + event.getAcao());
        if (event.getCarrinho() != null) {
            System.out.println("Itens no carrinho: " + event.getCarrinho().getCarrinho().size());
        }
    }
}
