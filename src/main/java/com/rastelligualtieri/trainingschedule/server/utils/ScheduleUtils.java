package com.rastelligualtieri.trainingschedule.server.utils;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ScheduleUtils {

    /**
     * Ritorna un oggetto che contiene le due categorie principali e se serve equipment o meno
     *
     * @param dataJson
     * @return
     */
    public static ScheduleStatistic calculateStatistics(String dataJson){

        ScheduleStatistic statistic = null;

        JSONObject schedule = new JSONObject(dataJson);
        JSONArray arr = schedule.getJSONArray("exercises");

        Map<String,Integer> tipiEsercizi = new HashMap<>();
        String isEquipmentNeeded="FALSE";

        for (int i = 0; i < arr.length(); i++)
        {
            JSONObject esercizio = arr.getJSONObject(i);

            String tipo=esercizio.getString("type");
            String equipment=esercizio.getString("equipment");
            if(equipment.equalsIgnoreCase("TRUE")){
                isEquipmentNeeded="TRUE";
            }

            if(tipiEsercizi.containsKey(tipo)){
                Integer toUpdate=tipiEsercizi.get(tipo);
                tipiEsercizi.put(tipo,toUpdate+1);
            }
            else{
                tipiEsercizi.put(tipo,1);
            }
        }

        Map<String,Integer> categoria1 = new HashMap<>();
        Map<String,Integer> categoria2 = new HashMap<>();

        for (Map.Entry<String, Integer> entry : tipiEsercizi.entrySet()) {
            if(categoria1.isEmpty()){
                categoria1.put(entry.getKey(),entry.getValue());
            }
            else if(entry.getValue()>categoria1.entrySet().iterator().next().getValue()){
                categoria1.put(entry.getKey(),entry.getValue());
            }
            else if(categoria2.isEmpty()){
                categoria2.put(entry.getKey(),entry.getValue());
            }
            else if(entry.getValue()>categoria2.entrySet().iterator().next().getValue()){
                categoria2.put(entry.getKey(),entry.getValue());
            }
        }

        String categoria1Titolo=null;
        String categoria2Titolo=null;
        if(!categoria1.isEmpty()){
            categoria1Titolo=categoria1.entrySet().iterator().next().getKey();
        }
        if(!categoria2.isEmpty()){
            categoria2Titolo=categoria2.entrySet().iterator().next().getKey();
        }

        statistic= new ScheduleStatistic(categoria1Titolo,categoria2Titolo,isEquipmentNeeded);

        return statistic;
    }
}
