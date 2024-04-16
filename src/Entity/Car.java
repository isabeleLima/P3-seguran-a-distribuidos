package Entity;

import java.io.Serializable;

public class Car implements Serializable {
    private String renavan;
    private String nome;
    private String categoria;
    private int anoFabricacao;
    private int quantidade;
    private double preco;

    public Car(String renavan, String nome, String categoria, int anoFabricacao, int quantidade, double preco) {
        this.renavan = renavan;
        this.nome = nome;
        this.categoria = categoria;
        this.anoFabricacao = anoFabricacao;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public String getRenavan() {
        return renavan;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "Entity.Car{" +
                "renavan='" + renavan + '\'' +
                ", nome='" + nome + '\'' +
                ", categoria='" + categoria + '\'' +
                ", anoFabricacao=" + anoFabricacao +
                ", quantidade=" + quantidade +
                ", preco=" + preco +
                '}';
    }

    public static class Client {
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
}
