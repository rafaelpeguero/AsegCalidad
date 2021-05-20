package com.mangoslr.application.repositorios;

import com.mangoslr.application.model.Notificacion;
import org.springframework.data.repository.CrudRepository;

public interface NotificacionRepo extends CrudRepository<Notificacion, Integer> {
}
