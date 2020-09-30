package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.Speaker;
import com.pluralsight.conferencedemo.repositories.SpeakerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SpeakersController {
    @Autowired
    private SpeakerRepository speakerRepository;

    @GetMapping("/api/v1/speakers")
    public List<Speaker> list() {
        return speakerRepository.findAll();
    }

    @GetMapping("/api/v1/speakers/{id}")
    public Speaker get(@PathVariable Long id) {
        return speakerRepository.getOne(id);
    }

    @PostMapping("/api/v1/speakers")
    public Speaker create(@RequestBody final Speaker speaker) {
        return speakerRepository.saveAndFlush(speaker);
    }

    @DeleteMapping("/api/v1/speakers/{id}")
    public void delete(@PathVariable Long id) {
        // TODO: Comprobar que no haya referencias en tablas hijas ANTES de borrar
        speakerRepository.deleteById(id);
    }

    @PutMapping("/api/v1/speakers/{id}")
    public Speaker update(@PathVariable Long id, @RequestBody Speaker speaker) {
        // Al ser PUT se espera que todos se pasan todos los atributos en el Body
        // Si fuera un PATCH se entiende que sólo modifican "algunos" de los atributos
        // TODO: Comprobar que se pasan todos los atributos en el Body, sino devolver un 400 Bad payload
        Speaker existingSpeaker = speakerRepository.getOne(id);
        // Sobreescribe el objeto con los atributos que nos llegan en el Body, excepto "speaker_id"
        BeanUtils.copyProperties(speaker, existingSpeaker, "speaker_id");
        // Guarda los cambios y lo devuelve
        return speakerRepository.saveAndFlush(existingSpeaker);
    }

}
