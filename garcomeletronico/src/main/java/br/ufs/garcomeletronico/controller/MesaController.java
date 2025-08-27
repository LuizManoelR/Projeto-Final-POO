package br.ufs.garcomeletronico.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import br.ufs.garcomeletronico.model.Mesa;
import br.ufs.garcomeletronico.service.MesaService;


@RestController
@RequestMapping("/api/mesa")
public class MesaController {



    private final MesaService mesaService;

    public MesaController(MesaService mesaService) {
        this.mesaService = mesaService;
    
    }
    // Listar mesas que chamaram o garçom
    @GetMapping("/garcom-solicitado")
    public List<Mesa> garcomSolicitado() {
        return mesaService.garcomSolicitado();
    }
    // Listar mesas que chamaram o garçom
    @GetMapping
    public List<Mesa> buscar() {
        return mesaService.buscarMesas();
    }

    // Alterar status da mesa (Livre <-> Ocupada)
    @PostMapping("/status/{id}")
    public String mudarStatus(@PathVariable String id) {
        mesaService.mudarStatus(id);
        return "Status da mesa " + id + " alterado.";
    }

    // Buscar mesas livres
    @GetMapping("/iniciar")
    public Mesa iniciar() {
        return mesaService.iniciar();
    }
}
