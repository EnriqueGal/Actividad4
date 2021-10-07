import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AddressBook {

    public HashMap<String,String> Contactos;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String numero,nombre;
        AddressBook addressBook = new AddressBook();

        addressBook.Load();
        System.out.println("\n"+"Agenda de Contactos");
        while (true){
            System.out.println("======================");
            System.out.println("Menu de opciones");
            System.out.println("======================"+"\n");
            System.out.println(" 1- Lista de Contactos");
            System.out.println(" 2- Crear Contacto");
            System.out.println(" 3- Borrar Contacto");
            System.out.println(" 4- Salir"+"\n");

            String menu = scanner.nextLine();

            if (menu.equals("4")){
                System.out.println("Saliendo de Agenda");
                break;
            }
            switch (menu) {
                case "1" -> {
                    addressBook.List();
                    scanner.nextLine();
                }
                case "2" -> {
                    System.out.println("Numero de Contacto a Guardar");
                    numero = scanner.nextLine();
                    System.out.println("Nombre de Contacto a Guardar");
                    nombre = scanner.nextLine();
                    addressBook.Create(numero, nombre);
                }
                case "3" -> {
                    System.out.println("Numero de Contacto a Borrar");
                    numero = scanner.nextLine();
                    addressBook.Delete(numero);
                }
                default -> System.out.println("Opcion erronea");
            }
        }
    }

    public void Load(){
        Path path = Paths.get("Agenda.csv");
        Contactos = new HashMap<>();
        File file = new File(path.toString());
        if (file.exists()){
            try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())){
                String fila = reader.readLine();
                while (fila != null){
                    String[] attributes = fila.split(",");
                    Contactos.put(attributes[0],attributes[1]);
                    fila = reader.readLine();
                }
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
        }
    }

    public void Save(){
        try (PrintWriter writer = new PrintWriter("Agenda.csv")){
            StringBuilder builder = new StringBuilder();
            for (Map.Entry<String,String> contacto: Contactos.entrySet()){
                builder.append(contacto.getKey());
                builder.append(',');
                builder.append(contacto.getValue());
                builder.append('\n');
            }
            writer.write(builder.toString());
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public void List(){
        System.out.println("======================");
        System.out.println("Contactos Registrados: ");
        System.out.println("======================");
        for (Map.Entry<String,String> contacto: Contactos.entrySet()){
            System.out.println(contacto.getKey() +", "+ contacto.getValue());
        }
    }

    public void Create(String Telefono, String Nombre){
        Contactos.put(Telefono,Nombre);
        Save();
    }

    public void Delete(String Telefono){
        Contactos.remove(Telefono);
        Save();
    }


}
