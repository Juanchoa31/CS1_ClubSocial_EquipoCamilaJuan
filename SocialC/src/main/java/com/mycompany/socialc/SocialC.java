package com.mycompany.socialc;
import com.mycompany.socialc.*;
import javax.swing.JOptionPane;
import ui.forms.HomeViewFormBill;

public class SocialC {
    private static Club club = new Club();
    
    public static void main(String[] args) {
        
        //opciones del menu
        while (true) {
        String menu = "1. Register a member\n" +
                      "2. Register a guest\n" +
                      "3. Pay a bill\n" +
                      "4. Register a consumption\n" +
                      "5. Increase funds\n" +
                      "6. Remove member\n" +
                      "7. List members\n" +
                      "8. Exit";
        
        //muestra menu y guarda opcion 
        String choice = JOptionPane.showInputDialog(menu);
        if (choice == null || choice.equals("8")) break; //con cancelar o opcion 8 sale del buclecito

        //segun opcion seleccionada ejecuta el case correspondiente
        switch (choice) {
            case "1" -> registerMember();
            case "2" -> registerGuest();
            case "3" -> payBill();
            case "4" -> registerConsumption();
            case "5" -> increaseFunds();
            case "6" -> removeMember();
            case "7" -> listMembers();
            default -> JOptionPane.showMessageDialog(null, "Invalid option");
        }
    }
}
    //lista miembros club
    private static void listMembers() {
        String members = club.listMembers();
        JOptionPane.showMessageDialog(null, members);
    }

    //registra un nuevo miembro
    private static void registerMember() {
        
        String id = JOptionPane.showInputDialog("Enter ID:");
        String name = JOptionPane.showInputDialog("Enter name:");
        String type = JOptionPane.showInputDialog("Enter type (VIP/Regular):");
        Partner partner = new Partner(id, name, type);
        
        if (club.addMember(partner)) {
            JOptionPane.showMessageDialog(null, "Member added");
        } else {
            JOptionPane.showMessageDialog(null, "Error adding member");
        }
    }

    //registra un invitado autorizado por un partner
    private static void registerGuest() {
        String partnerId = JOptionPane.showInputDialog("Enter member ID:");//id del partner
        Partner partner = club.members.get(partnerId);

        //verificando si miembro existe en el club
        if (partner == null) {
            JOptionPane.showMessageDialog(null, "Member not found.");
            return;
        }

        String guestName = JOptionPane.showInputDialog("Enter guest name:");
        Guest guest = new Guest(guestName);
        
        if (partner.addAuthorizedGuest(guest)) {
            JOptionPane.showMessageDialog(null, "Guest added.");
        } else {
            JOptionPane.showMessageDialog(null, "Could not add guest.");
        }
    }

    private static void payBill() {
        
        HomeViewFormBill menuBill = new HomeViewFormBill();//instancia del formulario de pago de facturas
        menuBill.setVisible(true); //muestra formulario en pantallita
        menuBill.setLocationRelativeTo(null); //centra formulario
    }   

    //registra un consumo de un partner o un invitado
    private static void registerConsumption() {
        String partnerId = JOptionPane.showInputDialog("Enter member ID:");//id del  miembro que hizo el consumo
        Partner partner = club.members.get(partnerId);

        if (partner == null) {//verifica si el miembro existe
            JOptionPane.showMessageDialog(null, "Member not found.");
            return;
        }

        //nombre si es invitado, si es el parner se deja en blanco
        String guestName = JOptionPane.showInputDialog("Enter guest name (leave blank if it's the member):");
        Guest guest = null;//inicializa gues para almacenar el invitado

        //si el campo no esta vacio, se busca el invitado en la lista
        if (!guestName.isEmpty()) { 
            for (Guest g : partner.authorizedGuests) {//recorre la lista de invitados
                if (g.name.equals(guestName)) {//encuentra y lo asigna a la variable guest o invitados
                    guest = g;
                    break;
                }
            }
            if (guest == null) {
                JOptionPane.showMessageDialog(null, "Guest not found.");
                return;
            }
        }

        //solicita descripción del consumo y la guarda
        String description = JOptionPane.showInputDialog("Enter consumption description:");
        //solicita monto del consumo y pasa el valor a decimal
        double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter amount of consumption:"));

        //intenta registrar consumo
        if (partner.registerConsumption(description, amount, guest)) {
            JOptionPane.showMessageDialog(null, "Consumption registered.");
        } else {
            JOptionPane.showMessageDialog(null, "Insufficient funds.");
        }
    }

    //aumentar fondos
    private static void increaseFunds() {
        String partnerId = JOptionPane.showInputDialog("Enter member ID:");//id del partner
        Partner partner = club.members.get(partnerId);

        //verificando si existe
        if (partner == null) {
            JOptionPane.showMessageDialog(null, "Member not found.");
            return;
        }
        
        //solicita monto, convierte a numero decimal
        double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter amount to add:"));
        
        if (partner.addFunds(amount)) {
            JOptionPane.showMessageDialog(null, "Funds increased successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "Error increasing funds. Please check the limit.");
        }
    }

    //elimina miembro
    private static void removeMember() {
        String partnerId = JOptionPane.showInputDialog("Enter member ID:");//solicita el id del partner
        if (club.removeMember(partnerId)){
            JOptionPane.showMessageDialog(null, "Member removed successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "Error removing member.");
        }
    }
}
        
   