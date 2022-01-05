package pt.uc.dei;

import data.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

import static javax.ws.rs.client.Entity.entity;

public class App 
{
    public static void main( String[] args ) throws IOException {
        WebTarget target = null;
        Response response;
        Client client = ClientBuilder.newClient();
        int selectedOption;
        Scanner scan = new Scanner(System.in);
        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(in);
        String name, password,answer;
        int manager_id;
        float exchange;

        while(true){
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println("-------------------------Rest CLI-------------------------\n");
            System.out.println("1 - Adicionar manager");
            System.out.println("2 - Adicionar cliente");
            System.out.println("3 - Adicionar moeda");
            System.out.println("4 - Listar managers");
            System.out.println("5 - Listar clientes");
            System.out.println("6 - Listar moedas");
            System.out.println("7 - Mostrar crédito cliente");
            System.out.println("8 - Mostrar pagamentos cliente");
            System.out.println("9 - Mostrar saldo cliente");
            System.out.println("10 - Mostrar total pagamentos");
            System.out.println("11 - Mostrar total saldos");
            System.out.println("12 - Mostrar total créditos");
            System.out.print(">");
            selectedOption = scan.nextInt();
            switch (selectedOption){
                case 1:
                    System.out.println("Nome:");
                    name = reader.readLine();

                    System.out.println("Password:");
                    password = reader.readLine();

                    target = client.target("http://localhost:8080/rest/services/myservice/manager");
                    Manager p = new Manager(name, password);
                    Entity<Manager> input = entity(p, MediaType.APPLICATION_JSON);
                    response = target.request().post(input);
                    answer = response.readEntity(String.class);
                    System.out.println("RESPONSE: " + answer);
                    response.close();
                    break;
                case 2:
                    System.out.println("Nome:");
                    name = reader.readLine();

                    System.out.println("Id do manager:");
                    manager_id = Integer.parseInt(reader.readLine());

                    target = client.target("http://localhost:8080/rest/services/myservice/client");
                    Clients c = new Clients(name, manager_id);
                    Entity<Clients> input2 = entity(c, MediaType.APPLICATION_JSON);
                    response = target.request().post(input2);
                    answer = response.readEntity(String.class);
                    System.out.println("RESPONSE: " + answer);
                    response.close();
                    break;
                case 3:
                    System.out.println("Nome moeda:");
                    name = reader.readLine();

                    System.out.println("Exchange rate:");
                    exchange = Float.parseFloat(reader.readLine());

                    target = client.target("http://localhost:8080/rest/services/myservice/currency");
                    Currency cur = new Currency(name, exchange);
                    Entity<Currency> input3 = entity(cur, MediaType.APPLICATION_JSON);
                    response = target.request().post(input3);
                    answer = response.readEntity(String.class);
                    System.out.println("RESPONSE: " + answer);
                    response.close();
                    break;
                case 4:
                    target = client.target("http://localhost:8080/rest/services/myservice/manager");
                    response = target.request().get();
                    List<Manager> managers = response.readEntity(new GenericType<List<Manager>>(){});
                    System.out.println("Managers: " + managers);
                    response.close();
                    break;
                case 5:
                    target = client.target("http://localhost:8080/rest/services/myservice/client");
                    response = target.request().get();
                    List<Clients> clients = response.readEntity(new GenericType<List<Clients>>(){});
                    System.out.println("Clientes: " + clients);
                    response.close();
                    break;
                case 6:
                    target = client.target("http://localhost:8080/rest/services/myservice/currency");
                    response = target.request().get();
                    List<Currency> currencies = response.readEntity(new GenericType<List<Currency>>(){});
                    System.out.println("Moedas: " + currencies);
                    response.close();
                    break;
                case 7:
                    target = client.target("http://localhost:8080/rest/services/myservice/client");
                    response = target.request().get();
                    List<Clients> clients2 = response.readEntity(new GenericType<List<Clients>>(){});
                    for( int i = 0; i<clients2.size(); i++){
                        System.out.println("<" + i + "> " + clients2.get(i).getName());
                    }
                    System.out.println("Escolha o cliente:");
                    int n = scan.nextInt();
                    Clients clientSelected = clients2.get(n);

                    target = client.target("http://localhost:8080/rest/services/myservice/client/credit");
                    target = target.queryParam("id", clientSelected.getId());
                    response = target.request().get();
                    float credit = response.readEntity(float.class);
                    System.out.println("Credit: " + credit);
                    break;
                case 8:
                    target = client.target("http://localhost:8080/rest/services/myservice/client");
                    response = target.request().get();
                    List<Clients> clients3 = response.readEntity(new GenericType<List<Clients>>(){});
                    for( int i = 0; i<clients3.size(); i++){
                        System.out.println("<" + i + "> " + clients2.get(i).getName());
                    }
                    System.out.println("Escolha o cliente:");
                    n = scan.nextInt();
                    Clients clientSelected2 = clients2.get(n);

                    target = client.target("http://localhost:8080/rest/services/myservice/client/payments");
                    target = target.queryParam("id", clientSelected.getId());
                    response = target.request().get();
                    float payments = response.readEntity(float.class);
                    System.out.println("Payments: " + payments);
                    break;
                case 9:
                    target = client.target("http://localhost:8080/rest/services/myservice/client");
                    response = target.request().get();
                    List<Clients> clients4 = response.readEntity(new GenericType<List<Clients>>(){});
                    for( int i = 0; i<clients4.size(); i++){
                        System.out.println("<" + i + "> " + clients2.get(i).getName());
                    }
                    System.out.println("Escolha o cliente:");
                    n = scan.nextInt();
                    Clients clientSelected3 = clients2.get(n);

                    target = client.target("http://localhost:8080/rest/services/myservice/client/balance");
                    target = target.queryParam("id", clientSelected.getId());
                    response = target.request().get();
                    float balance = response.readEntity(float.class);
                    System.out.println("Balance: " + balance);
                    break;
                case 10:
                    target = client.target("http://localhost:8080/rest/services/myservice/client/credit/total");
                    response = target.request().get();
                    float allCredits = response.readEntity(float.class);
                    System.out.println("Total Créditos: " + allCredits);
                    response.close();
                    break;
                case 11:
                    target = client.target("http://localhost:8080/rest/services/myservice/client/payments/total");
                    response = target.request().get();
                    float allPayments = response.readEntity(float.class);
                    System.out.println("Total Pagamentos: " + allPayments);
                    response.close();
                    break;
                case 12:
                    target = client.target("http://localhost:8080/rest/services/myservice/client/balance/total");
                    response = target.request().get();
                    float allBalances = response.readEntity(float.class);
                    System.out.println("Total Saldo: " + allBalances);
                    response.close();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + selectedOption);
            }
        }
    }
}
