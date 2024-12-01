package com.mycompany.socialc;

import java.util.HashMap;


class Club {
    public HashMap<String, Partner> members;

    public Club() {
        members = new HashMap<>();
    }

    public boolean addMember(Partner partner) {
        //verifica que hayan menos de 35 socios y que no este registrado
        if (members.size() < 35 && !members.containsKey(partner.id)) {
            members.put(partner.id, partner);//agrega socio al hashMap
            
            return true;
        }
        return false;   
    }

    //eliminar un socio del club
    public boolean removeMember(String id) {
        
        Partner partner = members.get(id);//obtiene socio del hash usando el id
        if (partner == null) {//verifica si el socio esta en el hash
            return false; //si no se encuentra
        }
        if (partner.getMembershipType().equals("VIP")) {//veridica si el partner es vip
            return false; //no se permite eliminar si es vip
        }
        if (!partner.unpaidBills.isEmpty()) {//verifica si partner tiene facturas pendientes
            return false; //debe pagar facturas para poderse eliminar
        }
        if (partner.authorizedGuests.size() > 1) {//verifica si socio tiene mas de un invitado
            return false; // false si tiene mas de 1 invitado
        }
        members.remove(id);
        //elimina al socio del hash si cumple con todo
        return true;
    }
    
    //lista los socios registrados en el club
    public String listMembers() {
        StringBuilder memberList = new StringBuilder("List of Members:\n");//eñ StringBuilder para eliminar socios
        for (Partner member : members.values()) {//itera sobre cada socio
            memberList.append("ID: ").append(member.id)//agrega id del partner
                      .append(", Name: ").append(member.name)//agrega nombre del part
                      .append(", Type: ").append(member.getMembershipType())//agrega tipo de suscripcion
                      .append(", Available Funds: $").append(member.availableFunds)//agrega fondos disponibles
                      .append("\n");//agrega linea entre socios
        }
    return memberList.toString();//retorna lista de partner como cadena de texto
    }
}
    
