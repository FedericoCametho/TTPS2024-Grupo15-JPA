package org.example.ttps2024grupo15.dao.carta.impl;

import jakarta.persistence.EntityManager;
import org.example.ttps2024grupo15.dao.GenericDAOHibernateJPA;

import org.example.ttps2024grupo15.dao.carta.CartaDelDiaDAO;
import org.example.ttps2024grupo15.dao.entitiManager.EMF;
import org.example.ttps2024grupo15.model.carta.CartaDelDia;
import org.example.ttps2024grupo15.model.carta.DiaSemana;

import java.time.LocalDate;
import java.util.List;

public class CartaDelDiaDAOHibernateJPA extends GenericDAOHibernateJPA<CartaDelDia> implements CartaDelDiaDAO {
    public CartaDelDiaDAOHibernateJPA() {
        super(CartaDelDia.class);
    }


    @Override
    public List<CartaDelDia> getCartasDelDiaByDiaDeSemana(DiaSemana diaSemana) {
        EntityManager em = EMF.getEMF().createEntityManager();
        try {
            return em.createQuery("SELECT c FROM CartaDelDia c WHERE c.diaSemana = :diaSemana", CartaDelDia.class).setParameter("diaSemana", diaSemana).getResultList();
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<CartaDelDia> getCartasDelDiaActivas() {
        EntityManager em = EMF.getEMF().createEntityManager();
        try {
            return em.createQuery("SELECT c FROM CartaDelDia c WHERE c.activa = true", CartaDelDia.class).getResultList();
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<CartaDelDia> getCartasDelDiaByDiaDeSemanaAndActivas(DiaSemana diaSemana) {
        EntityManager em = EMF.getEMF().createEntityManager();
        try {
            return em.createQuery("SELECT c FROM CartaDelDia c WHERE c.diaSemana = :diaSemana AND c.activa = true", CartaDelDia.class).setParameter("diaSemana", diaSemana).getResultList();
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<CartaDelDia> getCartasDelDiaByDateRange(LocalDate fechaInicio, LocalDate fechaFin) {
        EntityManager em = EMF.getEMF().createEntityManager();
        try {
            return em.createQuery("SELECT c FROM CartaDelDia c WHERE c.fechaInicio >= :fechaInicio AND c.fechaFin <= :fechaFin", CartaDelDia.class).setParameter("fechaInicio", fechaInicio).setParameter("fechaFin", fechaFin).getResultList();
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }
}
