package co.com.poli.shop.customer.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tbl_customers")
@Getter
@Setter
@AllArgsConstructor
public class Customer {

    public Customer() { }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El Nit/Documento no puede ser vacio")
    @Size( min = 10, message = "El tamaño del Nit/Documento es 10")
    @Column(name = "number_id", unique = true, nullable = false)
    private String numberID;


    @NotEmpty(message = "El Nombre no puede ser vacio")
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @NotEmpty(message = "El Email no puede ser vacio")
    @Email( message = "No es una direccion de email válido")
    @Column(unique = true, nullable = false)
    private String email;

    private String state;
}
