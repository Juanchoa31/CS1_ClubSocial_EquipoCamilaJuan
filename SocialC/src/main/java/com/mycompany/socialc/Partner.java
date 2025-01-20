package com.mycompany.socialc;

class Partner extends Member{
    private static final double MAX_REGULAR_FUNDS = 1000000;
    private static final double MAX_VIP_FUNDS = 5000000;
    private String membershipType;

    public Partner(String id, String name, String membershipType) {
        super(id, name, membershipType.equals("VIP") ? 100000 : 50000);//llama constructor
        this.membershipType = membershipType;//asigna tipo de membresia a membershipType
    }

    //retorna el tipo de membresia
    @Override
    public String getMembershipType() {
        return membershipType;
    }

    //añade fondos a la cuenta 
    public boolean addFunds(double amount) {
        double maxFunds = membershipType.equals("VIP") ? MAX_VIP_FUNDS : MAX_REGULAR_FUNDS;//define limite max de fondos
        if (availableFunds + amount <= maxFunds) {//verifica que saldo no exceda el limite de fondos
            availableFunds += amount;
            return true;
        }
        return false;
    }

    //pagar una factura
    public boolean payBill(Bill bill) {
        if (availableFunds >= bill.getAmount()) {//verif si hay fondos suficientes
            availableFunds -= bill.getAmount();//deduce el monto a fondos disponibles
            unpaidBills.remove(bill);
            return true;
        }
        return false;
    }

    //registra consumo
    public boolean registerConsumption(String description, double amount, Guest guest) {
        //verifica si hay fondos suficientes para consumir
        if ((guest == null && availableFunds >= amount) || (guest != null && availableFunds >= amount)) {
            Bill bill = new Bill(description, amount, this);//nueva factura para consumo
            unpaidBills.add(bill);
            
            if (guest != null) {
                guest.addUnpaidBill(bill);
            }
            return true;
        }
        return false;
    }
}
   