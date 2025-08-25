package br.ufs.garcomeletronico.listener;

import br.ufs.garcomeletronico.event.CarrinhoEvent;
import br.ufs.garcomeletronico.utils.BigDecimalUtils;

import java.math.BigDecimal;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

// Reage aos eventos publicados pelo Spring (@EventListener).
@Component
public class CarrinhoEventListener {

    @EventListener
    public void handleCarrinhoEvent(CarrinhoEvent event) {
        System.out.println("Evento de carrinho: " + event.getAcao());
        if (event.getCarrinho() != null) {
            BigDecimal valor = new BigDecimal(0);
            for(int i = 0; i< event.getCarrinho().getCarrinho().size(); i ++){

                valor = BigDecimalUtils.somar(valor,event.getCarrinho().getCarrinho().get(i).getQuantidade());

            }
            System.out.println("Itens no carrinho: " + valor);
        }
    }
}
