package Entity;

import java.io.Serializable;

public class Client implements Serializable {
    String name;

    String user;

    String senha;

    String tipo;

    public Client(String name, String user, String senha, String tipo) {
        this.name = name;
        this.user = user;
        this.senha = senha;
        this.tipo = tipo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}