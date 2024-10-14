package org.example.ttps2024grupo15.dao.usuario.impl;

import jakarta.persistence.EntityManager;
import org.example.ttps2024grupo15.dao.GenericDAOHibernateJPA;
import org.example.ttps2024grupo15.dao.entitiManager.EMF;
import org.example.ttps2024grupo15.dao.usuario.UsuarioDAO;
import org.example.ttps2024grupo15.model.permiso.Rol;
import org.example.ttps2024grupo15.model.usuario.Usuario;

import java.util.List;

public abstract class UsuarioDAOHibernateJPA<T extends Usuario> extends GenericDAOHibernateJPA<T> implements UsuarioDAO<T> {

    public UsuarioDAOHibernateJPA(Class<T> entityClass) {
        super(entityClass);
    }
    @Override
    public T getByEmail(String email) {;
        EntityManager em = EMF.getEMF().createEntityManager();
        try {
            return em.createQuery("SELECT u FROM " + this.clasePersistente.getSimpleName() + " u WHERE u.email = :email", this.clasePersistente).setParameter("email", email).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }
    @Override
    public T getByDni(int dni) {;
        EntityManager em = EMF.getEMF().createEntityManager();
        try{
            return em.createQuery("SELECT u FROM "+this.clasePersistente.getSimpleName()+" u WHERE u.dni = :dni", this.clasePersistente).setParameter("dni", dni).getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<T> getUsuariosPorRol(Rol rol){
        EntityManager em = EMF.getEMF().createEntityManager();
        try{
            return em.createQuery("SELECT u FROM "+this.clasePersistente.getSimpleName()+" u WHERE u.rol = :rol", this.clasePersistente).setParameter("rol", rol).getResultList();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<T> getUsuarioPorNombre(String nombre) {
        EntityManager em = EMF.getEMF().createEntityManager();
        try{
            return em.createQuery("SELECT u FROM "+this.clasePersistente.getSimpleName()+" u WHERE u.nombre LIKE :nombre", this.clasePersistente).setParameter("nombre", "%"+nombre+"%").getResultList();
            }catch (Exception e){
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<T> getUsuarioPorApellido(String apellido) {
        EntityManager em = EMF.getEMF().createEntityManager();
        try{
            return em.createQuery("SELECT u FROM "+this.clasePersistente.getSimpleName()+" u WHERE u.apellido LIKE :apellido", this.clasePersistente).setParameter("apellido", "%"+apellido+"%").getResultList();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }





}
