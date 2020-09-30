package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.Session;
import com.pluralsight.conferencedemo.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SessionsController {
    @Autowired
    private SessionRepository sessionRepository;

    @GetMapping("/api/v1/sessions")
    public List<Session> list() {
        return sessionRepository.findAll();
    }

    @GetMapping("/api/v1/sessions/{id}")
    public Session get(@PathVariable Long id) {
        return sessionRepository.getOne(id);
    }

    @PostMapping("/api/v1/sessions")
    public Session create(@RequestBody final Session session) {
        return sessionRepository.saveAndFlush(session);
    }

    @DeleteMapping("/api/v1/sessions/{id}")
    public void delete(@PathVariable Long id) {
        // TODO: Comprobar que no haya referencias en tablas hijas ANTES de borrar
        sessionRepository.deleteById(id);
    }

    @PutMapping("/api/v1/sessions/{id}")
    public Session update(@PathVariable Long id, @RequestBody Session session) {
        // Al ser PUT se espera que todos se pasan todos los atributos en el Body
        // Si fuera un PATCH se entiende que sólo modifican "algunos" de los atributos
        // TODO: Comprobar que se pasan todos los atributos en el Body, sino devolver un 400 Bad payload
        Session existingSession = sessionRepository.getOne(id);
        // Sobreescribe el objeto con los atributos que nos llegan en el Body, excepto "session_id"
        BeanUtils.copyProperties(session, existingSession, "session_id");
        // Guarda los cambios y lo devuelve
        return sessionRepository.saveAndFlush(existingSession);
    }

}
