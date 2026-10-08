package com.iset.web;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.iset.dao.OffreRepository;
import com.iset.entities.Offre;

@RestController
public class RestOffres {

    private final OffreRepository offreRepository;

    public RestOffres(OffreRepository offreRepository) {
        this.offreRepository = offreRepository;
    }

    // ==========================================
    // GET : récupérer toutes les offres
    // ==========================================
    @GetMapping("/Offres")
    public List<Offre> getOffres() {
        return offreRepository.findAll();
    }

    // ==========================================
    // GET : récupérer une offre par son code
    // ==========================================
    @GetMapping("/Offres/{code}")
    public Optional<Offre> getOffreByCode(@PathVariable Long code) {
        return offreRepository.findById(code);
    }

    // ==========================================
    // POST : ajouter une nouvelle offre
    // ==========================================
    @PostMapping("/Offres")
    public Offre saveOffre(@RequestBody Offre offre) {
        return offreRepository.save(offre);
    }

    // ==========================================
    // PUT : modifier une offre
    // ==========================================
    @PutMapping("/Offres/{code}")
    public Offre updateOffre(
            @PathVariable Long code,
            @RequestBody Offre offre) {

        Offre existingOffre = offreRepository.findById(code)
                .orElseThrow(() ->
                        new RuntimeException("Offre introuvable avec le code : " + code));

        existingOffre.setIntitule(offre.getIntitule());
        existingOffre.setSpecialite(offre.getSpecialite());
        existingOffre.setSociete(offre.getSociete());
        existingOffre.setNbpostes(offre.getNbpostes());
        existingOffre.setPays(offre.getPays());

        return offreRepository.save(existingOffre);
    }

    // ==========================================
    // DELETE : supprimer une offre
    // ==========================================
    @DeleteMapping("/Offres/{code}")
    public String deleteOffre(@PathVariable Long code) {

        if (!offreRepository.existsById(code)) {
            return "Offre introuvable avec le code : " + code;
        }

        offreRepository.deleteById(code);

        return "Offre supprimée avec succès : " + code;
    }
}