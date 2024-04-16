package Services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import Entity.Car;

public class CarServiceImpl extends UnicastRemoteObject implements CarService{

    public List<Car> carList;
    public String adress;
    public SincService sincServe;

    public boolean ocupado = false;
    public CarServiceImpl(SincService sincServe, String adress) throws RemoteException {
        super();
        this.carList = new ArrayList<>();
        this.sincServe = sincServe;
        this.adress = adress;
    }

    @Override
    public String getCarInfo(String renavan) throws RemoteException, InterruptedException {
        this.carList = getSincronized(this.sincServe);
        for (Car car : carList) {
            if (car.getRenavan().equals(renavan)) {
                return car.toString();
            }
        }
        setOcupado(false);
        return "Carro não encontrado";
    }

    @Override
    public boolean addCar(Car car) throws RemoteException, InterruptedException {
        this.carList = getSincronized(this.sincServe);
        setOcupado(false);
        return carList.add(car);
    }

    @Override
    public List<String> listCars() throws RemoteException, InterruptedException {
        this.sincServe.AddToQueue(this);


        this.carList = getSincronized(this.sincServe);
        List<String> carInfoList = new ArrayList<>();
        for (Car car : carList) {
            carInfoList.add(car.toString());
        }

        this.sincServe.removeFromQueue();
        setOcupado(false);
        return carInfoList;
    }

    @Override
    public String removeCar(String renavan) throws RemoteException, InterruptedException {
        this.sincServe.AddToQueue(this);


        Iterator<Car> iterator = carList.iterator();
        while (iterator.hasNext()) {
            Car car = iterator.next();
            if (car.getRenavan().equals(renavan)) {
                iterator.remove();
                setSrinconized(sincServe);
                this.sincServe.removeFromQueue();
                return "carro removido com sucesso!";
            }
        }
        setOcupado(false);
        return "erro ao remover carro";
    }

    @Override
    public String findCarByRenavan(String renavan) throws RemoteException, InterruptedException {
        this.sincServe.AddToQueue(this);


        this.carList = getSincronized(this.sincServe);

        for (Car car : carList) {
            if (car.getRenavan().equals(renavan)) {
                this.sincServe.removeFromQueue();

                return car.toString();
            }
        }
        setOcupado(false);
        return "Carro não encontrado";
    }

    @Override
    public int getNumberOfCars() throws RemoteException, InterruptedException {
        this.sincServe.AddToQueue(this);


        this.carList = getSincronized(this.sincServe);
        this.sincServe.removeFromQueue();
        setOcupado(false);
        return carList.size();
    }

    @Override
    public String buyCar(String renavan) throws RemoteException, InterruptedException {
        this.sincServe.AddToQueue(this);

        this.carList = getSincronized(this.sincServe);

        for (Car car : carList) {
            if (car.getRenavan().equals(renavan)) {
                if (car.getQuantidade() > 0) {
                    car.setQuantidade(car.getQuantidade() - 1);

                    setSrinconized(sincServe);
                    this.sincServe.removeFromQueue();

                    return "Carro comprado com sucesso: " + car.toString();

                } else {
                    return "Erro: O carro não está mais disponível em estoque";
                }
            }
        }
        setOcupado(false);
        return "Carro não encontrado";
    }

    @Override
    public List<Car> getSincronized(SincService server) throws RemoteException, InterruptedException {
        boolean wait = true;
        while(wait){
            List<Car> lista = server.returnCars(this);
            if(lista != null){
                System.out.println("sincronização realizada");
                this.carList = lista;
                wait=false;

            }else{
                System.out.println("Aguardando para sincronização dos dados");
                Thread.sleep(3000);
            }

        }
       return this.carList;
    }

    @Override
    public void setSrinconized(SincService server) throws RemoteException, InterruptedException {
        boolean wait = true;
        while(wait){
            if(server.SincCars(this.carList,this) == true){
                System.out.println("sincronização realizada");
                wait=false;

            }else{
                System.out.println("Aguardando para sincronização dos dados");
                Thread.sleep(2000);

            }

        }
    }

    public String getAdress() {
        return adress;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }
}
