package com.rastelligualtieri.trainingschedule.server.utils;

public class ScheduleStatistic {
    private String categoria1;
    private String categoria2;
    private String equipmentNedeed;

    public ScheduleStatistic(String categoria1, String categoria2, String equipmentNeeded){
        this.setCategoria1(categoria1);
        this.setCategoria2(categoria2);
        this.setEquipmentNedeed(equipmentNeeded);
    }

    public String getCategoria1() {
        return categoria1;
    }

    public void setCategoria1(String categoria1) {
        this.categoria1 = categoria1;
    }

    public String getCategoria2() {
        return categoria2;
    }

    public void setCategoria2(String categoria2) {
        this.categoria2 = categoria2;
    }

    public String getEquipmentNedeed() {
        return equipmentNedeed;
    }

    public void setEquipmentNedeed(String equipmentNedeed) {
        this.equipmentNedeed = equipmentNedeed;
    }
}
