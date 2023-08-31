package com.ap.homebanking.configurations;

import com.ap.homebanking.models.Client;
import com.ap.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebAuthentication extends GlobalAuthenticationConfigurerAdapter {
    private ClientRepository clientRepository;
    @Autowired
    public void setClientRepository( ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService( inputName-> {
            Client client = clientRepository.findByEmail( inputName );
            if (client != null) {
                if( client.getEmail().toLowerCase().contains("@admin") ){
                    return new  User( client.getEmail(), client.getPassword(), AuthorityUtils.createAuthorityList("ADMIN") );
                }
                return new User( client.getEmail(), client.getPassword(), AuthorityUtils.createAuthorityList("CLIENT") );
            } else {
                throw new UsernameNotFoundException( "El cliente " + inputName+ "no existe. " );
            }
        } );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
// En task 6:
// WebAuthentication Falta encapsular la propiedad. Falta crear el rol de Admin. Los permitAll() por lo general se dejan arriba de todo
/*
Correccion task5 : Ok Leandro!! Fijate que fienes codigo sepaguetti en tus clases, hay cosas mezcladas por ejemplo en tu clase Cient tienes
 propiedades entre tus guetters y setters al igual que los metodos propios de la clase no deberian estar entre tus guetters y setters.
  En account tienes el mismo problema  Fijate que en clientLoan lo has acomodado sabiendo que era ya para ti mas facil de leer
  En card ya de nuevo el client que tienes dentro de la clase card es una propiedad de card y deberia de estar con el resto de tus propiedades
   Pero bueno son cositas que no afectan en funcionamiento pero seran de gran aporte para ti y tu equipo cuando tengan que  buscar algo en
   especifico o hacerle mantenimiento a tu codigo.. te dejo la tarea aprobada!!
 */
/*
task 7
Calificación: Hay que validar que los números random de la cuenta y tarjeta no existan en la base de datos antes de instanciar los objetos.
 Hay que validar lo que llega por parámetro no este vacío como el color. El mensaje que hay que retornar no hay que ponerlo en un println,
 hay que mandarlo con el ResponseEntity. Hay que crear un rol Admin.
 */