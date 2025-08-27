package br.ufs.garcomeletronico.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufs.garcomeletronico.model.NotaFiscal;
import br.ufs.garcomeletronico.service.NotaFiscalService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/notaFiscal")
public class NotaFiscalController {

    private final NotaFiscalService notaFiscalService;


    NotaFiscalController(NotaFiscalService notaFiscalService) {
        this.notaFiscalService = notaFiscalService;
    }



    @GetMapping
    public List<NotaFiscal> getNotas() {
        return notaFiscalService.notasFiscais();
    }
    


}
