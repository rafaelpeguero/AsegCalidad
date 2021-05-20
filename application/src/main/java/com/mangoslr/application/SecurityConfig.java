package com.mangoslr.application;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;

@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                //Dashboard
                .antMatchers("/dashboard").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_EMPLEADO", "ROLE_VENDEDOR")
                .antMatchers("/dashboard/usuario").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_EMPLEADO", "ROLE_VENDEDOR", "ROLE_CLIENTE")
                .antMatchers("/dashboard/notificaciones").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_EMPLEADO", "ROLE_VENDEDOR", "ROLE_CLIENTE")
                .antMatchers("/dashboard/notificaciones/**//**").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_EMPLEADO", "ROLE_VENDEDOR", "ROLE_CLIENTE")
                .antMatchers("/dashboard/historial_venta").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_EMPLEADO", "ROLE_VENDEDOR", "ROLE_CLIENTE")
                .antMatchers("/dashboard/facturar").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_EMPLEADO", "ROLE_VENDEDOR")
                .antMatchers("/dashboard/facturar/**//**").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_EMPLEADO", "ROLE_VENDEDOR")
                .antMatchers("/dashboard/pedidos").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_EMPLEADO")
                .antMatchers("/dashboard/pedidos/**//**").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_EMPLEADO")
                .antMatchers("/dashboard/controldeproductos").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_EMPLEADO")
                .antMatchers("/dashboard/controldeproductos/**//**").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_EMPLEADO")
                .antMatchers("/dashboard/controldeusuarios").hasAuthority("ROLE_ADMINISTRADOR")
                .antMatchers("/dashboard/controldeusuarios/**//**").hasAuthority("ROLE_ADMINISTRADOR")
                .antMatchers("/dashboard/autorizacion").hasAuthority("ROLE_ADMINISTRADOR")
                .antMatchers("/dashboard/autorizacion/**//**").hasAuthority("ROLE_ADMINISTRADOR")
                //.antMatchers("/dashboard/**//**").permitAll()
                //Resources
                .antMatchers("/img/**//**").permitAll()
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMINISTRADOR")
                .antMatchers("/css/**//**").permitAll()
                .antMatchers("/js/**//**").permitAll()
                .antMatchers("/svg/**//**").permitAll()
                .antMatchers("/fonts/**//**").permitAll()
                .antMatchers("/sub/p/**//**").permitAll()
                .antMatchers("/o/**//**").permitAll()
                //login
                .antMatchers("/login*").anonymous()
                .antMatchers("/recuperar").anonymous()
                .antMatchers("/restablecer").anonymous()
                .antMatchers("/registrar").anonymous()
                .antMatchers("/cambiar/clave").anonymous()



                //Tienda
                .antMatchers("/v/carrito/**//**").permitAll()
                .antMatchers("/v/").permitAll()
                .antMatchers("/v/producto/**//**").permitAll()
                .antMatchers("/v/catalogo/**//**").permitAll()
                .antMatchers("/v/checkout").fullyAuthenticated()
                .antMatchers("/v/checkout/**//**").fullyAuthenticated()
                //region API
                .antMatchers("/api/producto/registrar").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_EMPLEADO")
                .antMatchers("/api/recuperar/clave").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_EMPLEADO")
                .antMatchers("/api/producto/contar").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_EMPLEADO")
                .antMatchers("/api/producto/fetch").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_EMPLEADO")
                .antMatchers("/api/usuario/registrar").anonymous()
                .antMatchers("/api/usuario/selectpick").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_EMPLEADO")
                .antMatchers("/api/tienda/**//**").permitAll()
                .antMatchers("/api/facturacion/registrar").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_EMPLEADO")
                //region API Usuario
                .antMatchers("/api/controldeusuarios/validar").hasAuthority("ROLE_ADMINISTRADOR")
                .antMatchers("/api/controldeusuarios/agregar").hasAuthority("ROLE_ADMINISTRADOR")
                .antMatchers("/api/controldeusuarios/modificarPerfil").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_EMPLEADO", "ROLE_VENDEDOR", "ROLE_CLIENTE")
                .antMatchers("/api/controldeusuarios/cambiarClave").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_EMPLEADO", "ROLE_VENDEDOR", "ROLE_CLIENTE")

                .antMatchers("/api/controldeusuarios/fetch").hasAuthority("ROLE_ADMINISTRADOR")
                .antMatchers("/api/controldeusuarios/contar").hasAuthority("ROLE_ADMINISTRADOR")
                //endregion
                .antMatchers("/api/autorizacion/autorizar").hasAuthority("ROLE_ADMINISTRADOR")
                .antMatchers("/api/autorizacion/**//**").hasAuthority("ROLE_ADMINISTRADOR")
                //region Historial
                .antMatchers("/api/historial/historialProductos").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_EMPLEADO", "ROLE_VENDEDOR", "ROLE_CLIENTE")
                .antMatchers("/api/historial/contarProductos").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_EMPLEADO", "ROLE_VENDEDOR", "ROLE_CLIENTE")
                .antMatchers("/api/historial/fetch").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_EMPLEADO")
                .antMatchers("/api/historial/contar").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_EMPLEADO")
                .antMatchers("/api/historial/**//**").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_EMPLEADO", "ROLE_VENDEDOR", "ROLE_CLIENTE")
                .antMatchers("/api/historial/fetchNotificaciones").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_EMPLEADO", "ROLE_VENDEDOR", "ROLE_CLIENTE")
                .antMatchers("/api/historial/contarNotificaciones").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_EMPLEADO", "ROLE_VENDEDOR", "ROLE_CLIENTE")
                .antMatchers("/api/historial/marcarTodas").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_EMPLEADO", "ROLE_VENDEDOR", "ROLE_CLIENTE")
                .antMatchers("/api/historial/dashboardData").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_EMPLEADO", "ROLE_VENDEDOR")
                .antMatchers("/dashboard/historial/activar").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_EMPLEADO")
                .antMatchers("/dashboard/historial/activar/**//**").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_EMPLEADO")
                .antMatchers("/api/historial/**//**").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_EMPLEADO", "ROLE_VENDEDOR", "ROLE_CLIENTE")
                //endregion
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login")
                .and().logout().logoutUrl("/perform_logout").deleteCookies("JSESSION").logoutSuccessUrl("/")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/access-denied");
        ;
    }
}
