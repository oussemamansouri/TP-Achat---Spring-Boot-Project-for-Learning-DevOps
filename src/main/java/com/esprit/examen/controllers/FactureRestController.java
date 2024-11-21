package com.esprit.examen.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esprit.examen.entities.Facture;
import com.esprit.examen.services.IFactureService;

import io.swagger.annotations.Api;


@RestController
@Api(tags = "Gestion des factures")
@RequestMapping("/facture")
@CrossOrigin("*")
public class FactureRestController {


    final IFactureService factureService;
    public FactureRestController(IFactureService factureService){
        this.factureService = factureService;
    }

    // http://localhost:8089/SpringMVC/facture/retrieve-all-factures
    @GetMapping("/retrieve-all-factures")
    public List<Facture> getFactures() {
        List<Facture> list = factureService.retrieveAllFactures();
        return list;
    }

    // http://localhost:8089/SpringMVC/facture/retrieve-facture/8
    @GetMapping("/retrieve-facture/{facture-id}")
    public Facture retrieveFacture(@PathVariable("facture-id") Long factureId) {
        return factureService.retrieveFacture(factureId);
    }
    
    @GetMapping("/retrieve-facture/{montant}")
    public List <Facture> retrieveFacture(@PathVariable("montant") float montant) {
        return  factureService.searchByMontantSup(montant);
    }
    
    @PostMapping("/add-new-facture")
    public Facture addNewFacture(@RequestBody Facture f) {
        return factureService.addFacture(f);
    }

    // http://localhost:8089/SpringMVC/facture/add-facture/{fournisseur-id}
    @PostMapping("/add-facture")
    public Facture addFacture(@RequestBody Facture f) {
        Facture facture = factureService.addFacture(f);
        return facture;
    }

    /*
     * une facture peut etre annulé si elle a été saisie par erreur Pour ce
     * faire, il suffit de mettre le champs active à false
     */
    // http://localhost:8089/SpringMVC/facture/cancel-facture/{facture-id}
    @PutMapping("/cancel-facture/{facture-id}")
    public void cancelFacture(@PathVariable("facture-id") Long factureId) {
        factureService.cancelFacture(factureId);
    }

    // http://localhost:8089/SpringMVC/facture/getFactureByFournisseur/{fournisseur-id}
    @GetMapping("/getFactureByFournisseur/{fournisseur-id}")
    public List<Facture> getFactureByFournisseur(@PathVariable("fournisseur-id") Long fournisseurId) {
        return factureService.getFacturesByFournisseur(fournisseurId);
    }

    // http://localhost:8089/SpringMVC/facture/assignOperateurToFacture/1/1
    @PutMapping(value = "/assignOperateurToFacture/{idOperateur}/{idFacture}")
    public void assignOperateurToFacture(@PathVariable("idOperateur") Long idOperateur, @PathVariable("idFacture") Long idFacture) {
        factureService.assignOperateurToFacture(idOperateur, idFacture);
    }

    // http://localhost:8089/SpringMVC/facture/pourcentageRecouvrement/{startDate}/{endDate}
    @GetMapping(value = "/pourcentageRecouvrement/{startDate}/{endDate}")
    public float pourcentageRecouvrement(
            @PathVariable(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @PathVariable(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        try {
            return factureService.pourcentageRecouvrement(startDate, endDate);
        } catch (Exception e) {
            return 0;
        }
    }

}
