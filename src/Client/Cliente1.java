package Client;

import Entity.Car;
import Gateway.Gateway;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class Cliente1 {
    public static void main(String[] args) {
        try {
            Gateway gateway = (Gateway) Naming.lookup("//localhost/Gateway");
            Scanner entry = new Scanner (System.in);

            System.out.println("BEM VINDO AO SISTEMA CLIENTE 1");

            while(true){
                System.out.println("DIGITE SEU USUARIO");
                String user = entry.nextLine();

                System.out.println("DIGITE SUA SENHA");
                String senha = entry.nextLine();

                if(gateway.authenticate(user,senha).equals("cliente")){
                    while(true) {
                        System.out.println("DIGITE OQ DESEJA FAZER");
                        System.out.println("1-VER CARROS DISPONIVEIS");
                        System.out.println("2-PESQUISAR UM CARRO");
                        System.out.println("3-COMPRAR UM CARRO");
                        System.out.println("4-SAIR");


                        String option = entry.nextLine();

                        switch (option) {
                            case "1": {
                                List<String> carsList = gateway.listCars();
                                System.out.println("Listando carros disponiveis");
                                for (String carInfo : carsList) {
                                    System.out.println(carInfo);
                                }
                                break;
                            }
                            case "2": {
                                System.out.println("Digite o renavam");
                                String renavam = entry.nextLine();

                                System.out.println(gateway.findCarByRenavan(renavam));
                                break;
                            }
                            case "3": {
                                System.out.println("Digite o renavam ");
                                String renavam = entry.nextLine();
                                System.out.println(gateway.buyCar(renavam));
                                break;
                            }
                            default:
                                break;
                        }
                        if(option.equals("3")){
                            break;
                        }
                    }
                }else if(gateway.authenticate(user,senha).equals("funcionario")){
                    while(true) {
                        System.out.println("DIGITE OQ DESEJA FAZER");
                        System.out.println("1-VER CARROS DISPONIVEIS");
                        System.out.println("2-ADICIONAR UM NOVO CARRO");
                        System.out.println("3-EXCLUIR UM CARRO");
                        System.out.println("4-PESQUISAR UM CARRO");
                        System.out.println("5-EXIBIR QUANTIDADE DE CARROS");
                        System.out.println("7-SAIR");


                        String option = entry.nextLine();

                        switch (option) {
                            case "1": {
                                List<String> carsList = gateway.listCars();
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
                                boolean carAdded = gateway.addCar(newCar);
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

                                System.out.println(gateway.removeCar(renavam));
                                break;
                            }
                            case "4": {
                                System.out.println("Digite o renavam");
                                String renavam = entry.nextLine();

                                System.out.println(gateway.findCarByRenavan(renavam));
                                break;
                            }
                            case "5": {
                                System.out.println("numero de carros disponiveis no total: ");
                                System.out.println(gateway.getNumberOfCars());
                                break;
                            }
                            default:
                                break;
                        }
                        if(option.equals("3")){
                            break;
                        }
                    }
                }else{
                    System.out.println(gateway.authenticate(user,senha));
                }


            }
        } catch (Exception e) {
            System.err.println("Server.Car.Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

}
