package Client;

import Entity.Car;
import Entity.Header;
import Gateway.Gateway;

import java.rmi.Naming;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Cliente2 {
    public static void main(String[] args) {
        try {
            Gateway gateway = (Gateway) Naming.lookup("//localhost:1092/gateway");
            Scanner entry = new Scanner (System.in);
            Date data = new Date();

            System.out.println("BEM VINDO AO SISTEMA CLIENTE 1");

            while(true){
                System.out.println("DIGITE SEU USUARIO");
                String user = entry.nextLine();

                System.out.println("DIGITE SUA SENHA");
                String senha = entry.nextLine();

                String resultLogin = gateway.authenticate(user,senha, new Header("user", "server", "auth"));

                if(resultLogin.equals("cliente")){
                    while(true) {
                        System.out.println("DIGITE OQ DESEJA FAZER");
                        System.out.println("1-VER CARROS DISPONIVEIS");
                        System.out.println("2-PESQUISAR UM CARRO");
                        System.out.println("3-COMPRAR UM CARRO");
                        System.out.println("4-SAIR");


                        String option = entry.nextLine();

                        switch (option) {
                            case "1": {
                                List<String> carsList = gateway.listCars(new Header("client", "server", "listCars"));
                                System.out.println("Listando carros disponiveis");
                                for (String carInfo : carsList) {
                                    System.out.println(carInfo);
                                }
                                break;
                            }
                            case "2": {
                                System.out.println("Digite o renavam");
                                String renavam = entry.nextLine();

                                System.out.println(gateway.findCarByRenavan(renavam, new Header("client", "server", "findCarByRenavam")));
                                break;
                            }
                            case "3": {
                                System.out.println("Digite o renavam ");
                                String renavam = entry.nextLine();
                                System.out.println(gateway.buyCar(renavam, new Header("client", "server", "buyCar")));
                                break;
                            }
                            default:
                                break;
                        }
                        if(option.equals("4")){
                            break;
                        }
                    }
                }else if(resultLogin.equals("funcionario")){
                    while(true) {
                        System.out.println("DIGITE OQ DESEJA FAZER");
                        System.out.println("1-VER CARROS DISPONIVEIS");
                        System.out.println("2-ADICIONAR UM NOVO CARRO");
                        System.out.println("3-EXCLUIR UM CARRO");
                        System.out.println("4-PESQUISAR UM CARRO");
                        System.out.println("5-EXIBIR QUANTIDADE DE CARROS");
                        System.out.println("6-SAIR");


                        String option = entry.nextLine();

                        switch (option) {
                            case "1": {
                                List<String> carsList = gateway.listCars(new Header("funcionario", "server", "listCars"));
                                System.out.println("Listando carros disponiveis");
                                for (String carInfo : carsList) {
                                    System.out.println(carInfo);
                                }
                                break;
                            }
                            case "2": {
                                System.out.println("Digite o renavam");
                                String renavam = entry.nextLine();

                                System.out.println("Digite o modelo");
                                String modelo = entry.nextLine();

                                System.out.println("Digite a categoria");
                                String categoria = entry.nextLine();

                                System.out.println("Digite o ano");
                                String ano = entry.nextLine();

                                System.out.println("Digite a quantidade");
                                String qtd = entry.nextLine();

                                System.out.println("Digite o preco");
                                double preco = entry.nextDouble();
                                Car newCar = new Car(renavam, modelo, categoria, Integer.parseInt(ano), Integer.parseInt(qtd), preco);
                                boolean carAdded = gateway.addCar(newCar, new Header("funcionario", "server", "addCar"));
                                if (carAdded) {
                                    System.out.println("Carro adicionado com sucesso");
                                } else {
                                    System.out.println("Falha ao adicionar o carro");
                                }
                                break;
                            }
                            case "3": {
                                System.out.println("Digite o renavam");
                                String renavam = entry.nextLine();

                                System.out.println(gateway.removeCar(renavam, new Header("funcionario", "server", "removeCar")));
                                break;
                            }
                            case "4": {
                                System.out.println("Digite o renavam");
                                String renavam = entry.nextLine();

                                System.out.println(gateway.findCarByRenavan(renavam, new Header("funcionario", "server", "findCarByRenavan")));
                                break;
                            }
                            case "5": {
                                System.out.println("numero de carros disponiveis no total: ");
                                System.out.println(gateway.getNumberOfCars(new Header("funcionario", "server", "getNumberOfCars")));
                                break;
                            }
                            default:
                                break;
                        }
                        if(option.equals("6")){
                            break;
                        }
                    }
                }else{
                    System.out.println(resultLogin);
                }


            }
        } catch (Exception e) {
            System.err.println("Server.Car.Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
