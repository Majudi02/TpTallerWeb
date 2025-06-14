package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.presentacion.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ServicioUsuarioImpl implements ServicioUsuario {

    private final RepositorioUsuarioNutriya repositorioUsuario;

    @Autowired
    public ServicioUsuarioImpl(RepositorioUsuarioNutriya repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public void registrarUsuario(UsuarioDTO usuarioDTO) {
        // Validar si ya existe usuario con ese email
        UsuarioNutriya existente = repositorioUsuario.buscarPorEmail(usuarioDTO.getEmail());
        if (existente != null) {
            throw new IllegalArgumentException("Email ya registrado");
        }

        UsuarioNutriya usuario;
        if ("cliente".equalsIgnoreCase(usuarioDTO.getTipoUsuario())) {
            Cliente cliente = new Cliente();
            cliente.setEmail(usuarioDTO.getEmail());
            cliente.setPassword(usuarioDTO.getPassword());
            cliente.setNombre(usuarioDTO.getNombre());
            cliente.setTokenConfirmacion(usuarioDTO.getTokenConfirmacion());
            cliente.setConfirmado(usuarioDTO.getConfirmado());

            cliente.setEdad(usuarioDTO.getEdad());
            cliente.setPesoActual(usuarioDTO.getPesoActual());
            cliente.setPesoDeseado(usuarioDTO.getPesoDeseado());
            cliente.setAltura(usuarioDTO.getAltura());
            cliente.setObjetivo(usuarioDTO.getObjetivo());
            usuario = cliente;

        } else if ("restaurante".equalsIgnoreCase(usuarioDTO.getTipoUsuario())) {
            UsuarioRestaurante usuarioRestaurante = new UsuarioRestaurante();
            usuarioRestaurante.setEmail(usuarioDTO.getEmail());
            usuarioRestaurante.setPassword(usuarioDTO.getPassword());

            Restaurante restaurante = new Restaurante();
            restaurante.setNombre(usuarioDTO.getNombre());
            restaurante.setDescripcion(usuarioDTO.getDescripcion());
            restaurante.setCalle(usuarioDTO.getCalle());
            restaurante.setNumero(usuarioDTO.getNumero());
            restaurante.setLocalidad(usuarioDTO.getLocalidad());
            restaurante.setZona(usuarioDTO.getZona());
            restaurante.setTiposComida(usuarioDTO.getTipoComidas());
            restaurante.setImagen(usuarioDTO.getImagen());
            usuarioRestaurante.setRestaurante(restaurante);

            usuarioRestaurante.setTokenConfirmacion(usuarioDTO.getTokenConfirmacion());
            usuarioRestaurante.setConfirmado(usuarioDTO.getConfirmado());

            usuario = usuarioRestaurante;

        } else if ("repartidor".equalsIgnoreCase(usuarioDTO.getTipoUsuario())) {
            Repartidor repartidor = new Repartidor();
            repartidor.setEmail(usuarioDTO.getEmail());
            repartidor.setPassword(usuarioDTO.getPassword());
            repartidor.setNombre(usuarioDTO.getNombre());
            repartidor.setApellido(usuarioDTO.getApellido());
            repartidor.setDni(usuarioDTO.getDni());
            repartidor.setTelefono(usuarioDTO.getTelefono());
            repartidor.setVehiculo(usuarioDTO.getVehiculo());
            repartidor.setTokenConfirmacion(usuarioDTO.getTokenConfirmacion());
            repartidor.setConfirmado(usuarioDTO.getConfirmado());
            usuario = repartidor;
        } else {
            throw new IllegalArgumentException("Tipo de usuario inválido");
        }

        repositorioUsuario.guardar(usuario);
    }

    @Override
    public UsuarioDTO getUsuario(String email) {
        UsuarioNutriya usuario = repositorioUsuario.buscarPorEmail(email);
        if (usuario == null) return null;
        return mapToDTO(usuario);
    }

    @Override
    public UsuarioDTO validarUsuario(String email, String password) {
        UsuarioNutriya usuario = repositorioUsuario.buscarPorEmailYPassword(email, password);
        if (usuario == null) return null;
        return mapToDTO(usuario);
    }

    @Override
    public UsuarioDTO buscarPorTokenConfirmacion(String token) {
        UsuarioNutriya usuario = repositorioUsuario.buscarPorTokenConfirmacion(token);
        return mapToDTO(usuario);
    }

    @Override
    public Boolean confirmarUsuarioPorToken(String token) {
        UsuarioNutriya usuario = repositorioUsuario.buscarPorTokenConfirmacion(token);
        if (usuario != null && !usuario.getConfirmado()) {
            usuario.setConfirmado(true);
            usuario.setTokenConfirmacion(null);
            repositorioUsuario.guardar(usuario);
            return true;
        }
        return false;
    }

    @Override
    public void editarUsuario(UsuarioDTO usuarioDTO) {
        UsuarioNutriya usuarioExistente = repositorioUsuario.buscarPorEmail(usuarioDTO.getEmail());
        if (usuarioExistente == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        if (usuarioDTO.getPassword() != null && !usuarioDTO.getPassword().isBlank()) {
            usuarioExistente.setPassword(usuarioDTO.getPassword());
        }

        if (usuarioExistente instanceof Cliente && "cliente".equalsIgnoreCase(usuarioDTO.getTipoUsuario())) {
            Cliente cliente = (Cliente) usuarioExistente;
            cliente.setNombre(usuarioDTO.getNombre());
            cliente.setEdad(usuarioDTO.getEdad());
            cliente.setPesoActual(usuarioDTO.getPesoActual());
            cliente.setPesoDeseado(usuarioDTO.getPesoDeseado());
            cliente.setAltura(usuarioDTO.getAltura());
            cliente.setObjetivo(usuarioDTO.getObjetivo());

        } else if (usuarioExistente instanceof UsuarioRestaurante && "restaurante".equalsIgnoreCase(usuarioDTO.getTipoUsuario())) {
            UsuarioRestaurante usuarioRestaurante = (UsuarioRestaurante) usuarioExistente;
            Restaurante restaurante = usuarioRestaurante.getRestaurante();

            restaurante.setNombre(usuarioDTO.getNombre());
            restaurante.setDescripcion(usuarioDTO.getDescripcion());
            restaurante.setCalle(usuarioDTO.getCalle());
            restaurante.setNumero(usuarioDTO.getNumero());
            restaurante.setLocalidad(usuarioDTO.getLocalidad());
            restaurante.setZona(usuarioDTO.getZona());
            restaurante.setTiposComida(usuarioDTO.getTipoComidas());

            // Si el campo imagen no es null, lo actualizamos
            if (usuarioDTO.getImagen() != null) {
                restaurante.setImagen(usuarioDTO.getImagen());
            }

        } else if (usuarioExistente instanceof Repartidor && "repartidor".equalsIgnoreCase(usuarioDTO.getTipoUsuario())) {
            Repartidor repartidor = (Repartidor) usuarioExistente;
            repartidor.setNombre(usuarioDTO.getNombre());
            repartidor.setApellido(usuarioDTO.getApellido());
            repartidor.setDni(usuarioDTO.getDni());
            repartidor.setTelefono(usuarioDTO.getTelefono());
            repartidor.setVehiculo(usuarioDTO.getVehiculo());

        } else {
            throw new IllegalArgumentException("Tipo de usuario inválido o no coincide con el tipo guardado");
        }

        // Guardamos los cambios
        repositorioUsuario.guardar(usuarioExistente);
    }


    private UsuarioDTO mapToDTO(UsuarioNutriya usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setEmail(usuario.getEmail());
        dto.setConfirmado(usuario.getConfirmado());
        dto.setId(usuario.getId());

        if (usuario instanceof Cliente) {
            Cliente c = (Cliente) usuario;
            dto.setNombre(c.getNombre());
            dto.setEdad(c.getEdad());
            dto.setPesoActual(c.getPesoActual());
            dto.setPesoDeseado(c.getPesoDeseado());
            dto.setAltura(c.getAltura());
            dto.setObjetivo(c.getObjetivo());
            dto.setTipoUsuario("cliente");
        } else if (usuario instanceof UsuarioRestaurante) {
            UsuarioRestaurante ur = (UsuarioRestaurante) usuario;
            Restaurante r = ur.getRestaurante();
            dto.setNombre(r.getNombre());
            dto.setDescripcion(r.getDescripcion());
            dto.setCalle(r.getCalle());
            dto.setNumero(r.getNumero());
            dto.setLocalidad(r.getLocalidad());
            dto.setZona(r.getZona());
            dto.setTipoComidas(r.getTiposComida());
            dto.setTipoUsuario("restaurante");
            dto.setImagen(r.getImagen());
        } else if (usuario instanceof Repartidor) {
            Repartidor rep = (Repartidor) usuario;
            dto.setNombre(rep.getNombre());
            dto.setApellido(rep.getApellido());
            dto.setDni(rep.getDni());
            dto.setTelefono(rep.getTelefono());
            dto.setVehiculo(rep.getVehiculo());
            dto.setTipoUsuario("repartidor");
        }

        return dto;
    }
}
